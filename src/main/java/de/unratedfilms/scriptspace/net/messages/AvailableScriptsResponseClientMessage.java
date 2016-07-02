
package de.unratedfilms.scriptspace.net.messages;

import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.Validate;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.unratedfilms.scriptspace.client.gui.CreateProgramScreen;
import de.unratedfilms.scriptspace.common.script.ScriptsEncoder;
import de.unratedfilms.scriptspace.common.script.SourceScript;
import de.unratedfilms.scriptspace.common.script.services.ScriptSourceService;

/**
 * After the client asked the server for all available {@link SourceScript}s using an {@link AvailableScriptsRequestServerMessage},
 * the server answers that request with this message.
 */
public class AvailableScriptsResponseClientMessage implements IMessage {

    private SourceScript[] availableScripts;

    public AvailableScriptsResponseClientMessage() {

    }

    public AvailableScriptsResponseClientMessage(SourceScript[] availableScripts) {

        Validate.notNull(availableScripts);
        this.availableScripts = availableScripts;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        availableScripts = new SourceScript[buf.readInt()];
        for (int i = 0; i < availableScripts.length; i++) {
            availableScripts[i] = ScriptsEncoder.readSourceScriptBinary(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(availableScripts.length);
        for (SourceScript availableScript : availableScripts) {
            ScriptsEncoder.writeSourceScriptBinary(buf, availableScript);
        }
    }

    public static class AvailableScriptsResponseClientMessageHandler implements IMessageHandler<AvailableScriptsResponseClientMessage, IMessage> {

        @Override
        @SideOnly (Side.CLIENT)
        public IMessage onMessage(AvailableScriptsResponseClientMessage message, MessageContext ctx) {

            Set<SourceScript> allAvailableScripts = new HashSet<>();

            // Consider the scripts which are stored on the client
            allAvailableScripts.addAll(ScriptSourceService.getAvailableScripts());

            // And, of course, the scripts which are stored on the server
            allAvailableScripts.addAll(Arrays.asList(message.availableScripts));

            // Open a create program GUI that displays the scripts
            Minecraft.getMinecraft().displayGuiScreen(new CreateProgramScreen(null, allAvailableScripts));

            // No reply
            return null;
        }

    }

}
