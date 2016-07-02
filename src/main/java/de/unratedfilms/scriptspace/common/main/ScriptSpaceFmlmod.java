
package de.unratedfilms.scriptspace.common.main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.unratedfilms.scriptspace.common.Consts;

@Mod (modid = Consts.MOD_ID)
public class ScriptSpaceFmlmod {

    @SidedProxy (clientSide = Consts.ROOT_PACKAGE + ".client.main.ClientHandler", serverSide = Consts.ROOT_PACKAGE + ".common.main.CommonHandler")
    private static CommonHandler proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit(event);
    }

}
