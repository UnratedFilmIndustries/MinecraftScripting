
package de.unratedfilms.scriptspace.common.items;

import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import de.unratedfilms.scriptspace.common.selection.Selection;
import de.unratedfilms.scriptspace.common.selection.SelectionBlock;
import de.unratedfilms.scriptspace.common.selection.SelectionCuboid;
import de.unratedfilms.scriptspace.common.selection.SelectionEntity;
import de.unratedfilms.scriptspace.common.selection.SelectionTileEntity;
import de.unratedfilms.scriptspace.common.selection.SelectionsEncoder;
import de.unratedfilms.scriptspace.common.util.Vec3i;

public class ItemSelection extends CustomItem {

    public static final ItemSelection INSTANCE  = new ItemSelection();
    public static final String        ITEM_NAME = "selection";

    public static Selection getSelection(ItemStack stack) {

        if (stack.hasTagCompound()) {
            return SelectionsEncoder.readNBT(stack.getTagCompound());
        } else {
            return null;
        }
    }

    public static void setSelection(ItemStack stack, Selection selection) {

        NBTTagCompound tag = new NBTTagCompound();
        SelectionsEncoder.writeNBT(tag, selection);
        stack.setTagCompound(tag);
    }

    private ItemSelection() {

        bFull3D = true;
        maxStackSize = 1;
        setUnlocalizedName(ITEM_NAME);
        setCreativeTab(CreativeTabs.tabMisc);
    }

    @SuppressWarnings ({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean advanced) {

        super.addInformation(stack, player, lines, advanced);

        Selection selection = getSelection(stack);
        lines.add(I18n.format(getUnlocalizedName() + ".selType." + getSelectionTypeString(selection)));
    }

    private String getSelectionTypeString(Selection selection) {

        if (selection instanceof SelectionBlock) {
            return "block";
        } else if (selection instanceof SelectionCuboid) {
            return "cuboid";
        } else if (selection instanceof SelectionEntity) {
            return "entity";
        } else if (selection instanceof SelectionTileEntity) {
            return "tileEntity";
        } else {
            return "none";
        }
    }

    /*
     * Note that the following listener methods do run both on the server and on the client.
     * That way, both the server and the client have the same selection information stored in the item stack's NBT without any manual syncing.
     */

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {

        TileEntity clickedTileEntity = world.getTileEntity(x, y, z);

        Selection oldSelection = getSelection(stack);
        Selection newSelection;

        if (clickedTileEntity != null) {
            newSelection = new SelectionTileEntity(clickedTileEntity);
        } else if (oldSelection instanceof SelectionBlock) {
            Vec3i corner1 = ((SelectionBlock) oldSelection).blockLocation;
            newSelection = new SelectionCuboid(world.provider.dimensionId, corner1, new Vec3i(x, y, z));
        } else {
            newSelection = new SelectionBlock(world.provider.dimensionId, new Vec3i(x, y, z));
        }

        setSelection(stack, newSelection);

        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

        SelectionEntity newSelection = new SelectionEntity(entity);
        setSelection(stack, newSelection);

        return true;
    }

}
