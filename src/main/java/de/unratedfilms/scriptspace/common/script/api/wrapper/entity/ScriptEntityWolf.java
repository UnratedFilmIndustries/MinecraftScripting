
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.passive.EntityWolf;
import de.unratedfilms.scriptspace.common.script.api.wrapper.consts.ScriptDyeColor;

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

    public String getCollarColor() {

        return ScriptDyeColor.fromNative(entityWolf.getCollarColor());
    }

    public void setCollarColor(String color) {

        entityWolf.setCollarColor(ScriptDyeColor.toNative(color));
    }

}
