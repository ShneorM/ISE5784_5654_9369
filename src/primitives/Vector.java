package primitives;

public class Vector extends Point {
    Vector(Double3 xyz) throws IllegalArgumentException {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("can't create the zero vector");
    }

    public Vector(double x, double y, double z) throws IllegalArgumentException {
        super(x, y, z);
        if (x == 0 && y == 0 && z == 0)
            throw new IllegalArgumentException("can't create the zero vector");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector && super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public double lengthSquared() {
        return this.dotProduct(this);
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
