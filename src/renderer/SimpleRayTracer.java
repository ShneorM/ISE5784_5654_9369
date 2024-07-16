package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
     * Initial attenuation factor for global effects.
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Maximum recursion level for color calculations.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * Minimum attenuation factor for global effects.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

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
    public Color traceRay(Ray ray,int numberOfSamples) {
        GeoPoint closestGeoPoint = findClosestIntersection(ray);
        return closestGeoPoint == null
                ? scene.background
                : calcColor(closestGeoPoint, ray,numberOfSamples);
    }

    /**
     * Calculates the color at a given geometric point considering local and global effects.
     *
     * @param closestGeoPoint the closest geometric point where the ray intersects.
     * @param ray             the ray that caused the intersection.
     * @return the calculated color at the intersection point.
     */
    private Color calcColor(GeoPoint closestGeoPoint, Ray ray,int numberOFSamples) {
        return calcColor(closestGeoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K,numberOFSamples).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at a given geometric point considering local and global effects.
     *
     * @param gp    the geometric point.
     * @param ray   the ray that caused the intersection.
     * @param level the current recursion level.
     * @param k     the attenuation factor.
     * @return the calculated color at the intersection point.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k,int numberOfSamples) {
        Color color = calcLocalEffects(gp, ray, k, numberOfSamples);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k,numberOfSamples));
    }

    /**
     * Calculates the global effects (reflection and refraction) at a given point.
     *
     * @param gp    the geometric point.
     * @param ray   the ray that caused the intersection.
     * @param level the current recursion level.
     * @param k     the attenuation factor.
     * @return the calculated color including global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k,int numberOfSamples) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kT, level, k,numberOfSamples)
                .add(calcGlobalEffect(constructReflectedRay(gp, ray), material.kR, level, k,numberOfSamples));
    }

    /**
     * Calculates the global effect for a single ray (either reflection or refraction).
     *
     * @param ray   the reflected or refracted ray.
     * @param kx    the attenuation factor for the effect.
     * @param level the current recursion level.
     * @param k     the combined attenuation factor.
     * @return the calculated color for the global effect.
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k,int numberOfSamples) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx,numberOfSamples))
                .scale(kx);
    }

    /**
     * Constructs the refracted ray at a given geometric point.
     *
     * @param gp  the geometric point.
     * @param ray the incident ray.
     * @return the refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
        Vector n = gp.getNormal();
        return new Ray(gp.point, ray.getDirection(), n);
    }

    /**
     * Constructs the reflected ray at a given geometric point.
     *
     * @param gp  the geometric point.
     * @param ray the incident ray.
     * @return the reflected ray.
     */
    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
        Vector n = gp.getNormal();
        double nv = alignZero(n.dotProduct(ray.getDirection()));
        return new Ray(gp.point, (!isZero(nv)) ? ray.getDirection().subtract(n.scale(2 * nv)) : ray.getDirection(), n);
    }

    /**
     * Finds the closest intersection point of a ray with the geometries in the scene.
     *
     * @param ray the ray to be traced.
     * @return the closest intersection point, or null if no intersections are found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * Calculates the local effects (diffuse and specular reflections) at a given point.
     *
     * @param gp  the geometric point at which the effects are calculated.
     * @param ray the ray that caused the intersection.
     * @param k   the attenuation factor.
     * @return the color including local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k,int numberOfSamples) {
        Vector n = gp.getNormal();
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Material mat = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        if (nv == 0) return color;
        Color iL;
        Vector l;
        Double3 ktr;
        for (LightSource lightSource : scene.lights) {
            l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (alignZero(nl * nv) > 0) { // sign(nl) == sign(nv)
                ktr = transparency(gp, lightSource, l, n,numberOfSamples);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(mat, nl)
                                    .add(calcSpecular(mat, n, l, nl, v))));
                }
            }
        }
        return color;
    }



    /**
     * calculates the transparency of a point getting light from a light source
     *
     * @param gp    the point
     * @param light the light source
     * @param l     direction vector from light source to point
     * @param n     normal vector of geometry from point
     * @return the transparency of the point as Double3 (rgb)
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        //Vector lightDirection = l.scale(-1); // from point to light source
        //double lightSourceDistance = light.getDistance(gp.point);
        return getTransparencyFromPoint(gp.point, n, l.scale(-1), light.getDistance(gp.point));
    }

    /**
     * calculates the transparency and shadow from single point light source
     *
     * @param point               the point
     * @param light               direction vector from point to light source
     * @param n                   normal vector of geometry from point
     * @param lightSourceDistance distance from point to light source
     * @return the transparency of the point as Double3 (rgb)
     */
    private Double3 getTransparencyFromPoint(Point point, Vector n, Vector light, double lightSourceDistance) {
        
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections( new Ray(point, light, n), lightSourceDistance);

        Double3 ktr = Double3.ONE;
        if (intersections == null)
            return ktr;

        for (GeoPoint intersection : intersections) {
            ktr = ktr.product(intersection.geometry.getMaterial().kT);
        }
        return ktr;
    }

    /**
     * calculates the transparency of a point getting light from a light source - when using soft shadow
     *
     * @param gp              the point
     * @param lightSource     the light source
     * @param l               direction vector from light source to point
     * @param n               normal vector of geometry from point
     * @param numberOfSamples amount of samples for super-sampling
     * @return the transparency of the point as Double3 (rgb)
     */
    private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n, int numberOfSamples) {
        if (lightSource.getRadius() == 0||lightSource.getPosition()==null||numberOfSamples<=1)
            return transparency(gp, lightSource, l, n);
        Vector orthogonalV = l.createOrthogonal();

        Board board = new Board(lightSource.getPosition(), orthogonalV, orthogonalV.crossProduct(l), lightSource.getRadius() * 2).setCircle(true);
        //calculate the average of the ktrs
        List<Point> points = board.getPoints(numberOfSamples);
        Double3 ktr = Double3.ZERO;
        for (Point point : points) {
           // lightDirection = point.subtract(gp.point).normalize();
            ktr = ktr.add(getTransparencyFromPoint(gp.point, n, /*lightDirection*/ point.subtract(gp.point).normalize(), gp.point.distance(point)));
        }
        return ktr.reduce(points.size());
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
