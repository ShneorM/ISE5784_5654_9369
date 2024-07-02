package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a plane in three-dimensional space.
 *
 * @author Shneor and Emanuel
 */
public class Plane extends Geometry {

    private final Point q;
    private final Vector normal;

    /**
     * Constructs a plane passing through a point with a given normal vector.
     *
     * @param q      The point on the plane.
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
    public Plane(Point p1, Point p2, Point p3) throws IllegalArgumentException {
        q = p1;
        try {
            normal = p1.subtract(p3).crossProduct(p1.subtract(p2)).normalize();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("all point must not be on the same line");
        }
    }


    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * get the normal of the plane
     *
     * @return the normal
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        //Check if the Q-P0 is the ZERO Vector
        if (q.equals(ray.getHead()))
            return null;
        //Check if the ray is parallel to the plane
        if (isZero(normal.dotProduct(ray.getDirection())))
            return null;
        //Calculate the Scalar t that will give us the point of Intersection with the plane
        double t = normal.dotProduct(q.subtract(ray.getHead())) / normal.dotProduct(ray.getDirection());
        if (t <= 0 || isZero(ray,t)||alignZero(t-maxDistance)>=0)
            return null;

        return List.of(new GeoPoint(this,ray.getPoint(t)));
    }


}
