
package de.unratedfilms.scriptspace.net.messages;

import java.util.Set;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.unratedfilms.scriptspace.common.script.SourceScript;
import de.unratedfilms.scriptspace.common.script.services.ScriptSourceService;
import io.netty.buffer.ByteBuf;

/**
 * The client asks the server for all {@link SourceScript}s it has available.
 * The server then responds with an {@link AvailableScriptsResponseClientMessage}.
 */
public class AvailableScriptsRequestServerMessage implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class AvailableScriptsRequestServerMessageHandler implements IMessageHandler<AvailableScriptsRequestServerMessage, AvailableScriptsResponseClientMessage> {

        @Override
        public AvailableScriptsResponseClientMessage onMessage(AvailableScriptsRequestServerMessage message, MessageContext ctx) {

            Set<SourceScript> availableScripts = ScriptSourceService.getAvailableScripts();
            SourceScript[] availableScriptsArr = availableScripts.toArray(new SourceScript[availableScripts.size()]);

            return new AvailableScriptsResponseClientMessage(availableScriptsArr);
        }

    }

}
