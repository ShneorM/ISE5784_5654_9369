package BVHAcceleration;

import org.junit.jupiter.api.Test;
import renderer.ReflectionRefractionTests;

import java.util.function.BiFunction;

/**
 * comparing building a tree automatically and then rendering vs just rendering with bvh
 */
public class TestAutomaticBuildingOfHierarchyStage3 {
    static final String mls = " milliseconds.";

    /**
     * Benchmarks a scene rendering function with different BVH tree configurations and prints the results.
     *
     * @param sceneName      The name of the scene being benchmarked.
     * @param renderFunction The function that renders the scene and returns the time taken.
     */
    private void benchmarkScene(String sceneName, BiFunction<Boolean, Integer, Long> renderFunction) {
        long withFlatBvh = renderFunction.apply(true, 0); // No hierarchy, flat BVH
        long withBinaryTreeEdgesBvh = renderFunction.apply(true, 1); // Binary tree with distance measured by edges
        long withBinaryTreeCentersBvh = renderFunction.apply(true, 2); // Binary tree with distance measured by centers
        long withTreeBvh = renderFunction.apply(true, 3); // Automatically built tree

        System.out.println(sceneName);
        System.out.println("The run with the flat BVH was " + withFlatBvh + " ms");
        System.out.println("The run with the automatically built binary tree was (distance measured by edges) " + withBinaryTreeEdgesBvh + mls);
        System.out.println("The run with the automatically built binary tree was (distance measured by centers) " + withBinaryTreeCentersBvh + mls);
        System.out.println("The run with the automatically built tree was " + withTreeBvh + mls);
        System.out.println("The code with the binary tree (Edges) was " + (double) withFlatBvh / withBinaryTreeEdgesBvh + " times faster with the tree than with the flat BVH.");
        System.out.println("The code with the binary tree (Centers) was " + (double) withFlatBvh / withBinaryTreeCentersBvh + " times faster with the tree than with the flat BVH.");
        System.out.println("The code with the tree was " + (double) withFlatBvh / withTreeBvh + " times faster with the tree than with the flat BVH.");
        System.out.println();
    }

    @Test
    public void comparePingPongTableScene() {
        benchmarkScene("ping pong:", ReflectionRefractionTests::createPingPongTableScene);

    }

    @Test
    public void compareTeapotScene() {
        benchmarkScene("teapot:", TeapotTest::teapot);
    }
}
