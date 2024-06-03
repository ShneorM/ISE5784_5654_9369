package primitives;

/**
 * Class Point is the basic class representing a Point of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Shneor and Emanuel
 */
public class Point {
    /**
     * the coordinates that stored in the point
     */
    protected final Double3 xyz;

    /**
     * get three double coordinates and construct a three-dimensional point (x,y,z)
     *
     * @param x the value of the x-axis of the point
     * @param y the value of the y-axis of the point
     * @param z the value of the z-axis of the point
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * get the coordinates already stored in a Double3 and construct a point of these coordinates
     *
     * @param xyz the coordinates
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Zero point (0,0,0)
     */
    //this was added since the main file used this static point, and we weren't allowed to change it
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * @param obj the object we are comparing to the calling object
     * @return whether the calling object and obj are equal
     * @author Shneor and Emanuel
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point other
                && this.xyz.equals(other.xyz);
    }

    /**
     * @return (x, y, z)
     * @author Shneor and Emanuel
     */
    @Override
    public String toString() {
        return xyz.toString();
    }

    /**
     * @param vector the vector that needs to be added to the point
     * @return the point that will be at the tip of the vector if it starts from the calling point
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * @param point the point from which the vector supposed to go to the calling point
     * @return the vector that goes from the called point to the calling point
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * @param point the point from which we will measure the distance
     * @return the distance between the called point and the calling point
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * @param point the point from which we will measure the distance
     * @return the squared distance between the called point and the calling point
     */
    public double distanceSquared(Point point) {
        return (xyz.d1 - point.xyz.d1) * (xyz.d1 - point.xyz.d1) +
                (xyz.d2 - point.xyz.d2) * (xyz.d2 - point.xyz.d2) +
                (xyz.d3 - point.xyz.d3) * (xyz.d3 - point.xyz.d3);
    }

    /**
     * Retrieves the x-coordinate of this point/vector.
     * @return The x-coordinate.
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * Retrieves the y-coordinate of this point/vector.
     * @return The y-coordinate.
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * Retrieves the z-coordinate of this point/vector.
     * @return The z-coordinate.
     */
    public double getZ() {
        return xyz.d3;
    }

}
