
package de.unratedfilms.scriptspace.net.messages;

import java.util.Set;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
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

            // The provider method is functional and therefore thread-safe; we do not need to care about threading here
            Set<SourceScript> availableScripts = ScriptSourceService.getAvailableScripts();
            SourceScript[] availableScriptsArr = availableScripts.toArray(new SourceScript[availableScripts.size()]);

            return new AvailableScriptsResponseClientMessage(availableScriptsArr);
        }

    }

}
