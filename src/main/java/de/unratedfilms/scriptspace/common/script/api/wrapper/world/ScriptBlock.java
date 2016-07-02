
package de.unratedfilms.scriptspace.common.script.api.wrapper.world;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class ScriptBlock {

    public static ScriptBlock atLocation(World world, int x, int y, int z) {

        Block b = world.getBlock(x, y, z);
        return b == null ? null : new ScriptBlock(b, world.getBlockMetadata(x, y, z));
    }

    public static ScriptBlock forName(String name) {

        return fromBlock(Block.getBlockFromName(name));
    }

    public static ScriptBlock forName(String name, int data) {

        return fromBlock(Block.getBlockFromName(name), data);
    }

    public static ScriptBlock fromBlock(Block b) {

        return b == null ? null : new ScriptBlock(b);
    }

    public static ScriptBlock fromBlock(Block b, int data) {

        return b == null ? null : new ScriptBlock(b, data);
    }

    public final Block block;
    public final int   data;

    private ScriptBlock(Block b) {

        this(b, 0);
    }

    private ScriptBlock(Block block, int data) {

        this.block = block;
        this.data = data;
    }

    public String getBlockName() {

        return Block.blockRegistry.getNameForObject(block);
    }

    @Override
    public boolean equals(Object obj) {

        if (! (obj instanceof ScriptBlock)) {
            return false;
        }
        ScriptBlock otherBlock = (ScriptBlock) obj;
        return otherBlock != null && block == otherBlock.block && data == otherBlock.data;
    }

    public boolean equalsIgnoreData(ScriptBlock otherBlock) {

        return otherBlock != null && block == otherBlock.block;
    }

}
