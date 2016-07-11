
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.factory.ScriptEntityFactory;
import de.unratedfilms.scriptspace.common.script.api.wrapper.nbt.ScriptTagCompound;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptWorld;
import de.unratedfilms.scriptspace.common.util.Utils;

public class ScriptEntity {

    public static ScriptEntity createEntityByName(String name, ScriptWorld world) {

        Entity e = EntityList.createEntityByName(name, world.world);
        return e != null ? createFromNative(e) : null;
    }

    public static ScriptEntity createFromNative(Entity entity) {

        return ScriptEntityFactory.createFromNative(entity);
    }

    @SuppressWarnings ("unchecked")
    public static String[] getAllEntityNames() {

        return collectionToSortedArray(EntityList.stringToClassMapping.keySet());
    }

    @SuppressWarnings ("unchecked")
    public static String[] getAllLivingEntityNames() {

        List<String> names = new ArrayList<>();
        for (Entry<String, Class<?>> entry : ((Map<String, Class<?>>) EntityList.stringToClassMapping).entrySet()) {
            Class<?> clazz = entry.getValue();
            if (!Modifier.isAbstract(clazz.getModifiers()) && EntityLiving.class.isAssignableFrom(clazz)) {
                names.add(entry.getKey());
            }
        }
        return collectionToSortedArray(names);
    }

    private static String[] collectionToSortedArray(Collection<String> collection) {

        String[] array = collection.toArray(new String[collection.size()]);
        Arrays.sort(array);
        return array;
    }

    public final Entity entity;

    public ScriptEntity(Entity entity) {

        this.entity = entity;
    }

    public int getEntityID() {

        return entity.getEntityId();
    }

    public String getInternalName() {

        return EntityList.getEntityString(entity);
    }

    public String getEntityName() {

        return entity.getCommandSenderName();
    }

    public ScriptTagCompound writeToTag() {

        NBTTagCompound tag = new NBTTagCompound();
        entity.writeToNBT(tag);
        return new ScriptTagCompound(tag);
    }

    public void readFromTag(ScriptTagCompound tag) {

        entity.readFromNBT(tag.tag);

        // If the entity is part of the world, notify the clients of the changed NBT data
        if (isAlive()) {
            Utils.syncEntityNBTToClients(entity);
        }
    }

    public String getTranslatedEntityName() {

        return getEntityName();
    }

    public ScriptVec3 getLocation() {

        return new ScriptVec3(entity.posX, entity.posY, entity.posZ);
    }

    public double getX() {

        return entity.posX;
    }

    public double getY() {

        return entity.posY;
    }

    public double getZ() {

        return entity.posZ;
    }

    public void setLocation(ScriptVec3 vec) {

        setLocation(vec.x, vec.y, vec.z);
    }

    public void setLocation(double x, double y, double z) {

        entity.setPosition(x, y, z);
    }

    public float getRotationYaw() {

        return entity.rotationYaw;
    }

    public void setRotationYaw(float rotationYaw) {

        entity.rotationYaw = rotationYaw;
    }

    public void setRotationPitch(float rotationPitch) {

        entity.rotationPitch = rotationPitch;
    }

    public float getRotationPitch() {

        return entity.rotationPitch;
    }

    public ScriptVec3 getVelocity() {

        return new ScriptVec3(entity.motionX, entity.motionY, entity.motionZ);
    }

    public void setVelocity(ScriptVec3 vec) {

        setVelocity(vec.x, vec.y, vec.z);
    }

    public void setVelocity(double x, double y, double z) {

        entity.setVelocity(x, y, z);
    }

    public void mountEntity(ScriptEntity scriptEnt) {

        entity.mountEntity(scriptEnt == null ? null : scriptEnt.entity);
    }

    public ScriptDataWatcher getDataWatcher() {

        return new ScriptDataWatcher(entity.getDataWatcher());
    }

    public ScriptWorld getWorld() {

        return new ScriptWorld(entity.worldObj);
    }

    public float getFallDistance() {

        return entity.fallDistance;
    }

    public void setFallDistance(float dist) {

        entity.fallDistance = dist;
    }

    public boolean isAlive() {

        return entity.isEntityAlive();
    }

    public boolean isDead() {

        return !isAlive();
    }

    public void setDead() {

        entity.worldObj.removeEntity(entity);
    }

    public double getDistanceToEntity(ScriptEntity se) {

        return entity.getDistanceToEntity(se.entity);
    }

    public double getDistanceSqToEntity(ScriptEntity se) {

        return entity.getDistanceSqToEntity(se.entity);
    }

    @Override
    public String toString() {

        return "ScriptEntity: " + entity.toString();
    }

}
