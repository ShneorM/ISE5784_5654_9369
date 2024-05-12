package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 *
 */
public class Camera implements Cloneable {
    private Point location;
    private Vector vUp, vRight, vTo;
    private double height = 0.0, width = 0.0, distance = 0.0;

    /**
     * @return
     */
    public Point getLocation() {
        return location;
    }

    /**
     * @return
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * @return
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * @return
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * @return
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return
     */
    public double getDistance() {
        return distance;
    }

    /**
     *
     */
    private Camera() {
    }

    /**
     * @return
     */
    static public Builder getBuilder() {
        return null;
    }

    /**
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }

    /**
     *
     */
    public static class Builder {
        final private Camera camera = new Camera();

        Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        Builder setDirection(Vector vUp, Vector vTo) throws IllegalArgumentException {
            if (!isZero(vUp.dotProduct(vTo)))
                throw new IllegalArgumentException("the vectors vTo and vUp are not perpendicular")
            camera.vUp = vUp.normalize();
            camera.vTo = vTo.normalize();
            return this;
        }

        Builder setVpSize(double width,double height){
            camera.width=width;
            camera.height=height;
            return this;
        }
        Builder  setVpDistance(double distance){
            camera.distance=distance;
            return this;
        }
    }

}
