package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import static primitives.Util.alignZero;

/**
 * SimpleRayTracer is a basic implementation of a ray tracer that extends RayTracerBase.
 * It traces rays through the scene and determines the color at each point of intersection.
 * If no intersections are found, it returns the background color of the scene.
 * If intersections are found, it calculates the color based on ambient light.
 *
 * @author Shneor and Emanuel
 * @see renderer.RayTracerBase
 * @see primitives.Ray
 * @see primitives.Color
 * @see scene.Scene
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     *
     */
    private static final double DELTA = 0.1;

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
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null
                ? scene.background
                : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * Calculates the color at a specific intersection point.
     * This includes ambient light and local effects like diffuse and specular reflections.
     *
     * @param intersection the intersection point.
     * @param ray          the ray that caused the intersection.
     * @return the color at the intersection point.
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculates the local effects (diffuse and specular reflections) at a given point.
     *
     * @param gp  the geometric point at which the effects are calculated.
     * @param ray the ray that caused the intersection.
     * @return the color including local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Material mat = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        if (nv == 0) return color;
        Color iL;
        Vector l;
        for (LightSource lightSource : scene.lights) {
            l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (alignZero(nl * nv) >= 0 && unshaded(gp, lightSource, l, l.scale(-1))) { // sign(nl) == sign(nv)
                iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(mat, nl)
                                .add(calcSpecular(mat, n, l, nl, v))));
            }
        }
        return color;
    }

    /**
     *
     */
    private static final double EPS = 0.1;

    /**
     * @param gp
     * @param light
     * @param l
     * @param n
     * @return
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n) {
        //Vector lightDirection = l.scale(-1); // from point to light source
        //Ray ray = new Ray(gp.point, lightDirection);
//        Vector epsVector = n.scale(EPS);
//        Point point = gp.point.add(n.scale(EPS));

        var intersections = scene.geometries.findIntersections(
                new Ray(gp.point.add(n.scale(EPS)), l.scale(-1)),
                light.getDistance(gp.point)
        );
        return intersections == null;
    }

    /**
     * Calculates the diffuse reflection component of the material.
     *
     * @param mat the material of the geometry.
     * @param nl  the dot product of the normal and light direction vectors.
     * @return the diffuse reflection component.
     */
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular reflection component of the material.
     *
     * @param material the material of the geometry.
     * @param n        the normal vector at the point of intersection.
     * @param l        the direction vector from the light source to the intersection point.
     * @param nl       the dot product of the normal and light direction vectors.
     * @param v        the direction vector of the ray.
     * @return the specular reflection component.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Double3 ks = material.kS;
        double vr = alignZero(v.scale(-1).dotProduct(l.subtract(n.scale(2 * nl)).normalize()));
        vr = (vr > 0) ? vr : 0; // Ensure vr is non-negative
        if (vr <= 0) return Double3.ZERO; // No specular reflection in this case

        double result = 1.0;
        for (int i = 0; i < material.nShininess; i++) {
            result *= vr;
        }

        return ks.scale(result);
    }
}
