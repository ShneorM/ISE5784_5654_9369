package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.BoundingBox class
 *
 * @author  Shneor and Emanuel
 */
class BoundingBoxTest {

    /**
     * Testing the method {@link geometries.BoundingBox#BoundingBox(double, double, double, double, double, double)} (double, double, double, double, double, double)}
     */
    @Test
    void testConstructor() {
// Create a BoundingBox
        BoundingBox boundingBox = new BoundingBox(1, 3, 2, 5, -1, 4);

        // Check min and max values
        assertEquals(1, boundingBox.getMinX(), "Min X value is incorrect");
        assertEquals(3, boundingBox.getMaxX(), "Max X value is incorrect");
        assertEquals(2, boundingBox.getMinY(), "Min Y value is incorrect");
        assertEquals(5, boundingBox.getMaxY(), "Max Y value is incorrect");
        assertEquals(-1, boundingBox.getMinZ(), "Min Z value is incorrect");
        assertEquals(4, boundingBox.getMaxZ(), "Max Z value is incorrect");
    }

    /**
     * Testing the method {@link geometries.BoundingBox#intersectBV(Ray)}
     */
    @Test
    void testIntersectBV() {
        // Create a BoundingBox
        BoundingBox boundingBox = new BoundingBox(1, 3, 2, 5, -1, 4);

        // Create a Ray that intersects the bounding box
        Ray intersectingRay = new Ray(new Point(0, 3, 2), new Vector(1, 0, 0));

        // Create a Ray that does not intersect the bounding box
        Ray nonIntersectingRay1 = new Ray(new Point(-5, -5, -5), new Vector(-1, -1, -1));
        Ray nonIntersectingRay2 = new Ray(Point.ZERO, new Vector(5,1,3));

        // Test intersection
        assertTrue(boundingBox.intersectBV(intersectingRay), "Ray should intersect the bounding box");
        assertFalse(boundingBox.intersectBV(nonIntersectingRay1), "Ray should not intersect the bounding box");
        assertFalse(boundingBox.intersectBV(nonIntersectingRay2), "Ray should not intersect the bounding box");
    }

    /**
     * Testing the method {@link geometries.BoundingBox#intersectBV(Ray, double)}
     */
    @Test
    void testIntersectBVWithDistance() {
        // Create a BoundingBox
        BoundingBox boundingBox = new BoundingBox(1, 3, 2, 5, -1, 4);

        // Create a Ray that intersects the bounding box
        Ray intersectingRay = new Ray(new Point(0, 3, 2), new Vector(1, 0, 0));

        // Test intersection within a certain distance
        assertTrue(boundingBox.intersectBV(intersectingRay, 10), "Ray should intersect the bounding box within the distance");
        assertFalse(boundingBox.intersectBV(intersectingRay, 0.5), "Ray should not intersect the bounding box within the distance");

    }

    /**
     * Testing the method {@link BoundingBox#getBoundingBoxCenter()}
     */
    @Test
    void testGetBoundingBoxCenter() {
        // Create a BoundingBox
        BoundingBox boundingBox = new BoundingBox(1, 3, 2, 5, -1, 4);

        // Get the center of the bounding box
        Point center = boundingBox.getBoundingBoxCenter();

        // Check the center values
        assertEquals(new Point(2, 3.5, 1.5), center, "Bounding box center is incorrect");


    }

    /**
     * Testing the method {@link geometries.BoundingBox#boundingBoxDistance(BoundingBox)}
     */
    @Test
    void testBoundingBoxDistanceBetweenCenters() {
        // Create two BoundingBoxes
        BoundingBox boundingBox1 = new BoundingBox(1, 3, 2, 5, -1, 4);
        BoundingBox boundingBox2 = new BoundingBox(4, 6, 7, 9, 0, 3);

        // Calculate the distance between the bounding boxes
        double distance = boundingBox1.boundingBoxDistance(boundingBox2,false);

        // Expected distance
        double expectedDistance = Math.sqrt(29.25);  // Calculated from the center points

        assertEquals(expectedDistance, distance, "Distance between bounding boxes is incorrect");

    }


    /**
     * Testing the method {@link geometries.BoundingBox#boundingBoxDistanceSquared(BoundingBox)}
     */
    @Test
    void testBoundingBoxDistanceBetweenCentersSquared() {

        // Create two BoundingBoxes
        BoundingBox boundingBox1 = new BoundingBox(1, 3, 2, 5, -1, 4);
        BoundingBox boundingBox2 = new BoundingBox(4, 6, 7, 9, 0, 3);

        // Calculate the squared distance between the bounding boxes
        double distanceSquared = boundingBox1.boundingBoxDistanceSquared(boundingBox2,false);

        // Expected squared distance
        double expectedDistanceSquared = 29.25;  // Calculated from the center points

        assertEquals(expectedDistanceSquared, distanceSquared, "Squared distance between bounding boxes is incorrect");

    }

    /**
     * Testing the method {@link geometries.BoundingBox#boundingBoxDistance(BoundingBox)}
     */
    @Test
    void testBoundingBoxDistanceBetweenEdges() {
        // Create two BoundingBoxes
        BoundingBox boundingBox1 = new BoundingBox(1, 3, 2, 5, -1, 4);
        BoundingBox boundingBox2 = new BoundingBox(4, 6, 7, 9, 0, 3);

        // Calculate the distance between the bounding boxes
        double distance = boundingBox1.boundingBoxDistance(boundingBox2,true);

        // Expected distance
        double expectedDistance = Math.sqrt(25+49+16);  // Calculated from the edges

        assertEquals(expectedDistance, distance, "Distance between bounding boxes is incorrect");

    }


    /**
     * Testing the method {@link geometries.BoundingBox#boundingBoxDistanceSquared(BoundingBox)}
     */
    @Test
    void testBoundingBoxDistanceBetweenEdgesSquared() {

        // Create two BoundingBoxes
        BoundingBox boundingBox1 = new BoundingBox(1, 3, 2, 5, -1, 4);
        BoundingBox boundingBox2 = new BoundingBox(4, 6, 7, 9, 0, 3);

        // Calculate the squared distance between the bounding boxes
        double distanceSquared = boundingBox1.boundingBoxDistanceSquared(boundingBox2,true);

        // Expected squared distance
        double expectedDistanceSquared = 25+49+16;  // Calculated from the edges

        assertEquals(expectedDistanceSquared, distanceSquared, "Squared distance between bounding boxes is incorrect");

    }


}