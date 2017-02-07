
package de.unratedfilms.scriptspace.common.script.api.settings;

import static net.minecraftforge.common.util.Constants.NBT.TAG_STRING;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptBlock;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItemStack;
import de.unratedfilms.scriptspace.common.util.ByteBufUtils2;
import de.unratedfilms.scriptspace.common.util.NBTUtils;
import io.netty.buffer.ByteBuf;

public class SettingsEncoder {

    /*
     * Binary
     */

    public static Setting readBinary(ByteBuf from) {

        String name = ByteBufUtils.readUTF8String(from);
        String displayName = ByteBufUtils.readUTF8String(from);

        byte discriminator = from.readByte();

        switch (discriminator) {
            case 0:
                return new SettingBoolean(name, displayName, from.readBoolean());
            case 1:
                return new SettingInt(name, displayName, from.readInt(), from.readInt(), from.readInt());
            case 2:
                return new SettingFloat(name, displayName, from.readFloat(), from.readFloat(), from.readFloat());
            case 3:
                return new SettingString(name, displayName, ByteBufUtils.readUTF8String(from));
            case 4:
                return new SettingStringList(name, displayName, ByteBufUtils2.readStringArray(from), ByteBufUtils.readUTF8String(from));
            case 5:
                int blockId = from.readInt();
                int blockData = from.readInt();
                return new SettingBlock(name, displayName, ScriptBlock.fromBlock(Block.getBlockById(blockId), blockData));
            case 6:
                return new SettingItemStack(name, displayName, new ScriptItemStack(ByteBufUtils2.readItemStack(from)));
        }

        throw new IllegalArgumentException("Unknown setting discriminator: " + discriminator);
    }

    public static void writeBinary(ByteBuf to, Setting setting) {

        ByteBufUtils.writeUTF8String(to, setting.name);
        ByteBufUtils.writeUTF8String(to, setting.displayName);

        if (setting instanceof SettingBoolean) {
            to.writeByte(0);
            SettingBoolean settingBool = (SettingBoolean) setting;
            to.writeBoolean(settingBool.enabled);
        }

        else if (setting instanceof SettingInt) {
            to.writeByte(1);
            SettingInt settingInt = (SettingInt) setting;
            to.writeInt(settingInt.value);
            to.writeInt(settingInt.min);
            to.writeInt(settingInt.max);
        }

        else if (setting instanceof SettingFloat) {
            to.writeByte(2);
            SettingFloat settingFloat = (SettingFloat) setting;
            to.writeFloat(settingFloat.value);
            to.writeFloat(settingFloat.min);
            to.writeFloat(settingFloat.max);
        }

        else if (setting instanceof SettingString) {
            to.writeByte(3);
            SettingString settingString = (SettingString) setting;
            ByteBufUtils.writeUTF8String(to, settingString.string);
        }

        else if (setting instanceof SettingStringList) {
            to.writeByte(4);
            SettingStringList settingStringList = (SettingStringList) setting;
            ByteBufUtils2.writeStringArray(to, settingStringList.options);
            ByteBufUtils.writeUTF8String(to, settingStringList.selected);
        }

        else if (setting instanceof SettingBlock) {
            to.writeByte(5);
            SettingBlock settingBlock = (SettingBlock) setting;
            to.writeInt(Block.getIdFromBlock(settingBlock.block.block));
            to.writeInt(settingBlock.block.data);
        }

        else if (setting instanceof SettingItemStack) {
            to.writeByte(6);
            SettingItemStack settingItemStack = (SettingItemStack) setting;
            ByteBufUtils2.writeItemStack(to, settingItemStack.stack.stack);
        }

        else {
            throw new IllegalArgumentException("Unknown setting type: " + setting.getClass().getName());
        }
    }

    /*
     * NBT
     */

    public static Setting readNBT(NBTTagCompound from) {

        String name = from.getString("name");
        String displayName = from.getString("displayName");

        byte discriminator = from.getByte("discriminator");

        switch (discriminator) {
            case 0:
                return new SettingBoolean(name, displayName, from.getBoolean("enabled"));
            case 1:
                return new SettingInt(name, displayName, from.getInteger("value"), from.getInteger("min"), from.getInteger("max"));
            case 2:
                return new SettingFloat(name, displayName, from.getFloat("value"), from.getFloat("min"), from.getFloat("max"));
            case 3:
                return new SettingString(name, displayName, from.getString("string"));
            case 4:
                return new SettingStringList(name, displayName, NBTUtils.readStringArray(from.getTagList("options", TAG_STRING)), from.getString("selected"));
            case 5:
                int blockId = from.getInteger("blockId");
                int blockData = from.getInteger("blockData");
                return new SettingBlock(name, displayName, ScriptBlock.fromBlock(Block.getBlockById(blockId), blockData));
            case 6:
                return new SettingItemStack(name, displayName, new ScriptItemStack(new ItemStack(from.getCompoundTag("itemStack"))));
        }

        throw new IllegalArgumentException("Unknown setting discriminator: " + discriminator);
    }

    public static void writeNBT(NBTTagCompound to, Setting setting) {

        to.setString("name", setting.name);
        to.setString("displayName", setting.displayName);

        if (setting instanceof SettingBoolean) {
            to.setByte("discriminator", (byte) 0);
            SettingBoolean settingBool = (SettingBoolean) setting;
            to.setBoolean("enabled", settingBool.enabled);
        }

        else if (setting instanceof SettingInt) {
            to.setByte("discriminator", (byte) 1);
            SettingInt settingInt = (SettingInt) setting;
            to.setInteger("value", settingInt.value);
            to.setInteger("min", settingInt.min);
            to.setInteger("max", settingInt.max);
        }

        else if (setting instanceof SettingFloat) {
            to.setByte("discriminator", (byte) 2);
            SettingFloat settingFloat = (SettingFloat) setting;
            to.setFloat("value", settingFloat.value);
            to.setFloat("min", settingFloat.min);
            to.setFloat("max", settingFloat.max);
        }

        else if (setting instanceof SettingString) {
            to.setByte("discriminator", (byte) 3);
            SettingString settingString = (SettingString) setting;
            to.setString("string", settingString.string);
        }

        else if (setting instanceof SettingStringList) {
            to.setByte("discriminator", (byte) 4);
            SettingStringList settingStringList = (SettingStringList) setting;
            to.setTag("options", NBTUtils.createStringArray(settingStringList.options));
            to.setString("selected", settingStringList.selected);
        }

        else if (setting instanceof SettingBlock) {
            to.setByte("discriminator", (byte) 5);
            SettingBlock settingBlock = (SettingBlock) setting;
            to.setInteger("blockId", Block.getIdFromBlock(settingBlock.block.block));
            to.setInteger("blockData", settingBlock.block.data);
        }

        else if (setting instanceof SettingItemStack) {
            to.setByte("discriminator", (byte) 6);
            SettingItemStack settingItemStack = (SettingItemStack) setting;
            settingItemStack.stack.stack.writeToNBT(NBTUtils.addNewCompoundTag(to, "itemStack"));
        }

        else {
            throw new IllegalArgumentException("Unknown setting type: " + setting.getClass().getName());
        }
    }

    private SettingsEncoder() {

    }

}
