package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The LightSource interface defines the common behaviors that all light sources
 * must implement. It includes methods for obtaining the intensity of the light
 * at a specific point and for getting the direction vector of the light relative
 * to a given point.
 *
 * @see Color
 * @see Point
 * @see Vector
 *
 * @author  Shneor and Emanuel
 */
public interface LightSource {
    /**
     * Gets the intensity of the light at a given point.
     *
     * @param p the point at which the light intensity is being calculated
     * @return the color intensity of the light at the specified point
     */
    public Color getIntensity(Point p);

    /**
     * Gets the direction vector of the light from the light source to a given point.
     *
     * @param p the point at which the direction is being calculated
     * @return the direction vector of the light at the specified point
     */
    public Vector getL(Point p);

    /**
     * Computes the distance from this point to the given point.
     *
     * @param point the point to compute the distance to.
     * @return the distance from this point to the given point.
     */
    double getDistance(Point point);

    /**
     * returns the light-source's radius
     * @return the light-source's radius
     */
    public double getRadius();

    /**
     * returns the position of the light
     * @return the position of the light
     */
    public Point getPosition();

}
