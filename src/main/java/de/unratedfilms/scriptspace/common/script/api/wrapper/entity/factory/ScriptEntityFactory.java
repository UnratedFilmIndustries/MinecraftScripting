
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity.factory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import de.unratedfilms.scriptspace.common.Consts;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntity;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityAgeable;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityLivingBase;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityOcelot;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityPig;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityPlayer;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntitySheep;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityTameable;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityVillager;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityWolf;

public class ScriptEntityFactory {

    public static ScriptEntity createFromNative(Entity entity) {

        // Mod: Custom NPCs
        if (Consts.HASMOD_CUSTOM_NPCS) {
            ScriptEntity wrappedCNPC = ScriptEntityFactoryCustomNPCs.createFromNative(entity);
            if (wrappedCNPC != null) {
                return wrappedCNPC;
            }
        }

        // Vanilla
        ScriptEntity wrappedVanilla = createFromNativeVanilla(entity);
        if (wrappedVanilla != null) {
            return wrappedVanilla;
        }

        // Default case
        return new ScriptEntity(entity);
    }

    private static ScriptEntity createFromNativeVanilla(Entity entity) {

        if (entity instanceof EntityPlayer) {
            return new ScriptEntityPlayer((EntityPlayer) entity);
        }

        if (entity instanceof EntityOcelot) {
            return new ScriptEntityOcelot((EntityOcelot) entity);
        }
        if (entity instanceof EntityWolf) {
            return new ScriptEntityWolf((EntityWolf) entity);
        }
        if (entity instanceof EntityTameable) {
            return new ScriptEntityTameable((EntityTameable) entity);
        }

        if (entity instanceof EntityPig) {
            return new ScriptEntityPig((EntityPig) entity);
        }
        if (entity instanceof EntitySheep) {
            return new ScriptEntitySheep((EntitySheep) entity);
        }
        if (entity instanceof EntityVillager) {
            return new ScriptEntityVillager((EntityVillager) entity);
        }
        if (entity instanceof EntityAgeable) {
            return new ScriptEntityAgeable((EntityAgeable) entity);
        }

        if (entity instanceof EntityLivingBase) {
            return new ScriptEntityLivingBase((EntityLivingBase) entity);
        }

        return null;
    }

    private ScriptEntityFactory() {

    }

}
