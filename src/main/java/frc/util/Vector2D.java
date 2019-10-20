package frc.util;

public class Vector2D {
    public final double x;
    public final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D fromAngle(double length, double angle) {
        return new Vector2D(length*Math.cos(angle), length*Math.sin(angle));
    }

    public double getLength() {
        return Math.hypot(this.x, this.y);
    }

    public double getAngle() {
        if(this.x >= 0 && this.y >= 0) {
            return Math.atan2(this.y, this.x);
        } else if(this.x < 0 && this.y >= 0) {
            return Math.PI-Math.atan2(this.y,this.x*-1);
        } else if(this.x < 0 && this.y < 0) {
            return Math.PI+Math.atan2(this.y*-1, this.x*-1);
        } else {
            return Math.PI*2-Math.atan2(this.y*-1, this.x);
        }
    }

    public Vector2D add(Vector2D otherVector) {
        return addVectors(this, otherVector);
    }

    public static Vector2D addVectors(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x+v2.x, v1.y+v2.y);
    }
}