
package de.unratedfilms.scriptspace.common.script.api.wrapper;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import de.unratedfilms.scriptspace.common.script.api.util.ScriptVec3;
import de.unratedfilms.scriptspace.common.script.api.wrapper.entity.ScriptEntity;
import de.unratedfilms.scriptspace.common.script.api.wrapper.tileentity.ScriptTileEntity;
import de.unratedfilms.scriptspace.common.script.api.wrapper.world.ScriptWorld;
import de.unratedfilms.scriptspace.common.selection.Selection;
import de.unratedfilms.scriptspace.common.util.Vec3i;

public class ScriptSelection {

    public final Selection selection;

    public ScriptSelection(Selection selection) {

        this.selection = selection;
    }

    /**
     * @see Selection#getWorld()
     */
    public ScriptWorld getWorld() {

        return new ScriptWorld(selection.getWorld());
    }

    /**
     * @see Selection#getCenter()
     */
    public ScriptVec3 getCenter() {

        return new ScriptVec3(selection.getCenter());
    }

    /**
     * @see Selection#getBlockCenter()
     */
    public ScriptVec3 getBlockCenter() {

        return new ScriptVec3(selection.getBlockCenter());
    }

    /**
     * @see Selection#getAABB()
     */
    public ScriptVec3 getMinCorner() {

        AxisAlignedBB aabb = selection.getAABB();
        return new ScriptVec3(aabb.minX, aabb.minY, aabb.minZ);
    }

    /**
     * @see Selection#getAABB()
     */
    public ScriptVec3 getMaxCorner() {

        AxisAlignedBB aabb = selection.getAABB();
        return new ScriptVec3(aabb.maxX, aabb.maxY, aabb.maxZ);
    }

    /**
     * @see Selection#intersects(double, double, double)
     */
    public boolean intersects(double x, double y, double z) {

        return selection.intersects(x, y, z);
    }

    /**
     * @see Selection#intersects(double, double, double)
     */
    public boolean intersects(ScriptVec3 location) {

        return intersects(location.x, location.y, location.z);
    }

    /**
     * @see Selection#getLocations(double)
     */
    public ScriptVec3[] getLocations(double distance) {

        List<ScriptVec3> locations = new ArrayList<>();

        for (Vec3 location : selection.getLocations(distance)) {
            locations.add(new ScriptVec3(location));
        }

        return locations.toArray(new ScriptVec3[locations.size()]);
    }

    /**
     * @see Selection#getBlockLocations()
     */
    public ScriptVec3[] getBlockLocations() {

        List<ScriptVec3> blockLocations = new ArrayList<>();

        for (Vec3i blockLocation : selection.getBlockLocations()) {
            blockLocations.add(new ScriptVec3(blockLocation));
        }

        return blockLocations.toArray(new ScriptVec3[blockLocations.size()]);
    }

    /**
     * @see Selection#getEntities()
     */
    public ScriptEntity[] getEntities() {

        List<ScriptEntity> entities = new ArrayList<>();

        for (Entity entity : selection.getEntities()) {
            entities.add(ScriptEntity.createFromNative(entity));
        }

        return entities.toArray(new ScriptEntity[entities.size()]);
    }

    /**
     * @see Selection#getTileEntities()
     */
    public ScriptTileEntity[] getTileEntities() {

        List<ScriptTileEntity> tileEntities = new ArrayList<>();

        for (TileEntity tileEntity : selection.getTileEntities()) {
            tileEntities.add(new ScriptTileEntity(tileEntity));
        }

        return tileEntities.toArray(new ScriptTileEntity[tileEntities.size()]);
    }

    @Override
    public String toString() {

        return selection.toString();
    }

}
