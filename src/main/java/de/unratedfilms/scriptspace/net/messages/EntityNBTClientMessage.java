
package de.unratedfilms.scriptspace.net.messages;

import org.apache.commons.lang3.Validate;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

/**
 * Tells the client it should update the {@link NBTTagCompound NBT tag} of the given entity to the supplied NBT tag.
 */
public class EntityNBTClientMessage implements IMessage {

    private int            entityId;
    private NBTTagCompound tag;

    public EntityNBTClientMessage() {

    }

    public EntityNBTClientMessage(int entityId, NBTTagCompound tag) {

        Validate.isTrue(entityId >= 0);
        Validate.notNull(tag);

        this.entityId = entityId;
        this.tag = tag;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        entityId = buf.readInt();
        tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(entityId);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class EntityNBTClientMessageHandler implements IMessageHandler<EntityNBTClientMessage, IMessage> {

        @Override
        @SideOnly (Side.CLIENT)
        public IMessage onMessage(EntityNBTClientMessage message, MessageContext ctx) {

            Minecraft mc = Minecraft.getMinecraft();

            if (mc.theWorld != null) {
                Entity e = mc.theWorld.getEntityByID(message.entityId);
                if (e != null) {
                    e.readFromNBT(message.tag);
                }
            }

            // No reply
            return null;
        }

    }

}
