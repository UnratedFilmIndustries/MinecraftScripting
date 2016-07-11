
package de.unratedfilms.scriptspace.common.script.api.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import de.unratedfilms.scriptspace.common.util.Utils;

public class ScriptVec2 {

    public double x, y;

    public ScriptVec2() {

        this(0, 0);
    }

    public ScriptVec2(double x, double y) {

        this.x = x;
        this.y = y;
    }

    public ScriptVec2 add(ScriptVec2 vec) {

        return add(vec.x, vec.y);
    }

    public ScriptVec2 add(double x, double y) {

        this.x += x;
        this.y += y;

        return this;
    }

    public ScriptVec2 multiply(ScriptVec2 vec) {

        return multiply(vec.x, vec.y);
    }

    public ScriptVec2 multiply(double x, double y) {

        this.x *= x;
        this.y *= y;

        return this;
    }

    public ScriptVec2 scale(double scale) {

        return multiply(scale, scale);
    }

    public double length() {

        return Math.sqrt(x * x + y * y);
    }

    public ScriptVec2 normalize() {

        double l = length();
        if (l > 0) {
            x /= l;
            y /= l;
        }

        return this;
    }

    public double distanceTo(ScriptVec2 vec) {

        double dx = x - vec.x;
        double dy = y - vec.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public ScriptVec2 toBlock() {

        x = getBlockX();
        y = getBlockY();

        return this;
    }

    public int getBlockX() {

        return Utils.floor(x);
    }

    public int getBlockY() {

        return Utils.floor(y);
    }

    @Override
    public ScriptVec2 clone() {

        return new ScriptVec2(x, y);
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {

        return "(" + x + "|" + y + ")";
    }

}
