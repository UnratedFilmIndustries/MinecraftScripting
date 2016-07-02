
package de.unratedfilms.scriptspace.client.gui.settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import de.unratedfilms.scriptspace.client.gui.settings.widgets.ItemPopup;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingBlock;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptBlock;
import de.unratedfilms.scriptspace.common.util.Deobf;

public class SetBlockButton extends SetAbstractItemButton {

    private static List<ItemStack> blocks = null;

    private final SettingBlock     setting;

    public SetBlockButton(SettingBlock setting) {

        super(setting.displayName, new ItemStack(setting.block.block, 1, setting.block.data), null);

        this.setting = setting;
    }

    @Override
    public SettingBlock applySetting() {

        Block block = Block.getBlockFromItem(item.getItem());
        int blockData = item.getItem() != null ? item.getItemDamage() : 0;

        return setting.withValue(ScriptBlock.fromBlock(block, blockData));
    }

    @Override
    public void handleClick(int mx, int my) {

        MC.getSoundHandler().playSound(Deobf.PositionedSoundRecord_create(new ResourceLocation("gui.button.press"), 1.0F));
        MC.displayGuiScreen(new ItemPopup(this, getBlocks(), (ConfigureProgramScreen) MC.currentScreen));
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

}
