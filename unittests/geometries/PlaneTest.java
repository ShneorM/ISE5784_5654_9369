package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Sphere class
 * @author Shneor and Emanuel
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane pl1 = new Plane(new Point(1, 3, 5), new Vector(2, 3, 2));
        Plane p123 = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 0, 2));
        Plane pl2 = new Plane(new Point(1, 3, 5), new Vector(1, 0, 0));

        //TC01: Testing the getNormal in the Plane
        assertEquals(new Vector(2 / Math.sqrt(17), 3 / Math.sqrt(17), 2 / Math.sqrt(17)),
                pl1.getNormal(new Point(3, 6, 7)),
                "ERROR: the getNormal doesn't work properly");

        //TC02: Testing the plane that was made by three point
        assertEquals(new Vector(0, 1, 0), p123.getNormal(new Point(1, 0, 0)), "the getNormal of plane constructed by 3 points doesn't work");

        // =============== Boundary Values Tests ==================
        //TC03: Testing with normalized vector
        assertEquals(new Vector(1, 0, 0),
                pl2.getNormal(new Point(2.5, 3, 5)),
                "ERROR: the getNormal doesn't work properly (TC03)");

    }

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)};
     */
    @Test
    void testPlaneConstructor() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: Testing the plane that was made by three point
        try {
            Plane p123 = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 0, 2));
        } catch (IllegalArgumentException ex) {
            fail("the Ctor plane with 3 points doesn't work (TC01)");
        }
        // =============== Boundary Values Tests ==================
        //TC02: Testing the Ctor with Three collinear points
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 0), new Point(0, 0, 1), new Point(0, 0, 2)),
                "the ctor with collinear points doesn't throw");
        //TC03 testing the ctor with two similar points
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 0), new Point(0, 0, 0), new Point(0, 0, 2)),
                "the ctor with two similar points doesn't throw");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)};
     */
    @Test
    void testFindIntersections() {
        Plane pl1 = new Plane(new Point(0, 0, 0), new Vector(1, 0, 0));
        Vector v1 = new Vector(1, 0, 1);
        Vector v2 = new Vector(0, 0, 1);
        Vector v3 = new Vector(1, 0, 0);



        // ============ Equivalence Partitions Tests ==============
        //TC01:

        // =============== Boundary Values Tests ==================
        // TC03: Test for ray-plane intersection when the ray is parallel to the plane and outside the plane.
        assertEquals(
                List.of(),
                pl1.findIntersections(new Ray(new Point(1,0,0), v2)),
                "ERROR: No intersection expected when the ray is parallel to the plane and outside it (TC03)"
        );

        // TC04: Test for ray-plane intersection when the ray is parallel to the plane and inside the plane.
        assertEquals(
                List.of(),
                pl1.findIntersections(new Ray(new Point(0,0,0), v2)),
                "ERROR: No intersection expected when the ray is parallel to the plane and inside it (TC04)"
        );

        // TC05: Test for ray-plane intersection when the ray is perpendicular to the plane and starts before the plane.
        assertEquals(
                List.of(new Point(0,0,0)),
                pl1.findIntersections(new Ray(new Point(-1,0,0), v3)),
                "ERROR: Expected intersection point when the ray is perpendicular to the plane and starts before it (TC05)"
        );

        // TC06: Test for ray-plane intersection when the ray is perpendicular to the plane and starts inside the plane.
        assertEquals(
                List.of(),
                pl1.findIntersections(new Ray(new Point(0,0,0), v3)),
                "ERROR: No intersection expected when the ray is perpendicular to the plane and starts inside it (TC06)"
        );

        // TC07: Test for ray-plane intersection when the ray is perpendicular to the plane and starts after the plane.
        assertEquals(
                List.of(),
                pl1.findIntersections(new Ray(new Point(1,0,0), v3)),
                "ERROR: No intersection expected when the ray is perpendicular to the plane and starts after it (TC07)"
        );
    }
}