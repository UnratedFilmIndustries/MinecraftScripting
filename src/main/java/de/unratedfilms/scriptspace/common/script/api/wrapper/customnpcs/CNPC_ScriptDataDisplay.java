
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptBossbarType;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptModelPart;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptNameVisibility;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptVisibility;
import noppes.npcs.entity.data.DataDisplay;

public class CNPC_ScriptDataDisplay {

    public final CNPC_ScriptEntityCustomNpc npc;
    public final DataDisplay                display;

    public CNPC_ScriptDataDisplay(CNPC_ScriptEntityCustomNpc npc) {

        this.npc = npc;
        display = npc.entityNpc.display;
    }

    public String getName() {

        return display.getName();
    }

    public void setName(String name) {

        display.setName(name);
    }

    public String getNameVisibility() {

        return CNPC_ScriptNameVisibility.fromNative(display.getShowName());
    }

    public void setNameVisibility(String nameVisibility) {

        display.setShowName(CNPC_ScriptNameVisibility.toNative(nameVisibility));
    }

    public String getTitle() {

        return display.getTitle();
    }

    public void setTitle(String title) {

        display.setTitle(title);
    }

    public String getSkinUrl() {

        return display.getSkinUrl();
    }

    public void setSkinUrl(String url) {

        display.setSkinUrl(url);
    }

    public String getSkinPlayer() {

        return display.getSkinPlayer();
    }

    public void setSkinPlayer(String name) {

        display.setSkinPlayer(name);
    }

    public String getSkinTexture() {

        return display.getSkinTexture();
    }

    public void setSkinTexture(String texture) {

        display.setSkinTexture(texture);
    }

    /**
     * @return The color format is hexadecimal, i.e. 0xFF0000 stands for red.
     */
    public int getSkinTint() {

        return display.getTint();
    }

    /**
     * @param tint The color format is hexadecimal, i.e. 0xFF0000 stands for red.
     */
    public void setSkinTint(int tint) {

        display.setTint(tint);
    }

    public String getOverlayTexture() {

        return display.getOverlayTexture();
    }

    public void setOverlayTexture(String texture) {

        display.setOverlayTexture(texture);
    }

    public String getCapeTexture() {

        return display.getCapeTexture();
    }

    public void setCapeTexture(String texture) {

        display.setCapeTexture(texture);
    }

    public boolean getHasLivingAnimation() {

        return display.getHasLivingAnimation();
    }

    public void setHashLivingAnimation(boolean enabled) {

        display.setHashLivingAnimation(enabled);
    }

    public String getVisibility() {

        return CNPC_ScriptVisibility.fromNative(display.getVisible());
    }

    public void setVisibility(String visibility) {

        display.setVisible(CNPC_ScriptVisibility.toNative(visibility));
    }

    public String getBossbarType() {

        return CNPC_ScriptBossbarType.fromNative(display.getBossbar());
    }

    public void setBossbarType(String bossbarType) {

        display.setBossbar(CNPC_ScriptBossbarType.toNative(bossbarType));
    }

    /**
     * @return Size values range from 1 to 30.
     */
    public int getSize() {

        return display.getSize();
    }

    /**
     * @param size Size values are allowed to range from 1 to 30.
     */
    public void setSize(int size) {

        display.setSize(size);
    }

    public float[] getModelScale(String part) {

        return display.getModelScale(CNPC_ScriptModelPart.toNative(part));
    }

    public void setModelScale(String part, float[] scale) {

        setModelScale(part, scale[0], scale[1], scale[2]);
    }

    public void setModelScale(String part, float x, float y, float z) {

        display.setModelScale(CNPC_ScriptModelPart.toNative(part), x, y, z);
    }

}
