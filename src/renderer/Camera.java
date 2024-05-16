package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.time.LocalDate;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
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
     * another ctor without permission!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */


    /**
     * @return
     */
    static public Builder getBuilder() {
        return new Builder();
    }

    /**
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //Image center
        Point pointCenter = location.add(vTo.scale(distance));

        //Calculate the size of each pixel
        double Rx = width/nX;
        double Ry = height/nY;

        //Calculation of displacement according to i j
        double Xj = (j - (double)(nX - 1)/2)*Rx;
        double Yi = -(i - (double)(nY - 1)/2)*Ry;

        //Calculating the pixels function according to i j and gives a point
        Point Pij = pointCenter;
        if (alignZero(Xj) != 0){
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (alignZero(Yi) != 0){
            Pij = Pij.add(vUp.scale(Yi));
        }

        //Calculation of the vector from the point to the screen according to i j
        Vector viewIJ =  Pij.subtract(location);

        //Returns the ray from the point by i j
        return new Ray(location,viewIJ);
    }

    /**
     *
     */
    public static class Builder {

        final private Camera camera = new Camera();

        /**
         * @param location
         * @return
         */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        /**
         * @param vUp
         * @param vTo
         * @return
         * @throws IllegalArgumentException
         */
        public Builder setDirection(Vector vUp, Vector vTo) throws IllegalArgumentException {
            if (!isZero(vUp.dotProduct(vTo)))
                throw new IllegalArgumentException("the vectors vTo and vUp are not perpendicular");
            camera.vUp = vUp.normalize();
            camera.vTo = vTo.normalize();
            return this;
        }

        /**
         * @param width
         * @param height
         * @return
         */
        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * @param distance
         * @return
         */
        public Builder setVpDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        /**
         * @return
         */
        public Camera build() throws CloneNotSupportedException {
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            String h = "height", w = "width", d = "distance";

            if (isZero(camera.height))
                throw new MissingResourceException("Missing data to render", "Camera", h);
            if (isZero(camera.width))
                throw new MissingResourceException("Missing data to render", "Camera", w);
            if (isZero(camera.distance))
                throw new MissingResourceException("Missing data to render", "Camera", d);
            if (camera.location == null)
                throw new MissingResourceException("Missing data to render", "Camera", "location");
            if (camera.vUp == null)
                throw new MissingResourceException("Missing data to render", "Camera", "vUp");
            if (camera.vTo == null)
                throw new MissingResourceException("Missing data to render", "Camera", "vTo");


            if (camera.height < 0)
                throw new IllegalArgumentException("The " + h + " value is invalid");
            if (camera.width < 0)
                throw new IllegalArgumentException("The " + w + " value is invalid");
            if (camera.distance < 0)
                throw new IllegalArgumentException("The " + d + " value is invalid");

            return (Camera) camera.clone();

        }
    }

}
