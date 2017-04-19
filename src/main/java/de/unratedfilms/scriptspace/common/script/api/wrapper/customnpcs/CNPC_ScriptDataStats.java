
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptRespawnBehavior;
import noppes.npcs.entity.data.DataStats;
import noppes.npcs.util.ValueUtil;

public class CNPC_ScriptDataStats {

    public final CNPC_ScriptEntityCustomNpc npc;
    public final DataStats                  stats;

    public CNPC_ScriptDataStats(CNPC_ScriptEntityCustomNpc npc) {

        this.npc = npc;
        stats = npc.entityNpc.stats;
    }

    public int getMaxHealth() {

        return stats.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {

        stats.setMaxHealth(maxHealth);
    }

    public void seExplosionResistance(float resistance) {

        stats.resistances.explosion = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getExplosionResistance() {

        return stats.resistances.explosion;
    }

    public void setMeleeResistance(float resistance) {

        stats.resistances.melee = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getMeleeResistance() {

        return stats.resistances.melee;
    }

    public void setArrowResistance(float resistance) {

        stats.resistances.arrow = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getArrowResistance() {

        return stats.resistances.arrow;
    }

    public void setKnockbackResistance(float resistance) {

        stats.resistances.knockback = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getKnockbackResistance() {

        return stats.resistances.knockback;
    }

    public int getCombatRegen() {

        return stats.getCombatRegen();
    }

    public void setCombatRegen(int combatRegen) {

        stats.setCombatRegen(combatRegen);
    }

    public int getHealthRegen() {

        return stats.getHealthRegen();
    }

    public void setHealthRegen(int healthRegen) {

        stats.setHealthRegen(healthRegen);
    }

    // TODO: Add mechanisms for melee and ranged stats

    public boolean getImmunityPotion() {

        return stats.potionImmune;
    }

    public void setImmunityPotion(boolean immune) {

        stats.potionImmune = immune;
    }

    public boolean getImmunityFallDamage() {

        return !stats.noFallDamage;
    }

    public void setImmunityFallDamage(boolean immune) {

        stats.noFallDamage = !immune;
    }

    public boolean getImmunityBurnInSun() {

        return stats.potionImmune;
    }

    public void setImmunityBurnInSun(boolean immune) {

        stats.potionImmune = immune;
    }

    public boolean getImmunityFire() {

        return stats.immuneToFire;
    }

    public void setImmunityFire(boolean immune) {

        stats.immuneToFire = immune;
    }

    public boolean getImmunityDrowning() {

        return !stats.canDrown;
    }

    public void setImmunityDrowning(boolean immune) {

        stats.canDrown = !immune;
    }

    public String getRespawnBehavior() {

        return CNPC_ScriptRespawnBehavior.fromNative(stats.getRespawnType());
    }

    public void setRespawnBehavior(String respawnBehavior) {

        stats.setRespawnType(CNPC_ScriptRespawnBehavior.toNative(respawnBehavior));
    }

    public int getRespawnTime() {

        return stats.getRespawnTime();
    }

    public void setRespawnTime(int respawnTime) {

        stats.setRespawnTime(respawnTime);
    }

    public boolean getHideDeadBody() {

        return stats.getHideDeadBody();
    }

    public void setHideDeadBody(boolean hideDeadBody) {

        stats.setHideDeadBody(hideDeadBody);
    }

    public int getAggroRange() {

        return stats.getAggroRange();
    }

    public void setAggroRange(int aggroRange) {

        stats.setAggroRange(aggroRange);
    }

}
