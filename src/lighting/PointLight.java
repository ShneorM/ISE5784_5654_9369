package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 *
 */
public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC=1.0,kL=0.0,kQ=0.0;

    /**
     *
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     *
     * @param kC
     * @return
     */
    public PointLight setKC(double kC){
        this.kC=kC;
        return this;
    }

    /**
     *
     * @param kL
     * @return
     */
    public PointLight setKL(double kL){
        this.kL=kL;
        return this;
    }

    /**
     *
     * @param kQ
     * @return
     */
    public PointLight setKQ(double kQ){
        this.kQ=kQ;
        return this;
    }
    @Override
    public Color getIntensity(Point p) {
        double d=position.distance(p);
        return intensity.scale(1/(kC+kL*d+kQ*d*d));
    }

    @Override
    public Vector getL(Point p) {
        if(p.equals(position))
            throw new IllegalArgumentException("can't have the point be equal to the position of the point light");
        return position.subtract(p).normalize();
    }
}
