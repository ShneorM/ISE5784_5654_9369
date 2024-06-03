package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 *
 * @author Shneor and Emanuel
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     *
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     *
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);
}
