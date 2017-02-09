
package de.unratedfilms.scriptspace.net.messages;

import org.apache.commons.lang3.Validate;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import de.unratedfilms.scriptspace.common.util.ByteBufUtils2;
import io.netty.buffer.ByteBuf;

/**
 * This message tells the server that it should add the given {@link ItemStack} to the players inventory.
 */
public class GiveItemStackServerMessage implements IMessage {

    private ItemStack stack;

    public GiveItemStackServerMessage() {

    }

    public GiveItemStackServerMessage(ItemStack stack) {

        Validate.notNull(stack);

        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        stack = ByteBufUtils2.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {

        ByteBufUtils2.writeItemStack(buf, stack);
    }

    public static class GiveItemStackServerMessageHandler implements IMessageHandler<GiveItemStackServerMessage, IMessage> {

        @Override
        public IMessage onMessage(GiveItemStackServerMessage message, MessageContext ctx) {

            EntityPlayerMP sourcePlayer = ctx.getServerHandler().playerEntity;

            Minecraft.getMinecraft().addScheduledTask(() -> {
                sourcePlayer.inventory.addItemStackToInventory(message.stack);
            });

            // No reply
            return null;
        }

    }

}
