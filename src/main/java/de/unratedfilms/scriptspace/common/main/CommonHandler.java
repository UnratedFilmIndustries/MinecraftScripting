
package de.unratedfilms.scriptspace.common.main;

import java.nio.file.Paths;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import de.unratedfilms.scriptspace.common.Config;
import de.unratedfilms.scriptspace.common.Consts;
import de.unratedfilms.scriptspace.common.items.CustomItems;
import de.unratedfilms.scriptspace.net.NetworkService;

public class CommonHandler {

    public void preInit(FMLPreInitializationEvent event) {

        // Retrieve dynamic "constants"
        Consts.MOD_VERSION = event.getModMetadata().version;
        Consts.MINECRAFT_DIR = Paths.get(".");

        // Initialize the configuration
        Config.initialize(event.getSuggestedConfigurationFile());

        // Initialize the network service
        NetworkService.initialize();

        // Register the custom items
        registerItems();
    }

    private void registerItems() {

        GameRegistry.registerItem(CustomItems.SELECTION, CustomItems.SELECTION.getName());
        GameRegistry.registerItem(CustomItems.PROGRAM, CustomItems.PROGRAM.getName());
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

        // Retrieve whether other mods are installed
        Consts.HASMOD_CUSTOM_NPCS = Loader.isModLoaded("customnpcs");
    }

}
