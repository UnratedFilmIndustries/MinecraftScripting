
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.Line;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.util.ValueUtil;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityLivingBase;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityPlayer;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptItemStack;
import de.unratedfilms.scriptspace.common.util.Utils;

/**
 * Most of this code has just been copied from Noppes' CustomNPCs API and its implementation.
 * You can find the API here: https://github.com/Noppes/CustomNPCsAPI
 */
public class CNPC_ScriptEntityCustomNpc extends ScriptEntityLivingBase {

    public final EntityNPCInterface entityNpc;

    public CNPC_ScriptEntityCustomNpc(EntityNPCInterface entityNpc) {

        super(entityNpc);

        this.entityNpc = entityNpc;
    }

    protected void updateClient() {

        entityNpc.updateClient = true;
        entityNpc.script.clientNeedsUpdate = true;
    }

    public CNPC_ScriptDataDisplay getDisplay() {

        return new CNPC_ScriptDataDisplay(this);
    }

    public CNPC_ScriptDataInventory getInventory() {

        return new CNPC_ScriptDataInventory(this);
    }

    public CNPC_ScriptDataStats getStats() {

        return new CNPC_ScriptDataStats(this);
    }

    public CNPC_ScriptDataAi getAi() {

        return new CNPC_ScriptDataAi(this);
    }

    public CNPC_ScriptFaction getFaction() {

        return new CNPC_ScriptFaction(entityNpc.getFaction());
    }

    public void setFaction(int id) {

        entityNpc.setFaction(id);
    }

    public ScriptVec3 getHome() {

        int[] startPos = entityNpc.ai.startPos;
        return new ScriptVec3(startPos[0], startPos[1], startPos[2]);
    }

    public void setHome(ScriptVec3 location) {

        entityNpc.ai.startPos = new int[] { Utils.floor(location.x), Utils.floor(location.y), Utils.floor(location.z) };
    }

    public ScriptVec3 getOffset() {

        return new ScriptVec3(entityNpc.ai.bodyOffsetX, entityNpc.ai.bodyOffsetY, entityNpc.ai.bodyOffsetZ);
    }

    public void setOffset(ScriptVec3 offset) {

        entityNpc.ai.bodyOffsetX = ValueUtil.correctFloat((float) offset.x, 0.0F, 9.0F);
        entityNpc.ai.bodyOffsetY = ValueUtil.correctFloat((float) offset.y, 0.0F, 9.0F);
        entityNpc.ai.bodyOffsetZ = ValueUtil.correctFloat((float) offset.z, 0.0F, 9.0F);
    }

    @Override
    public void setRotationYaw(float rotationYaw) {

        entityNpc.ai.orientation = (int) rotationYaw;
        super.setRotationYaw(rotationYaw);
    }

    /**
     * Basically completely resets the NPC. This will also call the init script.
     */
    public void reset() {

        entityNpc.reset();
    }

    public void say(String message) {

        entityNpc.saySurrounding(new Line(message));
    }

    public void say(ScriptEntityPlayer player, String message) {

        if (player == null || message == null || message.isEmpty()) {
            return;
        }
        entityNpc.say(player.entityPlayer, new Line(message));
    }

    @Override
    public void setDead() {

        entityNpc.delete();
        NoppesUtilServer.deleteNpc(entityNpc, null);
    }

    /**
     * @param target The entity the NPC should shoot at.
     * @param stack The item stack you the NPC to shoot.
     * @param accuracy The accuracy of the shot (0 - 100).
     */
    public void shootItem(ScriptEntityLivingBase target, ScriptItemStack stack, int accuracy) {

        if (stack == null) {
            return;
        }
        accuracy = ValueUtil.CorrectInt(accuracy, 0, 100);
        entityNpc.shoot(target.entityLivingBase, accuracy, stack.stack, false);
    }

    /**
     * If the player can't carry the item, it will fall on the ground (unless the player is in creative mode).
     *
     * @param player The player who should get the item.
     * @param stack The item stack the NPC should give to the player.
     */
    public void giveItem(ScriptEntityPlayer player, ScriptItemStack stack) {

        entityNpc.givePlayerItem(player.entityPlayer, stack.stack);
    }

    /**
     * Lets the NPC execute the given command.
     * On servers, the enable-command-block option in the server.properties needs to be set to true.
     *
     * @param command The command the NPC should run.
     */
    public void executeCommand(String command) {

        NoppesUtilServer.runCommand(entityNpc, entityNpc.getCommandSenderName(), command, null);
    }

    public long getAge() {

        return entityNpc.totalTicksAlive;
    }

}
