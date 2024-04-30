package primitives;

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
     * @return the head
     */
    public Point getHead() {
        return head;
    }

    /**
     * get the direction of the tube
     * @return the direction
     */
    public Vector getDirection() {
        return direction;
    }
}
