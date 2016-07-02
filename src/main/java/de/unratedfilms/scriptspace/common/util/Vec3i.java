
package de.unratedfilms.scriptspace.common.util;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class Vec3i {

    public int x, y, z;

    public Vec3i(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3i(Vec3 vec3) {

        this(Utils.floor(vec3.xCoord), Utils.floor(vec3.yCoord), Utils.floor(vec3.zCoord));
    }

    public AxisAlignedBB getAABB() {

        return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
    }

    public Vec3 toVec3() {

        return Vec3.createVectorHelper(x, y, z);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
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
        Vec3i other = (Vec3i) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "(" + x + "|" + y + "|" + z + ")";
    }

}
