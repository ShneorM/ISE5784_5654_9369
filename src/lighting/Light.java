package lighting;

import primitives.Color;

/**
 *
 */
abstract class Light {
    /**
     *
     */
    protected Color intensity;

    /**
     *
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     *
     * @return
     */
    public Color getIntensity() {
        return intensity;
    }
}
