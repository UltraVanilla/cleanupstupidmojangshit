package lordpipe.cleanupstupidmojangshit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.SignBlockEntity;

/**
 * Plugin to remove broken FilteredText tags on signs
 *
 * @author Copyright (c) lordpipe. Licensed GPLv3
 */
public class CleanUpStupidMojangShit extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

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
                                    (int)location.getX() + xOff,
                                    (int)location.getY() + yOff,
                                    (int)location.getZ() + zOff);

                            if (block.getState() instanceof Sign) {
                                CraftBlock craftBlock = (CraftBlock) block;

                                SignBlockEntity entity = (SignBlockEntity) craftBlock.getHandle().getBlockEntity(craftBlock.getPosition());
                                CompoundTag tag = entity.getUpdateTag();

                                System.out.println(tag.toString());

                                if (tag.get("FilteredText1") == null && tag.get("FilteredText2") == null
                                        && tag.get("FilteredText3") == null && tag.get("FilteredText4") == null) {
                                    continue;
                                }

                                tag.remove("FilteredText1");
                                tag.remove("FilteredText2");
                                tag.remove("FilteredText3");
                                tag.remove("FilteredText4");

                                entity.load(tag);

                                entity.saveWithFullMetadata();

                                entity.getLevel().sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 3);
                                System.out.println(block.getType());

                            }
                        }
                    }
                }

            }
        }, 0, 80);
    }
}
