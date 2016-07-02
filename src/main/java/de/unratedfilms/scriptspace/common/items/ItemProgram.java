
package de.unratedfilms.scriptspace.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.unratedfilms.scriptspace.client.gui.settings.ConfigureProgramScreen;
import de.unratedfilms.scriptspace.common.script.Program;
import de.unratedfilms.scriptspace.common.script.ScriptsEncoder;
import de.unratedfilms.scriptspace.common.util.ReflectionHelper;

public class ItemProgram extends CustomItem {

    public static final ItemProgram INSTANCE  = new ItemProgram();
    public static final String      ITEM_NAME = "program";

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

        stack.setStackDisplayName(StatCollector.translateToLocalFormatted(INSTANCE.getUnlocalizedName() + ".name", program.getSourceScript().getName()));
    }

    private ItemProgram() {

        bFull3D = true;
        maxStackSize = 1;
        setUnlocalizedName(ITEM_NAME);
        setCreativeTab(null);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

        // Only continue on the client
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            // When the user right clicks with an item stack, that stack must be the held item stack
            int slotId = player.inventory.currentItem;

            Program program = getProgram(stack);

            // What follows is some hacky reflection trickery to avoid NoClassDefFoundErrors on the server because it doesn't know what the hell a GuiScreen is ...
            ReflectionHelper.invokeDeclaredMethod(ItemProgram.class, this, "doDisplayConfigureProgramScreen", new Class[] { Program.class, int.class }, program, slotId);
        }

        return stack;
    }

    @SideOnly (Side.CLIENT)
    private void doDisplayConfigureProgramScreen(Program program, int slotId) {

        Minecraft.getMinecraft().displayGuiScreen(new ConfigureProgramScreen(null, program, slotId));
    }

}
