package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
    public Vector getNormal(Point point) throws IllegalArgumentException {

        return point.subtract(center).normalize();
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // If the ray's head coincides with the center of the sphere,
        // then the intersection point is at a distance of the radius.
        if (center.equals(ray.getHead())) {
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        Vector u = center.subtract(ray.getHead());
        double tm = u.dotProduct(ray.getDirection());
        double d = Math.sqrt(u.lengthSquared() - tm * tm);

        // If the distance between the center and the ray is greater than or equal to the radius,
        // there are no intersections.
        if (d >= radius || isZero(d - radius))
            return null;

        // Calculate the distance along the ray to the intersection points.
        double th = Math.sqrt(radius * radius - d * d);


        //tm - th == t1, tm + th == t2;
        if (tm + th < 0 || isZero(ray, tm + th)) {
            return null;
        }
        if ((tm - th < 0 || isZero(ray, tm - th)) && alignZero(tm + th - maxDistance) < 0) {
            return List.of(new GeoPoint(this, ray.getPoint(tm + th)));
        }
        if ((tm - th < 0 || isZero(ray, tm - th))) {//tm+th-maxDistance>=0
            return null;
        }
        //from here tm-th>0
        if (alignZero(tm + th - maxDistance) < 0) {
            return List.of(new GeoPoint(this, ray.getPoint(tm - th)), new GeoPoint(this, ray.getPoint(tm + th)));
        }
        if (alignZero(tm - th - maxDistance) < 0) {//tm+th>=maxDistance
            return List.of(new GeoPoint(this, ray.getPoint(tm - th)));
        }
        //tm+th>=maxDistance,tm-th>=maxDistance
        return null;
    }
}



