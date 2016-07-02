
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.passive.EntityTameable;

public class ScriptEntityTameable extends ScriptEntityAgeable {

    public final EntityTameable entityTameable;

    public ScriptEntityTameable(EntityTameable entityTameable) {

        super(entityTameable);

        this.entityTameable = entityTameable;
    }

    public boolean getTamed() {

        return entityTameable.isTamed();
    }

    public void setTamed(boolean tamed) {

        entityTameable.setTamed(tamed);
    }

    public boolean getSitting() {

        return entityTameable.isSitting();
    }

    public void setSitting(boolean sitting) {

        entityTameable.setSitting(sitting);
    }

    public String getOwnerUUID() {

        return entityTameable.func_152113_b();
    }

    public void setOwnerUUID(String uuid) {

        entityTameable.func_152115_b(uuid);
    }

}
