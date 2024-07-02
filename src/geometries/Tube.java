package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a tube in a three-dimensional coordinate system.
 * A tube is a type of radial geometry extended along a given axis.
 * It is defined by its radius and axis.
 *
 * @author Shneor and Emanuel
 */
public class Tube extends RadialGeometry {
    protected Ray axis; // The axis of the tube.

    /**
     * Constructs a tube with the specified radius and axis.
     *
     * @param radius The radius of the tube. Must be non-negative.
     * @param axis   The axis of the tube.
     * @throws IllegalArgumentException if the radius is negative.
     */
    public Tube(double radius, Ray axis) throws IllegalArgumentException {
        super(radius);
        this.axis = axis;
    }


    @Override
    public Vector getNormal(Point point) throws IllegalArgumentException{
        // Calculate the vector from the axis head to the given point
        Vector headToPoint = point.subtract(axis.getHead());
        // Calculate the projection of the head-to-point vector onto the axis direction
        double t = headToPoint.dotProduct(axis.getDirection());
        if (!isZero(t)) {
            // Calculate the point on the axis that is closest to the given point
            Point o = axis.getPoint(t);

            if(o.equals(point))
                throw new IllegalArgumentException("point can't be on the axis");
            // Calculate the vector from the closest point on the axis to the given point and normalize it
            return point.subtract(o).normalize();
        } else {
            //if t=0 we don't need to create the point o, we already have the closest point on the axis to the given point
            return point.subtract(axis.getHead()).normalize();
        }
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        return null;
    }

}