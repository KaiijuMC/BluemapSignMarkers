package dev.kugge.signmarkers.watcher

import com.flowpowered.math.vector.Vector3d
import dev.kugge.signmarkers.util.MarkerFactory
import dev.kugge.signmarkers.util.MarkerManager
import dev.kugge.signmarkers.util.TagFactory
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.SignChangeEvent

class SignWatcher : Listener {
    private val markerFactory = MarkerFactory()

    @EventHandler
    fun onSignWrite(event: SignChangeEvent) {
        val tf = TagFactory()
        val lines = event.lines().map(fun(it: Component): String {
            if (it is TextComponent) {
                return it.content();
            }
            return ""
        }).joinToString("\n")

        println("#########################")
        println(lines)
        val tags = tf.parseTags(lines)
        if (tags.isEmpty()) return

        val block = event.block
        val pos = Vector3d(block.x.toFloat(), block.y.toFloat(), block.z.toFloat())

        val text = tf.stripTags(lines);
        println("#########################")
        println(text)
        val marker = markerFactory.build(tags, text, pos) ?: return

        val markerManager = MarkerManager(block)
        markerManager.addMarker(marker);
    }

    @EventHandler
    fun onSignDestroy(event: BlockBreakEvent) {
        val block = event.block
        if (block.state !is Sign) return

        val markerManager = MarkerManager(block)
        markerManager.deleteMarker()
    }
}