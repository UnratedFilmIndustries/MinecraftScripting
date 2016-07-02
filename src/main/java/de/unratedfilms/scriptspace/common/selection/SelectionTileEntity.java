
package de.unratedfilms.scriptspace.common.selection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.Validate;
import de.unratedfilms.scriptspace.common.util.Vec3i;

public class SelectionTileEntity extends Selection {

    public final Vec3i selectedTileEntityLocation;

    public SelectionTileEntity(TileEntity selectedTileEntity) {

        this(selectedTileEntity.getWorldObj().provider.dimensionId, new Vec3i(selectedTileEntity.xCoord, selectedTileEntity.yCoord, selectedTileEntity.zCoord));
    }

    public SelectionTileEntity(int dimensionId, Vec3i selectedTileEntityLocation) {

        super(dimensionId);

        Validate.notNull(selectedTileEntityLocation);
        this.selectedTileEntityLocation = selectedTileEntityLocation;
    }

    public TileEntity getSelectedTileEntity() {

        return getWorld().getTileEntity(selectedTileEntityLocation.x, selectedTileEntityLocation.y, selectedTileEntityLocation.z);
    }

    @Override
    public Vec3 getCenter() {

        return Vec3.createVectorHelper(selectedTileEntityLocation.x + 0.5, selectedTileEntityLocation.y + 0.5, selectedTileEntityLocation.z + 0.5);
    }

    @Override
    public AxisAlignedBB getAABB() {

        return getSelectedTileEntity().getRenderBoundingBox();
    }

    @Override
    public boolean intersects(double x, double y, double z) {

        return getAABB().isVecInside(Vec3.createVectorHelper(x, y, z));
    }

    @Override
    public List<Vec3> getLocations(double distance) {

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
