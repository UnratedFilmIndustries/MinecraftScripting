
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import net.minecraft.item.ItemStack;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptArmorSlot;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItemStack;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.entity.data.DataInventory;

public class CNPC_ScriptDataInventory {

    @SuppressWarnings ("deprecation")
    private static IItemStack toNpcStack(ItemStack stack) {

        return ItemStackWrapper.createNew(stack);
    }

    public final CNPC_ScriptEntityCustomNpc npc;
    public final DataInventory              inventory;

    public CNPC_ScriptDataInventory(CNPC_ScriptEntityCustomNpc npc) {

        this.npc = npc;
        inventory = npc.entityNpc.inventory;
    }

    public ScriptItemStack getRightHand() {

        return new ScriptItemStack(inventory.getRightHand().getMCItemStack());
    }

    public void setRightHand(ScriptItemStack stack) {

        inventory.setRightHand(stack == null || stack.isAir() ? null : toNpcStack(stack.stack));
    }

    public ScriptItemStack getLeftHand() {

        return new ScriptItemStack(inventory.getLeftHand().getMCItemStack());
    }

    public void setLeftHand(ScriptItemStack stack) {

        inventory.setLeftHand(stack == null || stack.isAir() ? null : toNpcStack(stack.stack));
    }

    public ScriptItemStack getProjectile() {

        return new ScriptItemStack(inventory.getProjectile().getMCItemStack());
    }

    public void setProjectile(ScriptItemStack stack) {

        inventory.setProjectile(stack == null || stack.isAir() ? null : toNpcStack(stack.stack));
    }

    public ScriptItemStack getArmor(String slot) {

        return new ScriptItemStack(inventory.getArmor(CNPC_ScriptArmorSlot.toNative(slot)).getMCItemStack());
    }

    public void setArmor(String slot, ScriptItemStack stack) {

        inventory.setArmor(CNPC_ScriptArmorSlot.toNative(slot), stack == null || stack.isAir() ? null : toNpcStack(stack.stack));
    }

    public int getExpMin() {

        return inventory.getExpMin();
    }

    public int getExpMax() {

        return inventory.getExpMax();
    }

}
