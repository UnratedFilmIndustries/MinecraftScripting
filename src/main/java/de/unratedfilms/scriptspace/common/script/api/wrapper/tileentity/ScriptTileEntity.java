
package de.unratedfilms.scriptspace.common.script.api.wrapper.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.LegacyNamespacedRegistry;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagCompound;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptBlock;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptWorld;
import de.unratedfilms.scriptspace.common.util.Utils;

public class ScriptTileEntity {

    public static String[] getAllTileNames() {

        @SuppressWarnings ("deprecation")
        LegacyNamespacedRegistry<Class<? extends TileEntity>> registry = GameData.getTileEntityRegistry();

        return registry.getKeys().stream().map(ResourceLocation::toString).toArray(l -> new String[l]);
    }

    public final TileEntity tile;

    public ScriptTileEntity(TileEntity tile) {

        this.tile = tile;
    }

    public ScriptWorld getWorld() {

        return new ScriptWorld(tile.getWorld());
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

        return new ScriptWorld(tile.getWorld()).getBlock(tile.getPos().getX(), tile.getPos().getX(), tile.getPos().getX());
    }

    public ScriptVec3 getLocation() {

        return new ScriptVec3(tile.getPos());
    }

    public int getX() {

        return tile.getPos().getX();
    }

    public int getY() {

        return tile.getPos().getY();
    }

    public int getZ() {

        return tile.getPos().getZ();
    }

    private static String getInternalName(TileEntity te) {

        @SuppressWarnings ("deprecation")
        LegacyNamespacedRegistry<Class<? extends TileEntity>> registry = GameData.getTileEntityRegistry();

        return registry.getNameForObject(te.getClass()).toString();
    }

}
