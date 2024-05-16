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
 *
 */
class RayIntegrationTest {
    private static final int NX = 3;
    private static final int NY = 3;

    /**
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
    void testSphere() {
    }

}
