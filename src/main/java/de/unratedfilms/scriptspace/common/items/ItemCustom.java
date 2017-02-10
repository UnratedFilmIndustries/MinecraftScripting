
package de.unratedfilms.scriptspace.common.items;

import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.unratedfilms.scriptspace.common.Consts;

public class ItemCustom extends Item {

    public String getItemName() {

        return getRegistryName().getResourcePath();
    }

    public Item setItemName(String itemName) {

        setRegistryName(itemName);
        return setUnlocalizedName(Consts.MOD_ID + "." + itemName);
    }

    @Override
    @SuppressWarnings ({ "rawtypes", "unchecked" })
    public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean advanced) {

        lines.add(I18n.format(getUnlocalizedName() + ".desc"));
    }

}
