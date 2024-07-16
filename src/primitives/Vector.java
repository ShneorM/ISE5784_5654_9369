package primitives;

import static primitives.Util.isZero;

/**
 * Class Vector is the basic class representing a three-dimensional vector of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Shneor and Emanuel
 */
public class Vector extends Point {

    /**
     * construct a vector from a triad
     *
     * @param xyz the triad from which the vector will be constructed
     * @throws IllegalArgumentException if we try to construct the zero vector
     */
    public Vector(Double3 xyz) throws IllegalArgumentException {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("can't create the zero vector");
    }

    /**
     * get three double coordinates and construct a three-dimensional vector (x,y,z)
     *
     * @param x the value of the x-axis of the point
     * @param y the value of the y-axis of the point
     * @param z the value of the z-axis of the point
     * @throws IllegalArgumentException if we try to construct the zero vector
     */
    public Vector(double x, double y, double z) throws IllegalArgumentException {
        super(x, y, z);
        if (isZero(x) && isZero(y) && isZero(z))
            throw new IllegalArgumentException("can't create the zero vector");
    }

    /**
     *
     */
    public static Vector Y = new Vector(0, 1, 0);
    /**
     *
     */
    public static Vector Z = new Vector(0, 0, 1);

    /**
     * @param obj the object we are comparing to the calling object
     * @return whether the calling vector is equal to obj
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector && super.equals(obj);
    }

    /**
     * @return (x, y, z)
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * @return the squared length of the vector (x^2+y^2+z^2)
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * @return the length of the vector sqrt(x^2+y^2+z^2)
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * @param vector the vector that needs to be added to the vector
     * @return the vector-sum of the two vectors (the calling vector and the called vector)
     * @throws IllegalArgumentException if the sum  is of the vector is zero
     */
    public Vector add(Vector vector) throws IllegalArgumentException {
        return new Vector(vector.xyz.add(this.xyz));
    }

    /**
     * @param mult the scalar
     * @return the scaled vector (by the scalar)
     * @throws IllegalArgumentException if the scalar is zero
     */
    public Vector scale(double mult) throws IllegalArgumentException {
        if (isZero(mult) || xyz.scale(mult).equals(Double3.ZERO))
            throw new IllegalArgumentException("can't scale by zero");
        return new Vector(this.xyz.scale(mult));
    }

    /**
     * @param vector the vector that will be dot-producted with
     * @return the dot-product of the two vectors x1*s2+y1*y2+z1*z2
     */
    public double dotProduct(Vector vector) {
        return vector.xyz.d1 * this.xyz.d1 + vector.xyz.d2 * this.xyz.d2 + vector.xyz.d3 * this.xyz.d3;
    }

    /**
     * @param vector the vector that this vector will be cross-producted with
     * @return the cross product of the two vectors (y1*z2-y2*z1,z1*x2-z2*x1,x1*y2-x2*y1)
     * @throws IllegalArgumentException if the two vectors are parallel and the vector is zero
     */
    public Vector crossProduct(Vector vector) throws IllegalArgumentException {
        return new Vector(new Double3(this.xyz.d2 * vector.xyz.d3 - vector.xyz.d2 * this.xyz.d3, this.xyz.d3 * vector.xyz.d1 - vector.xyz.d3 * this.xyz.d1, this.xyz.d1 * vector.xyz.d2 - vector.xyz.d1 * this.xyz.d2));
    }

    /**
     * @return the normalized vector v/|v|
     */
    public Vector normalize() {

        return new Vector(this.xyz.reduce(this.length()));

    }

    /**
     * calculate a vector orthogonal to this vector
     *
     * @return a vector orthogonal to this vector
     */
    public Vector createOrthogonal() {
        if (Util.isZero(getY()) && Util.isZero(getZ()))
            return Vector.Y;
        return crossProduct(new Vector(1,0,0));
    }
}
