package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Sphere class
 * @author Emanuel and Shneor
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s1 = new Sphere( 6, new Point(2,1,4));
        Point p1=new Point(8,1,4);
        Vector normalP1 =new Vector(1,0,0);
        Sphere s2=new Sphere(0,new Point(0,0,0));


        //TC01: Test if the normal get right values
        assertEquals(normalP1,s1.getNormal(p1),"ERROR: getNormal of Sphere doesn't work");

        // =============== Boundary Values Tests ==================
        //TC02: Testing if the P-Center is ZERO
        assertThrows(IllegalArgumentException.class,()->s2.getNormal(new Point(0,0,0)),"ERROR: getNormal of sphere doesn't throw if the radius is zero");
    }
}