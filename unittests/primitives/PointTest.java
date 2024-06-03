package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Shneor Mizrachi and Emanuel Perel
 */
class PointTest {


    private final Point p523 = new Point(5, 2, 3);
    private final Point p123=new Point (1,2,3);

    /**
     * Test method for {@link primitives.Point#equals(Object)}
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: Test that the equals() method returns true for two identical points.
        assertEquals(new Point(5, 2, 3), p523, "ERROR: Point.equals doesn't work properly (when actually equals)");
        assertNotEquals(new Point(1, 2, 4), p523, "ERROR: Point.equals doesn't work properly (when actually not equals) ");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: tests the method add for points p1=(5,2,3) and vector (2,3,4) so the result should be (7,5,7)
        assertEquals(new Point(7, 5, 7), p523.add(new Vector(2, 3, 4)), "ERROR: the add method does not work currently, check Again!");

        // =============== Boundary Values Tests ==================

        //TC02: tests the method add for point p1=(5,2,3) and vector (-5,-2,-3) so the result should be (0,0,0)
        assertEquals(Point.ZERO, p523.add(new Vector(-5, -2, -3)), "ERROR: the add method doesn't work correctly in the boundary case");
    }

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        //TC01 tests the method subtract for (5,2,3)-(1,2,3)=(4,0,0)

        assertEquals(new Vector(4, 0, 0), p523.subtract(p123),"ERROR: subtract method doesn't work properly");

        // =============== Boundary Values Tests ==================

        //TC02 tests the method subtract, for (p1-p1)=0 that should throw an exception of vector 0

        assertThrows(IllegalArgumentException.class,()->p523.subtract(p523),"ERROR: subtraction of two similar points doesn't throw or throws the wrong exception");
    }


    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {

        // ============ Equivalence Partitions Tests ==============

        //TC01 tests the method distance between two point |(5,2,3)-(1,2,3)|=4
        assertEquals(4,p523.distance(p123),"Error: the distance method doesn't work properly");

        // =============== Boundary Values Tests ==================

        //TC02 tests the method distance between two similar points |(5,2,3)-(5,2,3)|=0
        assertEquals(0,p523.distance(p523),"Error: the distance method doesn't work properly, Boundary case");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {

        // ============ Equivalence Partitions Tests ==============

        //TC01 tests the method distanceSquared between two point |(5,2,3)-(1,2,3)|^2=16
        assertEquals(16,p523.distanceSquared(p123),"Error: the distanceSquared method doesn't work properly");

        // =============== Boundary Values Tests ==================

        //TC02 tests the method distanceSquared between two similar points |(5,2,3)-(5,2,3)|^2=0
        assertEquals(0,p523.distanceSquared(p523),"Error: the distanceSquared method doesn't work properly, Boundary case");

    }

}