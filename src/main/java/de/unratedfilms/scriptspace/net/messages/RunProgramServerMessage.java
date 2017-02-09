
package de.unratedfilms.scriptspace.net.messages;

import org.apache.commons.lang3.Validate;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import de.unratedfilms.scriptspace.common.script.Program;
import de.unratedfilms.scriptspace.common.script.ProgramExecutionException;
import de.unratedfilms.scriptspace.common.script.ScriptCompilationException;
import de.unratedfilms.scriptspace.common.script.ScriptsEncoder;
import de.unratedfilms.scriptspace.common.script.services.ProgramExecutionService;
import de.unratedfilms.scriptspace.common.script.services.ScriptCompilationService;
import de.unratedfilms.scriptspace.common.selection.Selection;
import de.unratedfilms.scriptspace.common.selection.SelectionsEncoder;
import io.netty.buffer.ByteBuf;

/**
 * This message tells the server that it should compile and run the given {@link Program} on the given {@link Selection}.
 * This is not an additional security risk since the user is allowed to send his own scripts to the server anyway.
 */
public class RunProgramServerMessage implements IMessage {

    private Program   program;
    private Selection selection;

    public RunProgramServerMessage() {

    }

    public RunProgramServerMessage(Program program, Selection selection) {

        Validate.notNull(program);
        Validate.notNull(selection);

        this.program = program;
        this.selection = selection;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        program = ScriptsEncoder.readProgramBinary(buf);
        selection = SelectionsEncoder.readBinary(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {

        ScriptsEncoder.writeProgramBinary(buf, program);
        SelectionsEncoder.writeBinary(buf, selection);
    }

    public static class RunProgramServerMessageHandler implements IMessageHandler<RunProgramServerMessage, IMessage> {

        @Override
        public IMessage onMessage(RunProgramServerMessage message, MessageContext ctx) {

            EntityPlayerMP sourcePlayer = ctx.getServerHandler().playerEntity;

            Minecraft.getMinecraft().addScheduledTask(() -> {
                try {
                    ProgramExecutionService.executeProgram(message.program, sourcePlayer, message.selection);
                } catch (ScriptCompilationException e) {
                    ScriptCompilationService.sendErrorMessagesOnCompilationException(e, sourcePlayer);
                } catch (ProgramExecutionException e) {
                    ProgramExecutionService.sendErrorMessagesOnExecutionException(e);
                }
            });

            // No reply
            return null;
        }

    }

}
