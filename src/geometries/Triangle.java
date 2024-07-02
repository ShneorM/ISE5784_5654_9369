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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        // Step 1: Find intersection with the plane
        List<Point> planeIntersections = plane.findIntersections(ray,maxDistance);
        if (planeIntersections == null) {
            return null;
        }

        // Step 2: Check if the intersection point is inside the triangle using barycentric coordinates
        Point P = planeIntersections.getFirst();


        // Handle case where P coincides with one of the vertices
        if (P.equals(vertices.get(0)) || P.equals(vertices.get(1)) || P.equals(vertices.get(2))) {
            return null;
        }

        // Vectors from triangle vertices to the point
        Vector v0v1 = vertices.get(1).subtract(vertices.get(0)); //AB
        Vector v0v2 = vertices.get(2).subtract(vertices.get(0)); //AC
        Vector v0P = P.subtract(vertices.get(0)); //AP

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
            return List.of(new GeoPoint(this,P));
        }
        return null;
    }



}
