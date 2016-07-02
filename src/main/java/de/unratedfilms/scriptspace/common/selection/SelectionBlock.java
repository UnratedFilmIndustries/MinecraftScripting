
package de.unratedfilms.scriptspace.common.selection;

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

public class SelectionBlock extends Selection {

    public final Vec3i blockLocation;

    public SelectionBlock(int dimensionId, Vec3i blockLocation) {

        super(dimensionId);

        Validate.notNull(blockLocation);
        this.blockLocation = blockLocation;
    }

    @Override
    public Vec3 getCenter() {

        return Vec3.createVectorHelper(blockLocation.x + 0.5, blockLocation.y + 0.5, blockLocation.z + 0.5);
    }

    @Override
    public AxisAlignedBB getAABB() {

        return AxisAlignedBB.getBoundingBox(blockLocation.x, blockLocation.y, blockLocation.z, blockLocation.x + 1, blockLocation.y + 1, blockLocation.z + 1);
    }

    @Override
    public boolean intersects(double x, double y, double z) {

        return getAABB().isVecInside(Vec3.createVectorHelper(x, y, z));
    }

    @Override
    public List<Vec3> getLocations(double distance) {

        List<Vec3> locations = new ArrayList<>();

        for (double x = blockLocation.x; x < blockLocation.x + 1 - Utils.DOUBLE_EPSILON; x += distance) {
            for (double y = blockLocation.y; y < blockLocation.y + 1 - Utils.DOUBLE_EPSILON; y += distance) {
                for (double z = blockLocation.z; z < blockLocation.z + 1 - Utils.DOUBLE_EPSILON; z += distance) {
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
