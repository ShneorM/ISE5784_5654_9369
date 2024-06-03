package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Shneor and Emanuel
 */
public class Geometries implements Intersectable {

    final private List<Intersectable> intersectableList = new LinkedList<>();

    public Geometries() {
    }

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        intersectableList.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        List<Point> res = null, shapePoints;
        for (var shape : intersectableList) {
            shapePoints = shape.findIntersections(ray);
            if (shapePoints != null) {
                if (res == null)
                    res = new LinkedList<>();

                res.addAll(shapePoints);
            }
        }
        return res;
    }
}
