
package de.unratedfilms.scriptspace.common.selection;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import de.unratedfilms.scriptspace.common.util.Utils;

public class SelectionCuboid extends Selection {

    public final BlockPos  corner1;
    public final BlockPos  corner2;

    private final BlockPos minCorner;
    private final BlockPos maxCorner;

    public SelectionCuboid(int dimensionId, BlockPos corner1, BlockPos corner2) {

        super(dimensionId);

        Validate.notNull(corner1);
        Validate.notNull(corner2);

        this.corner1 = corner1;
        this.corner2 = corner2;

        minCorner = new BlockPos(min(corner1.getX(), corner2.getX()), min(corner1.getY(), corner2.getY()), min(corner1.getZ(), corner2.getZ()));
        maxCorner = new BlockPos(max(corner1.getX(), corner2.getX()), max(corner1.getY(), corner2.getY()), max(corner1.getZ(), corner2.getZ()));
    }

    @Override
    public Vec3d getCenter() {

        return new Vec3d(avg(minCorner.getX(), maxCorner.getX() + 1), avg(minCorner.getY(), maxCorner.getY() + 1), avg(minCorner.getZ(), maxCorner.getZ() + 1));
    }

    @Override
    public AxisAlignedBB getAABB() {

        return new AxisAlignedBB(minCorner.getX(), minCorner.getY(), minCorner.getZ(), maxCorner.getX() + 1, maxCorner.getY() + 1, maxCorner.getZ() + 1);
    }

    private double avg(double value1, double value2) {

        return (value1 + value2) / 2;
    }

    @Override
    public boolean intersects(double x, double y, double z) {

        return getAABB().isVecInside(new Vec3d(x, y, z));
    }

    @Override
    public List<Vec3d> getLocations(double distance) {

        List<Vec3d> locations = new ArrayList<>();

        for (double x = minCorner.getX(); x < maxCorner.getX() + 1 - Utils.DOUBLE_EPSILON; x += distance) {
            for (double y = minCorner.getY(); y < maxCorner.getY() + 1 - Utils.DOUBLE_EPSILON; y += distance) {
                for (double z = minCorner.getZ(); z < maxCorner.getZ() + 1 - Utils.DOUBLE_EPSILON; z += distance) {
                    locations.add(new Vec3d(x, y, z));
                }
            }
        }

        return locations;
    }

    @Override
    public List<Entity> getEntities() {

        return getWorld().getEntitiesWithinAABB(Entity.class, getAABB(), (entity) -> ! (entity instanceof EntityPlayer));
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
