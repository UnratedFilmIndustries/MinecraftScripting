
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.passive.EntitySheep;

public class ScriptEntitySheep extends ScriptEntityAgeable {

    public final EntitySheep entitySheep;

    public ScriptEntitySheep(EntitySheep entitySheep) {

        super(entitySheep);

        this.entitySheep = entitySheep;
    }

    public int getFleeceColor() {

        return entitySheep.getFleeceColor();
    }

    public void setFleeceColor(int color) {

        entitySheep.setFleeceColor(color);
    }

    public boolean getSheared() {

        return entitySheep.getSheared();
    }

    public void setSheared(boolean sheared) {

        entitySheep.setSheared(sheared);
    }

}
