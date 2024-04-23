package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{

    private final Point center;
    public Sphere(Point center,double radius) throws IllegalArgumentException {
        super(radius);
        this.center=center;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
