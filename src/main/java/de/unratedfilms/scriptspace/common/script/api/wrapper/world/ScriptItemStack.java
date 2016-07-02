
package de.unratedfilms.scriptspace.common.script.api.wrapper.world;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagCompound;

public class ScriptItemStack {

    public final ItemStack stack;

    public ScriptItemStack(ItemStack is) {

        stack = is;
    }

    public ScriptItemStack(ScriptItem item, int size, int damage) {

        stack = new ItemStack(item == null ? null : item.item, size, damage);
    }

    public ScriptItemStack(ScriptBlock block, int size, int damage) {

        stack = new ItemStack(block == null ? null : block.block, size, damage);
    }

    public ScriptItem getItem() {

        return stack.getItem() == null ? null : ScriptItem.fromItem(stack.getItem());
    }

    public int getStackSize() {

        return stack.stackSize;
    }

    public void setStackSize(int size) {

        stack.stackSize = size;
    }

    public int getItemDamage() {

        return stack.getItemDamage();
    }

    public void setItemDamage(int damage) {

        stack.setItemDamage(damage);
    }

    public boolean hasTagCompound() {

        return stack.hasTagCompound();
    }

    public ScriptTagCompound getTagCompound() {

        return hasTagCompound() ? new ScriptTagCompound(stack.getTagCompound()) : null;
    }

    public void setTagCompound(ScriptTagCompound tag) {

        stack.setTagCompound(tag == null ? null : tag.tag);
    }

    public ScriptTagCompound writeToTag() {

        return new ScriptTagCompound(stack.writeToNBT(new NBTTagCompound()));
    }

    public void readFromTag(ScriptTagCompound tag) {

        stack.readFromNBT(tag.tag);
    }

}
