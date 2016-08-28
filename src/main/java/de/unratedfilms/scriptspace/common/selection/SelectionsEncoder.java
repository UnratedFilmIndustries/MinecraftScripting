
package de.unratedfilms.scriptspace.common.selection;

import net.minecraft.nbt.NBTTagCompound;
import de.unratedfilms.scriptspace.common.util.ByteBufUtils2;
import de.unratedfilms.scriptspace.common.util.NBTUtils;
import de.unratedfilms.scriptspace.common.util.Vec3i;
import io.netty.buffer.ByteBuf;

public class SelectionsEncoder {

    /*
     * Binary
     */

    public static Selection readBinary(ByteBuf from) {

        int dimensionId = from.readInt();

        byte discriminator = from.readByte();

        switch (discriminator) {
            case 0:
                Vec3i blockLocation = ByteBufUtils2.readVec3i(from);
                return new SelectionBlock(dimensionId, blockLocation);
            case 1:
                Vec3i corner1 = ByteBufUtils2.readVec3i(from);
                Vec3i corner2 = ByteBufUtils2.readVec3i(from);
                return new SelectionCuboid(dimensionId, corner1, corner2);
            case 2:
                int entityId = from.readInt();
                return new SelectionEntity(dimensionId, entityId);
            case 3:
                Vec3i tileEntityLocation = ByteBufUtils2.readVec3i(from);
                return new SelectionTileEntity(dimensionId, tileEntityLocation);
        }

        throw new IllegalArgumentException("Unknown selection discriminator: " + discriminator);
    }

    public static void writeBinary(ByteBuf to, Selection selection) {

        to.writeInt(selection.dimensionId);

        if (selection instanceof SelectionBlock) {
            to.writeByte(0);
            SelectionBlock block = (SelectionBlock) selection;
            ByteBufUtils2.writeVec3i(to, block.blockLocation);
        }

        else if (selection instanceof SelectionCuboid) {
            to.writeByte(1);
            SelectionCuboid cuboid = (SelectionCuboid) selection;
            ByteBufUtils2.writeVec3i(to, cuboid.corner1);
            ByteBufUtils2.writeVec3i(to, cuboid.corner2);
        }

        else if (selection instanceof SelectionEntity) {
            to.writeByte(2);
            SelectionEntity entity = (SelectionEntity) selection;
            to.writeInt(entity.selectedEntityId);
        }

        else if (selection instanceof SelectionTileEntity) {
            to.writeByte(3);
            SelectionTileEntity tileEntity = (SelectionTileEntity) selection;
            Vec3i tileEntityLocation = tileEntity.selectedTileEntityLocation;
            ByteBufUtils2.writeVec3i(to, tileEntityLocation.x, tileEntityLocation.y, tileEntityLocation.z);
        }

        else {
            throw new IllegalArgumentException("Unknown selection type: " + selection.getClass().getName());
        }
    }

    /*
     * NBT
     */

    public static Selection readNBT(NBTTagCompound from) {

        int dimensionId = from.getInteger("dimensionId");

        byte discriminator = from.getByte("discriminator");

        switch (discriminator) {
            case 0:
                Vec3i blockLocation = NBTUtils.readVec3i(from.getCompoundTag("blockLocation"));
                return new SelectionBlock(dimensionId, blockLocation);
            case 1:
                Vec3i corner1 = NBTUtils.readVec3i(from.getCompoundTag("corner1"));
                Vec3i corner2 = NBTUtils.readVec3i(from.getCompoundTag("corner2"));
                return new SelectionCuboid(dimensionId, corner1, corner2);
            case 2:
                int entityId = from.getInteger("entityId");
                return new SelectionEntity(dimensionId, entityId);
            case 3:
                Vec3i tileEntityLocation = NBTUtils.readVec3i(from.getCompoundTag("tileEntityLocation"));
                return new SelectionTileEntity(dimensionId, tileEntityLocation);
        }

        throw new IllegalArgumentException("Unknown selection discriminator: " + discriminator);
    }

    public static void writeNBT(NBTTagCompound to, Selection selection) {

        to.setInteger("dimensionId", selection.dimensionId);

        if (selection instanceof SelectionBlock) {
            to.setByte("discriminator", (byte) 0);
            SelectionBlock block = (SelectionBlock) selection;
            NBTUtils.writeVec3i(NBTUtils.addNewCompoundTag(to, "blockLocation"), block.blockLocation);
        }

        else if (selection instanceof SelectionCuboid) {
            to.setByte("discriminator", (byte) 1);
            SelectionCuboid cuboid = (SelectionCuboid) selection;
            NBTUtils.writeVec3i(NBTUtils.addNewCompoundTag(to, "corner1"), cuboid.corner1);
            NBTUtils.writeVec3i(NBTUtils.addNewCompoundTag(to, "corner2"), cuboid.corner2);
        }

        else if (selection instanceof SelectionEntity) {
            to.setByte("discriminator", (byte) 2);
            SelectionEntity entity = (SelectionEntity) selection;
            to.setInteger("entityId", entity.selectedEntityId);
        }

        else if (selection instanceof SelectionTileEntity) {
            to.setByte("discriminator", (byte) 3);
            SelectionTileEntity tileEntity = (SelectionTileEntity) selection;
            Vec3i tileEntityLocation = tileEntity.selectedTileEntityLocation;
            NBTUtils.writeVec3i(NBTUtils.addNewCompoundTag(to, "tileEntityLocation"), tileEntityLocation.x, tileEntityLocation.y, tileEntityLocation.z);
        }

        else {
            throw new IllegalArgumentException("Unknown selection type: " + selection.getClass().getName());
        }
    }

    private SelectionsEncoder() {

    }

}
