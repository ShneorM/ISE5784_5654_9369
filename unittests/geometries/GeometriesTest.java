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
        List<Point> result = geometries.findIntersections(new Ray(new Point(0.5, 0.25, 0.5), new Vector(0, 0, 1)), 10);
        assertEquals(2, result.size(), "Expected 2 intersection points");

        //TC02: only intersects the sphere
        result = geometries.findIntersections(new Ray(new Point(0.25, 0.25, -1), new Vector(0, 0, -1)), 500);
        assertEquals(1, result.size(), "Expected 1 intersection point with the sphere");

        //TC03: intersect everything
        result = geometries.findIntersections(new Ray(new Point(0.5, 0.25, -3), new Vector(0, 0, 1)), 100);
        assertEquals(5, result.size(), "Expected 5 intersection points with all shapes");
    }

    /**
     * Testing method {@link Geometries#flatten()}
     */
    @Test
    void testFlatten() {
        // Arrange: Create a complex structure of geometries
        Geometries innerGeometries1 = new Geometries(
                new Sphere(1, new Point(1, 1, 1)),
                new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0))
        );

        Geometries innerGeometries2 = new Geometries(
                new Sphere(2, new Point(2, 2, 2)),
                innerGeometries1  // Nested Geometries
        );

        Geometries mainGeometries = new Geometries(
                innerGeometries2,  // Nested Geometries
                new Triangle(new Point(3, 3, 3), new Point(4, 3, 3), new Point(3, 4, 3))
        );

        // Act: Call the flatten method
        mainGeometries.flatten();

        // Assert: Verify that the mainGeometries list is now flattened
        List<Container> flattenedList = mainGeometries.getContainerList();

        // Expected: 3 individual geometries (2 Spheres and 2 Triangles)
        assertEquals(4, flattenedList.size(), "The flattened list should contain exactly 4 simple geometries.");

        // Check types to ensure proper flattening
        assertInstanceOf(Sphere.class, flattenedList.get(0), "First element should be a Sphere.");
        assertInstanceOf(Sphere.class, flattenedList.get(1), "Second element should be a Triangle.");
        assertInstanceOf(Triangle.class, flattenedList.get(2), "Third element should be a Sphere.");
        assertInstanceOf(Triangle.class, flattenedList.get(3), "Fourth element should be a Triangle.");

    }

    /**
     * Testing method {@link Geometries#turnOnOffBvh(boolean)}
     */
    @Test
    void testTurnOnOffBvh() {
        // Arrange: Create a set of geometries
        Sphere sphere1 = new Sphere(new Point(1, 2, 3), 1);
        Sphere sphere2 = new Sphere(new Point(5, 5, 5), 1);
        Geometries nestedGeometries = new Geometries(sphere1, sphere2);

        Sphere sphere3 = new Sphere(new Point(10, 10, 10), 1);
        Triangle triangle = new Triangle(
                new Point(15, 15, 15),
                new Point(17, 15, 15),
                new Point(15, 17, 15)
        );
        Geometries mainGeometries = new Geometries(nestedGeometries, sphere3, triangle);

        // Act: Turn BVH on for all geometries
        mainGeometries.turnOnOffBvh(true);

        // Assert: Verify that BVH is turned on for all geometries
        for (Container container : mainGeometries.getContainerList()) {
            assertTrue(container.isBvh(), "BVH should be turned on for the container.");
            if (container instanceof Geometries subGeometries) {
                for (Container subContainer : subGeometries.getContainerList()) {
                    assertTrue(subContainer.isBvh(), "BVH should be turned on for the nested container.");
                }
            }
        }

        // Act: Turn BVH off for all geometries
        mainGeometries.turnOnOffBvh(false);

        // Assert: Verify that BVH is turned off for all geometries
        for (Container container : mainGeometries.getContainerList()) {
            assertFalse(container.isBvh(), "BVH should be turned off for the container.");
            if (container instanceof Geometries subGeometries) {
                for (Container subContainer : subGeometries.getContainerList()) {
                    assertFalse(subContainer.isBvh(), "BVH should be turned off for the nested container.");
                }
            }
        }
    }

    /**
     * Testing method {@link Geometries#buildBinaryBvhTree()}
     */
    @Test
    void testBuildBinaryBvhTree() {
        // Arrange: Create a set of geometries
        Sphere sphere1 = new Sphere(new Point(1, 2, 3), 1);
        Sphere sphere2 = new Sphere(new Point(5, 5, 5), 1);
        Sphere sphere3 = new Sphere(new Point(10, 10, 10), 1);
        Triangle triangle = new Triangle(
                new Point(15, 15, 15),
                new Point(17, 15, 15),
                new Point(15, 17, 15)
        );

        Geometries geometries = new Geometries(sphere1, sphere2, sphere3, triangle);

        // Act: Build the BVH tree
        geometries.buildBinaryBvhTree();

        // Assert: Verify the BVH tree structure
        List<Container> containerList = geometries.getContainerList();

        // The final structure should have 3 containers: two combined geometries and one remaining geometry
        assertEquals(2, ((Geometries)containerList.getFirst()).getContainerList().size(), "The BVH tree should contain exactly 2 containers.");

        // Check that each container is either a combined geometry or an individual one
        boolean foundCombined1 = false;
        boolean foundCombined2 = false;

        for (Container container : ((Geometries)containerList.getFirst()).getContainerList()) {
            if (container instanceof Geometries subGeometries) {
                if (subGeometries.getContainerList().size() == 2) {
                    if (!foundCombined1) {
                        foundCombined1 = true;
                    } else {
                        foundCombined2 = true;
                    }
                }
            }
        }

        assertTrue(foundCombined1, "There should be at least one combined container of geometries.");
        assertTrue(foundCombined2, "There should be at least two combined containers of geometries.");

        // Optionally, further checks can be done to validate specific contents of each container
    }

    /**
     * Testing method {@link Geometries#setBoundingBox()}
     */
    @Test
    void testSetBoundingBox() {
        // Arrange: Create a set of geometries with known bounding boxes
        Sphere sphere1 = new Sphere(new Point(1, 2, 3), 1);
        Sphere sphere2 = new Sphere(new Point(5, 5, 5), 2);

        Plane plane=new Plane(Point.ZERO,Vector.Z,Vector.Y);

        // Create instances of the subclass


        Geometries geometriesWithBoundingBoxes = new Geometries(sphere1, sphere2);
        Geometries geometries = new Geometries(geometriesWithBoundingBoxes, plane);

        // Act: Call the setBoundingBox method
        geometries.setBoundingBox();

        // Assert: Verify that the bounding box was set correctly
        BoundingBox boundingBox = geometries.getBoundingBox();

        // If any geometry is unbounded (like a plane), the entire bounding box should be null
        assertNull(boundingBox, "Bounding box should be null when an unbounded geometry is present.");

        // Now let's test without the unbounded geometry to ensure bounding box is calculated
        geometries = new Geometries(sphere1, sphere2);
        geometries.setBoundingBox();

        boundingBox = geometries.getBoundingBox();

        // Expected bounding box dimensions based on sphere1 and sphere2
        double expectedMinX = Math.min(sphere1.getCenter().getX() - sphere1.getRadius(), sphere2.getCenter().getX() - sphere2.getRadius());
        double expectedMaxX = Math.max(sphere1.getCenter().getX() + sphere1.getRadius(), sphere2.getCenter().getX() + sphere2.getRadius());
        double expectedMinY = Math.min(sphere1.getCenter().getY() - sphere1.getRadius(), sphere2.getCenter().getY() - sphere2.getRadius());
        double expectedMaxY = Math.max(sphere1.getCenter().getY() + sphere1.getRadius(), sphere2.getCenter().getY() + sphere2.getRadius());
        double expectedMinZ = Math.min(sphere1.getCenter().getZ() - sphere1.getRadius(), sphere2.getCenter().getZ() - sphere2.getRadius());
        double expectedMaxZ = Math.max(sphere1.getCenter().getZ() + sphere1.getRadius(), sphere2.getCenter().getZ() + sphere2.getRadius());

        assertNotNull(boundingBox, "Bounding box should not be null when all geometries are bounded.");
        assertEquals(expectedMinX, boundingBox.getMinX(), "Min X value is incorrect");
        assertEquals(expectedMaxX, boundingBox.getMaxX(), "Max X value is incorrect");
        assertEquals(expectedMinY, boundingBox.getMinY(), "Min Y value is incorrect");
        assertEquals(expectedMaxY, boundingBox.getMaxY(), "Max Y value is incorrect");
        assertEquals(expectedMinZ, boundingBox.getMinZ(), "Min Z value is incorrect");
        assertEquals(expectedMaxZ, boundingBox.getMaxZ(), "Max Z value is incorrect");
    }
}