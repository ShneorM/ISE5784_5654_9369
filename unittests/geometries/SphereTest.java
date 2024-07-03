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

        // TC01: Test for intersection of a ray with a sphere when the ray is outside the sphere, resulting in no intersection.
        assertNull(s1.findIntersections(new Ray(new Point(2, 2, 2), new Vector(0, -1, 2))), "ERROR: No intersection expected when the ray is outside the sphere (TC01)");

        // TC02: Test for intersection of a ray with a sphere when the ray originates before the sphere and intersects it.
        assertEquals(towPoints, s1.findIntersections(new Ray(new Point(-2, 0, 0.5), v1)),
                "ERROR: Expected two intersection points when the ray originates before the sphere and intersects it (TC02)");

        //TC03: Test for intersection of a ray with a sphere when the ray originates inside the sphere and intersects it.
        assertEquals(onePoint, s1.findIntersections(new Ray(new Point(0, 0, 0.5), v1)),
                "ERROR: Expected one intersection point when the ray originates inside the sphere and intersects it (TC03)");

        //TC04: Test for intersection of a ray with a sphere when the ray originates after the sphere and does not intersect it.
        assertNull(s1.findIntersections(new Ray(new Point(2, 0, 0.5), v1)), "ERROR: No intersection expected when the ray originates after the sphere and does not intersect it (TC04)");



        // =============== Boundary Values Tests ==================

        //TC05 testing getIntersections when ray passing through the center axis and intersects the sphere at two points
        assertEquals(List.of(new Point(0, 0, -1), new Point(0, 0, 1)),
                s1.findIntersections(new Ray(new Point(0, 0, -4), new Vector(0, 0, 2))),
                "ERROR: getIntersections when ray passing through the center axis and intersects the sphere at two points ");

        //TC06 testing getIntersections when ray passing through the center axis and intersects the sphere at one point
        assertEquals(List.of(new Point(0, 0, 1)),
                s1.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1))),
                "ERROR: getIntersections when ray passing through the center axis and intersects the sphere at one point ");

        //TC07 testing getIntersections when ray starts outside and does not intersect the sphere
        assertNull(s1.findIntersections(new Ray(new Point(0, 0, 4), new Vector(0, 0, 1))), "ERROR: getIntersections when ray starts outside and does not intersect the sphere, line of the ray passing through the center of the sphere");

        //TC08 testing getIntersections when ray starts inside the sphere
        assertEquals(List.of(new Point(0, 0, 1)),
                s1.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0, 0, 1))),
                "ERROR: getIntersections when ray starts inside the sphere, line of the ray passing through the center of the sphere");

        //TC09 testing getIntersections when ray starts on the sphere's surface
        assertNull(s1.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1))), "ERROR: getIntersections when ray starts on the sphere's surface, line of the ray passing through the center of the sphere");

        //TC10 testing getIntersections when ray starts very close to the sphere's surface
        assertEquals(List.of(new Point(0, 0, 1)),
                s1.findIntersections(new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 1))),
                "ERROR: getIntersections when ray starts very close to the sphere's surface, line of the ray passing through the center of the sphere");

        //TC11: testing the getIntersections when the line is out of the sphere and perpendicular to the vector between the center of the sphere and the start of the ray
        assertNull(s1.findIntersections(new Ray(new Point(0, 2, 0), new Vector(0, 0, 2))), "ERROR: No intersection expected when the line is outside of the sphere and perpendicular to the vector between the center of the sphere and the start of the ray (TC11)");

        //TC12: Test for ray passing through a sphere, starting exactly at the beginning of the sphere.
        assertEquals(onePoint, s1.findIntersections(new Ray(p1, v1)),
                "ERROR: Expected one intersection point when the ray passes through the sphere and starts exactly at the beginning of the sphere (TC12)");

        //TC13: Test for ray passing through a sphere, starting inside the sphere.
        assertNull(s1.findIntersections(new Ray(p2, v1)), "ERROR: No intersection expected when the ray passes through the sphere and starts inside the sphere (TC13)");

        //TC14: Test for tangential intersection of a ray with a sphere, where the ray starts before the sphere.
        assertNull(s1.findIntersections(new Ray(new Point(-0.5, 0, Math.sqrt(0.75)), new Vector(1.5, 0, Math.sqrt(0.75)))), "ERROR: No intersection expected for tangential intersection when the ray starts before the sphere (TC14)");

        //TC15: Test for tangential intersection of a ray with a sphere, where the ray starts exactly from the sphere's surface.
        assertNull(s1.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(1.5, 0, Math.sqrt(0.75)))),
                "ERROR: No intersection expected for tangential intersection when the ray starts exactly from the sphere's surface (TC15)");
        //TC16: Test for tangential intersection of a ray with a sphere, where the ray starts after the sphere.
        assertNull(s1.findIntersections(new Ray(new Point(0, 0, Math.sqrt((double) 4 / 3)), new Vector(1.5, 0, Math.sqrt(0.75)))), "ERROR: No intersection expected for tangential intersection when the ray starts after the sphere (TC16)");

    }

    @Test
    void testFindIntersectionsWithDistance() {
        Sphere sphere = new Sphere(1, new Point(0, 0, 0));

        // Test case 01: Ray intersects the sphere at two points within a large distance
        List<Point> result = sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(-1, 0, 0)), 1000);
        // TC01: Expected result size is 2
        assertEquals(2, result.size(), "ERROR: Ray should intersect the sphere at two points within the distance (TC01)");

        // Test case 02: Ray does not intersect the sphere within a very small distance
        result = sphere.findIntersections(new Ray(new Point(25, 0, 0), new Vector(-3, 0, 0)), 10);
        // TC02: Expected result is null (no intersection within the distance)
        assertNull(result, "ERROR: Ray should not intersect the sphere within the distance (TC02)");
    }

}