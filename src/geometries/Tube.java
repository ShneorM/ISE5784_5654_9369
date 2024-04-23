package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a tube in a three-dimensional coordinate system.
 * A tube is a type of radial geometry extended along a given axis.
 * It is defined by its radius and axis.
 * @author Shneor and Emanuel
 */
public class Tube extends RadialGeometry {
    protected Ray axis; // The axis of the tube.

    /**
     * Constructs a tube with the specified radius and axis.
     *
     * @param radius The radius of the tube. Must be non-negative.
     * @param axis The axis of the tube.
     * @throws IllegalArgumentException if the radius is negative.
     */
    public Tube(double radius, Ray axis) throws IllegalArgumentException {
        super(radius);
        this.axis = axis;
    }


    @Override
    public Vector getNormal(Point point) {
        return null; // Temporary implementation, needs to be overridden.
    }
}