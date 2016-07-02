
package de.unratedfilms.scriptspace.common.script.api.wrapper.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntity;
import de.unratedfilms.scriptspace.common.script.api.wrapper.tileentity.ScriptTileEntity;

public class ScriptWorld {

    public final World world;

    public ScriptWorld(World world) {

        this.world = world;
    }

    public ScriptBlock getBlock(ScriptVec3 location) {

        return getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public ScriptBlock getBlock(int x, int y, int z) {

        return ScriptBlock.atLocation(world, x, y, z);
    }

    public void setBlock(ScriptVec3 location, ScriptBlock block) {

        setBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), block);
    }

    public void setBlock(int x, int y, int z, ScriptBlock block) {

        world.setBlock(x, y, z, block.block, block.data, 2);
    }

    public void setBlockToAir(ScriptVec3 location) {

        setBlockToAir(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public void setBlockToAir(int x, int y, int z) {

        world.setBlockToAir(x, y, z);
    }

    public int getBlockData(ScriptVec3 location) {

        return getBlockData(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getBlockData(int x, int y, int z) {

        return world.getBlockMetadata(x, y, z);
    }

    public void setBlockData(ScriptVec3 location, int data) {

        setBlockData(location.getBlockX(), location.getBlockY(), location.getBlockZ(), data);
    }

    public void setBlockData(int x, int y, int z, int data) {

        world.setBlockMetadataWithNotify(x, y, z, data, 2);
    }

    public boolean canBlockSeeTheSky(ScriptVec3 location) {

        return canBlockSeeTheSky(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean canBlockSeeTheSky(int x, int y, int z) {

        return world.canBlockSeeTheSky(x, y, z);
    }

    public float getLightBrightness(ScriptVec3 location) {

        return getLightBrightness(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public float getLightBrightness(int x, int y, int z) {

        return world.getLightBrightness(x, y, z);
    }

    public int getBlockLightValue(ScriptVec3 location) {

        return getBlockLightValue(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getBlockLightValue(int x, int y, int z) {

        return world.getBlockLightValue(x, y, z);
    }

    public boolean hasTileEntity(ScriptVec3 location) {

        return hasTileEntity(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean hasTileEntity(int x, int y, int z) {

        Block b = world.getBlock(x, y, z);
        return b != null && b.hasTileEntity(world.getBlockMetadata(x, y, z));
    }

    public ScriptTileEntity getTileEntity(ScriptVec3 location) {

        return getTileEntity(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public ScriptTileEntity getTileEntity(int x, int y, int z) {

        TileEntity te = world.getTileEntity(x, y, z);
        return te == null ? null : new ScriptTileEntity(te);
    }

    public void addTileEntity(ScriptTileEntity scriptTile) {

        TileEntity tile = scriptTile.tile;
        Chunk chunk = world.getChunkFromBlockCoords(tile.xCoord, tile.zCoord);
        if (chunk != null) {
            chunk.addTileEntity(tile);
        }
    }

    public void removeTileEntity(ScriptVec3 location) {

        removeTileEntity(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public void removeTileEntity(int x, int y, int z) {

        world.removeTileEntity(x, y, z);
    }

    public void addEntity(ScriptEntity scriptEnt) {

        world.spawnEntityInWorld(scriptEnt.entity);
    }

    public void removeEntity(ScriptEntity scriptEnt) {

        scriptEnt.setDead();
    }

    public long getSeed() {

        return world.getSeed();
    }

    public long getTime() {

        return world.getWorldTime();
    }

    public void setTime(long time) {

        world.setWorldTime(time);
    }

    public boolean isRaining() {

        return world.isRaining();
    }

    public void setRaining(boolean raining) {

        world.getWorldInfo().setRaining(raining);
    }

    public void toggleRain() {

        setRaining(!isRaining());
    }

}
