package renderer;

import geometries.Geometry;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing RayIntegration Class
 *
 * @author Dan, Emanuel and Shneor
 */
class RayIntegrationTest {
    private static final int NX = 3;
    private static final int NY = 3;

    /**
     *
     * @param camera
     * @param geometry
     * @return
     */
    int rays(Camera camera, Geometry geometry) {
        List<Point> intersects = new LinkedList<Point>();// Initialize an empty list to store intersection points.
        // Iterate over all the pixels in the camera's view plane.
        for (int i = 0; i < NX; i++) {
            for (int j = 0; j < NY; j++) {
                // Construct a ray from the camera through the current pixel.
                Ray ray = camera.constructRay(NX, NY, i, j);
                // Find all intersection points between the ray and the geometry.
                List<Point> points = geometry.findIntersections(ray);
                intersects.addAll(points);//if is empty this will not add anything
            }
        }
        // Return the total number of intersection points found.
        return intersects.size();
    }



    @Test
    void testSphere() throws CloneNotSupportedException {
        //TC01:
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        Camera camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0))
                .setDirection(new Vector(0, -1, 0), new Vector(0, 0, -1))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();

        assertEquals(2, rays(camera, sphere), "wrong number of Intersections (sphere test 01)");

        //TC02:
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, -1, 0), new Vector(0, 0, -1))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(18, rays(camera, sphere), "wrong number of Intersections (sphere test 02)");

        //TC03:
        sphere = new Sphere(2, new Point(0, 0, -2));
        camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, -1, 0), new Vector(0, 0, -1))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(10, rays(camera, sphere), "wrong number of Intersections (sphere test 03)");

        //TC04:
        sphere = new Sphere(4, new Point(0, 0, -2));
        camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, -1, 0), new Vector(0, 0, -1))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(9, rays(camera, sphere), "wrong number of Intersections (sphere test 04)");

        //TC05:
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, -1, 0), new Vector(0, 0, -1))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(0, rays(camera, sphere), "wrong number of Intersections (sphere test 05)");
    }

    @Test
    void testTriangle() throws CloneNotSupportedException {
        //TC01:
        Triangle triangle = new Triangle(new Point(0, -1, -2), new Point(1, -1, -3), new Point(-1, -1, -2));
        Camera camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0))
                .setDirection(new Vector(0, -1, 0), new Vector(0, 0, -1))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(0, rays(camera, triangle), "Failed to find all intersection point with the triangle (triangle test 01)");

        //TC02:
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));

        assertEquals(2, rays(camera, triangle), "Failed to find all intersection point with the triangle (triangle test 02)");

    }

    @Test
    void testPlane() throws CloneNotSupportedException {
        //TC01:
        Plane plane = new Plane(new Point(0, 0, -1), new Vector(0, 0, 2));
        Camera camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0))
                .setDirection(new Vector(0, -1, 0), new Vector(0, 0, -1))
                .setVpSize(NX, NY)
                .setVpDistance(1)
                .build();
        assertEquals(9, rays(camera,plane),"Failed to find all intersection point with the plane (plane test 01)");

        //TC02:
        plane = new Plane(new Point(0, 0, -1), new Vector(0, 2, 4));
        assertEquals(9, rays(camera, plane), "Failed to find all intersection point with the plane (plane test 02)");

        //TC03:
        plane = new Plane(new Point(0, 0, -1), new Vector(0, 2, 2));
        assertEquals(6, rays(camera,plane),"Failed to find all intersection point with the plane (plane test 03)");
    }


}
