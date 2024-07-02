package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a cylinder in a three-dimensional coordinate system.
 * A cylinder is essentially a tube extended along a certain height.
 * It is defined by its radius, axis, and height.
 *
 * @author Shneor and Emanuel
 */
public class Cylinder extends Tube {

    private double height; // The height of the cylinder.

    /**
     * Constructs a cylinder with the specified radius, axis, and height.
     *
     * @param radius The radius of the cylinder. Must be non-negative.
     * @param axis   The axis of the cylinder.
     * @param height The height of the cylinder. Must be non-negative.
     * @throws IllegalArgumentException if the radius or height is negative.
     */
    public Cylinder(double radius, Ray axis, double height) throws IllegalArgumentException {
        super(radius, axis);
        if (height < 0) throw new IllegalArgumentException("the height can't be negative");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        // Define a point representing the head of the axis plus height direction
        Point headPlusHDirection;

        // Compute the headPlusHDirection based on the height of the axis
        if (!isZero(height))
            headPlusHDirection = axis.getHead().add(axis.getDirection().scale(height));
        else
            headPlusHDirection = axis.getHead();

        // Check if the provided point is either at the head of the axis or at headPlusHDirection
        if (point.equals(axis.getHead()) || point.equals(headPlusHDirection))
            return axis.getDirection();
            // Check if the vector from point to one of the centers is perpendicular to the axis (meaning that point is on one of the bases)
        else if (isZero(point.subtract(axis.getHead()).dotProduct(axis.getDirection())) ||
                isZero(point.subtract(headPlusHDirection).dotProduct(axis.getDirection())))
            return axis.getDirection();
            // If the point is not on one of the bases, delegate to the Tube for the normal vector
            //since the calculations should be as there
        else
            return super.getNormal(point);
    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        return null;
    }
}
