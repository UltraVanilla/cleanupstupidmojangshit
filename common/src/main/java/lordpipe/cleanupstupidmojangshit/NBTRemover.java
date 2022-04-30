package lordpipe.cleanupstupidmojangshit;

import org.bukkit.block.Block;

public interface NBTRemover {
    void removeFilteredTextTag(Block block);

    default boolean isSupported() {
        return false;
    }
}
