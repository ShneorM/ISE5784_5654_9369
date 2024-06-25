package lighting;

import primitives.Color;

/**
 * The Light class serves as an abstract base class for various types of light sources.
 * It encapsulates the common property of light intensity, which is represented by a color.
 * Subclasses of Light will specify additional properties and behaviors for different types of light sources.
 * @author Shneor and Emanuel
 * @see Color
 */
abstract class Light {
    /**
     * The intensity of the light, represented as a color.
     */
    protected Color intensity;

    /**
     * Constructs a Light with the specified intensity.
     *
     * @param intensity the color intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the intensity of the light.
     *
     * @return the color intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
