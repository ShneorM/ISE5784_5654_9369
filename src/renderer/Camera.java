package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.time.LocalDate;
import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * This class represents a Camera in a 3D space.
 */
public class Camera implements Cloneable {
    private Point location;
    private Vector vUp, vRight, vTo;
    private double height = 0.0, width = 0.0, distance = 0.0;

    /**
     * @return the location of the camera.
     */
    public Point getLocation() {
        return location;
    }

    /**
     * @return the "up" vector of the camera.
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * @return the "right" vector of the camera.
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * @return the "to" vector of the camera.
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * @return the height of the view plane.
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the width of the view plane.
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the distance from the camera to the view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Private constructor to prevent direct instantiation.
     */
    private Camera() {
    }

    /**
     * @return a new Builder instance to build a Camera.
     */
    static public Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a given pixel.
     * @param nX the number of horizontal pixels.
     * @param nY the number of vertical pixels.
     * @param j the pixel column.
     * @param i the pixel row.
     * @return the constructed ray.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Image center
        Point pointCenter = location.add(vTo.scale(distance));

        // Calculate the size of each pixel
        double Rx = width / nX;
        double Ry = height / nY;

        // Calculation of displacement according to i j
        double Xj = (j - (double)(nX - 1) / 2) * Rx;
        double Yi = -(i - (double)(nY - 1) / 2) * Ry;

        // Calculating the pixel's position according to i j and gives a point
        Point Pij = pointCenter;
        if (!isZero(Xj)){
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (!isZero(Yi)){
            Pij = Pij.add(vUp.scale(Yi));
        }

        // Calculation of the vector from the point to the screen according to i j
        Vector viewIJ =  Pij.subtract(location);

        // Returns the ray from the point by i j
        return new Ray(location, viewIJ);
    }

    /**
     * Builder class to build a Camera instance.
     */
    public static class Builder {

        final private Camera camera = new Camera();

        /**
         * @param location the location to set for the camera.
         * @return the Builder instance.
         */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        /**
         * @param vUp the "up" vector to set for the camera.
         * @param vTo the "to" vector to set for the camera.
         * @return the Builder instance.
         * @throws IllegalArgumentException if the vectors are not perpendicular.
         */
        public Builder setDirection(Vector vUp, Vector vTo) throws IllegalArgumentException {
            if (!isZero(vUp.dotProduct(vTo)))
                throw new IllegalArgumentException("the vectors vTo and vUp are not perpendicular");
            camera.vUp = vUp.normalize();
            camera.vTo = vTo.normalize();
            return this;
        }

        /**
         * @param width the width to set for the view plane.
         * @param height the height to set for the view plane.
         * @return the Builder instance.
         */
        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * @param distance the distance to set from the camera to the view plane.
         * @return the Builder instance.
         */
        public Builder setVpDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        /**
         * @return the built Camera instance.
         * @throws CloneNotSupportedException if cloning is not supported.
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
