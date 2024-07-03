package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for geometries.Triangle class
 *
 * @author Emanuel and Shneor
 */
class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Triangle tr = new Triangle(new Point(1, 0, 0), new Point(1, 2, 0), new Point(0, 1, 0));

        //TC01: Testing the getNormal in Triangle
        assertEquals(new Vector(0, 0, -1), tr.getNormal(new Point(1, 1.5, 0)), "ERROR: getNormal in Triangle doesn't work properly");
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
        assertThrows(IllegalArgumentException.class, () -> new Triangle(new Point(1, 0, 0), new Point(1, 2, 0), new Point(1, 3, 0)),
                "ERROR: the Ctor With points that points are collinear");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Triangle t1 = new Triangle(new Point(-10, 0, 0), new Point(10, 0, 0), new Point(0, 0, 10));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for ray-triangle intersection when the ray passes through the triangle.
        assertEquals(
                List.of(new Point(0, 0, 5)),
                t1.findIntersections(new Ray(new Point(0, -1, 5), new Vector(0, 1, 0))),
                "ERROR: Expected intersection point when the ray passes through the triangle (TC01)"
        );

        // TC02: Test for ray-triangle intersection when the ray passes beside one of the edges of the triangle.
        assertNull(t1.findIntersections(new Ray(new Point(-5, -1, 7), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes beside one of the edges of the triangle (TC02)");

        // TC03: Test for ray-triangle intersection when the ray passes beside one of the vertices of the triangle.
        assertNull(t1.findIntersections(new Ray(new Point(-13, -1, -1), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes beside one of the vertices of the triangle (TC03)");

        // =============== Boundary Values Tests ==================
        // TC04: Test for ray-triangle intersection when the ray passes along one of the edges of the triangle.
        assertNull(t1.findIntersections(new Ray(new Point(-5, -1, 5), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes through one of the edges of the triangle (TC04)");

        // TC05: Test for ray-triangle intersection when the ray passes through one of the vertices of the triangle.
        assertNull(t1.findIntersections(new Ray(new Point(-10, -1, 0), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes through one of the vertices of the triangle (TC05)");

        // TC06: Test for ray-triangle intersection when the ray passes along the extension of one of the edges of the triangle.
        assertNull(t1.findIntersections(new Ray(new Point(-5, -1, 15), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes along the extension of one of the edges of the triangle (TC06)");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray, double)} (Ray)}.
     */
    @Test
    void testFindIntersectionsWithDistance() {
        Triangle triangle = new Triangle(new Point(-10,0,0), new Point(10,0,0), new Point(0,0,10));
        // Test case 01: Ray intersects the polygon within a large distance
        List<Point> result = triangle.findIntersections(new Ray(new Point(0, -2, 2), new Vector(0, 1, 1)), 500);
        // TC01: Expected result size is 1
        assertEquals(1, result.size(), "ERROR: Ray should intersect the polygon within the distance (TC01)");

        // Test case 02: Ray does not intersect the polygon within a very small distance
        result = triangle.findIntersections(new Ray(new Point(0, -2, 2), new Vector(0, 1, 1)), 1);
        // TC02: Expected result is null (no intersection within the distance)
        assertNull(result, "ERROR: Ray should not intersect the polygon within the distance (TC01)");
    }
}