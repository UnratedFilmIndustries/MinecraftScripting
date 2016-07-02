
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.passive.EntityVillager;

public class ScriptEntityVillager extends ScriptEntityAgeable {

    public final EntityVillager entityVillager;

    public ScriptEntityVillager(EntityVillager entityVillager) {

        super(entityVillager);

        this.entityVillager = entityVillager;
    }

    public void setProfession(int prof) {

        entityVillager.setProfession(prof);
    }

    public int getProfession() {

        return entityVillager.getProfession();
    }

    public void setMating(boolean mating) {

        entityVillager.setMating(mating);
    }

    public boolean getMating() {

        return entityVillager.isMating();
    }

    public void setPlaying(boolean playing) {

        entityVillager.setPlaying(playing);
    }

    public boolean getPlaying() {

        return entityVillager.isPlaying();
    }

}
