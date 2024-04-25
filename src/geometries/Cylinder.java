package geometries;
import primitives.Ray;


/**
 * Represents a cylinder in a three-dimensional coordinate system.
 * A cylinder is essentially a tube extended along a certain height.
 * It is defined by its radius, axis, and height.
 * @author Shneor and Emanuel
 */
public class Cylinder extends Tube {

    private double height; // The height of the cylinder.

    /**
     * Constructs a cylinder with the specified radius, axis, and height.
     *
     * @param radius The radius of the cylinder. Must be non-negative.
     * @param axis The axis of the cylinder.
     * @param height The height of the cylinder. Must be non-negative.
     * @throws IllegalArgumentException if the radius or height is negative.
     */
    public Cylinder(double radius, Ray axis, double height) throws IllegalArgumentException {
        super(radius, axis);
        this.height = height;
    }
}
