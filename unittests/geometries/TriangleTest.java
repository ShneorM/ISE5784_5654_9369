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

    /**
     * Testing method {@link Geometries#setBoundingBox()}
     */
    @Test
    void testSetBoundingBox() {
        // Arrange: Create a collection of geometries with known bounding dimensions
        Triangle triangle = new Triangle(
                new Point(4, 5, 6),
                new Point(7, 8, 9),
                new Point(10, 11, 10)
        );

        ;

        // Act: Call the setBoundingBox method
        triangle.setBoundingBox();

        // Assert: Verify that the bounding box was set correctly
        BoundingBox boundingBox = triangle.getBoundingBox();

        // Calculate expected bounding box dimensions based on the geometries
        double expectedMinX =(4);
        double expectedMaxX = 10;
        double expectedMinY =( 5);
        double expectedMaxY = ( 11);
        double expectedMinZ = ( 6);
        double expectedMaxZ =( 10);

        assertNotNull(boundingBox, "Bounding box should not be null");
        assertEquals(expectedMinX, boundingBox.getMinX(), "Min X value is incorrect");
        assertEquals(expectedMaxX, boundingBox.getMaxX(), "Max X value is incorrect");
        assertEquals(expectedMinY, boundingBox.getMinY(), "Min Y value is incorrect");
        assertEquals(expectedMaxY, boundingBox.getMaxY(), "Max Y value is incorrect");
        assertEquals(expectedMinZ, boundingBox.getMinZ(), "Min Z value is incorrect");
        assertEquals(expectedMaxZ, boundingBox.getMaxZ(), "Max Z value is incorrect");
    }
}