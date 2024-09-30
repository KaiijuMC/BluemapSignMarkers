package dev.kugge.signmarkers.util

import com.flowpowered.math.vector.Vector3d
import de.bluecolored.bluemap.api.markers.Marker
import dev.kugge.signmarkers.SignMarkers
import org.bukkit.block.Block

class MarkerManager (var block: Block) {
    val pos = Vector3d(block.x.toDouble(), block.y.toDouble(), block.z.toDouble())
    val id = "marker-" + pos.x + "-" + pos.y + "-" + pos.z

    fun addMarker(marker: Marker){
        val id = "marker-" + pos.x + "-" + pos.y + "-" + pos.z
        SignMarkers.markerSet[block.world]!!.put(id, marker)
    }

    fun deleteMarker(){
        // delete old marker
        val markerSet = SignMarkers.markerSet[block.world] ?: return

        val marker = markerSet[id]
        if (marker != null) {
            markerSet.remove(id)
        }
    }
}