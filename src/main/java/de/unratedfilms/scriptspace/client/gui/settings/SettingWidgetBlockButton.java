
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import de.unratedfilms.guilib.core.MouseButton;
import de.unratedfilms.guilib.widgets.model.Button;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingBlock;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptBlock;

public class SettingWidgetBlockButton extends SettingWidgetAbstractItemButton {

    private static List<ItemStack> blocks = null;

    private final SettingBlock     setting;

    public SettingWidgetBlockButton(SettingBlock setting) {

        super(setting.displayName, new ItemStack(setting.block.block, 1, setting.block.data), null);
        setHandler(new Handler());

        this.setting = setting;
    }

    @Override
    public SettingBlock applySetting() {

        Block block = Block.getBlockFromItem(getItemStack().getItem());
        int blockData = getItemStack().getItem() != null ? getItemStack().getItemDamage() : 0;

        return setting.withValue(ScriptBlock.fromBlock(block, blockData));
    }

    @SuppressWarnings ("unchecked")
    private static List<ItemStack> getBlocks() {

        if (blocks == null) {
            blocks = new ArrayList<>();
            blocks.add(new ItemStack(Block.getBlockFromName("air"), 0, 0)); // same as null, 0, 0
            for (Iterator<Item> iterator = Item.itemRegistry.iterator(); iterator.hasNext();) {
                Item item = iterator.next();
                if (item instanceof ItemBlock) {
                    item.getSubItems(item, null, blocks);
                }
            }
            // CreativeTabs.tabBlock.displayAllReleventItems(blocks);
        }
        return blocks;
    }

    private class Handler extends SettingWidgetAbstractItemButton.Handler {

        // Note that button == SetBlockButton.this
        @Override
        public void buttonClicked(Button button, MouseButton mouseButton) {

            super.buttonClicked(button, mouseButton);

            if (mouseButton == MouseButton.LEFT) {
                MC.displayGuiScreen(new ItemPopup(SettingWidgetBlockButton.this, getBlocks(), (ConfigureProgramScreen) MC.currentScreen));
            }
        }

    }

}
