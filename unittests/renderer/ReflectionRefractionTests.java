/**
 *
 */
package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
    /** Scene for the tests */
    private final Scene scene = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheres() throws CloneNotSupportedException {
        scene.geometries.add(
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.4).setKS(0.3).setNShininess(100).setKT(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKL(0.0004).setKQ(0.0000006));

        Camera cam = cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheresOnMirrors() throws CloneNotSupportedException {
        scene.geometries.add(
                new Sphere(400d,new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setNShininess(20)
                                .setKD(new Double3(0.5, 0, 0))),
                new Sphere(200d,new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setNShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setKL(0.00001).setKQ(0.000005));

        Camera cam = cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build();
                cam.renderImage();
                cam.writeToImage();
    }

    /** Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow */
    @Test
    public void trianglesTransparentSphere() throws CloneNotSupportedException {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60)),
                new Sphere(30d,new Point(60, 50, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.2).setKS(0.2).setNShininess(30).setKT(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKL(4E-5).setKQ(2E-7));

        Camera cam = cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build();
                cam.renderImage();
                cam.writeToImage();
    }
}
