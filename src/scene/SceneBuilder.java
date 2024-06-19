package scene;

import geometries.*;
import lighting.AmbientLight;
import org.w3c.dom.*;
import primitives.Color;
import primitives.Point;
import primitives.Double3;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * A class to build a scene from an XML file.
 */
public class SceneBuilder {

    /**
     * Builds a Scene object from an XML file.
     *
     * @param fileName the name of the XML file
     * @return the built Scene object
     */
    public static Scene buildSceneFromXml(String fileName) {
        try {
            // Create a File object from the file name
            File inputFile = new File(fileName);

            // Initialize XML document builder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            // Normalize XML document
            doc.getDocumentElement().normalize();

            // Get scene name
            String sceneName = doc.getDocumentElement().getAttribute("name");
            Scene scene = new Scene(sceneName);

            // Parse background color
            String bgColorStr = doc.getDocumentElement().getAttribute("background-color");
            scene.setBackground(parseColor(bgColorStr));

            // Parse ambient light
            Node ambientLightNode = doc.getElementsByTagName("ambient-light").item(0);
            if (ambientLightNode != null) {
                Element ambientLightElement = (Element) ambientLightNode;
                String alColorStr = ambientLightElement.getAttribute("color");
                scene.setAmbientLight(new AmbientLight(parseColor(alColorStr), Double3.ONE));
            }

            // Parse geometries
            Geometries geometries = new Geometries();
            NodeList geometryList = doc.getElementsByTagName("geometries").item(0).getChildNodes();
            for (int i = 0; i < geometryList.getLength(); i++) {
                Node geometryNode = geometryList.item(i);
                if (geometryNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element geometryElement = (Element) geometryNode;
                    geometries.add(parseGeometry(geometryElement));
                }
            }
            scene.setGeometries(geometries);

            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse XML file");
        }
    }

    /**
     * Parses a color string in the format "R G B" and returns a Color object.
     *
     * @param colorStr the color string
     * @return the parsed Color object
     */
    private static Color parseColor(String colorStr) {
        String[] rgb = colorStr.split(" ");
        return new Color(Double.parseDouble(rgb[0]), Double.parseDouble(rgb[1]), Double.parseDouble(rgb[2]));
    }

    /**
     * Parses a geometry element and returns the corresponding Geometry object.
     *
     * @param geometryElement the geometry element
     * @return the parsed Geometry object
     */
    private static Geometry parseGeometry(Element geometryElement) {

        String type = geometryElement.getTagName();
        switch (type) {
            case "sphere":
                double radius = Double.parseDouble(geometryElement.getAttribute("radius"));
                Point center = parsePoint(geometryElement.getAttribute("center"));
                return new Sphere(radius,center);
            case "triangle":
                Point p0 = parsePoint(geometryElement.getAttribute("p0"));
                Point p1 = parsePoint(geometryElement.getAttribute("p1"));
                Point p2 = parsePoint(geometryElement.getAttribute("p2"));
                return new Triangle(p0, p1, p2);
            case "polygon":
                List<Point> points=new LinkedList<>();
                int i = 0;
                String pointAttribute;
                // Loop until no more point attributes are found
                while ( !(pointAttribute = geometryElement.getAttribute("p" + i)).isEmpty()) {
                    points.add(parsePoint(pointAttribute));
                    i++;
                }
                return new Polygon(points);
            default:
                throw new IllegalArgumentException("Unknown geometry type: " + type);
        }
    }

    /**
     * Parses a point string in the format "X Y Z" and returns a Point object.
     *
     * @param pointStr the point string
     * @return the parsed Point object
     */
    private static Point parsePoint(String pointStr) {
        String[] xyz = pointStr.split(" ");
        return new Point(Double.parseDouble(xyz[0]), Double.parseDouble(xyz[1]), Double.parseDouble(xyz[2]));
    }
}
