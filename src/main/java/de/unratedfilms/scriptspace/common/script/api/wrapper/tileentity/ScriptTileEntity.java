
package de.unratedfilms.scriptspace.common.script.api.wrapper.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagCompound;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptBlock;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptWorld;
import de.unratedfilms.scriptspace.common.util.ReflectionHelper;
import de.unratedfilms.scriptspace.common.util.Utils;

public class ScriptTileEntity {

    public static ScriptTileEntity createFromTag(ScriptTagCompound tag) {

        TileEntity te = TileEntity.createAndLoadEntity(tag.tag);
        return te != null ? new ScriptTileEntity(te) : null;
    }

    public static String[] getAllTileNames() {

        return ReflectionHelper.TILE_ENTITY__CLASS_TO_NAME.values().toArray(new String[0]);
    }

    public final TileEntity tile;

    public ScriptTileEntity(TileEntity tile) {

        this.tile = tile;
    }

    public ScriptWorld getWorld() {

        return new ScriptWorld(tile.getWorldObj());
    }

    public String getInternalName() {

        return getInternalName(tile);
    }

    public ScriptTagCompound writeToTag() {

        NBTTagCompound tag = new NBTTagCompound();
        tile.writeToNBT(tag);
        return new ScriptTagCompound(tag);
    }

    public void readFromTag(ScriptTagCompound tag) {

        tile.readFromNBT(tag.tag);

        // Notify the clients of the changed NBT data
        Utils.syncTileEntityToClients(tile);
    }

    public int getBlockData() {

        return tile.getBlockMetadata();
    }

    public void onInventoryChanged() {

        tile.markDirty();
    }

    public ScriptBlock getBlock() {

        return ScriptBlock.atLocation(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public ScriptVec3 getLocation() {

        return new ScriptVec3(getX(), getY(), getZ());
    }

    public int getX() {

        return tile.xCoord;
    }

    public int getY() {

        return tile.yCoord;
    }

    public int getZ() {

        return tile.zCoord;
    }

    private static String getInternalName(TileEntity te) {

        return ReflectionHelper.TILE_ENTITY__CLASS_TO_NAME.get(te.getClass());
    }

}
