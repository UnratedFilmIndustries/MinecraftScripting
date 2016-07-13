
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
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

    public static String[] getAllEntityNames() {

        return getSortedRealEntities().keySet().toArray(new String[0]);
    }

    public static String[] getAllLivingEntityNames() {

        List<String> names = new ArrayList<>();

        for (Entry<String, Class<? extends Entity>> entry : getSortedRealEntities().entrySet()) {
            Class<? extends Entity> entityClass = entry.getValue();
            if (EntityLiving.class.isAssignableFrom(entityClass)) {
                names.add(entry.getKey());
            }
        }

        return names.toArray(new String[0]);
    }

    /*
     * Mod entities are registered twice. Once entity is called "NAME", while the other one is called "MOD.NAME".
     * Although both entities actually mean the same entity type, most applications only work with the "MOD.NAME" version.
     * Therefore, this method filters out all those virtual "NAME" mod entities. Note that vanilla entities are not affected.
     *
     * Moreover, all abstract entities are filtered out.
     * Finally, as a bonus, this method already sorts the entire map by entity name.
     */
    @SuppressWarnings ("unchecked")
    private static SortedMap<String, Class<? extends Entity>> getSortedRealEntities() {

        SortedMap<String, Class<? extends Entity>> result = new TreeMap<>();

        for (Entry<String, Class<? extends Entity>> entity : ((Map<String, Class<? extends Entity>>) EntityList.stringToClassMapping).entrySet()) {
            String entityName = entity.getKey();
            Class<? extends Entity> entityClass = entity.getValue();

            // Filter out abstract entities
            if (Modifier.isAbstract(entityClass.getModifiers())) {
                continue;
            }

            // Filter out only-"NAME" mod entities; note that EntityRegistration.getEntityName() returns the name without the "MOD." prefix!
            EntityRegistration modEntity = EntityRegistry.instance().lookupModSpawn(entityClass, false);
            if (modEntity != null && entityName.equals(modEntity.getEntityName())) {
                continue;
            }

            result.put(entityName, entityClass);
        }

        return result;
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
