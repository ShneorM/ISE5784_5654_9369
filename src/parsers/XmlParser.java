package parsers;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import renderer.ImageWriter;
import scene.Scene;
import lighting.AmbientLight;

import static java.nio.file.Files.exists;


public class XmlParser {
    public static Scene parseSceneFromXml(String filename,Scene scene)  {
        try {

            Path path = Paths.get(filename);
            if (!exists(path)) {
                return null;
            }


            File xmlFile = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();


            Element sceneElement = (Element) doc.getElementsByTagName("scene").item(0);
            String backgroundColor = sceneElement.getAttribute("background-color");


            // parse background color
            if (backgroundColor != null && !backgroundColor.isEmpty()) {

                Point pBackgroundColor =  parsePointFromElement(backgroundColor);
                scene.setBackground(new Color(pBackgroundColor.getX(), pBackgroundColor.getY(), pBackgroundColor.getZ()));

            }



            // parse ambient light
            Element ambientLightElement = (Element) doc.getElementsByTagName("ambient-light").item(0);
            String ambientLight = ambientLightElement.getAttribute("color");

            if (ambientLight != null && !ambientLight.isEmpty()) {
                Point pAmbientLight =  parsePointFromElement(ambientLight);

                try {
                    double emission = Double.parseDouble(ambientLightElement.getAttribute("emission"));


                    scene.setAmbientLight(
                            new AmbientLight(
                                    new Color(
                                            pAmbientLight.getX(),
                                            pAmbientLight.getY(),
                                            pAmbientLight.getZ()),
                                    emission));
                }
                catch (Exception e) {

                    scene.setAmbientLight(
                            new AmbientLight(
                                    new Color(
                                            pAmbientLight.getX(),
                                            pAmbientLight.getY(),
                                            pAmbientLight.getZ()),
                                    new Double3(1, 1, 1)));
                }

            }



            // parse geometries
            Element geometriesElement = (Element) doc.getElementsByTagName("geometries").item(0);
            if (geometriesElement != null) {
                Geometries geometries = new Geometries();
                NodeList shapeElements = geometriesElement.getElementsByTagName("*");
                for (int i = 0; i < shapeElements.getLength(); i++) {

                    Element shapeElement = (Element) shapeElements.item(i);
                    String shapeType = shapeElement.getNodeName();

                    switch (shapeType) {
                        case "sphere" -> {
                            // parse sphere parameters and add it to geometries

                            //Element center = (Element) shapeElement.getElementsByTagName("sphere").item(0);
                            Point centerPoint = parsePointFromElement(shapeElement.getAttribute("center"));

                            double radius = Double.parseDouble(shapeElement.getAttribute("radius"));
                            geometries.add(new Sphere(radius,centerPoint));
                        }
                        case "triangle" -> {
                            // parse triangle parameters and add it to geometries
                            Point pointA = parsePointFromElement( shapeElement.getAttribute("p0"));
                            Point pointB = parsePointFromElement( shapeElement.getAttribute("p1"));
                            Point pointC = parsePointFromElement( shapeElement.getAttribute("p2"));

                            geometries.add(new Triangle(pointA, pointB, pointC));
                        }
                        // add more shape types here
                    }
                }

                scene.setGeometries(geometries);
            }

            return scene;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ImageWriter parseImageWriterFromXml(String filename)  {
        try {

            Path path = Paths.get(filename);
            if (!exists(path)) {
                return null;
            }


            File xmlFile = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();


            Element sceneElement = (Element) doc.getElementsByTagName("ImageWriter").item(0);

            int nX = Integer.parseInt(sceneElement.getAttribute("nX"));
            int nY = Integer.parseInt(sceneElement.getAttribute("nY"));
            String imageName =  sceneElement.getAttribute("imageName");
            return new ImageWriter(imageName , nX, nY);
        }
        catch (Exception e){
            return null;
        }

    }

    private static Point parsePointFromElement(String pointElement) {

        String[] colorValues = pointElement.split(" ");
        int r = Integer.parseInt(colorValues[0]);
        int g = Integer.parseInt(colorValues[1]);
        int b = Integer.parseInt(colorValues[2]);
        return new Point(r, g, b);
    }
}