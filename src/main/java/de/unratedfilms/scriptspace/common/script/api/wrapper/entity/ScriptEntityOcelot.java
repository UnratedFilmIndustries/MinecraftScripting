
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.passive.EntityOcelot;

public class ScriptEntityOcelot extends ScriptEntityTameable {

    public final EntityOcelot entityOcelot;

    public ScriptEntityOcelot(EntityOcelot entityOcelot) {

        super(entityOcelot);

        this.entityOcelot = entityOcelot;
    }

    public int getTameSkin() {

        return entityOcelot.getTameSkin();
    }

    public void setTameSkin(int skin) {

        entityOcelot.setTameSkin(skin);
    }
}
