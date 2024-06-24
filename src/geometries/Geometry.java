package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry class is the basic abstract class representing any kind of geometry,
 * in Euclidean geometry within a 3-Dimensional Cartesian coordinate system.
 *
 * @author Shneor and Emanuel
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission the emission color to set
     * @return the geometry itself for chaining
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Gets the emission color of the geometry.
     *
     * @return the emission color
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

