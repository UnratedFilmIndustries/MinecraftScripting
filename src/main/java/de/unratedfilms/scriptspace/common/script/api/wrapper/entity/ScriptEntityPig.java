
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.passive.EntityPig;

public class ScriptEntityPig extends ScriptEntityAgeable {

    public final EntityPig entityPig;

    public ScriptEntityPig(EntityPig entityPig) {

        super(entityPig);

        this.entityPig = entityPig;
    }

    public void setSaddled(boolean saddle) {

        entityPig.setSaddled(saddle);
    }

    public boolean getSaddled() {

        return entityPig.getSaddled();
    }

}
