
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import de.unratedfilms.scriptspace.client.gui.settings.widgets.ItemPopup;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingItemStack;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItemStack;
import de.unratedfilms.scriptspace.common.util.Deobf;

public class SetItemStackButton extends SetAbstractItemButton {

    private static List<ItemStack> allItemsCache = null;

    /**
     * See {@link net.minecraft.client.gui.inventory.GuiContainerCreative#updateCreativeSearch()}
     */
    @SuppressWarnings ("unchecked")
    private static List<ItemStack> getAllItems() {

        if (allItemsCache == null) {
            allItemsCache = new ArrayList<>();
            for (Iterator<Item> iterator = Item.itemRegistry.iterator(); iterator.hasNext();) {
                Item item = iterator.next();
                if (item != null && item.getCreativeTab() != null) {
                    item.getSubItems(item, null, allItemsCache);
                }
            }
            for (Enchantment ench : Enchantment.enchantmentsList) {
                if (ench != null && ench.type != null) {
                    Items.enchanted_book.func_92113_a(ench, allItemsCache);
                }
            }
        }

        return allItemsCache;
    }

    private final SettingItemStack setting;

    public SetItemStackButton(SettingItemStack setting) {

        super(setting.displayName, setting.stack.stack, null);

        this.setting = setting;
    }

    @Override
    public SettingItemStack applySetting() {

        return setting.withValue(new ScriptItemStack(item));
    }

    @Override
    public void handleClick(int mx, int my) {

        MC.getSoundHandler().playSound(Deobf.PositionedSoundRecord_create(new ResourceLocation("gui.button.press"), 1.0F));
        MC.displayGuiScreen(new ItemPopup(this, getItems(), (ConfigureProgramScreen) MC.currentScreen));
    }

    private List<ItemStack> getItems() {

        List<ItemStack> list = new ArrayList<>();
        for (ItemStack item : MC.thePlayer.inventory.mainInventory) {
            if (item != null) {
                list.add(item);
            }
        }
        for (ItemStack armor : MC.thePlayer.inventory.armorInventory) {
            if (armor != null) {
                list.add(armor);
            }
        }
        list.addAll(getAllItems());
        return list;
    }

}
