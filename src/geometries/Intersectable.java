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
public abstract class Intersectable {

    /**
     *
     */
    public static class GeoPoint {
        /**
         *
         */
        public Geometry geometry;
        /**
         *
         */
        public Point point;

        /**
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {

            this.geometry = geometry;
            this.point = point;
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
            return "Geometry: " + geometry.toString() + "Point: " + point.toString();
        }

    }
    /**
     * Finds the intersections of this object with the given ray.
     *
     * @param ray The ray to intersect with.
     * @return A list of points representing the intersections of this object with the given ray.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    /**
     *
     * @param ray
     * @return
     */
    public abstract List<GeoPoint> findGeoIntersections(Ray ray);

    /**
     *
     * @param ray
     * @return
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);


}
