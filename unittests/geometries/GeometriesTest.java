package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Geometries class
 *
 * @author Emanuel and Shneor
 */
class GeometriesTest {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries(
                new Plane(new Point(0, 0, 2), new Vector(0, 0, 1)),
                new Triangle(new Point(0, 0, 1), new Point(1, 0, 1), new Point(1, 1, 1)),
                new Sphere(0.5, new Point(0.25, 0.25, -1)),
                new Polygon(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 1, 0), new Point(0, 1, 0))
        );

        // ============ Equivalence Partitions Tests ==============
        //TC01: intersect half of the shapes
        List<Point> result = geometries.findIntersections(new Ray(new Point(0.5, 0.25, 0.5), new Vector(0, 0, 1)));
        assertEquals(2, result.size(), "Expected 2 intersection points");

        // =============== Boundary Values Tests ==================
        //TC02: only intersects the sphere
        result = geometries.findIntersections(new Ray(new Point(0.25, 0.25, -1), new Vector(0, 0, -1)));
        assertEquals(1, result.size(), "Expected 1 intersection point with the sphere");

        //TC03: intersect everything
        result = geometries.findIntersections(new Ray(new Point(0.5, 0.25, -3), new Vector(0, 0, 1)));
        assertEquals(5, result.size(), "Expected 5 intersection points with all shapes");

        //TC04: Doesn't intersect anything
        result = geometries.findIntersections(new Ray(new Point(0, 0, 800), new Vector(0, 0, 800)));
        assertNull(result, "Expected no intersection points");

        geometries = new Geometries();
        //TC05: There are no bodies at all
        result = geometries.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
        assertNull(result, "Expected no intersection points since there are no geometries");
    }

    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray, double)}
     */
    @Test
    void testFindIntersectionsWithDistance() {
        Geometries geometries = new Geometries(
                new Plane(new Point(0, 0, 2), new Vector(0, 0, 1)),
                new Triangle(new Point(0, 0, 1), new Point(1, 0, 1), new Point(1, 1, 1)),
                new Sphere(0.5, new Point(0.25, 0.25, -1)),
                new Polygon(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 1, 0), new Point(0, 1, 0))
        );

        //TC01: intersect half of the shapes
        List<Point> result = geometries.findIntersections(new Ray(new Point(0.5, 0.25, 0.5), new Vector(0, 0, 1)),10);
        assertEquals(2, result.size(), "Expected 2 intersection points");

        //TC02: only intersects the sphere
        result = geometries.findIntersections(new Ray(new Point(0.25, 0.25, -1), new Vector(0, 0, -1)),500);
        assertEquals(1, result.size(), "Expected 1 intersection point with the sphere");

        //TC03: intersect everything
        result = geometries.findIntersections(new Ray(new Point(0.5, 0.25, -3), new Vector(0, 0, 1)),100);
        assertEquals(5, result.size(), "Expected 5 intersection points with all shapes");
    }


}