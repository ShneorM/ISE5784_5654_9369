package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}