package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * interface Geometry is the basic interface representing any kind of geometry like sphere, plane etc. of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Shneor and Emanuel
 */
public interface Geometry extends Intersectable {
    /**
     * @param point the point on the surface of the geometry body
     * @return the normal vector to the point on the surface
     */
    Vector getNormal(Point point);
}

