
package de.unratedfilms.scriptspace.common.items;

import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import de.unratedfilms.scriptspace.client.selection.SelectionStorage;
import de.unratedfilms.scriptspace.common.selection.Selection;
import de.unratedfilms.scriptspace.common.selection.SelectionBlock;
import de.unratedfilms.scriptspace.common.selection.SelectionCuboid;
import de.unratedfilms.scriptspace.common.selection.SelectionEntity;
import de.unratedfilms.scriptspace.common.selection.SelectionTileEntity;
import de.unratedfilms.scriptspace.common.selection.SelectionsEncoder;

public class ItemSelection extends ItemCustom {

    static final ItemSelection INSTANCE = new ItemSelection();

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

        setUnlocalizedName("selection");
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.MISC);
        setFull3D();
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
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

        // If we're on the client, choose the clicked selection as the current selection
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            SelectionStorage.chosenSelection = getSelection(stack);
        }

        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {

        TileEntity clickedTileEntity = world.getTileEntity(x, y, z);

        Selection oldSelection = getSelection(stack);
        Selection newSelection;

        if (clickedTileEntity != null) {
            newSelection = new SelectionTileEntity(clickedTileEntity);
        } else if (oldSelection instanceof SelectionBlock) {
            BlockPos corner1 = ((SelectionBlock) oldSelection).blockLocation;
            newSelection = new SelectionCuboid(world.provider.getDimension(), corner1, new BlockPos(x, y, z));
        } else {
            newSelection = new SelectionBlock(world.provider.getDimension(), new BlockPos(x, y, z));
        }

        setSelection(stack, newSelection);

        // If we're on the client, choose the modified selection as the current selection
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            SelectionStorage.chosenSelection = newSelection;
        }

        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

        SelectionEntity newSelection = new SelectionEntity(entity);
        setSelection(stack, newSelection);

        // If we're on the client, choose the modified selection as the current selection
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            SelectionStorage.chosenSelection = newSelection;
        }

        return true;
    }

}
