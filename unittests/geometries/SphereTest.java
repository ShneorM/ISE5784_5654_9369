package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Sphere class
 *
 * @author Emanuel and Shneor
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s1 = new Sphere(6, new Point(2, 1, 4));
        Point p1 = new Point(8, 1, 4);
        Vector normalP1 = new Vector(1, 0, 0);
        Sphere s2 = new Sphere(0, new Point(0, 0, 0));


        //TC01: Test if the normal get right values
        assertEquals(normalP1, s1.getNormal(p1), "ERROR: getNormal of Sphere doesn't work");

        // =============== Boundary Values Tests ==================
        //TC02: Testing if the P-Center is ZERO
        assertThrows(IllegalArgumentException.class, () -> s2.getNormal(new Point(0, 0, 0)), "ERROR: getNormal of sphere doesn't throw if the radius is zero");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Sphere s1 = new Sphere(1, new Point(0, 0, 0));
        Vector v1 = new Vector(1, 0, 0);
        Point p1 = new Point(-1 * Math.sqrt(0.75), 0, 0.5);
        Point p2 = new Point(Math.sqrt(0.75), 0, 0.5);
        List<Point> towPoints = List.of(p1, p2);
        List<Point> onePoint = List.of(p2);

        // ============ Equivalence Partitions Tests ==============
//~~~~~~~ page 7 number 1
        //TC01
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(2, 2, 2), new Vector(0, -1, 2)))
                , "ERROR: ");
//~~~~~~~ page 7 number 2
        //TC02
        assertEquals(towPoints, s1.findIntersections(new Ray(new Point(-2, 0, 0.5), v1)),
                "ERROR:");
//~~~~~~~ page 7 number 3
        //TC03
        assertEquals(onePoint, s1.findIntersections(new Ray(new Point(0, 0, 0.5), v1)),
                "ERROR:");
//~~~~~~~ page 7 number 4
        //TC04
        assertEquals(List.of(), s1.findIntersections(new Ray(new Point(2, 0, 0.5), v1)),
                "ERROR:");


        // =============== Boundary Values Tests ==================
//~~~~~~~~~~~~~~~page 8 number 1
        //TC05
        assertEquals(List.of(new Point(0, 0, -1), new Point(0, 0, 1)),
                s1.findIntersections(new Ray(new Point(0, 0, -4), new Vector(0, 0, 2))),
                "ERROR: ");

        //TC06
        assertEquals(List.of(new Point(0, 0, 1)),
                s1.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, 2))),
                "ERROR: ");

        //TC07
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(0, 0, 4), new Vector(0, 0, 2))),
                "ERROR: ");
        //TC08
        assertEquals(List.of(new Point(0, 0, 1)),
                s1.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0, 0, 2))),
                "ERROR: ");

        //TC09
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 0, 2))),
                "ERROR: ");

        //TC10
        assertEquals(List.of(new Point(0, 0, 1)),
                s1.findIntersections(new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 2))),
                "ERROR: ");

//~~~~~~~~~~~~~~~page 8 number 4
        //TC11 testing the getIntersections when the line is out of the sphere and perpendicular to the vector between the center of the sphere and the start of the ray
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(0, 2, 0), new Vector(0, 0, 2))),
                "ERROR: when the line is out of the sphere and perpendicular to the vector between the center of the sphere and the start of the ray");

//~~~~~~~ page 8 number 2
        //TC12
        assertEquals(onePoint, s1.findIntersections(new Ray(p1, v1)),
                "ERROR:");
        //TC13
        assertEquals(List.of(), s1.findIntersections(new Ray(p2, v1)),
                "ERROR:");

//~~~~~~~~~~~~~~~page 8 number 3
        //TC14: A ray on the straight tangent to the ball that starts before the ball
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(-0.5, 0, Math.sqrt(0.75)), new Vector(1.5, 0, Math.sqrt(0.75)))),
                "ERROR: ");

        //TC15: A ray on the straight tangent to the ball that starts in the ball
        assertEquals(List.of(new Point(-0.5, 0, Math.sqrt(0.75))),
                s1.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(1.5, 0, Math.sqrt(0.75)))),
                "ERROR: ");

        //TC16: A ray on the straight tangent to the ball that starts after the ball
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(0, 0, Math.sqrt((double) 4 / 3)), new Vector(1.5, 0, Math.sqrt(0.75)))),
                "ERROR: ");
    }

}