package geometries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for geometries.Tube class
 * @author Emanuel and Shneor
 */
class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Tube t1 = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)));
        Point p1=new Point(3,3,1);
        Point p2=new Point(0,0,-1);
        Vector normalP1 =new Vector(0,0,1);
        Vector normalP2 =new Vector(0,0,-1);

        // ============ Equivalence Partitions Tests ==============
        //TC01: Testing the getNormal with random Tube and random point
        assertEquals(normalP1,t1.getNormal(p1),"ERROR: getNormal of tube doesn't work properly (TC01)");

        // =============== Boundary Values Tests ==================
        //TC02: Testing the getNormal with a point that is the closest to the head of the ray
        assertEquals(normalP2,t1.getNormal(p2),"ERROR: getNormal of tube doesn't work properly (TC02)");

        assertThrows(IllegalArgumentException.class,()->t1.getNormal(new Point(9 + 1/Math.sqrt(2), 9 + 1/Math.sqrt(2), 0)),
                "ERROR: the getNormal of the cylinder when the point is on the second base");

    }

}