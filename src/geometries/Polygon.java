package geometries;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
//public class Polygon extends Geometry
//this is what was written but geometry is an interface, so we changed it
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    /**
     * The size of the polygon - the amount of the vertices in the polygon
     */
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with the normal. If all the rest consequent edges will generate the same sign
        // - the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }
    /**
     * Polygon constructor based on a list of vertices. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of vertices
     */
    public Polygon(List<Point> vertices) {
        this(vertices.toArray(new Point[0]));
    }
    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        // Finds intersections between the ray and the plane containing this polygon.
        var list = plane.findIntersections(ray,maxDistance);
        if (list==null)
            return null;
        // Calculates the vectors from the ray's head to each vertex of the polygon.
        List<Vector> vs = new ArrayList<>(size);
        for (Point vertex : vertices) {
            vs.add(vertex.subtract(ray.getHead()));
        }
        // Initializes a list to store the calculated 't' values for each edge.
        List<Double> ts = new ArrayList<>(size);

        // Calculates the cross products of consecutive edges to get the normal vectors of each polygon edge.
        List<Vector> ns = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            ns.add(vs.get(i).crossProduct(vs.get((i + 1) % size)));
        }

        // Calculates the 't' values by taking the dot product of each normal vector and the ray direction.
        for (Vector n : ns) {
            ts.add(n.dotProduct(ray.getDirection()));
        }

        // Determines the direction of the first 't' value and assigns a flag accordingly.
        int flag;
        if (isZero(ts.getFirst()))
            return null;
        else if (ts.getFirst() > 0)
            flag = 1;
        else
            flag = -1;

        // Checks if there are any invalid 't' values that violate the direction determined by the first 't' value.
        for (Double t : ts) {
            if (isZero(t))
                return null;
            if ((t > 0 && flag == -1) || (t < 0 && flag == 1))
                return null;
        }

        // Returns the list of intersection points.
        return List.of(new GeoPoint(this, list.getFirst()));
    }
}
