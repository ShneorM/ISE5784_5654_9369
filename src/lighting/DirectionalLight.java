package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a light source that has a specific direction
 * but no specific origin point. This type of light is typically used to simulate
 * sunlight or other distant light sources that are considered to have parallel rays.
 * The direction of the light is constant and does not change with position.
 *
 * @see Light
 * @see LightSource
 * @see Color
 * @see Point
 * @see Vector
 *
 * @author Shneor and Emanuel
 *
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * Constructs a DirectionalLight with the specified intensity and direction.
     * The direction vector is normalized upon creation.
     *
     * @param intensity the color intensity of the light
     * @param direction the direction vector of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Gets the intensity of the light at a given point.
     * Since a directional light has no specific origin, the intensity is the same
     * at all points.
     *
     * @param p the point at which the light intensity is being calculated
     * @return the color intensity of the light
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    /**
     * Gets the direction vector of the light from the light source to a given point.
     * For a directional light, this is always the same direction.
     *
     * @param p the point at which the direction is being calculated
     * @return the direction vector of the light
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
