package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 *
 */
public class SimpleRayTracer extends RayTracerBase{

    /**
     * @param scene
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        return null;
    }
}
