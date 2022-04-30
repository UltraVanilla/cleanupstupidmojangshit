package lordpipe.cleanupstupidmojangshit;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

/**
 * Plugin to remove broken FilteredText tags on signs
 *
 * @author Copyright (c) lordpipe. Licensed GPLv3
 */
public class CleanUpStupidMojangShit extends JavaPlugin implements Listener {

    private static final String[] SUPPORTED_VERSION = new String[]{"v1_18_R1", "v1_18_R2", "v1_17_R1"};
    private final NBTRemover nbtRemover = getNBTRemover();

    private NBTRemover getNBTRemover() {
        NBTRemover nbtRemover = null;
        for (String version : SUPPORTED_VERSION) {
            try {
                // Check NMS version
                Class.forName("org.bukkit.craftbukkit." + version + ".block.CraftBlock").getName();
                nbtRemover = (NBTRemover) Class.forName("lordpipe.cleanupstupidmojangshit." + version + ".NBTRemover").getConstructor().newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {

            }
        }
        if (nbtRemover == null) {
            nbtRemover = block -> {
                throw new IllegalStateException("This version is unsupported!");
            };
        }
        return nbtRemover;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        final NBTRemover nbtRemover = getNBTRemover();

        if (!nbtRemover.isSupported()) {
            getLogger().log(Level.SEVERE, "This version is unsupported! Disabling...");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            getServer().getPluginManager().registerEvents(this, this);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChunkLoaded(ChunkLoadEvent event) {
        for (BlockState tileEntity : event.getChunk().getTileEntities()) {
            if (tileEntity instanceof Sign) {
                nbtRemover.removeFilteredTextTag(tileEntity.getBlock());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block != null && block.getState() instanceof Sign) {
            nbtRemover.removeFilteredTextTag(block);
        }
    }

}
