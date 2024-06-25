package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry class is the basic abstract class representing any kind of geometry,
 * in Euclidean geometry within a 3-Dimensional Cartesian coordinate system.
 * It includes properties for the material and emission color, as well as methods
 * for setting and getting these properties. Additionally, it provides an abstract
 * method for calculating the normal vector at a given point on the geometry's surface.
 * This class extends the Intersectable class.
 *
 * @see Intersectable
 * @see Color
 * @see Material
 * @see Point
 * @see Vector
 *
 * @author Shneor and Emanuel
 */
public abstract class Geometry extends Intersectable {
    /**
     * The emission color of the geometry.
     */
    protected Color emission = Color.BLACK;

    /**
     * The material of the geometry.
     */
    private Material material = new Material();

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
     * Sets the material of the geometry.
     *
     * @param material the material to set
     * @return the geometry itself for chaining
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
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
     * Gets the material of the geometry.
     *
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Calculates the normal vector to the geometry at a given point.
     *
     * @param point the point on the surface of the geometry
     * @return the normal vector to the point on the surface
     */
    public abstract Vector getNormal(Point point);
}
