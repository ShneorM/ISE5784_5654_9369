package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
/**
 * interface Intersectable is the basic interface representing any kind of Intersectable object like sphere, plane etc. of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Shneor and Emanuel
 */
public interface Intersectable {
    /**
     * Finds the intersections of this object with the given ray.
     *
     * @param ray The ray to intersect with.
     * @return A list of points representing the intersections of this object with the given ray.
     */
    List<Point> findIntersections(Ray ray);
}
