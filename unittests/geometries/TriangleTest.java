package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for geometries.Triangle class
 * @author Emanuel and Shneor
 */
class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Triangle tr = new Triangle( new Point(1, 0, 0), new Point(1, 2, 0), new Point(0, 1, 0));

        //TC01: Testing the getNormal in Triangle
        assertEquals(new Vector(0, 0, -1),tr.getNormal(new Point(1, 1.5, 0)), "ERROR: getNormal in Triangle doesn't work properly");
    }

    /**
     * Test method for {@link geometries.Triangle#Triangle(Point, Point, Point)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Testing the Ctor of Triangle
        assertDoesNotThrow(() -> new Triangle(new Point(1, 0, 0), new Point(1, 2, 0), new Point(0, 1, 0)), "ERROR: The Ctor doesn't work (TC01)");

        // =============== Boundary Values Tests ==================

        //TC02: testing the ctor of Triangle in the case that points are collinear
        assertThrows(IllegalArgumentException.class, () -> new Triangle( new Point(1, 0, 0), new Point(1, 2, 0), new Point(1, 3, 0)),
                "ERROR: the Ctor With points that points are collinear");
    }
}