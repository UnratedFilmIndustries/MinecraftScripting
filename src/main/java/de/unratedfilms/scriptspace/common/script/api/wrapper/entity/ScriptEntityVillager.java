
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class ScriptEntityVillager extends ScriptEntityAgeable {

    public final EntityVillager entityVillager;

    public ScriptEntityVillager(EntityVillager entityVillager) {

        super(entityVillager);

        this.entityVillager = entityVillager;
    }

    public void setProfession(String prof) {

        entityVillager.setProfession(VillagerRegistry.instance().getRegistry().getValue(new ResourceLocation(prof)));
    }

    public String getProfession() {

        return entityVillager.getProfessionForge().getRegistryName().toString();
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
