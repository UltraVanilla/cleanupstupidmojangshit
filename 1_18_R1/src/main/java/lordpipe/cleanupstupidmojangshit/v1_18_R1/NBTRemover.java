package lordpipe.cleanupstupidmojangshit.v1_18_R1;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftBlock;

public class NBTRemover implements lordpipe.cleanupstupidmojangshit.NBTRemover {

    @Override
    public void removeFilteredTextTag(Block block) {
        CraftBlock craftBlock = (CraftBlock) block;

        SignBlockEntity entity = (SignBlockEntity) craftBlock.getHandle().getBlockEntity(craftBlock.getPosition());
        CompoundTag tag = entity.getUpdateTag();

        if (tag.get("FilteredText1") == null && tag.get("FilteredText2") == null
            && tag.get("FilteredText3") == null && tag.get("FilteredText4") == null) {
            return;
        }

        tag.remove("FilteredText1");
        tag.remove("FilteredText2");
        tag.remove("FilteredText3");
        tag.remove("FilteredText4");

        entity.load(tag);

        entity.setChanged();

        entity.getLevel().sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 3);

    }

    @Override
    public boolean isSupported() {
        return true;
    }
}
