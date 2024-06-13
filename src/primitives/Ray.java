package primitives;

import java.util.List;

/**
 * Class Ray is the basic class representing a Ray (an infinite line that starts from one place and goes on)
 * of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 *
 * @author Shneor and Emanuel
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * construct a ray from the head and the normalized direction
     *
     * @param head      the head of the ray
     * @param direction the direction of the ray
     */
    public Ray(Point head, Vector direction) {
        //we don't want to use the normalize (that creates new object) function if not necessary
        if (direction.lengthSquared() != 1) direction = direction.normalize();
        this.head = head;
        this.direction = direction;
    }

    /**
     * Computes the point on the ray based on the given parameter.
     *
     * @param t The parameter determining the position of the point along the ray.
     * @return The point on the ray corresponding to the given parameter.
     */
    public Point getPoint(double t) {
        if (Util.isZero(t))
            return getHead();
        return getHead().add(getDirection().scale(t));
    }


    /**
     * @param obj the parameter that will be compared to the calling object
     * @return whether the calling object and obj have the same head and direction
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Ray other
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    /**
     * @return Ray{head=(the head), direction=(the direction)}
     */
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head.toString() +
                ", direction=" + direction.toString() +
                '}';
    }

    /**
     * get the head of the tube
     *
     * @return the head
     */
    public Point getHead() {
        return head;
    }

    /**
     * get the direction of the tube
     *
     * @return the direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Finds the closest point to the head from a list of points.
     * The distance is calculated using the squared distance to avoid unnecessary square root computations.
     *
     * @param points a list of Point objects from which the closest point to the head will be determined.
     * @return the Point object from the list that is closest to the head.
     */
    public Point findClosestPoint(List<Point> points){
        if (points.isEmpty()) {
            return null;
        }
        Point closest = points.getFirst();
        double minDistanceSquared = closest.distanceSquared(getHead());
        for (var point : points) {
            if (point.distanceSquared(getHead()) < minDistanceSquared) {
                closest = point;
                minDistanceSquared=point.distanceSquared(getHead());
            }
        }
        return closest;
    }
}

