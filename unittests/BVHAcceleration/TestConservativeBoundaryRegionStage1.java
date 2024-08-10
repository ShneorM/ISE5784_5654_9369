package BVHAcceleration;

import geometries.Container;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static BVHAcceleration.TeapotTest.teapot;
import static java.lang.System.currentTimeMillis;
import static renderer.ReflectionRefractionTests.createPingPongTableScene;

/**
 * test the conservative boundary region
 * and compare it to working without bvh
 */
public class TestConservativeBoundaryRegionStage1 {
    @Test
    public void comparePingPongTableScene() {

        long noBvh = createPingPongTableScene(false);
        long withBvh = createPingPongTableScene(true);

        System.out.println("at this stage we only use the bounding box for each box with no hierarchy");
        System.out.println("the run with no bvh acceleration was " + noBvh + " milliseconds. the run with the bvh was " + withBvh + " milliseconds.");
        System.out.println("the code run " + (double) noBvh / withBvh + " times faster with the bvh than without");


    }

    @Test
    public void compareTeaPotScene() {

        long noBvh = teapot(false);
        long withBvh = teapot(true);

        System.out.println("at this stage we only use the bounding box for each box with no hierarchy");
        System.out.println("the run with no bvh acceleration was " + noBvh + " milliseconds. the run with the bvh was " + withBvh + " milliseconds.");
        System.out.println("the code run " + (double) noBvh / withBvh + " times faster with the bvh than without");


    }

    @Test
    public void comparingTrianglesToBoxes() {
        int bigNumber = 1_000_000;
        long start, finish;
        Container triangle = new Triangle(new Point(0, 1, 1), new Point(0, 1, 0), Point.ZERO);

        Ray ray = new Ray(new Point(-1, -5, 5), new Vector(1, 0, 0));
        double maxDistance = 10;

        triangle.setBvh(false);
        start = currentTimeMillis();
        for (int i = 0; i < bigNumber; ++i) {
            triangle.findGeoIntersections(ray, 10);
        }
        finish = currentTimeMillis();
        long trianglesTime = finish - start;


        triangle.setBvh(true);
        start = currentTimeMillis();
        for (int i = 0; i < bigNumber; ++i) {
            triangle.findGeoIntersections(ray, maxDistance);
        }
        finish=currentTimeMillis();
        long boxesTime = finish - start;

        System.out.println("triangles with no box " + trianglesTime+" milliseconds");
        System.out.println("triangles with  box " + boxesTime+" milliseconds");

    }

}
