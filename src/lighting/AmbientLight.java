package lighting;

import primitives.Color;
import primitives.Double3;

/**
 *
 * @author Emanuel and Shneor
 */
public class AmbientLight {
    /**
     *
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK,0);

    final private Color intensity;

    /**
     *
     * @param Ia
     * @param Ka
     */
    public AmbientLight(Color Ia, Double3 Ka)
    {
        intensity = Ia.scale(Ka);
    }

    /**
     *
     * @param Ia
     * @param Ka
     */
    public AmbientLight(Color Ia, double Ka)
    {
        intensity = Ia.scale(Ka);
    }
    Color getIntensity()
    {
        return intensity;
    }
}
