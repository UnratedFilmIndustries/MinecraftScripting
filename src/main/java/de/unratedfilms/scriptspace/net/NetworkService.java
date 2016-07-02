
package de.unratedfilms.scriptspace.net;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import de.unratedfilms.scriptspace.common.Consts;
import de.unratedfilms.scriptspace.net.messages.AvailableScriptsRequestServerMessage;
import de.unratedfilms.scriptspace.net.messages.AvailableScriptsRequestServerMessage.AvailableScriptsRequestServerMessageHandler;
import de.unratedfilms.scriptspace.net.messages.AvailableScriptsResponseClientMessage;
import de.unratedfilms.scriptspace.net.messages.AvailableScriptsResponseClientMessage.AvailableScriptsResponseClientMessageHandler;
import de.unratedfilms.scriptspace.net.messages.ChangeProgramItemServerMessage;
import de.unratedfilms.scriptspace.net.messages.ChangeProgramItemServerMessage.ChangeProgramItemServerMessageHandler;
import de.unratedfilms.scriptspace.net.messages.EntityNBTClientMessage;
import de.unratedfilms.scriptspace.net.messages.EntityNBTClientMessage.EntityNBTClientMessageHandler;
import de.unratedfilms.scriptspace.net.messages.RunProgramServerMessage;
import de.unratedfilms.scriptspace.net.messages.RunProgramServerMessage.RunProgramServerMessageHandler;

public class NetworkService {

    public static final SimpleNetworkWrapper DISPATCHER        = NetworkRegistry.INSTANCE.newSimpleChannel(Consts.MOD_ID);

    private static int                       nextDiscriminator = 0;

    public static void initialize() {

        // Available scripts messages
        registerMessage(AvailableScriptsRequestServerMessageHandler.class, AvailableScriptsRequestServerMessage.class, Side.SERVER);
        registerMessage(AvailableScriptsResponseClientMessageHandler.class, AvailableScriptsResponseClientMessage.class, Side.CLIENT);

        // Change program item message
        registerMessage(ChangeProgramItemServerMessageHandler.class, ChangeProgramItemServerMessage.class, Side.SERVER);

        // Run program message
        registerMessage(RunProgramServerMessageHandler.class, RunProgramServerMessage.class, Side.SERVER);

        // Entity NBT message
        registerMessage(EntityNBTClientMessageHandler.class, EntityNBTClientMessage.class, Side.CLIENT);

    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {

        DISPATCHER.registerMessage(messageHandler, requestMessageType, nextDiscriminator, side);
        nextDiscriminator++;
    }

    private NetworkService() {

    }

}
