
package de.unratedfilms.scriptspace.client.main;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import de.unratedfilms.scriptspace.client.keys.KeyBindings;
import de.unratedfilms.scriptspace.client.keys.KeyHandler;
import de.unratedfilms.scriptspace.client.render.SelectionRenderer;
import de.unratedfilms.scriptspace.common.Consts;
import de.unratedfilms.scriptspace.common.main.CommonHandler;

public class ClientHandler extends CommonHandler {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

        super.preInit(event);

        // The minecraft dir might be different on the client
        Consts.MINECRAFT_DIR = Minecraft.getMinecraft().mcDataDir.toPath();
    }

    @Override
    public void init(FMLInitializationEvent event) {

        super.init(event);

        // Initialize the key system
        KeyBindings.initialize();
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

        super.postInit(event);

        // Initialize the selection renderer
        SelectionRenderer selectionRenderer = new SelectionRenderer();
        MinecraftForge.EVENT_BUS.register(selectionRenderer);
    }

}
