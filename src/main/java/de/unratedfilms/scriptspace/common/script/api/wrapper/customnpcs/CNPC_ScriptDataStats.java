
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptRespawnBehavior;
import noppes.npcs.DataStats;
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
        npc.updateClient();
    }

    public void seExplosionResistance(float resistance) {

        stats.resistances.explosion = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getExplosionResistance() {

        return stats.resistances.explosion;
    }

    public void setMeleeResistance(float resistance) {

        stats.resistances.playermelee = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
    }

    public float getMeleeResistance() {

        return stats.resistances.playermelee;
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

        return stats.combatRegen;
    }

    public void setCombatRegen(int combatRegen) {

        stats.combatRegen = combatRegen;
    }

    public int getHealthRegen() {

        return stats.healthRegen;
    }

    public void setHealthRegen(int healthRegen) {

        stats.healthRegen = healthRegen;
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

        return CNPC_ScriptRespawnBehavior.fromNative(stats.spawnCycle);
    }

    public void setRespawnBehavior(String respawnBehavior) {

        stats.spawnCycle = CNPC_ScriptRespawnBehavior.toNative(respawnBehavior);
    }

    public int getRespawnTime() {

        return stats.respawnTime;
    }

    public void setRespawnTime(int respawnTime) {

        stats.respawnTime = respawnTime;
    }

    public boolean getHideDeadBody() {

        return stats.hideKilledBody;
    }

    public void setHideDeadBody(boolean hideDeadBody) {

        stats.hideKilledBody = hideDeadBody;
        npc.updateClient();
    }

    public int getAggroRange() {

        return stats.aggroRange;
    }

    public void setAggroRange(int aggroRange) {

        stats.aggroRange = aggroRange;
    }

}
