
package de.unratedfilms.scriptspace.common.selection;

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

public class SelectionBlock extends Selection {

    public final BlockPos blockLocation;

    public SelectionBlock(int dimensionId, BlockPos blockLocation) {

        super(dimensionId);

        Validate.notNull(blockLocation);
        this.blockLocation = blockLocation;
    }

    @Override
    public Vec3d getCenter() {

        return new Vec3d(blockLocation.getX() + 0.5, blockLocation.getY() + 0.5, blockLocation.getZ() + 0.5);
    }

    @Override
    public AxisAlignedBB getAABB() {

        return new AxisAlignedBB(blockLocation);
    }

    @Override
    public boolean intersects(double x, double y, double z) {

        return getAABB().isVecInside(new Vec3d(x, y, z));
    }

    @Override
    public List<Vec3d> getLocations(double distance) {

        List<Vec3d> locations = new ArrayList<>();

        for (double x = blockLocation.getX(); x < blockLocation.getX() + 1 - Utils.DOUBLE_EPSILON; x += distance) {
            for (double y = blockLocation.getY(); y < blockLocation.getY() + 1 - Utils.DOUBLE_EPSILON; y += distance) {
                for (double z = blockLocation.getZ(); z < blockLocation.getZ() + 1 - Utils.DOUBLE_EPSILON; z += distance) {
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
        result = prime * result + (blockLocation == null ? 0 : blockLocation.hashCode());
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
        SelectionBlock other = (SelectionBlock) obj;
        if (blockLocation == null) {
            if (other.blockLocation != null) {
                return false;
            }
        } else if (!blockLocation.equals(other.blockLocation)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "block at " + blockLocation.toString();
    }

}
