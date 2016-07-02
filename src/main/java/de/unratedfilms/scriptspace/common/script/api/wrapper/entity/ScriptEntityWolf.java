
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.passive.EntityWolf;

public class ScriptEntityWolf extends ScriptEntityTameable {

    public final EntityWolf entityWolf;

    public ScriptEntityWolf(EntityWolf entityWolf) {

        super(entityWolf);

        this.entityWolf = entityWolf;
    }

    public boolean isAngry() {

        return entityWolf.isAngry();
    }

    public void setAngry(boolean angry) {

        entityWolf.setAngry(angry);
    }

    public int getCollarColor() {

        return entityWolf.getCollarColor();
    }

    public void setCollarColor(int color) {

        entityWolf.setCollarColor(color);
    }

}
