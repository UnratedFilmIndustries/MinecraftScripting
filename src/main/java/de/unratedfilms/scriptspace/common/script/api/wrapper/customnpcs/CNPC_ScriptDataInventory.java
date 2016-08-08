
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import noppes.npcs.DataInventory;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItemStack;

public class CNPC_ScriptDataInventory {

    public final CNPC_ScriptEntityCustomNpc npc;
    public final DataInventory              inventory;

    public CNPC_ScriptDataInventory(CNPC_ScriptEntityCustomNpc npc) {

        this.npc = npc;
        inventory = npc.entityNpc.inventory;
    }

    public ScriptItemStack getRightHand() {

        return new ScriptItemStack(inventory.getWeapon());
    }

    public void setRightHand(ScriptItemStack stack) {

        inventory.setWeapon(stack == null || stack.isAir() ? null : stack.stack);
        npc.updateClient();
    }

    public ScriptItemStack getLeftHand() {

        return new ScriptItemStack(inventory.getOffHand());
    }

    public void setLeftHand(ScriptItemStack stack) {

        inventory.setOffHand(stack == null || stack.isAir() ? null : stack.stack);
        npc.updateClient();
    }

    public ScriptItemStack getProjectileItem() {

        return new ScriptItemStack(inventory.getProjectile());
    }

    public void setProjectileItem(ScriptItemStack stack) {

        inventory.setProjectile(stack == null || stack.isAir() ? null : stack.stack);
        npc.entityNpc.script.aiNeedsUpdate = true;
    }

    /**
     * @param slot The armor slot to return (0: head, 1: body, 2: legs, 3: boots).
     * @return The item stack that is currently present in the given armor slot.
     */
    public ScriptItemStack getArmor(int slot) {

        return new ScriptItemStack(inventory.armor.get(Integer.valueOf(slot)));
    }

    /**
     * @param slot The armor slot to fill (0: head, 1: body, 2: legs, 3: boots).
     * @param stack The item stack that should be put into the given armor slot.
     */
    public void setArmor(int slot, ScriptItemStack stack) {

        inventory.armor.put(slot, stack == null || stack.isAir() ? null : stack.stack);
        npc.updateClient();
    }

    public int getExpMin() {

        return inventory.minExp;
    }

    public int getExpMax() {

        return inventory.maxExp;
    }

}
