
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import java.util.ArrayList;
import java.util.List;
import noppes.npcs.DataAI;
import noppes.npcs.util.ValueUtil;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptAnimation;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptMovementBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptMovementPathBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptRetaliationBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptRotationBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptShelteringBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptTacticalBehavior;
import de.unratedfilms.scriptspace.common.util.Utils;

public class CNPC_ScriptDataAi {

    public final CNPC_ScriptEntityCustomNpc npc;
    public final DataAI                     ai;

    public CNPC_ScriptDataAi(CNPC_ScriptEntityCustomNpc npc) {

        this.npc = npc;
        ai = npc.entityNpc.ai;
    }

    public String getAnimation() {

        return CNPC_ScriptAnimation.fromNative(ai.animationType);
    }

    public void setAnimation(String animation) {

        ai.animationType = CNPC_ScriptAnimation.toNative(animation);
    }

    /**
     * @return The NPC's current animation.
     *         For example, when the NPC is set to LYING, it wont be lying while walking so it will be NORMAL.
     */
    public String getCurrentAnimation() {

        return CNPC_ScriptAnimation.fromNative(npc.entityNpc.currentAnimation);
    }

    /**
     * @return Whether or not the NPC will try to return to his home position.
     */
    public boolean getReturnsToHome() {

        return ai.returnToStart;
    }

    /**
     * @param returnsToHome Whether or not the NPC will try to return to his home position.
     */
    public void setReturnsToHome(boolean returnsToHome) {

        ai.returnToStart = returnsToHome;
    }

    public String getRetaliationBehavior() {

        return CNPC_ScriptRetaliationBehavior.fromNative(ai.onAttack);
    }

    public void setRetaliationBehavior(String retaliationBehavior) {

        ai.onAttack = CNPC_ScriptRetaliationBehavior.toNative(retaliationBehavior);
        npc.entityNpc.updateAI = true;
    }

    public String getMovementBehavior() {

        return CNPC_ScriptMovementBehavior.fromNative(ai.movingType);
    }

    public void setMovementBehavior(String movementBehavior) {

        ai.movingType = CNPC_ScriptMovementBehavior.toNative(movementBehavior);
        npc.entityNpc.updateAI = true;
    }

    public String getRotationBehavior() {

        return CNPC_ScriptRotationBehavior.fromNative(ai.standingType);
    }

    public void setRotationBehavior(String rotationBehavior) {

        ai.standingType = CNPC_ScriptRotationBehavior.toNative(rotationBehavior);
        npc.entityNpc.updateAI = true;
    }

    public float getRotationYaw() {

        return npc.getRotationYaw();
    }

    public void setRotationYaw(float rotationYaw) {

        npc.setRotationYaw(rotationYaw);
    }

    /**
     * @return Wandering ranges are allowed to range from 1 to 50.
     */
    public int getWanderingRange() {

        return ai.walkingRange;
    }

    /**
     * @param range Wandering ranges are allowed to range from 1 to 50.
     */
    public void setWanderingRange(int range) {

        ai.walkingRange = ValueUtil.CorrectInt(range, 1, 50);
    }

    public boolean getInteractWithNpcs() {

        return ai.npcInteracting;
    }

    public void setInteractWithNpcs(boolean interactWithNpcs) {

        ai.npcInteracting = interactWithNpcs;
    }

    public boolean getStopOnInteract() {

        return ai.stopAndInteract;
    }

    public void setStopOnInteract(boolean stopOnInteract) {

        ai.stopAndInteract = stopOnInteract;
    }

    /**
     * @return Walking speeds are allowed to range from 1 to 10.
     */
    public int getWalkingSpeed() {

        return ai.getWalkingSpeed();
    }

    /**
     * @param walkingSpeed Walking speeds are allowed to range from 1 to 10.
     */
    public void setWalkingSpeed(int walkingSpeed) {

        ai.setWalkingSpeed(ValueUtil.CorrectInt(walkingSpeed, 0, 10));
    }

    public ScriptVec3[] getMovementPath() {

        List<int[]> nativePath = ai.getMovingPath();
        ScriptVec3[] path = new ScriptVec3[nativePath.size()];

        for (int waypointIndex = 0; waypointIndex < path.length; waypointIndex++) {
            int[] nativeWaypoint = nativePath.get(waypointIndex);
            path[waypointIndex] = new ScriptVec3(nativeWaypoint[0], nativeWaypoint[1], nativeWaypoint[2]);
        }

        return path;
    }

    public void setMovementPath(ScriptVec3[] path) {

        List<int[]> nativePath = new ArrayList<>();

        for (ScriptVec3 waypoint : path) {
            nativePath.add(new int[] { Utils.floor(waypoint.x), Utils.floor(waypoint.y), Utils.floor(waypoint.z) });
        }

        ai.setMovingPath(nativePath);
    }

    public String getMovementPathBehavior() {

        return CNPC_ScriptMovementPathBehavior.fromNative(ai.movingPattern);
    }

    public void setMovementPathBehavior(String movementPathBehavior) {

        ai.movingPattern = CNPC_ScriptMovementPathBehavior.toNative(movementPathBehavior);
    }

    public boolean getPauseAtMovementPathWaypoints() {

        return ai.movingPause;
    }

    public void setPauseAtMovementPathWaypoints(boolean pauseAtMovementPathWaypoints) {

        ai.movingPause = pauseAtMovementPathWaypoints;
    }

    public int getDoorInteract() {

        return ai.doorInteract;
    }

    public void setDoorInteract(int type) {

        ai.doorInteract = type;
        npc.entityNpc.updateAI = true;
    }

    public boolean getCanSwim() {

        return ai.canSwim;
    }

    public void setCanSwim(boolean canSwim) {

        ai.canSwim = canSwim;
    }

    public String getShelteringBehavior() {

        return CNPC_ScriptShelteringBehavior.fromNative(ai.findShelter);
    }

    public void setShelteringBehavior(String shelteringBehavior) {

        ai.findShelter = CNPC_ScriptShelteringBehavior.toNative(shelteringBehavior);
        npc.entityNpc.updateAI = true;
    }

    /**
     * @return Whether the NPC requires direct line of sight to attack.
     */
    public boolean getAttackRequiresLineOfSigth() {

        return ai.directLOS;
    }

    /**
     * @param attackRequiresLineOfSigth Whether the NPC requires direct line of sight to attack.
     */
    public void setAttackRequiresLineOfSigth(boolean attackRequiresLineOfSigth) {

        ai.directLOS = attackRequiresLineOfSigth;
        npc.entityNpc.updateAI = true;
    }

    public boolean getLeapAtTarget() {

        return ai.canLeap;
    }

    public void setLeapAtTarget(boolean leapAtTarget) {

        ai.canLeap = leapAtTarget;
        npc.entityNpc.updateAI = true;
    }

    public String getTacticalBehavior() {

        return CNPC_ScriptTacticalBehavior.fromNative(ai.tacticalVariant);
    }

    public void setTacticalBehavior(String tacticalBehavior) {

        ai.tacticalVariant = CNPC_ScriptTacticalBehavior.toNative(tacticalBehavior);
        npc.entityNpc.updateAI = true;
    }

    public int getTacticalRange() {

        return ai.tacticalRadius;
    }

    public void setTacticalRange(int tacticalRange) {

        ai.tacticalRadius = tacticalRange;
    }

}
