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
    @Override
    public double getRadius() {
        return 0;
    }

    @Override
    public Point getPosition() {
        return null;
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }


    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return  Double.POSITIVE_INFINITY;
    }
}
