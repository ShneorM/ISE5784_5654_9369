package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of geometric shapes that can be intersected by rays.
 * It implements the Intersectable interface, allowing it to find intersections with a given ray.
 * This class supports adding multiple geometric shapes and finding their intersections collectively.
 * <p>
 * Author: Shneor and Emanuel
 */
public class Geometries extends Intersectable {

    /**
     * A list to hold all the geometric shapes that can be intersected.
     */
    private final List<Intersectable> intersectableList = new LinkedList<>();

    /**
     * Default constructor for creating an empty collection of geometries.
     */
    public Geometries() {
    }

    /**
     * Constructor that initializes the collection with one or more geometric shapes.
     *
     * @param geometries one or more geometric shapes to be added to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds one or more geometric shapes to the collection.
     *
     * @param geometries one or more geometric shapes to be added to the collection.
     */
    public void add(Intersectable... geometries) {
        intersectableList.addAll(List.of(geometries));
    }

    /**
     * Finds all the intersection points between the given ray and the geometric shapes in the collection.
     *
     * @param ray the ray to intersect with the geometric shapes.
     * @return a list of intersection points, or null if no intersections are found.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> res = null, shapeGeoPoints;
        for (var shape : intersectableList) {
            shapeGeoPoints = shape.findGeoIntersectionsHelper(ray);
            if (shapeGeoPoints != null) {
                if (res == null)
                    res = new LinkedList<>();
                res.addAll(shapeGeoPoints);
            }
        }
        return res;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }


}
