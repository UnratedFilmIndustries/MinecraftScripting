
package de.unratedfilms.scriptspace.common.script;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import cpw.mods.fml.common.network.ByteBufUtils;
import de.unratedfilms.scriptspace.common.script.api.settings.Setting;
import de.unratedfilms.scriptspace.common.script.api.settings.SettingsEncoder;
import de.unratedfilms.scriptspace.common.util.NBTUtils;

public class ScriptsEncoder {

    /*
     * Binary
     */

    public static SourceScript readSourceScriptBinary(ByteBuf from) {

        String name = ByteBufUtils.readUTF8String(from);
        String code = ByteBufUtils.readUTF8String(from);

        return new SourceScript(name, code);
    }

    public static void writeSourceScriptBinary(ByteBuf to, SourceScript sourceScript) {

        ByteBufUtils.writeUTF8String(to, sourceScript.getName());
        ByteBufUtils.writeUTF8String(to, sourceScript.getCode());
    }

    public static Program readProgramBinary(ByteBuf from) {

        SourceScript sourceScript = readSourceScriptBinary(from);

        Setting[] settings = new Setting[from.readInt()];
        for (int i = 0; i < settings.length; i++) {
            settings[i] = SettingsEncoder.readBinary(from);
        }

        return new Program(sourceScript, Arrays.asList(settings));
    }

    public static void writeProgramBinary(ByteBuf to, Program program) {

        writeSourceScriptBinary(to, program.getSourceScript());

        to.writeInt(program.getSettings().size());
        for (Setting setting : program.getSettings()) {
            SettingsEncoder.writeBinary(to, setting);
        }
    }

    /*
     * NBT
     */

    public static SourceScript readSourceScriptNBT(NBTTagCompound from) {

        String name = from.getString("name");
        String code = from.getString("code");

        return new SourceScript(name, code);
    }

    public static void writeSourceScriptNBT(NBTTagCompound to, SourceScript sourceScript) {

        to.setString("name", sourceScript.getName());
        to.setString("code", sourceScript.getCode());
    }

    public static Program readProgramNBT(NBTTagCompound from) {

        SourceScript sourceScript = readSourceScriptNBT(from.getCompoundTag("sourceScript"));

        List<Setting> settings = new ArrayList<>();
        for (NBTTagCompound settingCompound : NBTUtils.readCompoundArray(from.getTagList("settings", Constants.NBT.TAG_COMPOUND))) {
            settings.add(SettingsEncoder.readNBT(settingCompound));
        }

        return new Program(sourceScript, settings);
    }

    public static void writeProgramNBT(NBTTagCompound to, Program program) {

        writeSourceScriptNBT(NBTUtils.addNewCompoundTag(to, "sourceScript"), program.getSourceScript());

        NBTTagCompound[] settingCompounds = new NBTTagCompound[program.getSettings().size()];
        for (int i = 0; i < settingCompounds.length; i++) {
            NBTTagCompound settingCompound = new NBTTagCompound();
            SettingsEncoder.writeNBT(settingCompound, program.getSettings().get(i));
            settingCompounds[i] = settingCompound;
        }
        to.setTag("settings", NBTUtils.createCompoundArray(settingCompounds));
    }

    private ScriptsEncoder() {

    }

}
