
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.widgets.model.Button;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingItemStack;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItemStack;

public class SettingWidgetItemStackButton extends SettingWidgetAbstractItemButton<SettingItemStack> {

    private static List<ItemStack> allItemsCache = null;

    /**
     * See {@link net.minecraft.client.gui.inventory.GuiContainerCreative#updateCreativeSearch()}
     */
    @SuppressWarnings ("unchecked")
    private static List<ItemStack> getAllItems() {

        if (allItemsCache == null) {
            allItemsCache = new ArrayList<>();
            allItemsCache.add(new ItemStack((Item) null));
            for (Item item : Item.REGISTRY) {
                if (item != null && item.getCreativeTab() != null) {
                    item.getSubItems(item, null, allItemsCache);
                }
            }
            for (Enchantment ench : Enchantment.REGISTRY) {
                if (ench != null && ench.type != null) {
                    Items.ENCHANTED_BOOK.getAll(ench, allItemsCache);
                }
            }
        }

        return allItemsCache;
    }

    public SettingWidgetItemStackButton(SettingItemStack setting) {

        super(setting, setting.stack.stack, null);
        button.setHandler(new Handler());
    }

    @Override
    public SettingItemStack applySetting() {

        return setting.withValue(new ScriptItemStack(button.getItemStack()));
    }

    private List<ItemStack> getItems() {

        List<ItemStack> list = new ArrayList<>();
        for (ItemStack item : MC.player.inventory.mainInventory) {
            if (item != null) {
                list.add(item.copy());
            }
        }
        for (ItemStack armor : MC.player.inventory.armorInventory) {
            if (armor != null) {
                list.add(armor.copy());
            }
        }
        list.addAll(getAllItems());
        return list;
    }

    private class Handler extends SettingWidgetAbstractItemButton.Handler {

        // Note that button == SetItemStackButton.this
        @Override
        public void buttonClicked(Button button, MouseButton mouseButton) {

            super.buttonClicked(button, mouseButton);

            if (mouseButton == MouseButton.LEFT) {
                MC.displayGuiScreen(new ItemPopup(SettingWidgetItemStackButton.this, getItems(), (ConfigureProgramScreen) MC.currentScreen));
            }
        }

    }

}
