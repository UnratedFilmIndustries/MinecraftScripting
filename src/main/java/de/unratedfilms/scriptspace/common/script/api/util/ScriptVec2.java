
package de.unratedfilms.scriptspace.common.script.api.util;

public class ScriptVec2 {

    public double x, y;

    public ScriptVec2() {

        this(0, 0);
    }

    public ScriptVec2(double x, double y) {

        this.x = x;
        this.y = y;
    }

    public void add(ScriptVec2 vec) {

        x += vec.x;
        y += vec.y;
    }

    public void add(double x, double y) {

        this.x += x;
        this.y += y;
    }

    public void multiply(ScriptVec2 vec) {

        x *= vec.x;
        y *= vec.y;
    }

    public void multiply(double x, double y) {

        this.x *= x;
        this.y *= y;
    }

    public void scale(double s) {

        x *= s;
        y *= s;
    }

    public double length() {

        return Math.sqrt(x * x + y * y);
    }

    public void normalize() {

        double l = length();
        if (l > 0) {
            x /= l;
            y /= l;
        }
    }

    public double distanceTo(ScriptVec2 vec) {

        double dx = x - vec.x;
        double dy = y - vec.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
