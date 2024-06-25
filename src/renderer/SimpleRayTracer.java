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
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(intersection, ray));
    }

    /**
     * @param gp
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Material mat = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        if (nv == 0) return color;
        Color iL;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (alignZero(nl * nv) >= 0) { // sign(nl) == sign(nv)
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
     * @param mat
     * @param nl
     * @return
     */
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.kD.scale(Math.abs(nl));
    }

    /**
     *
     * @param material
     * @param n
     * @param l
     * @param nl
     * @param v
     * @return
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Double3 ks = material.kS;


        //Vector r = l.subtract(n.scale(2 * nl)).normalize();
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
