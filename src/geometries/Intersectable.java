package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Intersectable is the basic abstract class representing any kind of intersectable object like sphere, plane, etc.,
 * in Euclidean geometry within a 3-Dimensional Cartesian coordinate system.
 *
 * @author Shneor and Emanuel
 */
public abstract class Intersectable {

    /**
     * GeoPoint is an inner class that represents a point of intersection with additional information about
     * the geometry to which it belongs.
     */
    public static class GeoPoint {
        /**
         * The geometry to which the point of intersection belongs.
         */
        public Geometry geometry;
        /**
         * The point of intersection.
         */
        public Point point;

        /**
         * Constructs a GeoPoint with the given geometry and point.
         *
         * @param geometry the geometry to which the point of intersection belongs
         * @param point    the point of intersection
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * @return
         */
        public Vector getNormal() {
            return geometry.getNormal(point);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return obj instanceof GeoPoint other &&
                    other.geometry == this.geometry &&
                    other.point.equals(this.point);
        }

        @Override
        public String toString() {
            return "Geometry: " + geometry.toString() + " Point: " + point.toString();
        }
    }

    /**
     * Finds the intersections of this object with the given ray.
     *
     * @param ray the ray to intersect with
     * @return a list of points representing the intersections of this object with the given ray, or null if there are none
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * @param ray
     * @param maxDistance
     * @return
     */
    public List<Point> findIntersections(Ray ray, double maxDistance) {
        var geoList = findGeoIntersections(ray, maxDistance);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * @param ray
     * @return
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * @param ray
     * @param maxDistance
     * @return
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * @param ray
     * @param maxDistance
     * @return
     */
    protected abstract List<GeoPoint>
    findGeoIntersectionsHelper(Ray ray, double maxDistance);
}
