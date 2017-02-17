
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import com.mojang.authlib.GameProfile;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptBossbarType;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptNameVisibility;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptVisibility;
import noppes.npcs.DataDisplay;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.util.ValueUtil;

public class CNPC_ScriptDataDisplay {

    public final CNPC_ScriptEntityCustomNpc npc;
    public final DataDisplay                display;

    public CNPC_ScriptDataDisplay(CNPC_ScriptEntityCustomNpc npc) {

        this.npc = npc;
        display = npc.entityNpc.display;
    }

    public String getName() {

        return display.name;
    }

    public void setName(String name) {

        display.name = name;
        npc.updateClient();
    }

    public String getNameVisibility() {

        return CNPC_ScriptNameVisibility.fromNative(display.showName);
    }

    public void setNameVisibility(String nameVisibility) {

        display.showName = CNPC_ScriptNameVisibility.toNative(nameVisibility);
        npc.updateClient();
    }

    public String getTitle() {

        return display.title;
    }

    public void setTitle(String title) {

        display.title = title;
        npc.updateClient();
    }

    public String getSkinUrl() {

        return display.url;
    }

    public void setSkinUrl(String url) {

        display.url = url;
        if (url.isEmpty()) {
            display.skinType = 0;
        } else {
            display.skinType = 2;
        }
        npc.updateClient();
    }

    public String getSkinPlayer() {

        return display.playerProfile == null ? "" : display.playerProfile.getName();
    }

    public void setSkinPlayer(String name) {

        if (name == null || name.isEmpty()) {
            display.playerProfile = null;
            display.skinType = 0;
        } else {
            display.playerProfile = new GameProfile(null, name);
            display.skinType = 1;
        }
        npc.updateClient();
    }

    public String getSkinTexture() {

        return display.texture;
    }

    public void setSkinTexture(String texture) {

        display.texture = texture;
        npc.entityNpc.textureLocation = null;
        display.skinType = 0;
        npc.updateClient();
    }

    public String getOverlayTexture() {

        return display.glowTexture;
    }

    public void setOverlayTexture(String texture) {

        display.glowTexture = texture;
        npc.entityNpc.textureGlowLocation = null;
        npc.updateClient();
    }

    public String getCapeTexture() {

        return display.cloakTexture;
    }

    public void setCapeTexture(String texture) {

        display.cloakTexture = texture;
        npc.entityNpc.textureCloakLocation = null;
        npc.updateClient();
    }

    public boolean getHasLivingAnimation() {

        return !display.disableLivingAnimation;
    }

    public void setHashLivingAnimation(boolean enabled) {

        display.disableLivingAnimation = !enabled;
        npc.updateClient();
    }

    public String getVisibility() {

        return CNPC_ScriptVisibility.fromNative(display.visible);
    }

    public void setVisibility(String visibility) {

        display.visible = CNPC_ScriptVisibility.toNative(visibility);
        npc.updateClient();
    }

    public String getBossbarType() {

        return CNPC_ScriptBossbarType.fromNative(display.showBossBar);
    }

    public void setBossbar(String bossbarType) {

        display.showBossBar = CNPC_ScriptBossbarType.toNative(bossbarType);
        npc.updateClient();
    }

    /**
     * @return Size values range from 1 to 30.
     */
    public int getSize() {

        return display.modelSize;
    }

    /**
     * @param size Size values are allowed to range from 1 to 30.
     */
    public void setSize(int size) {

        display.modelSize = ValueUtil.CorrectInt(size, 1, 30);
        npc.updateClient();
    }

    public void setHeadScale(float x, float y, float z) {

        ModelData modelData = ((EntityCustomNpc) npc.entityNpc).modelData;

        modelData.head.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
        modelData.head.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
        modelData.head.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);

        npc.updateClient();
    }

    public void setBodyScale(float x, float y, float z) {

        ModelData modelData = ((EntityCustomNpc) npc.entityNpc).modelData;

        modelData.body.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
        modelData.body.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
        modelData.body.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);

        npc.updateClient();
    }

    public void setArmsScale(float x, float y, float z) {

        ModelData modelData = ((EntityCustomNpc) npc.entityNpc).modelData;

        modelData.arms.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
        modelData.arms.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
        modelData.arms.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);

        npc.updateClient();
    }

    public void setLegsScale(float x, float y, float z) {

        ModelData modelData = ((EntityCustomNpc) npc.entityNpc).modelData;

        modelData.legs.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
        modelData.legs.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
        modelData.legs.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);

        npc.updateClient();
    }

}
