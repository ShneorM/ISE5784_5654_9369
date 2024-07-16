/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import geometries.Polygon;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
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

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() throws CloneNotSupportedException {
        scene.geometries.add(
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setNShininess(20)
                                .setKT(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
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

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() throws CloneNotSupportedException {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60)),
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
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


    /**
     * Creating a ping pong table with reflections and transparencies
     *
     * @throws CloneNotSupportedException
     */
    @Test
    public void createPingPongTableScene() throws CloneNotSupportedException {
        final Scene scene = new Scene("Ping Pong Table Scene");

        // Define colors
        Color greenColor = new Color(0, 100, 0);
        Color grayColor = new Color(105, 105, 105);
        Color whiteColor = new Color(255, 255, 255);
        Color orangeColor = new Color(255, 165, 0);
        Color lampColor = new Color(139, 0, 0); // Red color for the lamp

        double tableHeight = 20;
        // Create ping pong table
        Polygon table = (Polygon) new Polygon(
                new Point(-50, tableHeight, -100),
                new Point(50, tableHeight, -100),
                new Point(50, tableHeight, 100),
                new Point(-50, tableHeight, 100)
        ).setEmission(greenColor)
                .setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(20).setKR(0.2));
        scene.geometries.add(table);

        // Create net
        Polygon net = (Polygon) new Polygon(
                new Point(-50, tableHeight, 0),
                new Point(50, tableHeight, 0),
                new Point(50, tableHeight + 10, 0),
                new Point(-50, tableHeight + 10, 0)
        ).setEmission(Color.BLACK)
                .setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(20).setKT(0.3));
        scene.geometries.add(net);

        // Create ping pong balls (spheres)
        Sphere ball1 = (Sphere) new Sphere(3, new Point(-20, tableHeight + 10, -50))
                .setEmission(orangeColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.7)).setEmission(new Color(255, 140, 0));
        Sphere ball3 = (Sphere) new Sphere(6, new Point(-20, tableHeight + 10, -50))
                .setMaterial(new Material().setKD(0.01).setKS(0.01).setNShininess(100).setKT(0.8)).setEmission(new Color(255, 140, 0));

        // Add light source inside the ball1
        scene.lights.add(new PointLight(new Color(255, 140, 0), new Point(-20, tableHeight + 10, -50))
                .setKL(0.001).setKQ(0.0005));

        Sphere ball2 = (Sphere) new Sphere(3, new Point(20, tableHeight + 10, 50))
                .setEmission(new Color(107, 113, 112))
                .setMaterial(new Material().setKD(0.05).setKS(0.05).setNShininess(100).setKR(0.5));
        scene.geometries.add(ball1, ball2, ball3);

        // Define room dimensions
        double roomWidth = 200;
        double roomHeight = 100;
        double roomDepth = 300;

        // Create room walls (polygons)
        Polygon leftWall = (Polygon) new Polygon(
                new Point(-roomWidth / 2, 0, -roomDepth / 2),
                new Point(-roomWidth / 2, roomHeight, -roomDepth / 2),
                new Point(-roomWidth / 2, roomHeight, roomDepth / 2),
                new Point(-roomWidth / 2, 0, roomDepth / 2)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.1).setKS(0.2).setNShininess(20).setKR(0.3));
        scene.geometries.add(leftWall);

        Polygon rightWall = (Polygon) new Polygon(
                new Point(roomWidth / 2, 0, -roomDepth / 2),
                new Point(roomWidth / 2, roomHeight, -roomDepth / 2),
                new Point(roomWidth / 2, roomHeight, roomDepth / 2),
                new Point(roomWidth / 2, 0, roomDepth / 2)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.1).setKS(0.2).setNShininess(20).setKR(0.3));
        scene.geometries.add(rightWall);

        Polygon backWall = (Polygon) new Polygon(
                new Point(-roomWidth / 2, 0, roomDepth / 2),
                new Point(-roomWidth / 2, roomHeight, roomDepth / 2),
                new Point(roomWidth / 2, roomHeight, roomDepth / 2),
                new Point(roomWidth / 2, 0, roomDepth / 2)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.1).setKS(0.2).setNShininess(20).setKR(0.3));
        scene.geometries.add(backWall);

        Polygon frontWall = (Polygon) new Polygon(
                new Point(-roomWidth / 2, 0, -roomDepth / 2),
                new Point(-roomWidth / 2, roomHeight, -roomDepth / 2),
                new Point(roomWidth / 2, roomHeight, -roomDepth / 2),
                new Point(roomWidth / 2, 0, -roomDepth / 2)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.1).setKS(0.2).setNShininess(20).setKR(0.3));
        scene.geometries.add(frontWall);

        Polygon ceiling = (Polygon) new Polygon(
                new Point(-roomWidth / 2, roomHeight, -roomDepth / 2),
                new Point(-roomWidth / 2, roomHeight, roomDepth / 2),
                new Point(roomWidth / 2, roomHeight, roomDepth / 2),
                new Point(roomWidth / 2, roomHeight, -roomDepth / 2)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(20).setKR(0.5));
        scene.geometries.add(ceiling);

        // Create floor polygon with gray color
        Plane floor = (Plane) new Plane(
                new Point(-roomWidth / 2, 0, -roomDepth / 2),
                new Point(-roomWidth / 2, 0, roomDepth / 2),
                new Point(roomWidth / 2, 0, roomDepth / 2)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(20));
        scene.geometries.add(floor);

        // <editor-fold desc="legs">
        // Create table legs
        double legWidth = 2;
        double legDepth = 2;

        // Four legs positioned at the corners of the table
        Polygon leg1 = (Polygon) new Polygon(
                new Point(-50 + legWidth, 0, -100 + legDepth),
                new Point(-50 + legWidth, tableHeight, -100 + legDepth),
                new Point(-50, tableHeight, -100),
                new Point(-50, 0, -100)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.1).setKS(0.2).setNShininess(20));
        scene.geometries.add(leg1);

        Polygon leg2 = (Polygon) new Polygon(
                new Point(50 - legWidth, 0, -100 + legDepth),
                new Point(50 - legWidth, tableHeight, -100 + legDepth),
                new Point(50, tableHeight, -100),
                new Point(50, 0, -100)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.1).setKS(0.2).setNShininess(20));
        scene.geometries.add(leg2);

        Polygon leg3 = (Polygon) new Polygon(
                new Point(-50 + legWidth, 0, 100 - legDepth),
                new Point(-50 + legWidth, tableHeight, 100 - legDepth),
                new Point(-50, tableHeight, 100),
                new Point(-50, 0, 100)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.1).setKS(0.2).setNShininess(20));
        scene.geometries.add(leg3);

        Polygon leg4 = (Polygon) new Polygon(
                new Point(50 - legWidth, 0, 100 - legDepth),
                new Point(50 - legWidth, tableHeight, 100 - legDepth),
                new Point(50, tableHeight, 100),
                new Point(50, 0, 100)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.1).setKS(0.2).setNShininess(20));
        scene.geometries.add(leg4);
        // </editor-fold>

// Create lamp on the ceiling using 4 triangles
        Point lampTop = new Point(0, roomHeight - 10, 0); // Top point of the lamp
        Point lampBase1 = new Point(-5, roomHeight, -5);
        Point lampBase2 = new Point(5, roomHeight, -5);
        Point lampBase3 = new Point(5, roomHeight, 5);
        Point lampBase4 = new Point(-5, roomHeight, 5);

        // Create triangles for the lamp
        scene.geometries.add(new Triangle(lampTop, lampBase1, lampBase2).setEmission(lampColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.3)));
        scene.geometries.add(new Triangle(lampTop, lampBase2, lampBase3).setEmission(lampColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.3)));
        scene.geometries.add(new Triangle(lampTop, lampBase3, lampBase4).setEmission(lampColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.3)));
        scene.geometries.add(new Triangle(lampTop, lampBase4, lampBase1).setEmission(lampColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.3)));


        // Set ambient light suitable for enclosed room
        scene.setAmbientLight(new AmbientLight(new Color(50, 50, 50), 0.2)); // Dim ambient light

        // Add spot light
        scene.lights.add(new SpotLight(new Color(400, 200, 200), new Point(0, roomHeight - 10, 50), new Vector(0, -1, -1))
                .setKL(1E-4).setKQ(1.5E-7));

        // Set up the camera
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), Vector.Y)
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, roomHeight / 2, roomDepth / 2 - 50)) // Adjust camera position
                .setVpDistance(150)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("pingPongTable", 600, 600));

        Camera cam = cameraBuilder.build();

        // Render the image
        cam.renderImage();
        cam.writeToImage();
    }



}

