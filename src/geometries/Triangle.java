package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Class Triangle is the basic class representing a Triangle of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Shneor and Emanuel
 */
public class Triangle extends Polygon {
    /**
     * Constructs a triangle with the specified points.
     *
     * @param p1 the first vertex of the triangle
     * @param p2 the second vertex of the triangle
     * @param p3 the third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Find intersections of the ray with the plane.
        var listPoint = plane.findIntersections(ray);
        if (listPoint.isEmpty())
            return listPoint;

        // Calculate vectors from the ray's head to the vertices of the triangle.
        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());

        // Calculate normals of the triangle's edges.
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // Calculate dot products between normals and ray's direction.
        double t1 = n1.dotProduct(ray.getDirection());
        double t2 = n2.dotProduct(ray.getDirection());
        double t3 = n3.dotProduct(ray.getDirection());

        // Check if the ray intersects the triangle.
        boolean flag = !isZero(t1) && !isZero(t2) && !isZero(t3);

        // Check if all the t's have the same sign.
        if (!flag || !((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)))
            flag = false;

        // Return intersection points if the ray intersects the triangle, otherwise return an empty list.
        if (flag)
            return listPoint;
        else
            return List.of();
    }

}
