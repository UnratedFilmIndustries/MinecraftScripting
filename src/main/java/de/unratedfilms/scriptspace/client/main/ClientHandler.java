
package de.unratedfilms.scriptspace.client.main;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
        FMLCommonHandler.instance().bus().register(new KeyHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

        super.postInit(event);

        // Initialize the selection renderer
        SelectionRenderer selectionRenderer = new SelectionRenderer();
        FMLCommonHandler.instance().bus().register(selectionRenderer);
        MinecraftForge.EVENT_BUS.register(selectionRenderer);
    }

}
