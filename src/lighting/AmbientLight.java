package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient lighting in a scene.
 * Ambient light is a global light source that affects all objects equally
 * and adds a base level of illumination to the scene.
 *
 * <p>This class includes methods for creating ambient light with varying intensities
 * and provides a way to retrieve the intensity of the ambient light.</p>
 *
 * @author Emanuel and Shneor
 */
public class AmbientLight extends Light {
    /**
     * A constant representing no ambient light.
     * This can be used to indicate that no ambient light is present in the scene.
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);


    /**
     * Constructs an AmbientLight object with the specified intensity and attenuation factor.
     *
     * @param Ia The base color intensity of the ambient light.
     * @param Ka The attenuation factor represented as a Double3 object. This scales the intensity.
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Constructs an AmbientLight object with the specified intensity and attenuation factor.
     *
     * @param Ia The base color intensity of the ambient light.
     * @param Ka The attenuation factor represented as a double. This scales the intensity.
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Returns the intensity of the ambient light.
     *
     * @return The intensity of the ambient light as a Color object.
     */
    public Color getIntensity() {
        return intensity;
    }
}
