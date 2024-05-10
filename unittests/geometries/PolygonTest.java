package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Polygon class
 * @author Emanuel and Shneor
 */
class PolygonTest {

    /**
     * Test method for {@link geometries.Polygon#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Polygon pol1 = new Polygon(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 2, 0), new Point(0, 1, 0));

        //TC01: Testing the getNormal in Polygon with four points
        assertEquals(new Vector(0, 0, -1), pol1.getNormal(new Point(1, 1.5, 0)), "ERROR: getNormal in Polygon doesn't work properly");
    }

    /**
     * Test method for {@link geometries.Polygon#Polygon(Point...)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Testing the Ctor of Polygon
        assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 2, 0), new Point(0, 1, 0)), "ERROR: The Ctor doesn't work (TC01)");

        // =============== Boundary Values Tests ==================
        //TC02: testing the ctor of polygon in the case that not all the points are on the same plane
        assertThrows(IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(1, 2, 0), new Point(0, 1, 0)),
                "ERROR: the Ctor With points that not on the same plane doesn't throw");

        //TC03: testing the ctor of polygon in the case that some points are collinear
        assertThrows(IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 2, 0), new Point(1, 3, 0)),
                "ERROR: the Ctor With points that some points are collinear");
    }
    /**
     * Test method for {@link geometries.Polygon#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Polygon t1 = new Polygon(new Point(-10,0,0),new Point(10,0,0), new Point(0,0,10));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for ray-triangle intersection when the ray passes through the triangle.
        assertEquals(
                List.of(new Point(0,0,5)),
                t1.findIntersections(new Ray(new Point(0,-1,5), new Vector(0,1,0))),
                "ERROR: Expected intersection point when the ray passes through the triangle (TC01)"
        );

        // TC02: Test for ray-triangle intersection when the ray passes beside one of the edges of the triangle.
        assertEquals(
                List.of(),
                t1.findIntersections(new Ray(new Point(-5,-1,7), new Vector(0,1,0))),
                "ERROR: No intersection expected when the ray passes beside one of the edges of the triangle (TC02)"
        );

        // TC03: Test for ray-triangle intersection when the ray passes beside one of the vertices of the triangle.
        assertEquals(
                List.of(),
                t1.findIntersections(new Ray(new Point(-13, -1, -1), new Vector(0, 1, 0))),
                "ERROR: No intersection expected when the ray passes beside one of the vertices of the triangle (TC03)"
        );

        // =============== Boundary Values Tests ==================
        // TC04: Test for ray-triangle intersection when the ray passes along one of the edges of the triangle.
        assertEquals(
                List.of(),
                t1.findIntersections(new Ray(new Point(-5, -1, 5), new Vector(0, 1, 0))),
                "ERROR: No intersection expected when the ray passes through one of the edges of the triangle (TC04)"
        );

        // TC05: Test for ray-triangle intersection when the ray passes through one of the vertices of the triangle.
        assertEquals(
                List.of(),
                t1.findIntersections(new Ray(new Point(-10, -1, 0), new Vector(0, 1, 0))),
                "ERROR: No intersection expected when the ray passes through one of the vertices of the triangle (TC05)"
        );

        // TC06: Test for ray-triangle intersection when the ray passes along the extension of one of the edges of the triangle.
        assertEquals(
                List.of(),
                t1.findIntersections(new Ray(new Point(-5, -1, 15), new Vector(0, 1, 0))),
                "ERROR: No intersection expected when the ray passes along the extension of one of the edges of the triangle (TC06)"
        );
    }
}