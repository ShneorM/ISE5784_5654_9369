package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

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
    public List<Point> findIntersections(Ray ray) {
        // If the ray's head coincides with the center of the sphere,
        // then the intersection point is at a distance of the radius.
        if (center.equals(ray.getHead())) {
            return List.of(ray.getPoint(radius));
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
        List<Point> res = null;

        //tm - th == t1, tm + th == t2;
        if (tm + th < 0 || isZero(tm + th)) {
            return null;
        }
        if (tm - th < 0 || isZero(tm - th)) {
            return List.of(ray.getPoint(tm + th));
        }
        return List.of(ray.getPoint(tm - th), ray.getPoint(tm + th));

    }

}

