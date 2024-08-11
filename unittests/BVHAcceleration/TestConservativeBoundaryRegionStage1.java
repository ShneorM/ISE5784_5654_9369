package BVHAcceleration;

import geometries.Container;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.ReflectionRefractionTests;

import java.util.function.Function;

import static BVHAcceleration.TeapotTest.teapot;
import static java.lang.System.currentTimeMillis;
import static renderer.ReflectionRefractionTests.createPingPongTableScene;

/**
 * test the conservative boundary region
 * and compare it to working without bvh
 */
public class TestConservativeBoundaryRegionStage1 {
    static final String mls=" milliseconds.";
    /**
     * Benchmarks a scene rendering function with and without BVH acceleration and prints the results.
     *
     * @param sceneName      The name of the scene being benchmarked.
     * @param renderFunction The function that renders the scene and returns the time taken.
     */
    private void benchmarkScene(String sceneName, Function<Boolean, Long> renderFunction) {
        long noBvh = renderFunction.apply(false); // Render without BVH acceleration
        long withBvh = renderFunction.apply(true); // Render with BVH acceleration

        System.out.println(sceneName + ":");
        System.out.println("At this stage, we only use the bounding box for each box with no hierarchy.");
        System.out.println("The run with no BVH acceleration was " + noBvh + " ms");
        System.out.println("The run with the BVH was " + withBvh + " ms");
        System.out.println("The code ran " + (double) noBvh / withBvh + " times faster with the BVH than without.");
        System.out.println();
    }

    @Test
    public void comparePingPongTableScene() {
        benchmarkScene("Ping Pong Table Scene", ReflectionRefractionTests::createPingPongTableScene);
    }

    @Test
    public void compareTeapotScene() {
        benchmarkScene("Teapot Scene", TeapotTest::teapot);
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

        System.out.println("triangles with no box " + trianglesTime+mls);
        System.out.println("triangles with box " + boxesTime+mls);
        System.out.println();

    }

}
