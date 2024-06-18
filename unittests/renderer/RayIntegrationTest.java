package renderer;

import geometries.Geometry;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing RayIntegration Class for various geometries such as Sphere, Triangle, and Plane.
 * The tests ensure that the number of intersections found by rays cast from the camera match the expected values.
 *
 * @see renderer.Camera
 * @see geometries.Sphere
 * @see geometries.Triangle
 * @see geometries.Plane
 * @see primitives.Ray
 * @see scene.Scene
 *
 * @author Emanuel and Shneor
 */
class RayIntegrationTest {
    private static final int NX = 3; // Number of horizontal pixels in the view plane.
    private static final int NY = 3; // Number of vertical pixels in the view plane.

    /**
     * Returns the number of intersection points between rays cast from a camera and a geometry object.
     *
     * @param camera   the Camera object used to cast the rays
     * @param geometry the Geometry object to intersect with the rays
     * @return the number of intersection points found
     */
    int rays(Camera camera, Geometry geometry) {
        List<Point> intersects = new LinkedList<>(); // Initialize an empty list to store intersection points.
        // Iterate over all the pixels in the camera's view plane.
        for (int i = 0; i < NX; i++) {
            for (int j = 0; j < NY; j++) {
                // Construct a ray from the camera through the current pixel.
                Ray ray = camera.constructRay(NX, NY, i, j);
                // Find all intersection points between the ray and the geometry.
                List<Point> points = geometry.findIntersections(ray);
                if (points != null) {
                    intersects.addAll(points);
                }
            }
        }
        // Return the total number of intersection points found.
        return intersects.size();
    }

    /**
     * Test method for intersections with spheres.
     *
     * @throws CloneNotSupportedException if cloning is not supported
     */
    @Test
    void testSphere() throws CloneNotSupportedException {
        // TC01: Testing a sphere directly in front of the camera
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        Camera camera = Camera.getBuilder()
                .setImageWriter(new ImageWriter("test", 1, 1))
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setLocation(new Point(0, 0, 0))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(2, rays(camera, sphere), "Wrong number of intersections (sphere test 01)");

        // TC02: Testing a larger sphere that intersects multiple rays
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        camera = Camera.getBuilder()
                .setImageWriter(new ImageWriter("test", 1, 1))
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(18, rays(camera, sphere), "Wrong number of intersections (sphere test 02)");

        // TC03: Testing a sphere with adjusted camera position
        sphere = new Sphere(2, new Point(0, 0, -2));
        camera = Camera.getBuilder()
                .setImageWriter(new ImageWriter("test", 1, 1))
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(10, rays(camera, sphere), "Wrong number of intersections (sphere test 03)");

        // TC04: Testing a sphere that intersects many rays
        sphere = new Sphere(4, new Point(0, 0, -2));
        camera = Camera.getBuilder()
                .setImageWriter(new ImageWriter("test", 1, 1))
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(9, rays(camera, sphere), "Wrong number of intersections (sphere test 04)");

        // TC05: Testing a sphere that does not intersect with rays
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        camera = Camera.getBuilder()
                .setImageWriter(new ImageWriter("test", 1, 1))
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(0, rays(camera, sphere), "Wrong number of intersections (sphere test 05)");
    }

    /**
     * Test method for intersections with triangles.
     *
     * @throws CloneNotSupportedException if cloning is not supported
     */
    @Test
    void testTriangle() throws CloneNotSupportedException {
        // TC01: Testing a triangle that does not intersect with any rays
        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        Camera camera = Camera.getBuilder()
                .setImageWriter(new ImageWriter("test", 1, 1))
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setLocation(new Point(0, 0, 0))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(1, rays(camera, triangle), "Failed to find all intersection points with the triangle (triangle test 01)");

        // TC02: Testing a triangle that intersects with multiple rays
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, rays(camera, triangle), "Failed to find all intersection points with the triangle (triangle test 02)");
    }

    /**
     * Test method for intersections with planes.
     *
     * @throws CloneNotSupportedException if cloning is not supported
     */
    @Test
    void testPlane() throws CloneNotSupportedException {
        // TC01: Testing a plane parallel to the view plane
        Plane plane = new Plane(new Point(0, 0, -1), new Vector(0, 0, 2));
        Camera camera = Camera.getBuilder()
                .setImageWriter(new ImageWriter("test", 1, 1))
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setLocation(new Point(0, 0, 0))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(9, rays(camera, plane), "Failed to find all intersection points with the plane (plane test 01)");

        // TC02: Testing a plane at an angle to the view plane
        plane = new Plane(new Point(0, 0, -1), new Vector(0, 2, 4));
        assertEquals(9, rays(camera, plane), "Failed to find all intersection points with the plane (plane test 02)");

        // TC03: Testing a plane at a different angle to the view plane
        plane = new Plane(new Point(0, 0, -1), new Vector(0, 2, 2));
        assertEquals(6, rays(camera, plane), "Failed to find all intersection points with the plane (plane test 03)");
    }
}
