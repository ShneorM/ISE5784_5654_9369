package primitives;

/**
 * Class Point is the basic class representing a Point of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Shneor and Emanuel
 */
public class Point {
    final Double3 xyz;

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * @author Shneor and Emanuel
     * @param obj the object we are comparing to the calling object
     * @return whether the calling object and obj are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point other
                && this.xyz.equals(other.xyz);
    }

    /**
     * @author Shneor and Emanuel
     * @return (x,y,z)
     */
    @Override
    public String toString() {
        return xyz.toString();
    }

    public Point add(Vector vector){
        return new Point(xyz.add(vector.xyz));
    }

    public Vector subtract(Point point){
        return new Vector(xyz.subtract(point.xyz));
    }

    public double distance(Point point){
        return Math.sqrt(distanceSquared(point));
    }
    public double distanceSquared(Point point){
        return (xyz.d1-point.xyz.d1)*(xyz.d1-point.xyz.d1)+
         (xyz.d2-point.xyz.d2)*(xyz.d2-point.xyz.d2)+
         (xyz.d3-point.xyz.d3)*(xyz.d3-point.xyz.d3);
    }
}
