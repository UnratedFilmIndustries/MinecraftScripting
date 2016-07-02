
package de.unratedfilms.scriptspace.common.selection;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.Validate;
import de.unratedfilms.scriptspace.common.util.Utils;
import de.unratedfilms.scriptspace.common.util.Vec3i;

public class SelectionCuboid extends Selection {

    public final Vec3i  corner1;
    public final Vec3i  corner2;

    private final Vec3i minCorner;
    private final Vec3i maxCorner;

    public SelectionCuboid(int dimensionId, Vec3i corner1, Vec3i corner2) {

        super(dimensionId);

        Validate.notNull(corner1);
        Validate.notNull(corner2);

        this.corner1 = corner1;
        this.corner2 = corner2;

        minCorner = new Vec3i(min(corner1.x, corner2.x), min(corner1.y, corner2.y), min(corner1.z, corner2.z));
        maxCorner = new Vec3i(max(corner1.x, corner2.x), max(corner1.y, corner2.y), max(corner1.z, corner2.z));
    }

    @Override
    public Vec3 getCenter() {

        return Vec3.createVectorHelper(avg(minCorner.x, maxCorner.x + 1), avg(minCorner.y, maxCorner.y + 1), avg(minCorner.z, maxCorner.z + 1));
    }

    @Override
    public AxisAlignedBB getAABB() {

        return AxisAlignedBB.getBoundingBox(minCorner.x, minCorner.y, minCorner.z, maxCorner.x + 1, maxCorner.y + 1, maxCorner.z + 1);
    }

    private double avg(double value1, double value2) {

        return (value1 + value2) / 2;
    }

    @Override
    public boolean intersects(double x, double y, double z) {

        return getAABB().isVecInside(Vec3.createVectorHelper(x, y, z));
    }

    @Override
    public List<Vec3> getLocations(double distance) {

        List<Vec3> locations = new ArrayList<>();

        for (double x = minCorner.x; x < maxCorner.x + 1 - Utils.DOUBLE_EPSILON; x += distance) {
            for (double y = minCorner.y; y < maxCorner.y + 1 - Utils.DOUBLE_EPSILON; y += distance) {
                for (double z = minCorner.z; z < maxCorner.z + 1 - Utils.DOUBLE_EPSILON; z += distance) {
                    locations.add(Vec3.createVectorHelper(x, y, z));
                }
            }
        }

        return locations;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List<Entity> getEntities() {

        return getWorld().getEntitiesWithinAABBExcludingEntity(null, getAABB(), new IEntitySelector() {

            @Override
            public boolean isEntityApplicable(Entity entity) {

                return ! (entity instanceof EntityPlayer);
            }

        });
    }

    @Override
    public List<TileEntity> getTileEntities() {

        return Utils.getTileEntitiesInAABB(getWorld(), getAABB());
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (corner1 == null ? 0 : corner1.hashCode());
        result = prime * result + (corner2 == null ? 0 : corner2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SelectionCuboid other = (SelectionCuboid) obj;
        if (corner1 == null) {
            if (other.corner1 != null) {
                return false;
            }
        } else if (!corner1.equals(other.corner1)) {
            return false;
        }
        if (corner2 == null) {
            if (other.corner2 != null) {
                return false;
            }
        } else if (!corner2.equals(other.corner2)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "cuboid from " + corner1 + " to " + corner2;
    }

}
