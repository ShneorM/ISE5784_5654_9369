package geometries;

import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Polygon class
 *
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
        Polygon pol = new Polygon(new Point(-10, 0, 0), new Point(10, 0, 0), new Point(0, 0, 10));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for ray-polygon intersection when the ray passes through the polygon.
        assertEquals(
                List.of(new Point(0, 0, 5)),
                pol.findIntersections(new Ray(new Point(0, -1, 5), new Vector(0, 1, 0))),
                "ERROR: Expected intersection point when the ray passes through the polygon (TC01)"
        );

        // TC02: Test for ray-polygon intersection when the ray passes beside one of the edges of the polygon.
        assertNull(pol.findIntersections(new Ray(new Point(-5, -1, 7), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes beside one of the edges of the polygon (TC02)");

        // TC03: Test for ray-polygon intersection when the ray passes beside one of the vertices of the polygon.
        assertNull(pol.findIntersections(new Ray(new Point(-13, -1, -1), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes beside one of the vertices of the polygon (TC03)");

        // =============== Boundary Values Tests ==================
        // TC04: Test for ray-polygon intersection when the ray passes along one of the edges of the polygon.
        assertNull(pol.findIntersections(new Ray(new Point(-5, -1, 5), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes through one of the edges of the polygon (TC04)");

        // TC05: Test for ray-polygon intersection when the ray passes through one of the vertices of the polygon.
        assertNull(pol.findIntersections(new Ray(new Point(-10, -1, 0), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes through one of the vertices of the polygon (TC05)");

        // TC06: Test for ray-polygon intersection when the ray passes along the extension of one of the edges of the polygon.
        assertNull(pol.findIntersections(new Ray(new Point(-5, -1, 15), new Vector(0, 1, 0))), "ERROR: No intersection expected when the ray passes along the extension of one of the edges of the polygon (TC06)");
    }


    /**
     * Test method for {@link geometries.Polygon#findIntersections(Ray, double)}.
     */
    @Test
    void testFindIntersectionsWithDistance() {
        Polygon polygon = new Polygon(
                new Point(0, 1, 0),
                new Point(1, 1, 0),
                new Point(1, 0, 0),
                new Point(-1, -1, 0)
        );
        // Test case 01: Ray does not intersect the polygon within a very large distance
        List<Point> result = polygon.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-0.5, -0.5, -2)), 500);
        assertEquals(1, result.size(), "ERROR: Ray should intersect the polygon within the distance (TC01)");

        // Test case 02: Ray does not intersect the polygon within a very small distance
        result = polygon.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-0.5, -0.5, -2)), 1);
        // TC02: Expected result is null (no intersection within the distance)
        assertNull(result, "ERROR: Ray should not intersect the polygon within the distance (TC02)");
    }
}


