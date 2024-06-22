package geometries;

/**
 * Class RadialGeometry is the basic class representing any Radial Geometry like sphere,tube cylinder etc. of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Shneor and Emanuel
 */
abstract public class RadialGeometry extends Geometry {
    /**
     * the radius of the specific body
     */
    protected final double radius;

    /**
     * construct a Radial Geometry with a certain radius
     * @param radius the radius of the body
     * @throws IllegalArgumentException if the radius is negative
     */
    public RadialGeometry(double radius) throws IllegalArgumentException {
        if (radius < 0) throw new IllegalArgumentException("radius " + radius + " can't be negative");
        this.radius = radius;
    }
}
