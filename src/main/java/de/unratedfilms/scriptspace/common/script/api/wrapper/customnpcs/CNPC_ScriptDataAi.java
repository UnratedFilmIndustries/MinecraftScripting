
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import java.util.ArrayList;
import java.util.List;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptAnimation;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptMovementBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptMovementPathBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptRetaliationBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptRotationBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptShelteringBehavior;
import de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs.consts.CNPC_ScriptTacticalBehavior;
import de.unratedfilms.scriptspace.common.util.Utils;
import noppes.npcs.entity.data.DataAI;

public class CNPC_ScriptDataAi {

    public final CNPC_ScriptEntityCustomNpc npc;
    public final DataAI                     ai;

    public CNPC_ScriptDataAi(CNPC_ScriptEntityCustomNpc npc) {

        this.npc = npc;
        ai = npc.entityNpc.ais;
    }

    public String getAnimation() {

        return CNPC_ScriptAnimation.fromNative(ai.getAnimation());
    }

    public void setAnimation(String animation) {

        ai.setAnimation(CNPC_ScriptAnimation.toNative(animation));
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

        return ai.getReturnsHome();
    }

    /**
     * @param returnsToHome Whether or not the NPC will try to return to his home position.
     */
    public void setReturnsToHome(boolean returnsToHome) {

        ai.setReturnsHome(returnsToHome);
    }

    public String getRetaliationBehavior() {

        return CNPC_ScriptRetaliationBehavior.fromNative(ai.getRetaliateType());
    }

    public void setRetaliationBehavior(String retaliationBehavior) {

        ai.setRetaliateType(CNPC_ScriptRetaliationBehavior.toNative(retaliationBehavior));
    }

    public String getMovementBehavior() {

        return CNPC_ScriptMovementBehavior.fromNative(ai.getMovingType());
    }

    public void setMovementBehavior(String movementBehavior) {

        ai.setMovingType(CNPC_ScriptMovementBehavior.toNative(movementBehavior));
    }

    public String getRotationBehavior() {

        return CNPC_ScriptRotationBehavior.fromNative(ai.getStandingType());
    }

    public void setRotationBehavior(String rotationBehavior) {

        ai.setStandingType(CNPC_ScriptRotationBehavior.toNative(rotationBehavior));
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

        return ai.getWanderingRange();
    }

    /**
     * @param range Wandering ranges are allowed to range from 1 to 50.
     */
    public void setWanderingRange(int range) {

        ai.setWanderingRange(range);
    }

    public boolean getInteractWithNpcs() {

        return ai.getInteractWithNPCs();
    }

    public void setInteractWithNpcs(boolean interactWithNpcs) {

        ai.setInteractWithNPCs(interactWithNpcs);
    }

    public boolean getStopOnInteract() {

        return ai.getStopOnInteract();
    }

    public void setStopOnInteract(boolean stopOnInteract) {

        ai.setStopOnInteract(stopOnInteract);
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

        ai.setWalkingSpeed(walkingSpeed);
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

        return ai.getDoorInteract();
    }

    public void setDoorInteract(int type) {

        ai.setDoorInteract(type);
    }

    public boolean getCanSwim() {

        return ai.getCanSwim();
    }

    public void setCanSwim(boolean canSwim) {

        ai.setCanSwim(canSwim);
    }

    public String getShelteringBehavior() {

        return CNPC_ScriptShelteringBehavior.fromNative(ai.getSheltersFrom());
    }

    public void setShelteringBehavior(String shelteringBehavior) {

        ai.setSheltersFrom(CNPC_ScriptShelteringBehavior.toNative(shelteringBehavior));
    }

    /**
     * @return Whether the NPC requires direct line of sight to attack.
     */
    public boolean getAttackRequiresLineOfSigth() {

        return ai.getAttackLOS();
    }

    /**
     * @param attackRequiresLineOfSigth Whether the NPC requires direct line of sight to attack.
     */
    public void setAttackRequiresLineOfSigth(boolean attackRequiresLineOfSigth) {

        ai.setAttackLOS(attackRequiresLineOfSigth);
    }

    public boolean getLeapAtTarget() {

        return ai.getLeapAtTarget();
    }

    public void setLeapAtTarget(boolean leapAtTarget) {

        ai.setLeapAtTarget(leapAtTarget);
    }

    public String getTacticalBehavior() {

        return CNPC_ScriptTacticalBehavior.fromNative(ai.getTacticalType());
    }

    public void setTacticalBehavior(String tacticalBehavior) {

        ai.setTacticalType(CNPC_ScriptTacticalBehavior.toNative(tacticalBehavior));
    }

    public int getTacticalRange() {

        return ai.getTacticalRange();
    }

    public void setTacticalRange(int tacticalRange) {

        ai.setTacticalRange(tacticalRange);
    }

}
