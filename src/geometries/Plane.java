package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    private final Point q;
    private final Vector normal;

    public Plane(Point q, Vector normal) {
        this.q = q;
        //we don't want to use the normalize (that creates new object) function if not necessary
        if (normal.lengthSquared() != 1) normal = normal.normalize();
        this.normal = normal;
    }

    public Plane(Point p1, Point p2, Point p3) {
        q = p1;
        normal = null;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    public Vector getNormal() {
        return normal;
    }

}
