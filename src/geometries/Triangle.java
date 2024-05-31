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

//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        // Find intersections of the ray with the plane.
//        var listPoint = plane.findIntersections(ray);
//        if (listPoint.isEmpty())
//            return listPoint;
//
//        // Calculate vectors from the ray's head to the vertices of the triangle.
//        Vector v1 = vertices.get(0).subtract(ray.getHead());
//        Vector v2 = vertices.get(1).subtract(ray.getHead());
//        Vector v3 = vertices.get(2).subtract(ray.getHead());
//
//        // Calculate normals of the triangle's edges.
//        Vector n1 = v1.crossProduct(v2).normalize();
//        Vector n2 = v2.crossProduct(v3).normalize();
//        Vector n3 = v3.crossProduct(v1).normalize();
//
//        // Calculate dot products between normals and ray's direction.
//        double t1 = n1.dotProduct(ray.getDirection());
//        double t2 = n2.dotProduct(ray.getDirection());
//        double t3 = n3.dotProduct(ray.getDirection());
//
//        // Check if the ray intersects the triangle.
//        boolean flag = !isZero(t1) && !isZero(t2) && !isZero(t3);
//
//        // Check if all the t's have the same sign.
//        if (!flag || !((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)))
//            flag = false;
//
//        // Return intersection points if the ray intersects the triangle, otherwise return an empty list.
//        if (flag)
//            return listPoint;
//        else
//            return List.of();
//    }
@Override
public List<Point> findIntersections(Ray ray) {
    // Step 1: Find intersection with the plane
    List<Point> planeIntersections = plane.findIntersections(ray);
    if (planeIntersections.isEmpty()) {
        return List.of();
    }

    // Step 2: Check if the intersection point is inside the triangle using barycentric coordinates
    Point P = planeIntersections.getFirst();

    // Get the triangle vertices
    Point v0 = vertices.get(0);
    Point v1 = vertices.get(1);
    Point v2 = vertices.get(2);

    // Handle case where P coincides with one of the vertices
    if (P.equals(v0) || P.equals(v1) || P.equals(v2)) {
        return List.of();
    }

    // Vectors from triangle vertices to the point
    Vector v0v1 = v1.subtract(v0);
    Vector v0v2 = v2.subtract(v0);
    Vector v0P = P.subtract(v0);

    // Dot products
    double dot00 = v0v2.dotProduct(v0v2);
    double dot01 = v0v2.dotProduct(v0v1);
    double dot02 = v0v2.dotProduct(v0P);
    double dot11 = v0v1.dotProduct(v0v1);
    double dot12 = v0v1.dotProduct(v0P);

    // Barycentric coordinates
    double invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
    double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
    double v = (dot00 * dot12 - dot01 * dot02) * invDenom;

    // Check if point is in triangle (excluding the boundaries)
    if (!isZero(u) && u > 0 && !isZero(v) && v > 0 && !isZero(u + v - 1) && u + v < 1) {
        return List.of(P);
    }

    return List.of();
}
}
