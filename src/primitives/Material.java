package primitives;

/**
 *
 */
public class Material {
    /**
     *
     */
    public Double3 kD=Double3.ZERO,kS=Double3.ZERO;
    /**
     *
     */
    public int nShininess=0;

    /**
     *
     * @param nShininess
     * @return
     */
    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     *
     * @param kS
     * @return
     */
    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }
    /**
     *
     * @param kS
     * @return
     */
    public Material setKS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    /**
     *
     * @param kD
     * @return
     */
    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }
    /**
     *
     * @param kD
     * @return
     */
    public Material setKD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
}
