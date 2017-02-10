
package de.unratedfilms.scriptspace.common.main;

import java.nio.file.Paths;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import de.unratedfilms.scriptspace.common.Config;
import de.unratedfilms.scriptspace.common.Consts;
import de.unratedfilms.scriptspace.common.items.CustomItems;
import de.unratedfilms.scriptspace.net.NetworkService;

public class CommonHandler {

    public void preInit(FMLPreInitializationEvent event) {

        // Retrieve dynamic "constants"
        Consts.MINECRAFT_DIR = Paths.get(".");

        // Initialize the configuration
        Config.initialize(event.getSuggestedConfigurationFile());

        // Initialize the network service
        NetworkService.initialize();

        // Register the custom items
        registerItems();
    }

    private void registerItems() {

        GameRegistry.register(CustomItems.SELECTION);
        GameRegistry.register(CustomItems.PROGRAM);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

        // Retrieve whether other mods are installed
        Consts.HASMOD_CUSTOM_NPCS = Loader.isModLoaded("customnpcs");
    }

}
