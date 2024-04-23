package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {
    protected Ray axis;

    public Tube(double radius) throws IllegalArgumentException {
        super(radius);
    }
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
