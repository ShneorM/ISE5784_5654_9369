package BVHAcceleration;

import org.junit.jupiter.api.Test;

import static BVHAcceleration.TeapotTest.teapot;
import static renderer.ReflectionRefractionTests.createPingPongTableScene;

public class TestAutomaticBuildingOfHierarchyStage3 {
    static final String mls = " milliseconds.";

    @Test
    public void comparePingPongTableScene() {

        long withFlatBvh = createPingPongTableScene(true);
        long withTreeEdgesBvh = createPingPongTableScene(true, 1);
        long withTreeCentersBvh = createPingPongTableScene(true, 2);


        System.out.println("at this stage we only use the bounding box for each box with no hierarchy");
        System.out.println("the run with the flat bvh was " + withFlatBvh + mls);
        System.out.println("the run with the automatically built tree was (distance measured by edges) " + withTreeEdgesBvh + mls);
        System.out.println("the run with the automatically built tree was (distance measured by centers) " + withTreeCentersBvh + mls);
        System.out.println("the code with the tree (with the edges) " + (double) withFlatBvh / withTreeEdgesBvh + " times faster with the tree than with the bvh but without ");
        System.out.println("the code with the tree (with the centers) " + (double) withFlatBvh / withTreeCentersBvh + " times faster with the tree than with the bvh but without ");
        System.out.println();

    }
    @Test
    public void compareTeapotScene() {

        long withFlatBvh = teapot(true);
        long withTreeEdgesBvh = teapot(true, 1);
        long withTreeCentersBvh = teapot(true, 2);


        System.out.println("at this stage we only use the bounding box for each box with no hierarchy");
        System.out.println("the run with the flat bvh was " + withFlatBvh + mls);
        System.out.println("the run with the automatically built tree was (distance measured by edges) " + withTreeEdgesBvh + mls);
        System.out.println("the run with the automatically built tree was (distance measured by centers) " + withTreeCentersBvh + mls);
        System.out.println("we will use for comparison the tree that was built with the distance measured by the edges");
        System.out.println("the code with the tree " + (double) withFlatBvh / withTreeEdgesBvh + " times faster with the tree than with the bvh but without ");
        System.out.println();

    }
}
