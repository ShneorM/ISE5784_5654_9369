package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Cylinder class
 * @author Emanuel and Shneor
 */
class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {

        double height=9;
        Ray ray1=new Ray(new Point(0,0,0),new Vector(1,1,0));
        Cylinder cyl1= new Cylinder(1,ray1,9);
        // ============ Equivalence Partitions Tests ==============
        //TC01: testing the getNormal of the cylinder when the point is on the tube
        assertEquals(new Vector(0,0,1),cyl1.getNormal(new Point(3,3,1)),"ERROR: the getNormal of the cylinder when the point is on the tube");
        //TC02: testing the getNormal of the cylinder when the point is on one of the bases
        assertEquals(new Vector(1/Math.sqrt(2),1/Math.sqrt(2),0),cyl1.getNormal(new Point(0,0,0.5)),
                "ERROR: the getNormal of the cylinder when the point is on one of the bases");
        //TC03: testing the getNormal of the cylinder when the point is on the second base
        assertEquals(new Vector(1/Math.sqrt(2),1/Math.sqrt(2),0),cyl1.getNormal(ray1.getPoint(9).add(new Vector(0,0,0.5))),
                "ERROR: the getNormal of the cylinder when the point is on the second base");

        // =============== Boundary Values Tests ==================
        //TC03: testing the getNormal with the point on the corner of the cylinder
        assertEquals(new Vector(1/Math.sqrt(2),1/Math.sqrt(2),0),
                cyl1.getNormal(new Point(0,0,1)),
                "ERROR: the getNormal with the point on the corner of the cylinder");
        //TC04: testing the getNormal with the point on the center, of the base  of the cylinder
        assertEquals(new Vector(1/Math.sqrt(2),1/Math.sqrt(2),0),
                cyl1.getNormal(new Point(0,0,0)),
                "ERROR: the getNormal with the point on the center of the base  of the cylinder");

        //TC05: testing the getNormal with the point on the corner of the cylinder
        assertEquals(new Vector(1/Math.sqrt(2),1/Math.sqrt(2),0),
                cyl1.getNormal(new Point(0,0,1).add(ray1.getDirection().scale(9))),
                "ERROR: the getNormal with the point on the corner of the cylinder");
        //TC06: testing the getNormal with the point on the center, of the base  of the cylinder
        assertEquals(new Vector(1/Math.sqrt(2),1/Math.sqrt(2),0),
                cyl1.getNormal(new Point(0,0,0).add(ray1.getDirection().scale(9))),
                "ERROR: the getNormal with the point on the center of the base  of the cylinder");

    }
}