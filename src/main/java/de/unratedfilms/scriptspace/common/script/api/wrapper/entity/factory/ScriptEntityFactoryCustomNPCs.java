
package de.unratedfilms.scriptspace.common.script.api.wrapper.entity.factory;

import net.minecraft.entity.Entity;
import noppes.npcs.entity.EntityNPCInterface;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.CNPC_ScriptEntityCustomNpc;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntity;

class ScriptEntityFactoryCustomNPCs {

    public static ScriptEntity createFromNative(Entity entity) {

        if (entity instanceof EntityNPCInterface) {
            return new CNPC_ScriptEntityCustomNpc((EntityNPCInterface) entity);
        }

        return null;
    }

    private ScriptEntityFactoryCustomNPCs() {

    }

}
