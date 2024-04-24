package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,1,1);
        Vector v1 = new Vector(2,3,4);
        Point p3 =p1.add(v1);
        assertEquals(p3,new Point(3,4,5),"that add method not work currently, check Again!");
    }



    @Test
    void testDistance() {
    }

    @Test
    void testDistanceSquared() {
    }

    @Test
    void testSubtract() {
    }
}