
package de.unratedfilms.scriptspace.common.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class NBTUtils {

    public static NBTTagCompound addNewCompoundTag(NBTTagCompound parent, String name) {

        NBTTagCompound newTag = new NBTTagCompound();
        parent.setTag(name, newTag);
        return newTag;
    }

    public static String[] readStringArray(NBTTagList from) {

        String[] arr = new String[from.tagCount()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = from.getStringTagAt(i);
        }
        return arr;
    }

    public static NBTTagList createStringArray(String[] arr) {

        NBTTagList to = new NBTTagList();
        for (String s : arr) {
            to.appendTag(new NBTTagString(s));
        }
        return to;
    }

    public static NBTTagCompound[] readCompoundArray(NBTTagList from) {

        NBTTagCompound[] arr = new NBTTagCompound[from.tagCount()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = from.getCompoundTagAt(i);
        }
        return arr;
    }

    public static NBTTagList createCompoundArray(NBTTagCompound[] arr) {

        NBTTagList to = new NBTTagList();
        for (NBTTagCompound c : arr) {
            to.appendTag(c);
        }
        return to;
    }

    public static BlockPos /* subclass of Vec3i, but more commonly used */ readVec3i(NBTTagCompound from) {

        return new BlockPos(from.getInteger("x"), from.getInteger("y"), from.getInteger("z"));
    }

    public static void writeVec3i(NBTTagCompound to, Vec3i vec3i) {

        writeVec3i(to, vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public static void writeVec3i(NBTTagCompound to, int x, int y, int z) {

        to.setInteger("x", x);
        to.setInteger("y", y);
        to.setInteger("z", z);
    }

    private NBTUtils() {

    }

}
