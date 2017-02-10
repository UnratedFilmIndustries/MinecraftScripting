
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.passive.EntitySheep;
import de.unratedfilms.scriptspace.common.script.api.wrapper.consts.ScriptDyeColor;

public class ScriptEntitySheep extends ScriptEntityAgeable {

    public final EntitySheep entitySheep;

    public ScriptEntitySheep(EntitySheep entitySheep) {

        super(entitySheep);

        this.entitySheep = entitySheep;
    }

    public String getFleeceColor() {

        return ScriptDyeColor.fromNative(entitySheep.getFleeceColor());
    }

    public void setFleeceColor(String color) {

        entitySheep.setFleeceColor(ScriptDyeColor.toNative(color));
    }

    public boolean getSheared() {

        return entitySheep.getSheared();
    }

    public void setSheared(boolean sheared) {

        entitySheep.setSheared(sheared);
    }

}
