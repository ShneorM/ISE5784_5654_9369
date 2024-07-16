package lighting;

import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    /**
     * First scene for some of tests
     */
    private final Scene scene1 = new Scene("Test scene");
    /**
     * Second scene for some of tests
     */
    private final Scene scene2 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

    /**
     * First camera builder for some of tests
     */
    private final Camera.Builder camera1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene1))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0, 0, -1), Vector.Y)
            .setVpSize(150, 150).setVpDistance(1000);
    /**
     * Second camera builder for some of tests
     */
    private final Camera.Builder camera2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene2))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0, 0, -1), Vector.Y)
            .setVpSize(200, 200).setVpDistance(1000);

    /**
     * Shininess value for most of the geometries in the tests
     */
    private static final int SHININESS = 301;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final double KD = 0.5;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final double KS = 0.5;
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

    /**
     * Material for some of the geometries in the tests
     */
    private final Material material = new Material().setKD(KD3).setKS(KS3).setNShininess(SHININESS);
    /**
     * Light color for tests with triangles
     */
    private final Color trianglesLightColor = new Color(800, 500, 250);
    /**
     * Light color for tests with sphere
     */
    private final Color sphereLightColor = new Color(800, 500, 0);
    /**
     * Color of the sphere
     */
    private final Color sphereColor = new Color(BLUE).reduce(2);

    /**
     * Center of the sphere
     */
    private final Point sphereCenter = new Point(0, 0, -50);
    /**
     * Radius of the sphere
     */
    private static final double SPHERE_RADIUS = 50d;

    /**
     * The triangles' vertices for the tests with triangles
     */
    private final Point[] vertices =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    /**
     * Position of the light in tests with sphere
     */
    private final Point sphereLightPosition = new Point(-50, -50, 25);
    /**
     * Light direction (directional and spot) in tests with sphere
     */
    private final Vector sphereLightDirection = new Vector(1, 1, -0.5);
    /**
     * Position of the light in tests with triangles
     */
    private final Point trianglesLightPosition = new Point(30, 10, -100);
    /**
     * Light direction (directional and spot) in tests with triangles
     */
    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);

    /**
     * The sphere in appropriate tests
     */
    private final Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter)
            .setEmission(sphereColor).setMaterial(new Material().setKD(KD).setKS(KS).setNShininess(SHININESS));
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() throws CloneNotSupportedException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(sphereLightColor, sphereLightDirection));

        Camera cam = camera1.setImageWriter(new ImageWriter("lightSphereDirectional", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() throws CloneNotSupportedException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
                .setKL(0.001).setKQ(0.0002));

        Camera cam = camera1.setImageWriter(new ImageWriter("lightSpherePoint", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void sphereSpot() throws CloneNotSupportedException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, sphereLightDirection)
                .setKL(0.001).setKQ(0.0001));

        Camera cam = camera1.setImageWriter(new ImageWriter("lightSphereSpot", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() throws CloneNotSupportedException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

        Camera cam = camera2.setImageWriter(new ImageWriter("lightTrianglesDirectional", 500, 500)) //
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() throws CloneNotSupportedException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setKL(0.001).setKQ(0.0002));

        Camera cam = camera2.setImageWriter(new ImageWriter("lightTrianglesPoint", 500, 500)) //
                .build();//
        cam.renderImage(); //
        cam.writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight
     */
    @Test
    public void trianglesSpot() throws CloneNotSupportedException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKL(0.001).setKQ(0.0001));

        Camera cam = camera2.setImageWriter(new ImageWriter("lightTrianglesSpot", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a narrow spotlight
     */
    @Test
    public void sphereSpotSharp() throws CloneNotSupportedException {
        scene1.geometries.add(sphere);
        scene1.lights
                .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                        .setNarrowBeam(10).setKL(0.001).setKQ(0.00004));

        Camera cam = camera1.setImageWriter(new ImageWriter("lightSphereSpotSharp", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a narrow spotlight
     */
    @Test
    public void trianglesSpotSharp() throws CloneNotSupportedException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setNarrowBeam(20).setKL(0.001).setKQ(0.00004));

        Camera cam = camera2.setImageWriter(new ImageWriter("lightTrianglesSpotSharp", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a narrow spotlight and a point light
     */
    @Test
    public void sphereMultiLight() throws CloneNotSupportedException {
        scene1.geometries.add(sphere);
        scene1.lights
                .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                        .setNarrowBeam(10).setKL(0.001).setKQ(0.00004));
        scene1.lights.add(new PointLight(new Color(1000,10,0), sphereLightPosition.add(new Vector(100,100,100))).setKL(0.001).setKQ(0.00004));

        Camera cam = camera1.setImageWriter(new ImageWriter("lightSphereMultiLight", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by two point lights
     */
    @Test
    public void trianglesMultiColor() throws CloneNotSupportedException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setKL(0.001).setKQ(0.0002));
        scene2.lights.add(new PointLight(new Color(1203,0,0),trianglesLightPosition.add(new Vector(0,0,50))));
        Camera cam = camera2.setImageWriter(new ImageWriter("lightTrianglesMultiColor", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }
}
