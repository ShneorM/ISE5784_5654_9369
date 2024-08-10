package BVHAcceleration;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import lighting.*;
import renderer.*;
import renderer.SimpleRayTracer;

import static java.lang.Math.random;

/**
 * tests the hierarchy of the geometries,
 * compares it relative to conservative boundary region (stage 1)
 * and no bvh at all (stage 0)
 */
public class TestBoundaryVolumeHierarchyStage2 {
    static final String mls=" milliseconds";
    @Test
    public void compareNestedGeometryToFlatGeometry(){
        long noBvh= createSmallNestedGeometryScene1(false,false);
        long flatBvh = createSmallNestedGeometryScene1(true,true);
        long nestedBvh= createSmallNestedGeometryScene1(true,false);

        System.out.println("for simple scene:");
        System.out.println("with no bvh it took "+ noBvh+mls);
        System.out.println("with flat bvh it took "+ flatBvh+mls);
        System.out.println("with nested bvh it took "+ nestedBvh+mls);
        System.out.println("the nested time was "+((double)flatBvh/nestedBvh) + " times faster than the flat-bvh run and "+(double)noBvh/nestedBvh+" times than the no-bvh run");
        System.out.println();


        noBvh= createDeeplyNestedGeometryScene(false,false);
        flatBvh = createDeeplyNestedGeometryScene(true,true);
        nestedBvh= createDeeplyNestedGeometryScene(true,false);

        System.out.println("for more complicated scene:");
        System.out.println("with no bvh it took "+ noBvh+mls);
        System.out.println("with flat bvh it took "+ flatBvh+mls);
        System.out.println("with nested bvh it took "+ nestedBvh+mls);
        System.out.println("the nested time was "+(double)flatBvh/nestedBvh+" times faster than the flat-bvh run and "+(double)noBvh/nestedBvh+" times than the no-bvh run");
        System.out.println();

        System.out.println("as you can see it is very much context dependent how much faster it will be");
    }
    /**
     * Creating a scene with nested geometries and a manual BVH hierarchy
     *
     * @return the time that the rendering took
     */
    static public long createSmallNestedGeometryScene1(boolean bvh, boolean flat) {
        final Scene scene = new Scene("Nested Geometry Scene");



        // Define colors
        Color blueColor = new Color(0, 0, 255);
        Color redColor = new Color(255, 0, 0);
        Color greenColor = new Color(0, 255, 0);
        Color yellowColor = new Color(255, 255, 0);
        Color whiteColor = new Color(255, 255, 255);
        Color grayColor = new Color(169, 169, 169);

        // Create floor plane
        Plane floor = (Plane) new Plane(
                new Point(-200, -50, -200),
                new Point(200, -50, -200),
                new Point(200, -50, 200)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(20));
        scene.geometries.add(floor);

        // Create first group of spheres
        Sphere sphere1 = (Sphere) new Sphere(10, new Point(-30, 0, -30))
                .setEmission(redColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5));

        Sphere sphere2 = (Sphere) new Sphere(15, new Point(-15, 0, -15))
                .setEmission(greenColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5));

        Sphere sphere3 = (Sphere) new Sphere(8, new Point(0, 0, 0))
                .setEmission(blueColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5));

        Geometries group1 = new Geometries(sphere1, sphere2, sphere3);

        // Create second group of geometries with different shapes
        Sphere sphere4 = (Sphere) new Sphere(20, new Point(30, 0, 30))
                .setEmission(yellowColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5));

        Polygon polygon1 = (Polygon) new Polygon(
                new Point(20, 0, 20),
                new Point(40, 0, 20),
                new Point(40, 0, 40),
                new Point(20, 0, 40)
        ).setEmission(whiteColor)
                .setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(50));

        Geometries group2 = new Geometries(sphere4, polygon1);

        // Create third group with a combination of previous groups
        Geometries group3 = new Geometries(group1, group2);

        // Add everything to the scene
        scene.geometries.add(group3);

        // Add a light source
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 100, 0))
                .setKL(0.001).setKQ(0.0005));

        // Set ambient light
        scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.1));

        // Set up BVH if needed
        scene.geometries.turnOnOffBvh(bvh);

        // Ensure perpendicular vectors:
        Vector directionVector = new Vector(0, -1, -1).normalize();
        Vector upVector = new Vector(1, 0, -1).normalize(); // Original attempt

        // Check if they are perpendicular:
        double dotProduct = directionVector.dotProduct(upVector);
        if (dotProduct != 0) {
            // Adjust the upVector to be perpendicular to the directionVector
            upVector = directionVector.crossProduct(new Vector(1, 0, 0)).normalize(); // New upVector
        }
        if(flat)
            scene.geometries.flatten();


        // Set up the camera with the corrected vectors
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setDirection(directionVector, upVector) // Corrected perpendicular vectors
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 100, 100)) // Camera is placed to get a clear view
                .setVpDistance(150)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("nestedGeometryScene", 600, 600));

        long start = System.currentTimeMillis();
        cameraBuilder.build().renderImage().writeToImage();
        long finish = System.currentTimeMillis();

        return finish - start;
    }
    /**
     * Creating a complex scene with deeply nested geometries
     *
     * @return the time that the rendering took
     */
    static public long createSmallNestedGeometryScene2(boolean bvh,boolean flat) {
        final Scene scene = new Scene("Deeply Nested Geometry Scene");

        // Define colors
        Color blueColor = new Color(0, 0, 255);
        Color redColor = new Color(255, 0, 0);
        Color greenColor = new Color(0, 255, 0);
        Color yellowColor = new Color(255, 255, 0);
        Color whiteColor = new Color(255, 255, 255);
        Color grayColor = new Color(169, 169, 169);
        Color purpleColor = new Color(128, 0, 128);

        // Create floor plane
        Plane floor = (Plane) new Plane(
                new Point(-500, -50, -500),
                new Point(500, -50, -500),
                new Point(500, -50, 500)
        ).setEmission(grayColor)
                .setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(20));
        scene.geometries.add(floor);

        // Create first level of nested geometries (Level 1)
        Sphere sphere1 = (Sphere) new Sphere(10, new Point(-30, 0, -30))
                .setEmission(redColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5));

        Polygon polygon1 = (Polygon) new Polygon(
                new Point(-50, 0, -50),
                new Point(-10, 0, -50),
                new Point(-10, 0, -10),
                new Point(-50, 0, -10)
        ).setEmission(blueColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));

        Geometries level1Group1 = new Geometries(sphere1, polygon1);

        // Create second level of nested geometries (Level 2)
        Sphere sphere2 = (Sphere) new Sphere(15, new Point(50, 0, 50))
                .setEmission(greenColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5));

        Polygon polygon2 = (Polygon) new Polygon(
                new Point(40, 0, 40),
                new Point(60, 0, 40),
                new Point(60, 0, 60),
                new Point(40, 0, 60)
        ).setEmission(yellowColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));

        Geometries level2Group1 = new Geometries(level1Group1, sphere2, polygon2);

        // Create third level of nested geometries (Level 3)
        Sphere sphere3 = (Sphere) new Sphere(20, new Point(-100, 0, 100))
                .setEmission(purpleColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5));

        Polygon polygon3 = (Polygon) new Polygon(
                new Point(-120, 0, 120),
                new Point(-80, 0, 120),
                new Point(-80, 0, 80),
                new Point(-120, 0, 80)
        ).setEmission(whiteColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));

        Geometries level3Group1 = new Geometries(level2Group1, sphere3, polygon3);

        // Create fourth level of nested geometries (Level 4)
        Sphere sphere4 = (Sphere) new Sphere(25, new Point(150, 0, -150))
                .setEmission(grayColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5));

        Polygon polygon4 = (Polygon) new Polygon(
                new Point(140, 0, -140),
                new Point(160, 0, -140),
                new Point(160, 0, -160),
                new Point(140, 0, -160)
        ).setEmission(redColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));

        Geometries level4Group1 = new Geometries(level3Group1, sphere4, polygon4);

        // Add the top-level group to the scene
        scene.geometries.add(level4Group1);

        // Add a light source
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 300, 0))
                .setKL(0.001).setKQ(0.0005));

        // Set ambient light
        scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.1));

        if(flat)
            scene.geometries.flatten();
        // Set up BVH if needed
        scene.geometries.turnOnOffBvh(bvh);

        // Set up the camera with perpendicular vectors
        Vector directionVector = new Vector(0, -1, -1).normalize();
        Vector upVector = directionVector.crossProduct(new Vector(1, 0, 0)).normalize(); // Ensure perpendicular

        // Set up the camera with the corrected vectors
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setDirection(directionVector, upVector) // Corrected perpendicular vectors
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 300, 300)) // Camera is placed to get a clear view
                .setVpDistance(250)
                .setVpSize(400, 400)
                .setImageWriter(new ImageWriter("deeplyNestedGeometryScene", 800, 800));

        long start = System.currentTimeMillis();
        cameraBuilder.build().renderImage().writeToImage();
        long finish = System.currentTimeMillis();

        return finish - start;
    }
    /**
     * Creating a complex scene with deeply nested geometries
     *
     * @return the time that the rendering took
     */
    static public long createDeeplyNestedGeometryScene(boolean bvh,boolean flat) {
        final Scene scene = new Scene("Deeply Nested Geometry Scene");

        // Define colors
        Color blueColor = new Color(0, 0, 255);
        Color redColor = new Color(255, 0, 0);
        Color greenColor = new Color(0, 255, 0);
        Color yellowColor = new Color(255, 255, 0);
        Color whiteColor = new Color(255, 255, 255);
        Color grayColor = new Color(169, 169, 169);
        Color purpleColor = new Color(128, 0, 128);



        Geometries topRight=new Geometries();
        Geometries topLeft=new Geometries();
        Geometries downLeft=new Geometries();
        Geometries downRight=new Geometries();

        int bigNumber=100 ;
       double offset=50,smallOffset;
        for (int i = 0; i <bigNumber ; i++) {
            topRight.add(new Sphere(
                    new Point(0,offset+(random()-0.5)*10,offset+(random()-0.5)*10),
                    random()*3)
                    .setEmission(greenColor)
                    .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5))
);
            topLeft.add(new Sphere(
                    new Point(0,-offset+(random()-0.5)*10,offset+(random()-0.5)*10),
                    random()*3)
            .setEmission(greenColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5)));
            downLeft.add(new Sphere(
                    new Point(0,-offset+(random()-0.5)*10,-offset+(random()-0.5)*10),
                    random()*3)
            .setEmission(greenColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5)));
            downRight.add(new Sphere(
                    new Point(0,offset+(random()-0.5)*10,-offset+(random()-0.5)*10),
                    random()*3)
            .setEmission(greenColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5)));

            smallOffset=(random()-0.5)*15;
            topRight.add(new Triangle(
                    new Point(0,offset+smallOffset+(random()-0.5)*2,offset+smallOffset+(random()-0.5)*2),
                    new Point(0,offset+smallOffset+(random()-0.5)*2,offset+smallOffset+(random()-0.5)*2),
                    new Point(0,offset+smallOffset+(random()-0.5)*2,offset+smallOffset+(random()-0.5)*2)
            ).setEmission(greenColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5))
);
            topLeft.add(new Triangle(
                    new Point(0,-offset+smallOffset+(random()-0.5)*2,offset+smallOffset+(random()-0.5)*2),
                    new Point(0,-offset+smallOffset+(random()-0.5)*2,offset+smallOffset+(random()-0.5)*2),
                    new Point(0,-offset+smallOffset+(random()-0.5)*2,offset+smallOffset+(random()-0.5)*2)
            ).setEmission(greenColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5))
);
            downLeft.add(new Triangle(
                    new Point(0,-offset+smallOffset+(random()-0.5)*2,-offset+smallOffset+(random()-0.5)*2),
                    new Point(0,-offset+smallOffset+(random()-0.5)*2,-offset+smallOffset+(random()-0.5)*2),
                    new Point(0,-offset+smallOffset+(random()-0.5)*2,-offset+smallOffset+(random()-0.5)*2)
            ).setEmission(greenColor)
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5))
);
            downRight.add(new Triangle(
                    new Point(0,offset+smallOffset+(random()-0.5)*2,-offset+smallOffset+(random()-0.5)*2),
                    new Point(0,offset+smallOffset+(random()-0.5)*2,-offset+smallOffset+(random()-0.5)*2),
                    new Point(0,offset+smallOffset+(random()-0.5)*2,-offset+smallOffset+(random()-0.5)*2))
                    .setEmission(greenColor)
                    .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100).setKT(0.5))
);

        }

        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(-50,0 , 100))
                .setKL(0.001).setKQ(0.0005));


        scene.geometries.add(topRight,topLeft,downLeft,downRight);
        // Set ambient light
        scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.1));

        if(flat)
            scene.geometries.flatten();
        // Set up BVH if needed
        scene.geometries.turnOnOffBvh(bvh);

        // Set up the camera with perpendicular vectors
        Vector directionVector = Vector.X;
        Vector upVector = Vector.Z;

        // Set up the camera with the corrected vectors
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setDirection(directionVector, upVector) // Corrected perpendicular vectors
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(-500, 0, 0)) // Camera is placed to get a clear view
                .setVpDistance(250)
                .setVpSize(400, 400)
                .setImageWriter(new ImageWriter("deeplyNestedGeometryScene", 800, 800));

        long start = System.currentTimeMillis();
        cameraBuilder.build().renderImage().writeToImage();
        long finish = System.currentTimeMillis();

        return finish - start;
    }


}
