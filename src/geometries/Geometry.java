package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * interface Geometry is the basic interface representing any kind of geometry like sphere, plane etc. of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Shneor and Emanuel
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

    /**
     *
     * @param emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     *
     * @return
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * @param point the point on the surface of the geometry body
     * @return the normal vector to the point on the surface
     */
    public abstract Vector getNormal(Point point);
}

