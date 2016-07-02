
package de.unratedfilms.scriptspace.common.script.api.util;

import net.minecraft.util.Vec3;
import de.unratedfilms.scriptspace.common.util.Utils;
import de.unratedfilms.scriptspace.common.util.Vec3i;

public class ScriptVec3 {

    public double x, y, z;

    public ScriptVec3() {

        this(0, 0, 0);
    }

    public ScriptVec3(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ScriptVec3(Vec3 vec3) {

        this(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    public ScriptVec3(Vec3i vec3i) {

        this(vec3i.x, vec3i.y, vec3i.z);
    }

    public ScriptVec3 add(ScriptVec3 vec) {

        x += vec.x;
        y += vec.y;
        z += vec.z;

        return this;
    }

    public ScriptVec3 add(double x, double y, double z) {

        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    public ScriptVec3 multiply(ScriptVec3 vec) {

        x *= vec.x;
        y *= vec.y;
        z *= vec.z;

        return this;
    }

    public ScriptVec3 multiply(double x, double y, double z) {

        this.x *= x;
        this.y *= y;
        this.z *= z;

        return this;
    }

    public ScriptVec3 scale(double s) {

        x *= s;
        y *= s;
        z *= s;

        return this;
    }

    public double length() {

        return Math.sqrt(x * x + y * y + z * z);
    }

    public ScriptVec3 normalize() {

        double l = length();
        if (l > 0) {
            x /= l;
            y /= l;
            z /= l;
        }

        return this;
    }

    public double distanceTo(ScriptVec3 vec) {

        double dx = x - vec.x;
        double dy = y - vec.y;
        double dz = z - vec.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public ScriptVec3 toBlock() {

        x = getBlockX();
        y = getBlockY();
        z = getBlockZ();

        return this;
    }

    public int getBlockX() {

        return Utils.floor(x);
    }

    public int getBlockY() {

        return Utils.floor(y);
    }

    public int getBlockZ() {

        return Utils.floor(z);
    }

    @Override
    public ScriptVec3 clone() {

        return new ScriptVec3(x, y, z);
    }

}
