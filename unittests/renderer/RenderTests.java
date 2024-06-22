package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.AmbientLight;
import primitives.*;
import renderer.*;
import scene.Scene;
import scene.SceneBuilder;

import java.io.File;

/** Test rendering a basic image
 * @author Emanuel, Shneor and Dan */
public class RenderTests {
   /** Scene of the tests */
   private final Scene          scene  = new Scene("Test scene");
   /** Camera builder of the tests */
   private final Camera.Builder camera = Camera.getBuilder()
      .setRayTracer(new SimpleRayTracer(scene))
      .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0,1,0))
      .setVpDistance(100)
      .setVpSize(500, 500);

   /** Produce a scene with basic 3D model and render it into a png image with a
    * grid */
   @Test
   public void renderTwoColorTest() throws CloneNotSupportedException {
      scene.geometries.add(new Sphere( 50d,new Point(0, 0, -100)),
                           new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                           // left
                           new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                                        new Point(-100, -100, -100)), // down
                           // left
                           new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
      scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
         .setBackground(new Color(75, 127, 90));

      // right
      var c=camera
         .setImageWriter(new ImageWriter("base render test", 1000, 1000))
         .build();
         c.renderImage();
         c.printGrid(100, new Color(YELLOW));
         c.writeToImage();
   }
// For stage 6 - please disregard in stage 5
   /**
    * Produce a scene with basic 3D model - including individual lights of the
    * bodies and render it into a png image with a grid
    */
   @Test
   public void renderMultiColorTest() throws CloneNotSupportedException {
      scene.geometries.add( // center
              new Sphere(50,new Point(0, 0, -100)),
              // up left
              new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
                      .setEmission(new Color(GREEN)),
              // down left
              new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
                      .setEmission(new Color(RED)),
              // down right
              new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
                      .setEmission(new Color(BLUE)));
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2, 0.2, 0.2))); //

      Camera cam = camera
              .setImageWriter(new ImageWriter("color render test", 1000, 1000))
              .build();
              cam.renderImage();
              cam.printGrid(100, new Color(WHITE));
              cam.writeToImage();
   }
   /** Test for XML based scene - for bonus */
   @Test
   public void basicRenderXml() throws CloneNotSupportedException {
      /*
       * Directory path for the image file generation - relative to the user
       * directory
       */
      final String FOLDER_PATH = System.getProperty("user.dir") + "\\XMLFiles\\";

      // Build scene from XML
      Scene scene = SceneBuilder.buildSceneFromXml(FOLDER_PATH + "renderTestTwoColors.xml");
      Camera cam = camera
              .setRayTracer(new SimpleRayTracer(scene))
              .setImageWriter(new ImageWriter("XMLRenderTest", 1000, 1000))
              .build();
      cam.renderImage();
      cam.printGrid(100, new Color(BLACK));
      cam.writeToImage();
   }
}

