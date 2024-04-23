package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Sphere is the basic class representing a Sphere of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Shneor and Emanuel
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs a sphere with the specified radius and center point.
     *
     * @param radius the radius of the sphere
     * @param center the center point of the sphere
     * @throws IllegalArgumentException if the radius is negative
     */
    public Sphere(double radius, Point center) throws IllegalArgumentException {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
