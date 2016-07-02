
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldSettings.GameType;

public class ScriptEntityPlayer extends ScriptEntityLivingBase {

    public final EntityPlayer entityPlayer;

    public ScriptEntityPlayer(EntityPlayer entityPlayer) {

        super(entityPlayer);

        this.entityPlayer = entityPlayer;
    }

    public String getUsername() {

        return entityPlayer.getCommandSenderName();
    }

    public void addXPLevels(int levels) {

        entityPlayer.addExperienceLevel(levels);
    }

    public void addXP(int xp) {

        entityPlayer.addExperience(xp);
    }

    public int getXPLevel() {

        return entityPlayer.experienceLevel;
    }

    public void setXPLevel(int level) {

        entityPlayer.experienceLevel = level;
    }

    public void sendPlayerAbilities() {

        entityPlayer.sendPlayerAbilities();
    }

    public void addChatMessage(String str) {

        entityPlayer.addChatComponentMessage(new ChatComponentText(str));
    }

    public void setGameType(int id) {

        entityPlayer.setGameType(GameType.getByID(id));
    }

    @Override
    public void setDead() {

    }

    // Case in-sensitive
    public void setGameType(String name) {

        entityPlayer.setGameType(GameType.getByName(name.toLowerCase()));
    }

}
