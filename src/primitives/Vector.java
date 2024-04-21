package primitives;

public class Vector extends Point {
    public Vector(Double3 xyz) {
        super(xyz);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector && super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public double lengthSquared() {
        return this.distanceSquared(new Point(Double3.ZERO));
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector add(Vector vector) {
        return new Vector(vector.xyz.add(this.xyz));
    }

    public Vector scale(double mult) {
        return new Vector(this.xyz.scale(mult));
    }

    public double dotProduct(Vector vector) {
        return vector.xyz.d1 * this.xyz.d1 +
                vector.xyz.d2 * this.xyz.d2 +
                vector.xyz.d3 * this.xyz.d3;
    }

    public Vector crossProduct(Vector vector) {
        return new Vector(new Double3(this.xyz.d2 * vector.xyz.d3 - vector.xyz.d2 * this.xyz.d3,
                this.xyz.d3 * vector.xyz.d1 - vector.xyz.d3 * this.xyz.d1,
                this.xyz.d1 * vector.xyz.d2 - vector.xyz.d1 * this.xyz.d2));
    }

    public Vector normalize() {
        return new Vector(this.xyz.reduce(this.length()));
    }


}
