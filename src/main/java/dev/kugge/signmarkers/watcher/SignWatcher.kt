package dev.kugge.signmarkers.watcher;

import de.bluecolored.bluemap.api.markers.POIMarker;
import dev.kugge.signmarkers.SignMarkers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SignWatcher implements Listener {
    @EventHandler
    public void onSignWrite(SignChangeEvent event) {
        // Check for existing sign at position xyz and delete it
        Component header = event.line(0);
        if (header == Component.empty() || header == null) return;
        if (!header.toString().contains("[map]")) return;

        // Don't require a line 3, in fact require a secondary tag and loop through for tags hell have defaults for [text] [poi] [map] etc.
        Component cicon = event.line(3);
        if (cicon == Component.empty() || cicon == null) return;

        String icon = "./markers/" + LegacyComponentSerializer.legacySection().serialize(cicon) + ".png";
        File iconFile = new File(SignMarkers.webRoot + "/" + icon);
        Vector2i anchor = new Vector2i(25, 45);
        if (!iconFile.exists()) {
            icon = "./assets/poi.svg";
        } else {
            try {
                BufferedImage image = ImageIO.read(iconFile);
                int width = image.getWidth();
                int height = image.getHeight();
                anchor = new Vector2i(height / 2, width / 2);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        Component clabel1 = event.line(1);
        if (clabel1 == Component.empty() || clabel1 == null) return;

        Component clabel2 = event.line(2);
        String label = LegacyComponentSerializer.legacySection().serialize(clabel1)
                + LegacyComponentSerializer.legacySection().serialize(clabel2);

        Block block = event.getBlock();
        Vector3d pos = new Vector3d(block.getX(), block.getY(), block.getZ());

        String id = "marker-" + pos.getX() + "-" + pos.getY() + "-" + pos.getZ();
        POIMarker marker = new POIMarker(label, pos, icon, anchor);
        SignMarkers.markerSet.get(block.getWorld()).put(id, marker);

        // Leave [Map] and Icon in but don't add them to marker
        event.line(0, Component.empty());
        event.line(3, Component.empty());
    }
}
