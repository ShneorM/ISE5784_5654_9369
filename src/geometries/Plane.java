package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a plane in three-dimensional space.
 * @author Shneor and Emanuel
 */
public class Plane implements Geometry {

    private final Point q;
    private final Vector normal;

    /**
     * Constructs a plane passing through a point with a given normal vector.
     *
     * @param q The point on the plane.
     * @param normal The normal vector to the plane. If the length of the normal vector is not 1, it will be normalized.
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        //we don't want to use the normalize (that creates new object) function if not necessary
        if (normal.lengthSquared() != 1) normal = normal.normalize();
        this.normal = normal;
    }

    /**
     * Constructs a plane passing through three non-collinear points.
     *
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     * @throws IllegalArgumentException If all three points are collinear, meaning they lie on the same line.
     */
    public Plane(Point p1, Point p2, Point p3) throws IllegalArgumentException{
        q = p1;
        try {
            Vector v1 = p1.subtract(p3);
            Vector v2 = p1.subtract(p2);
            normal = v1.crossProduct(v2).normalize();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("all point must not be in the same line");
        }
    }


    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * get the normal of the plane
     * @return the normal
     */
    public Vector getNormal() {
        return normal;
    }

}
