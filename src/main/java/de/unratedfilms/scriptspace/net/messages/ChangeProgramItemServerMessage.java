
package de.unratedfilms.scriptspace.net.messages;

import org.apache.commons.lang3.Validate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.unratedfilms.scriptspace.common.items.CustomItems;
import de.unratedfilms.scriptspace.common.items.ItemProgram;
import de.unratedfilms.scriptspace.common.script.Program;
import de.unratedfilms.scriptspace.common.script.ScriptsEncoder;
import io.netty.buffer.ByteBuf;

/**
 * This message tells the server that it should set the {@link Program} stored in a given {@link ItemProgram} to the supplied program object.
 * Note that the {@link #slotId} can be set to {@code -1} in order to add a new item to the players inventory.
 */
public class ChangeProgramItemServerMessage implements IMessage {

    private int     slotId;
    private Program program;

    public ChangeProgramItemServerMessage() {

    }

    public ChangeProgramItemServerMessage(int slotId, Program program) {

        Validate.isTrue(slotId >= -1);
        Validate.notNull(program);

        this.slotId = slotId;
        this.program = program;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        slotId = buf.readInt();
        program = ScriptsEncoder.readProgramBinary(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(slotId);
        ScriptsEncoder.writeProgramBinary(buf, program);
    }

    public static class ChangeProgramItemServerMessageHandler implements IMessageHandler<ChangeProgramItemServerMessage, IMessage> {

        @Override
        public IMessage onMessage(ChangeProgramItemServerMessage message, MessageContext ctx) {

            EntityPlayerMP sourcePlayer = ctx.getServerHandler().playerEntity;

            ItemStack programItemStack;
            if (message.slotId == -1) {
                programItemStack = new ItemStack(CustomItems.PROGRAM);
                ItemProgram.setProgram(programItemStack, message.program);
                sourcePlayer.inventory.addItemStackToInventory(programItemStack);
            } else {
                programItemStack = sourcePlayer.inventory.getStackInSlot(message.slotId);

                // Only continue if the slot id was -1 or pointed to a real program item stack
                if (programItemStack != null && programItemStack.getItem().equals(CustomItems.PROGRAM)) {
                    ItemProgram.setProgram(programItemStack, message.program);
                    sourcePlayer.inventory.setInventorySlotContents(message.slotId, programItemStack);
                }
            }

            // No reply
            return null;
        }

    }

}
