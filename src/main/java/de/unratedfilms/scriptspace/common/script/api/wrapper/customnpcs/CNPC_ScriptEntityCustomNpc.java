
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.util.ValueUtil;

public class CNPC_ScriptEntityCustomNpc extends CNPC_ScriptEntityCustomNpcBase {

    public final EntityCustomNpc entityNpc;

    public CNPC_ScriptEntityCustomNpc(EntityCustomNpc entityNpc) {

        super(entityNpc);

        this.entityNpc = entityNpc;
    }

    public void setHeadScale(float x, float y, float z) {

        entityNpc.modelData.head.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
        entityNpc.modelData.head.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
        entityNpc.modelData.head.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);

        entityNpc.script.clientNeedsUpdate = true;
    }

    public void setBodyScale(float x, float y, float z) {

        entityNpc.modelData.body.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
        entityNpc.modelData.body.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
        entityNpc.modelData.body.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);

        entityNpc.script.clientNeedsUpdate = true;
    }

    public void setArmsScale(float x, float y, float z) {

        entityNpc.modelData.arms.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
        entityNpc.modelData.arms.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
        entityNpc.modelData.arms.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);

        entityNpc.script.clientNeedsUpdate = true;
    }

    public void setLegsScale(float x, float y, float z) {

        entityNpc.modelData.legs.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
        entityNpc.modelData.legs.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
        entityNpc.modelData.legs.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);

        entityNpc.script.clientNeedsUpdate = true;
    }

}
