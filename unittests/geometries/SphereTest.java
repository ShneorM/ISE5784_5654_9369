package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    /**
     * Test method for {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01
        Sphere s1=new Sphere(1,new Point(0,0,0));
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(2,2,2),new Vector(0,-1,2)))
                ,"ERROR: ");


        // =============== Boundary Values Tests ==================

        //TC05
        assertEquals(List.of(new Point(0,0,-1),new Point(0,0,1)),
                s1.findIntersections(new Ray(new Point(0,0,-4),new Vector(0,0,2))),
                "ERROR: ");

        //TC06
        assertEquals(List.of(new Point(0,0,1)),
                s1.findIntersections(new Ray(new Point(0,0,0),new Vector(0,0,2))),
                "ERROR: ");

        //TC07
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(0,0,4),new Vector(0,0,2))),
                "ERROR: ");
        //TC08
        assertEquals(List.of(new Point(0,0,1)),
                s1.findIntersections(new Ray(new Point(0,0,-1),new Vector(0,0,2))),
                "ERROR: ");

        //TC09
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(0,0,1),new Vector(0,0,2))),
                "ERROR: ");

        //TC10
        assertEquals(List.of(new Point(0,0,1)),
                s1.findIntersections(new Ray(new Point(0,0,0.5),new Vector(0,0,2))),
                "ERROR: ");

        //TC11 testing the getIntersections when the line is out of the sphere and perpendicular to the vector between the center of the sphere and the start of the ray
        assertEquals(List.of(),
                s1.findIntersections(new Ray(new Point(0,2,0),new Vector(0,0,2))),
                "ERROR: when the line is out of the sphere and perpendicular to the vector between the center of the sphere and the start of the ray");
    }
}