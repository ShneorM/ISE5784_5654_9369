package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 *
 * @author Shneor Mizrachi and Emanuel Perel
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}
     */
    @Test
    void testFindClosestPoint() {


        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        List<Point> points = new ArrayList<>(List.of(
                new Point(7, 8, 9),
                new Point(1, 2, 3),
                new Point(4, 5, 6)
        ));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Point closest to the origin is in the middle of the list
        assertEquals(new Point(1, 2, 3), ray.findClosestPoint(points),
                "The closest point to the origin (0,0,0) should be (1,2,3)");

        // =============== Boundary Values Tests ==================

        // TC02: Empty list of points
        points = new ArrayList<>();
        assertNull(ray.findClosestPoint(points),
                "The closest point to the origin should be null when the list is empty");

        // TC03: Point closest to the origin is at the beginning of the list
        points = new ArrayList<>(List.of(
                new Point(1, 2, 3),
                new Point(7, 8, 9),
                new Point(4, 5, 6)
        ));
        assertEquals(new Point(1, 2, 3), ray.findClosestPoint(points),
                "The closest point to the origin (0,0,0) should be (1,2,3) when it is the first point in the list");

        // TC04: Point closest to the origin is at the end of the list
        points = new ArrayList<>(List.of(
                new Point(7, 8, 9),
                new Point(4, 5, 6),
                new Point(1, 2, 3)
        ));
        assertEquals(new Point(1, 2, 3), ray.findClosestPoint(points),
                "The closest point to the origin (0,0,0) should be (1,2,3) when it is the last point in the list");


    }

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}
     */
    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the getPoint of ray when the scalar is positive
        assertEquals(new Point(0, 0, 4), ray.getPoint(3), "Test when the scalar is positive (TC01)");

        // TC02: Test when the scalar is negative
        assertEquals(new Point(0, 0, -2), ray.getPoint(-3), "Test when the scalar is negative (TC02)");

        // =============== Boundary Values Tests ==================
        // TC03: Test when the scalar is zero
        assertEquals(new Point(0, 0, 1), ray.getPoint(0), "Test when the scalar is zero (TC03)");

    }
}