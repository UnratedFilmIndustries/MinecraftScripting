
package de.unratedfilms.scriptspace.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;

public class ReflectionHelper {

    private static final int                  ENTITY_LIVING_BASE__POTION_FIELD_INDEX = 51;
    private static final int                  TILE_ENTITY__CLASS_TO_NAME_INDEX       = 2;

    public static final Field                 ENTITY_LIVING_BASE__POTIONS_NEED_UPDATE;
    public static final Map<Class<?>, String> TILE_ENTITY__CLASS_TO_NAME;

    static {

        try {
            ENTITY_LIVING_BASE__POTIONS_NEED_UPDATE = EntityLivingBase.class.getDeclaredFields()[ENTITY_LIVING_BASE__POTION_FIELD_INDEX];
            ENTITY_LIVING_BASE__POTIONS_NEED_UPDATE.setAccessible(true);

            TILE_ENTITY__CLASS_TO_NAME = cpw.mods.fml.relauncher.ReflectionHelper.getPrivateValue(TileEntity.class, null, TILE_ENTITY__CLASS_TO_NAME_INDEX);
        }
        // Rethrow all exceptions because we can't recover from this error
        catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeDeclaredMethod(Class<?> c, Object obj, String name, Class<?>[] parameterTypes, Object... arguments) {

        try {
            Method method = c.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return method.invoke(obj, arguments);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // This should never happen
            throw new RuntimeException(e);
        }
    }

}
