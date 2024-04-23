package geometries;
import primitives.*;
public interface Geometry {
    /**
     * @param point the point on the surface of the geometry body
     * @return the normal vector to the point on the surface
     */
    public Vector getNormal(Point point);
}

