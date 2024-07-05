package primitives;

/**
 * The Material class represents the material properties of a geometric object.
 * It includes properties for diffuse and specular reflection coefficients, as well as shininess.
 * These properties are used in lighting calculations to determine how the material interacts with light.
 *
 * @author Emanuel and Shneor
 * @see Double3
 */
public class Material {
    /**
     * The diffuse reflection coefficient.
     * Represents the proportion of the light that is diffusely reflected from the surface.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * The specular reflection coefficient.
     * Represents the proportion of the light that is specularly reflected from the surface.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * The transparency coefficient.
     * Represents the proportion of the light that is transmitted through the surface.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The reflection coefficient.
     * Represents the proportion of the light that is reflected from the surface.
     */
    public Double3 kR = Double3.ZERO;

    /**
     * Sets the reflection coefficient using a Double3 value.
     *
     * @param kR the reflection coefficient to set
     * @return the Material itself for chaining
     */
    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Sets the reflection coefficient using a double value.
     *
     * @param kR the reflection coefficient to set
     * @return the Material itself for chaining
     */
    public Material setKR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Sets the transparency coefficient using a Double3 value.
     *
     * @param kT the transparency coefficient to set
     * @return the Material itself for chaining
     */
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Sets the transparency coefficient using a double value.
     *
     * @param kT the transparency coefficient to set
     * @return the Material itself for chaining
     */
    public Material setKT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * The shininess factor.
     * Controls the sharpness of the specular highlight.
     */
    public int nShininess = 0;

    /**
     * Sets the shininess factor of the material.
     *
     * @param nShininess the shininess factor to set
     * @return the Material itself for chaining
     */
    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Sets the specular reflection coefficient using a Double3 value.
     *
     * @param kS the specular reflection coefficient to set
     * @return the Material itself for chaining
     */
    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient using a double value.
     *
     * @param kS the specular reflection coefficient to set
     * @return the Material itself for chaining
     */
    public Material setKS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient using a Double3 value.
     *
     * @param kD the diffuse reflection coefficient to set
     * @return the Material itself for chaining
     */
    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient using a double value.
     *
     * @param kD the diffuse reflection coefficient to set
     * @return the Material itself for chaining
     */
    public Material setKD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
}
