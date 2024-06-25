package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 *
 */
public interface LightSource {
    /**
     *
     * @param p
     * @return
     */
    public Color getIntensity(Point p);

    /**
     *
     * @param p
     * @return
     */
    public Vector getL(Point p);
}
