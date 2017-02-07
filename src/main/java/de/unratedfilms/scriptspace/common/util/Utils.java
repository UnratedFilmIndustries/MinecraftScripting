
package de.unratedfilms.scriptspace.common.util;

import static de.unratedfilms.scriptspace.common.Consts.LOGGER;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import de.unratedfilms.scriptspace.net.NetworkService;
import de.unratedfilms.scriptspace.net.messages.EntityNBTClientMessage;

public class Utils {

    public final static double DOUBLE_EPSILON = 1E-5;

    public static int floor(double value) {

        return (int) Math.floor(value);
    }

    public static List<TileEntity> getTileEntitiesInAABB(World world, AxisAlignedBB selAABB) {

        List<TileEntity> tileEntities = new ArrayList<>();

        int minX = (int) selAABB.minX;
        int minY = (int) selAABB.minY;
        int minZ = (int) selAABB.minZ;

        int maxX = (int) selAABB.maxX - 1;
        int maxY = (int) selAABB.maxY - 1;
        int maxZ = (int) selAABB.maxZ - 1;

        for (int x = minX >> 4; x <= maxX >> 4; x++) {
            for (int z = minZ >> 4; z <= maxZ >> 4; z++) {
                Chunk chunk = world.getChunkFromChunkCoords(x, z);

                if (chunk != null) {
                    for (TileEntity tileEntity : chunk.getTileEntityMap().values()) {
                        if (!tileEntity.isInvalid()) {
                            if (tileEntity.getPos().getX() >= minX && tileEntity.getPos().getY() >= minY && tileEntity.getPos().getZ() >= minZ &&
                                    tileEntity.getPos().getX() <= maxX && tileEntity.getPos().getY() <= maxY && tileEntity.getPos().getZ() <= maxZ) {
                                tileEntities.add(tileEntity);
                            }
                        }
                    }
                }
            }
        }

        return tileEntities;
    }

    /**
     * @param entity The {@link Entity} whose NBT data should be sent to the client so that the client knows the entitie's current NBT state.
     * @throws IllegalStateException When called from the client.
     */
    public static void syncEntityNBTToClients(Entity entity) {

        // Manually sync the entity NBT using a custom packet
        NBTTagCompound tag = new NBTTagCompound();
        entity.writeToNBT(tag);
        int dim = entity.dimension;
        NetworkService.DISPATCHER.sendToDimension(new EntityNBTClientMessage(entity.getEntityId(), tag), dim);

        // Sync any potions which are active on the entity
        if (entity instanceof EntityLivingBase) {
            try {
                ReflectionHelper.ENTITY_LIVING_BASE__POTIONS_NEED_UPDATE.setBoolean(entity, true);
            } catch (IllegalAccessException e) {
                LOGGER.error("Can't sync the active potions of an entity to all clients because of a reflection error", e);
            }
        }
    }

    /**
     * @param tileEntity The {@link TileEntity} whose state data should be sent to the client so that the client knows the tile entitie's current state.
     * @throws IllegalStateException When called from the client.
     */
    public static void syncTileEntityToClients(TileEntity tileEntity) {

        Packet packet = tileEntity.getDescriptionPacket();
        if (packet != null) {
            int dim = tileEntity.getWorld().provider.getDimension();
            MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayersInDimension(packet, dim);
        }
    }

}
