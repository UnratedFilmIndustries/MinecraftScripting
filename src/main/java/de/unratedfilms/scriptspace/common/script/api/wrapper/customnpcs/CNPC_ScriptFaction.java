
package de.unratedfilms.scriptspace.common.script.api.wrapper.customnpcs;

import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntityPlayer;
import noppes.npcs.controllers.data.Faction;

public class CNPC_ScriptFaction {

    public final Faction faction;

    public CNPC_ScriptFaction(Faction faction) {

        this.faction = faction;
    }

    public int getId() {

        return faction.getId();
    }

    public String getName() {

        return faction.getName();
    }

    public int getDefaultPoints() {

        return faction.getDefaultPoints();
    }

    public int getColor() {

        return faction.getColor();
    }

    public boolean isFriendlyToPlayer(ScriptEntityPlayer player) {

        return faction.isFriendlyToPlayer(player.entityPlayer);
    }

    public boolean isNeutralToPlayer(ScriptEntityPlayer player) {

        return faction.isNeutralToPlayer(player.entityPlayer);
    }

    public boolean isAggressiveToPlayer(ScriptEntityPlayer player) {

        return faction.isAggressiveToPlayer(player.entityPlayer);
    }

    public boolean isAggressiveToNpc(CNPC_ScriptEntityCustomNpc npc) {

        return faction.isAggressiveToNpc(npc.entityNpc);
    }

}
