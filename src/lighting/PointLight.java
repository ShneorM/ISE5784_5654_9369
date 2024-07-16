package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source, which has a specific position
 * in space and emits light equally in all directions from that position. The intensity
 * of the light diminishes with distance based on attenuation factors.
 *
 * @see Light
 * @see LightSource
 * @see Color
 * @see Point
 * @see Vector
 *
 * @author Shneor and Emanuel
 */
public class PointLight extends Light implements LightSource {
    private Point position;
    private double kC = 1.0, kL = 0.0, kQ = 0.0;

    /**
     * radius for soft shadows
     */
    private double radius = 0.0;
    /**
     * Constructs a PointLight with the specified intensity and position.
     *
     * @param intensity the color intensity of the light
     * @param position the position of the light in space
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor
     * @return the PointLight itself for chaining
     */
    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor
     * @return the PointLight itself for chaining
     */
    public PointLight setKL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor
     * @return the PointLight itself for chaining
     */
    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Sets the radius of the PointLight and returns the updated PointLight object.
     *
     * @param radius the radius to be set for the PointLight
     * @return the updated PointLight object with the new radius
     */
    public PointLight setRadius(double radius){
        this.radius = radius;
        return this;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = getDistance(p);
        return intensity.scale(1 / (kC + kL * d + kQ * d * d));
    }


    @Override
    public Vector getL(Point p) {
        if (p.equals(position)) {
            throw new IllegalArgumentException("The point cannot be equal to the position of the point light.");
        }
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        if (point.equals(position)) {
            throw new IllegalArgumentException("The point cannot be equal to the position of the point light.");
        }
        return point.distance(position);
    }
}
