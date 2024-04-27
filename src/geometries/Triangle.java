package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

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
        return null;
    }
}
