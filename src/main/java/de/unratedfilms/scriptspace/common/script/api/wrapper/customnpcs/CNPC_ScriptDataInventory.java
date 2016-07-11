
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import net.minecraft.item.ItemStack;
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

        ItemStack stack = inventory.getWeapon();
        if (stack == null || stack.getItem() == null) {
            return null;
        }
        return new ScriptItemStack(stack);
    }

    public void setRightHand(ScriptItemStack stack) {

        if (stack == null) {
            inventory.setWeapon(null);
        } else {
            inventory.setWeapon(stack.stack);
        }
        npc.updateClient();
    }

    public ScriptItemStack getLeftHand() {

        ItemStack stack = inventory.getOffHand();
        if (stack == null || stack.getItem() == null) {
            return null;
        }
        return new ScriptItemStack(stack);
    }

    public void setLeftHand(ScriptItemStack stack) {

        if (stack == null) {
            inventory.setOffHand(null);
        } else {
            inventory.setOffHand(stack.stack);
        }
        npc.updateClient();
    }

    public ScriptItemStack getProjectileItem() {

        ItemStack stack = inventory.getProjectile();
        if (stack == null || stack.getItem() == null) {
            return null;
        }
        return new ScriptItemStack(stack);
    }

    public void setProjectileItem(ScriptItemStack stack) {

        if (stack == null) {
            inventory.setProjectile(null);
        } else {
            inventory.setProjectile(stack.stack);
        }
        npc.entityNpc.script.aiNeedsUpdate = true;
    }

    /**
     * @param slot The armor slot to return (0: head, 1: body, 2: legs, 3: boots).
     */
    public ScriptItemStack getArmor(int slot) {

        ItemStack stack = inventory.armor.get(Integer.valueOf(slot));
        if (stack == null) {
            return null;
        }
        return new ScriptItemStack(stack);
    }

    /**
     * @param slot The armor slot to fill (0: head, 1: body, 2: legs, 3: boots).
     * @param stack The item stack that should be put into the given armor slot.
     */
    public void setArmor(int slot, ScriptItemStack stack) {

        if (stack == null) {
            inventory.armor.put(Integer.valueOf(slot), null);
        } else {
            inventory.armor.put(Integer.valueOf(slot), stack.stack);
        }
        npc.updateClient();
    }

    public int getExpMin() {

        return inventory.minExp;
    }

    public int getExpMax() {

        return inventory.maxExp;
    }

}
