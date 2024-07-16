package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBase class serves as an abstract base class for ray tracing in a 3D scene.
 * Ray tracing is a technique for rendering images by simulating the way rays of light interact with objects in the scene.
 * Subclasses of RayTracerBase are expected to implement the {@code traceRay} method, which determines the color seen along a given ray.
 *
 * <p>This class holds a reference to the {@link scene.Scene} object, which contains all the objects and light sources in the scene.</p>
 *
 * @see primitives.Ray
 * @see primitives.Color
 * @see scene.Scene
 *
 * @author Shneor and Emanuel
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the specified scene.
     *
     * @param scene the scene to be rendered.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray through the scene and determines the color seen along the ray.
     * This method must be implemented by subclasses to provide the specific ray tracing algorithm.
     *
     * @param ray the ray to be traced.
     * @return the color seen along the ray.
     */
    public abstract Color traceRay(Ray ray,int numberOfSamples);
}
