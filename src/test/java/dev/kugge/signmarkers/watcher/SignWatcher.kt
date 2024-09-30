package dev.kugge.signmarkers.watcher

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.SignChangeEvent
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class SignWatcherTest {
    @Test
    fun onSignWrite() {
        val blockMock = Mockito.mock(Block::class.java)
        val playerMock = Mockito.mock(Player::class.java)
        val watcher = SignWatcher()
        val adventureLines = ArrayList<Component>()
        val tc = Mockito.mock(TextComponent::class.java)
        Mockito.`when`(tc.toString()).thenReturn("[map] this is my component")
        adventureLines.add(tc)
        watcher.onSignWrite(SignChangeEvent(blockMock, playerMock, adventureLines))
    }
}