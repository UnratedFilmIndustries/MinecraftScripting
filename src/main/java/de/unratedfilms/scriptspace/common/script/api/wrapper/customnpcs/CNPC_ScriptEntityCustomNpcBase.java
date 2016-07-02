
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import net.minecraft.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.Line;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.util.ValueUtil;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityLivingBase;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityPlayer;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItemStack;
import de.unratedfilms.scriptspace.common.util.Utils;

public class CNPC_ScriptEntityCustomNpcBase extends ScriptEntityLivingBase {

    public final EntityNPCInterface entityNpcBase;

    public CNPC_ScriptEntityCustomNpcBase(EntityNPCInterface entityNpcBase) {

        super(entityNpcBase);

        this.entityNpcBase = entityNpcBase;
    }

    public int getSize() {

        return entityNpcBase.display.modelSize;
    }

    public void setSize(int size) {

        if (size > 30) {
            size = 30;
        } else if (size < 1) {
            size = 1;
        }
        entityNpcBase.display.modelSize = size;
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    @Override
    public void setRotationYaw(float rotationYaw) {

        entityNpcBase.ai.orientation = (int) rotationYaw;
        super.setRotationYaw(rotationYaw);
    }

    public String getName() {

        return entityNpcBase.display.name;
    }

    public void setName(String name) {

        entityNpcBase.display.name = name;
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public String getTitle() {

        return entityNpcBase.display.title;
    }

    public void setTitle(String title) {

        entityNpcBase.display.title = title;
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public String getTexture() {

        return entityNpcBase.display.texture;
    }

    public void setTexture(String texture) {

        entityNpcBase.display.texture = texture;
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public ScriptVec3 getHome() {

        int[] startPos = entityNpcBase.ai.startPos;
        return new ScriptVec3(startPos[0], startPos[1], startPos[2]);
    }

    public void setHome(ScriptVec3 vec) {

        entityNpcBase.ai.startPos = new int[] { Utils.floor(vec.x), Utils.floor(vec.y), Utils.floor(vec.z) };
    }

    public void setMaxHealth(int health) {

        entityNpcBase.stats.setMaxHealth(health);
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public void setReturnToHome(boolean bo) {

        entityNpcBase.ai.returnToStart = bo;
    }

    public boolean getReturnToHome() {

        return entityNpcBase.ai.returnToStart;
    }

    public CNPC_ScriptFaction getFaction() {

        return new CNPC_ScriptFaction(entityNpcBase.getFaction());
    }

    public void setFaction(int id) {

        entityNpcBase.setFaction(id);
    }

    public void shootItem(ScriptEntityLivingBase target, ScriptItemStack itemStack, int accuracy) {

        if (itemStack == null) {
            return;
        }
        if (accuracy < 0) {
            accuracy = 0;
        } else if (accuracy > 100) {
            accuracy = 100;
        }
        entityNpcBase.shoot(target.entityLivingBase, accuracy, itemStack.stack, false);
    }

    public void say(String message) {

        entityNpcBase.saySurrounding(new Line(message));
    }

    public void say(ScriptEntityPlayer player, String message) {

        if (player == null || message == null || message.isEmpty()) {
            return;
        }
        entityNpcBase.say(player.entityPlayer, new Line(message));
    }

    @Override
    public void setDead() {

        entityNpcBase.delete();
        NoppesUtilServer.deleteNpc(entityNpcBase, null);
    }

    public void reset() {

        entityNpcBase.reset();
    }

    public ScriptItemStack getRightItem() {

        ItemStack stack = entityNpcBase.inventory.getWeapon();
        if (stack == null || stack.getItem() == null) {
            return null;
        }
        return new ScriptItemStack(stack);
    }

    public void setRightItem(ScriptItemStack stack) {

        if (stack == null) {
            entityNpcBase.inventory.setWeapon(null);
        } else {
            entityNpcBase.inventory.setWeapon(stack.stack);
        }
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public ScriptItemStack getLeftItem() {

        ItemStack stack = entityNpcBase.getOffHand();
        if (stack == null || stack.getItem() == null) {
            return null;
        }
        return new ScriptItemStack(stack);
    }

    public void setLeftItem(ScriptItemStack stack) {

        if (stack == null) {
            entityNpcBase.inventory.setOffHand(null);
        } else {
            entityNpcBase.inventory.setOffHand(stack.stack);
        }
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public ScriptItemStack getProjectileItem() {

        ItemStack stack = entityNpcBase.inventory.getProjectile();
        if (stack == null || stack.getItem() == null) {
            return null;
        }
        return new ScriptItemStack(stack);
    }

    public void setProjectileItem(ScriptItemStack stack) {

        if (stack == null) {
            entityNpcBase.inventory.setProjectile(null);
        } else {
            entityNpcBase.inventory.setProjectile(stack.stack);
        }
        entityNpcBase.script.aiNeedsUpdate = true;
    }

    public ScriptItemStack getArmor(int slot) {

        ItemStack stack = entityNpcBase.inventory.armor.get(Integer.valueOf(slot));
        if (stack == null) {
            return null;
        }
        return new ScriptItemStack(stack);
    }

    public void setArmor(int slot, ScriptItemStack stack) {

        if (stack == null) {
            entityNpcBase.inventory.armor.put(Integer.valueOf(slot), null);
        } else {
            entityNpcBase.inventory.armor.put(Integer.valueOf(slot), stack.stack);
        }
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public String getAnimation() {

        return CNPC_ScriptAnimation.fromNative(entityNpcBase.ai.animationType);
    }

    public void setAnimation(String animation) {

        entityNpcBase.ai.animationType = CNPC_ScriptAnimation.toNative(animation);
    }

    public String getRotationBehavior() {

        return CNPC_ScriptRotationBehavior.fromNative(entityNpcBase.ai.standingType);
    }

    public void setRotationBehavior(String rotationBehavior) {

        entityNpcBase.ai.standingType = CNPC_ScriptRotationBehavior.toNative(rotationBehavior);
    }

    public void setVisibleType(int type) {

        entityNpcBase.display.visible = type;
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public int getVisibleType() {

        return entityNpcBase.display.visible;
    }

    public void setShowName(int type) {

        entityNpcBase.display.showName = type;
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public int getShowName() {

        return entityNpcBase.display.showName;
    }

    public int getShowBossBar() {

        return entityNpcBase.display.showBossBar;
    }

    public void setShowBossBar(int type) {

        entityNpcBase.display.showBossBar = (byte) type;
        entityNpcBase.script.clientNeedsUpdate = true;
    }

    public int getMeleeStrength() {

        return entityNpcBase.stats.getAttackStrength();
    }

    public void setMeleeStrength(int strength) {

        entityNpcBase.stats.setAttackStrength(strength);
    }

    public int getMeleeSpeed() {

        return entityNpcBase.stats.attackSpeed;
    }

    public void setMeleeSpeed(int speed) {

        entityNpcBase.stats.attackSpeed = speed;
    }

    public int getRangedStrength() {

        return entityNpcBase.stats.pDamage;
    }

    public void setRangedStrength(int strength) {

        entityNpcBase.stats.pDamage = strength;
    }

    public int getRangedSpeed() {

        return entityNpcBase.stats.pSpeed;
    }

    public void setRangedSpeed(int speed) {

        entityNpcBase.stats.pSpeed = speed;
    }

    public int getRangedBurst() {

        return entityNpcBase.stats.burstCount;
    }

    public void setRangedBurst(int count) {

        entityNpcBase.stats.burstCount = count;
    }

    public void giveItem(ScriptEntityPlayer player, ScriptItemStack stack) {

        entityNpcBase.givePlayerItem(player.entityPlayer, stack.stack);
    }

    public void executeCommand(String command) {

        NoppesUtilServer.runCommand(entityNpcBase, entityNpcBase.getCommandSenderName(), command, null);
    }

    public void seExplosionResistance(float resistance) {

        entityNpcBase.stats.resistances.explosion = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getExplosionResistance() {

        return entityNpcBase.stats.resistances.explosion;
    }

    public void setMeleeResistance(float resistance) {

        entityNpcBase.stats.resistances.playermelee = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getMeleeResistance() {

        return entityNpcBase.stats.resistances.playermelee;
    }

    public void setArrowResistance(float resistance) {

        entityNpcBase.stats.resistances.arrow = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getArrowResistance() {

        return entityNpcBase.stats.resistances.arrow;
    }

    public void setKnockbackResistance(float resistance) {

        entityNpcBase.stats.resistances.knockback = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getKnockbackResistance() {

        return entityNpcBase.stats.resistances.knockback;
    }

    public void setRetaliateType(int type) {

        entityNpcBase.ai.onAttack = type;
        entityNpcBase.setResponse();
    }

    public int getCombatRegen() {

        return entityNpcBase.stats.combatRegen;
    }

    public void setCombatRegen(int regen) {

        entityNpcBase.stats.combatRegen = regen;
    }

    public int getHealthRegen() {

        return entityNpcBase.stats.healthRegen;
    }

    public void setHealthRegen(int regen) {

        entityNpcBase.stats.healthRegen = regen;
    }

    public long getAge() {

        return entityNpcBase.totalTicksAlive;
    }

}
