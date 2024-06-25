package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;

public class SpotLight extends PointLight {
    private Vector direction;

    /**
     *
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    @Override
    public Color getIntensity(Point point){
        //the direction is always normalized
        return super.getIntensity(point).scale(max(0,getL(point).dotProduct(direction)));
    }
}
