package dev.kugge.signmarkers.util

import com.flowpowered.math.vector.Vector2i
import com.flowpowered.math.vector.Vector3d
import de.bluecolored.bluemap.api.markers.HtmlMarker
import de.bluecolored.bluemap.api.markers.Marker
import de.bluecolored.bluemap.api.markers.POIMarker
import dev.kugge.signmarkers.models.TagList
import net.pwall.mustache.Template

class MarkerFactory {
    fun build(tags: TagList, text: String, coords: Vector3d): Marker? {
        if (tags.contains(AvailableTags.POI.value)) {
            var icon = "assets/poi.svg";
            var iconSize = Vector2i(25.0, 45.0);

            if (tags.contains(AvailableTags.ICON.value)) {
                val tag = tags.get(AvailableTags.ICON.value);
                println(tag)
                if (tag != null) {
                    println(tag.value)
                    if (tag.value != null) {
                        icon = "assets/SignMarkers/${tag.value}.svg"
                    }
                }
            }

            return POIMarker(text, coords, icon, iconSize)
        }

        if (tags.contains(AvailableTags.TEXT.value)) {
            val inputStream = this.javaClass.classLoader.getResourceAsStream("views/header.html") ?: return null

            val htmlTemplate = Template.parse(
                inputStream
            );
            data class Text(val text: String);
            val htmlText = htmlTemplate.processToString(Text(text))
            return HtmlMarker(text, coords, htmlText)
        }

        return null
    }
}