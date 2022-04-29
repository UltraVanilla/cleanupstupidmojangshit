package lordpipe.cleanupstupidmojangshit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

/**
 * Plugin to remove broken FilteredText tags on signs
 *
 * @author Copyright (c) lordpipe. Licensed GPLv3
 */
public class CleanUpStupidMojangShit extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        NBTRemover nbtRemover1;
        try {
            Class.forName("org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock").getName();
            nbtRemover1 = (NBTRemover) Class.forName("lordpipe.cleanupstupidmojangshit.v1_18_R2.NBTRemover").getConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
            try {
                Class.forName("org.bukkit.craftbukkit.v1_18_R1.block.CraftBlock").getName();
                nbtRemover1 = (NBTRemover) Class.forName("lordpipe.cleanupstupidmojangshit.v1_18_R1.NBTRemover").getConstructor().newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e2) {
                nbtRemover1 = block -> {
                    throw new IllegalStateException("This version is unsupported for CleanupStupidMojangShit plugin!");
                };
            }
        }
        final NBTRemover nbtRemover = nbtRemover1;

        if (!nbtRemover.isSupported()) {
            getLogger().log(Level.SEVERE, "This version is unsppotrted! Disabling...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Player player = Bukkit.getOnlinePlayers().stream()
                        .skip((int) (Bukkit.getOnlinePlayers().size() * Math.random())).findFirst().orElse(null);

                if (player == null) return;

                Location location = player.getLocation();
                World world = location.getWorld();

                for (int xOff = -8; xOff <= 8; xOff++) {
                    for (int yOff = -4; yOff <= 4; yOff++) {
                        for (int zOff = -8; zOff <= 8; zOff++) {
                            Block block = world.getBlockAt(
                                    (int) location.getX() + xOff,
                                    (int) location.getY() + yOff,
                                    (int) location.getZ() + zOff);

                            if (block.getState() instanceof Sign) {
                                nbtRemover.removeFilteredTextTag(block);
                            }
                        }
                    }
                }

            }
        }, 0, 80);
    }
}
