
package de.unratedfilms.scriptspace.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import de.unratedfilms.scriptspace.client.gui.settings.ConfigureProgramScreen;
import de.unratedfilms.scriptspace.common.script.Program;
import de.unratedfilms.scriptspace.common.script.ScriptsEncoder;
import de.unratedfilms.scriptspace.common.util.ReflectionHelper;

public class ItemProgram extends ItemCustom {

    static final ItemProgram INSTANCE = new ItemProgram();

    public static Program getProgram(ItemStack stack) {

        if (stack.hasTagCompound()) {
            return ScriptsEncoder.readProgramNBT(stack.getTagCompound());
        } else {
            return null;
        }
    }

    public static void setProgram(ItemStack stack, Program program) {

        NBTTagCompound tag = new NBTTagCompound();
        ScriptsEncoder.writeProgramNBT(tag, program);
        stack.setTagCompound(tag);

        String programDisplayName = program.getTitle().isEmpty() ? program.getSourceScript().getName() : program.getTitle() + " (" + program.getSourceScript().getName() + ")";
        stack.setStackDisplayName(I18n.format(INSTANCE.getUnlocalizedName() + ".name", programDisplayName));
    }

    private ItemProgram() {

        setItemName("program");
        setMaxStackSize(1);
        setFull3D();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ActionResult<ItemStack> result = super.onItemRightClick(worldIn, playerIn, handIn);
        ItemStack stack = result.getResult();

        // Only continue on the client
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            // When the user right clicks with an item stack, that stack must be the held item stack
            int slotId = playerIn.inventory.currentItem;

            Program program = getProgram(stack);

            // What follows is some hacky reflection trickery to avoid NoClassDefFoundErrors on the server because it doesn't know what the hell a GuiScreen is ...
            ReflectionHelper.invokeDeclaredMethod(ItemProgram.class, this, "doDisplayConfigureProgramScreen", new Class[] { Program.class, int.class }, program, slotId);
        }

        return result;
    }

    @SideOnly (Side.CLIENT)
    private void doDisplayConfigureProgramScreen(Program program, int slotId) {

        Minecraft.getMinecraft().displayGuiScreen(new ConfigureProgramScreen(null, program, slotId));
    }

}
