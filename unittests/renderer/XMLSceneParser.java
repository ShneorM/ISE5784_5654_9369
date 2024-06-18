package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import javax.xml.parsers.*;
import java.io.*;

public class XMLSceneParser {

    public static Scene parseSceneFromXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        Scene scene = new Scene("Parsed Scene");

        // Parse camera settings
        Element cameraElement = (Element) doc.getElementsByTagName("camera").item(0);
        parseCamera(cameraElement, scene);

        // Parse ambient light
        Element ambientLightElement = (Element) doc.getElementsByTagName("ambientLight").item(0);
        parseAmbientLight(ambientLightElement, scene);

        // Parse background
        Element backgroundElement = (Element) doc.getElementsByTagName("background").item(0);
        parseBackground(backgroundElement, scene);

        // Parse geometries
        Element geometriesElement = (Element) doc.getElementsByTagName("geometries").item(0);
        parseGeometries(geometriesElement, scene);

        return scene;
    }

    private static void parseCamera(Element cameraElement, Scene scene) {
        Camera.Builder cameraBuilder = Camera.getBuilder();

        Element locationElement = (Element) cameraElement.getElementsByTagName("location").item(0);
        double locX = Double.parseDouble(locationElement.getAttribute("x"));
        double locY = Double.parseDouble(locationElement.getAttribute("y"));
        double locZ = Double.parseDouble(locationElement.getAttribute("z"));
        Point location = new Point(locX, locY, locZ);
        cameraBuilder.setLocation(location);

        // Parse direction
        Element directionElement = (Element) cameraElement.getElementsByTagName("direction").item(0);
        double toX = Double.parseDouble(directionElement.getAttribute("to_x"));
        double toY = Double.parseDouble(directionElement.getAttribute("to_y"));
        double toZ = Double.parseDouble(directionElement.getAttribute("to_z"));
        double upX = Double.parseDouble(directionElement.getAttribute("up_x"));
        double upY = Double.parseDouble(directionElement.getAttribute("up_y"));
        double upZ = Double.parseDouble(directionElement.getAttribute("up_z"));
        Vector to = new Vector(toX, toY, toZ);
        Vector up = new Vector(upX, upY, upZ);
        cameraBuilder.setDirection(to, up);

        // Parse vpDistance and vpSize
        double vpDistance = Double.parseDouble(cameraElement.getElementsByTagName("vpDistance").item(0).getTextContent());
        cameraBuilder.setVpDistance(vpDistance);

        Element vpSizeElement = (Element) cameraElement.getElementsByTagName("vpSize").item(0);
        double vpWidth = Double.parseDouble(vpSizeElement.getAttribute("width"));
        double vpHeight = Double.parseDouble(vpSizeElement.getAttribute("height"));
        cameraBuilder.setVpSize(vpWidth, vpHeight);

        // Set ray tracer (assuming a default SimpleRayTracer for simplicity)
        cameraBuilder.setRayTracer(new SimpleRayTracer(scene));


    }

    private static void parseAmbientLight(Element ambientLightElement, Scene scene) {
        Color ambientColor = parseColorAttribute(ambientLightElement.getAttribute("color"));
        double intensity = Double.parseDouble(ambientLightElement.getAttribute("intensity"));
        scene.setAmbientLight(new AmbientLight(ambientColor, intensity));
    }

    private static void parseBackground(Element backgroundElement, Scene scene) {
        Color bgColor = parseColorAttribute(backgroundElement.getAttribute("color"));
        scene.setBackground(bgColor);
    }

    private static void parseGeometries(Element geometriesElement, Scene scene) {
        NodeList geometries = geometriesElement.getElementsByTagName("*");
        for (int i = 0; i < geometries.getLength(); i++) {
            Element geomElement = (Element) geometries.item(i);
            String tagName = geomElement.getTagName();
            switch (tagName) {
                case "sphere":
                    double radius = Double.parseDouble(geomElement.getAttribute("radius"));
                    String centerStr = geomElement.getAttribute("center");
                    Point center = parsePoint(centerStr);
                    scene.geometries.add(new Sphere(radius, center));
                    break;
                case "triangle":
                    String point1Str = geomElement.getAttribute("point1");
                    String point2Str = geomElement.getAttribute("point2");
                    String point3Str = geomElement.getAttribute("point3");
                    Point point1 = parsePoint(point1Str);
                    Point point2 = parsePoint(point2Str);
                    Point point3 = parsePoint(point3Str);
                    scene.geometries.add(new Triangle(point1, point2, point3));
                    break;
                // Add more geometry types as needed
            }
        }
    }

    private static Point parsePoint(String pointStr) {
        String[] coordinates = pointStr.split(",");
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);
        double z = Double.parseDouble(coordinates[2]);
        return new Point(x, y, z);
    }

    private static Color parseColorAttribute(String colorStr) {
        String[] rgb = colorStr.split(",");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);
        return new Color(r, g, b);
    }
}
