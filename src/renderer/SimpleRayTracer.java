package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * SimpleRayTracer is a basic implementation of a ray tracer that extends RayTracerBase.
 * It traces rays through the scene and determines the color at each point of intersection.
 * If no intersections are found, it returns the background color of the scene.
 * If intersections are found, it calculates the color based on ambient light.
 *
 * @see renderer.RayTracerBase
 * @see primitives.Ray
 * @see primitives.Color
 * @see scene.Scene
 *
 * @author Shneor and Emanuel
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a SimpleRayTracer with the specified scene.
     *
     * @param scene the scene to be rendered.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray through the scene and determines the color at the point of intersection.
     * If no intersections are found, the background color is returned.
     *
     * @param ray the ray to be traced.
     * @return the color at the point of intersection, or the background color if no intersections are found.
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findIntersections(ray);
        if (intersections == null)
            return this.scene.background;
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color at the given point based on the ambient and other lights in the scene.
     *
     * @param point the point at which to calculate the color.
     * @return the color at the given point.
     */
    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }
}
