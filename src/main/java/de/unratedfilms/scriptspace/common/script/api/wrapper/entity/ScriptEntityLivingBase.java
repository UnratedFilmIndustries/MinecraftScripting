
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItemStack;

public class ScriptEntityLivingBase extends ScriptEntity {

    public final EntityLivingBase entityLivingBase;

    public ScriptEntityLivingBase(EntityLivingBase entityLivingBase) {

        super(entityLivingBase);

        this.entityLivingBase = entityLivingBase;
    }

    public void setHealth(float health) {

        entityLivingBase.setHealth(health);
    }

    public float getHealth() {

        return entityLivingBase.getHealth();
    }

    public boolean isChild() {

        return entityLivingBase.isChild();
    }

    public Random getRNG() {

        return entityLivingBase.getRNG();
    }

    public void clearActivePotions() {

        entityLivingBase.clearActivePotions();
    }

    public ScriptItemStack getHeldItem() {

        return new ScriptItemStack(entityLivingBase.getHeldItem());
    }

    public ScriptItemStack getCurrentItemOrArmor(int slot) {

        return new ScriptItemStack(entityLivingBase.getEquipmentInSlot(slot));
    }

    public void setCurrentItemOrArmor(int slot, ScriptItemStack stack) {

        entityLivingBase.setCurrentItemOrArmor(slot, stack == null || stack.isAir() ? null : stack.stack);
    }

    public void setSprinting(boolean sprint) {

        entityLivingBase.setSprinting(sprint);
    }

    public void setJumping(boolean jump) {

        entityLivingBase.setJumping(jump);
    }

    public void setLocationAndUpdate(ScriptVec3 vec) {

        entityLivingBase.setPositionAndUpdate(vec.x, vec.y, vec.z);
    }

    public boolean canEntityBeSeen(ScriptEntity entity) {

        return entityLivingBase.canEntityBeSeen(entity.entity);
    }

}
