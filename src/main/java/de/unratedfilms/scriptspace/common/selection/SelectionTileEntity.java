
package de.unratedfilms.scriptspace.common.selection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.Validate;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SelectionTileEntity extends Selection {

    public final BlockPos selectedTileEntityLocation;

    public SelectionTileEntity(TileEntity selectedTileEntity) {

        this(selectedTileEntity.getWorld().provider.getDimension(), selectedTileEntity.getPos());
    }

    public SelectionTileEntity(int dimensionId, BlockPos selectedTileEntityLocation) {

        super(dimensionId);

        Validate.notNull(selectedTileEntityLocation);
        this.selectedTileEntityLocation = selectedTileEntityLocation;
    }

    public TileEntity getSelectedTileEntity() {

        return getWorld().getTileEntity(selectedTileEntityLocation);
    }

    @Override
    public Vec3d getCenter() {

        return new Vec3d(selectedTileEntityLocation.getX() + 0.5, selectedTileEntityLocation.getY() + 0.5, selectedTileEntityLocation.getZ() + 0.5);
    }

    @Override
    public AxisAlignedBB getAABB() {

        return getSelectedTileEntity().getRenderBoundingBox();
    }

    @Override
    public boolean intersects(double x, double y, double z) {

        return getAABB().isVecInside(new Vec3d(x, y, z));
    }

    @Override
    public List<Vec3d> getLocations(double distance) {

        return Arrays.asList(getCenter());
    }

    @Override
    public List<Entity> getEntities() {

        return Collections.emptyList();
    }

    @Override
    public List<TileEntity> getTileEntities() {

        TileEntity selectedTileEntity = getSelectedTileEntity();
        return selectedTileEntity == null ? Collections.<TileEntity> emptyList() : Arrays.asList(selectedTileEntity);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (getBlockCenter() == null ? 0 : getBlockCenter().hashCode());
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
        SelectionTileEntity other = (SelectionTileEntity) obj;
        if (getBlockCenter() == null) {
            if (other.getBlockCenter() != null) {
                return false;
            }
        } else if (!getBlockCenter().equals(other.getBlockCenter())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "tile entity at " + getBlockCenter();
    }

}
