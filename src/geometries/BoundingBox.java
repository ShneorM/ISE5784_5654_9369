package geometries;

import org.jetbrains.annotations.NotNull;
import primitives.Point;
import primitives.Ray;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.pow;
import static primitives.Util.alignZero;


/**
 * class represents axis-aligned bounding box, it is used to check if ray is in the area of a geometry
 * by checking if the ray direction come with intersection in the bounding box of the geometry.
 * It means to us that the calculation of all the intersections of the same ray should be taken into account
 */
public class BoundingBox {

    public final static  char X='x';
    public final static  char Y='y';
    public final static  char Z='z';


    //region x,y,z min and max
    /**
     * xMin - the minimum value of X coordinate of this bounding box
     */
    private double xMin;
    /**
     * yMin - the minimum value of Y coordinate of this bounding box
     */
    private double yMin;
    /**
     * zMin - the minimum value of Z coordinate of this bounding box
     */
    private double zMin;
    /**
     * xMax - the maximum value of X coordinate of this bounding box
     */
    private double xMax;
    /**
     * yMax - the maximum value of Y coordinate of this bounding box
     */
    private double yMax;
    /**
     * zMax - the maximum value of Z coordinate of this bounding box
     */
    private double zMax;
//endregion

    /**
     * constructor for new values for class BoundingBox, with 6 inputs, minimum value and maximum value inputs per axis
     *
     * @param x1 minimum/maximum value in x axis
     * @param x2 minimum/maximum value in x axis
     * @param y1 minimum/maximum value in y axis
     * @param y2 minimum/maximum value in y axis
     * @param z1 minimum/maximum value in z axis
     * @param z2 minimum/maximum value in z axis
     */
    public BoundingBox(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.xMin = Math.min(x1, x2);
        this.xMax = max(x1, x2);
        this.yMin = Math.min(y1, y2);
        this.yMax = max(y1, y2);
        this.zMin = Math.min(z1, z2);
        this.zMax = max(z1, z2);
    }
    //region getters

    /**
     * get min X value
     *
     * @return min x
     */
    public double getMinX() {
        return xMin;
    }

    /**
     * get max X value
     *
     * @return max x
     */
    public double getMaxX() {
        return xMax;
    }

    /**
     * get min y value
     *
     * @return min y
     */
    public double getMinY() {
        return yMin;
    }

    /**
     * get max y value
     *
     * @return max y
     */
    public double getMaxY() {
        return yMax;
    }

    /**
     * get min z value
     *
     * @return min z
     */
    public double getMinZ() {
        return zMin;
    }

    /**
     * get max z value
     *
     * @return max z
     */
    public double getMaxZ() {
        return zMax;
    }
//endregion

    /**
     * Function which checks if a ray intersects the bounding region
     *
     * @param ray the ray to check for intersection
     * @return boolean result, true if intersects, false otherwise
     */
    public boolean intersectBV(Ray ray) {
        return intersectBV(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Function which checks if a ray intersects the bounding region
     *
     * @param ray         the ray to check for intersection
     * @param maxDistance the max distance that the ray allowed to go
     * @return boolean result, true if intersects, false otherwise
     */
    public boolean intersectBV(Ray ray, double maxDistance) {
        Point p0 = ray.getHead();
        Point dirHead = ray.getDirection(); //will cast the Vector to Point

        // the coordinates of the ray direction
        double
                dirHeadX = alignZero(dirHead.getX()),
                dirHeadY = alignZero(dirHead.getY()),
                dirHeadZ = alignZero(dirHead.getZ()),

                // the coordinates of the ray starting point
                rayStartPointX = alignZero(p0.getX()),
                rayStartPointY = alignZero(p0.getY()),
                rayStartPointZ = alignZero(p0.getZ()),

                // define default variables for calculations
                //the t's represent how many times does we need to go with the ray (of length 1) to be in the box
                txMin, txMax,
                tyMin, tyMax,
                tzMin, tzMax;

        // for all 3 axes:
        //
        // calculate the intersection distance t0 and t1
        // (t_Min represent the min and t_Max represent the max)
        //
        //  1. when the values for t are negative, the box is behind the ray (no need to find intersections)
        //  2. if the ray is parallel to an axis it will not intersect with the bounding volume plane for this axis.
        //  3. we first find where the ray intersects the planes defined by each face of the bounding cube,
        //     after that, we find the ray's first and second intersections with the planes.

        if (dirHeadX > 0) {
            txMax = alignZero((getMaxX() - rayStartPointX) / dirHeadX);
            txMin = alignZero((getMinX() - rayStartPointX) / dirHeadX);
        } else if (dirHeadX < 0) {
            txMax = (getMinX() - rayStartPointX) / dirHeadX;
            txMin = alignZero((getMaxX() - rayStartPointX) / dirHeadX);
        } else { // preventing parallel to the x-axis
            if (rayStartPointX >= getMaxX() || rayStartPointX <= getMinX())
                return false;
            txMax = Double.POSITIVE_INFINITY;
            txMin = Double.NEGATIVE_INFINITY;
        }
        if (txMax <= 0) {
            return false; // if value for t_Max is negative, the box is behind the ray.
        }

        if (dirHeadY > 0) {
            tyMax = alignZero((getMaxY() - rayStartPointY) / dirHeadY);
            tyMin = alignZero((getMinY() - rayStartPointY) / dirHeadY);
        } else if (dirHeadY < 0) {
            tyMax = alignZero((getMinY() - rayStartPointY) / dirHeadY);
            tyMin = alignZero((getMaxY() - rayStartPointY) / dirHeadY);
        } else { // preventing parallel to the y-axis
            if (rayStartPointY >= getMaxY() || rayStartPointY <= getMinY())
                return false;
            tyMax = Double.POSITIVE_INFINITY;
            tyMin = Double.NEGATIVE_INFINITY;
        }
        if (tyMax <= 0) {
            return false; // if value for tyMax is negative, the box is behind the ray.
        }
        // cases where the ray misses the cube
        // the ray misses the box when t0x is greater than t1y and when t0y is greater than  t1x
        if ((txMin > tyMax) || (tyMin > txMax)) {
            return false;
        }

        // we find which one of these two points lie on the cube by comparing their values:
        // we simply need to choose the point which value for t is the greatest.
        if (tyMin > txMin)
            txMin = tyMin;
        // we find the second point where the ray intersects the box
        // we simply need to choose the point which value for t is the smallest
        if (tyMax < txMax)
            txMax = tyMax;

        if (dirHeadZ > 0) {
            tzMax = (getMaxZ() - rayStartPointZ) / dirHeadZ;
            tzMin = (getMinZ() - rayStartPointZ) / dirHeadZ;
        } else if (dirHeadZ < 0) {
            tzMax = (getMinZ() - rayStartPointZ) / dirHeadZ;
            tzMin = (getMaxZ() - rayStartPointZ) / dirHeadZ;
        } else { // preventing parallel to the z axis
            if (rayStartPointZ >= getMaxZ() || rayStartPointZ <= getMinZ())
                return false;
            tzMax = Double.POSITIVE_INFINITY;
            tzMin = Double.NEGATIVE_INFINITY;
        }
        if (tzMax <= 0) {
            return false; // if value for tzMax is negative, the box is behind the ray.
        }

        //txs here represent the tighter bound of x and y
        // cases where the ray misses the cube
        // the ray misses the box when t0 is greater than t1z and when t0z is greater than  t1
        if (txMin > tzMax || tzMin > txMax)
            return false;

        return maxDistance > max(txMin, tzMin);
    }

    //region distance metric
    /**
     * Calculates the Euclidean distance between this bounding box and another bounding box.
     * The distance is calculated based on the edges of the bounding boxes by default.
     *
     * @param other The other bounding box to calculate the distance to.
     * @return The Euclidean distance between this bounding box and the other bounding box.
     */
    public double boundingBoxDistance(BoundingBox other) {
        return boundingBoxDistance(other, true);
    }

    /**
     * Calculates the squared Euclidean distance between this bounding box and another bounding box.
     * The squared distance is calculated to avoid the computational cost of a square root operation.
     * The distance is calculated based on the edges of the bounding boxes by default.
     *
     * @param other The other bounding box to calculate the squared distance to.
     * @return The squared Euclidean distance between this bounding box and the other bounding box.
     */
    public double boundingBoxDistanceSquared(BoundingBox other) {
        return boundingBoxDistanceSquared(other, true);
    }

    /**
     * Calculates the Euclidean distance between this bounding box and another bounding box.
     * The distance can be calculated either between the edges of the bounding boxes or their centers.
     *
     * @param other The other bounding box to calculate the distance to.
     * @param edges If true, the distance is calculated between the edges of the bounding boxes.
     *              If false, the distance is calculated between the centers of the bounding boxes.
     * @return The Euclidean distance between this bounding box and the other bounding box.
     */
    public double boundingBoxDistance(BoundingBox other, boolean edges) {
        return Math.sqrt(boundingBoxDistanceSquared(other, edges));
    }

    /**
     * Calculates the squared Euclidean distance between this bounding box and another bounding box.
     * The squared distance is calculated to avoid the computational cost of a square root operation.
     * The distance can be calculated either between the edges of the bounding boxes or their centers.
     *
     * @param other The other bounding box to calculate the squared distance to.
     * @param edges If true, the squared distance is calculated between the edges of the bounding boxes.
     *              If false, the squared distance is calculated between the centers of the bounding boxes.
     * @return The squared Euclidean distance between this bounding box and the other bounding box.
     */
    public double boundingBoxDistanceSquared(BoundingBox other, boolean edges) {
        if (edges)
            return boundingBoxDistanceBetweenEdgesSquared(other);
        return boundingBoxDistanceBetweenCentersSquared(other);
    }

    /**
     * calculate the maximum distance between the edges
     *
     * @param other the other boundingBox
     * @return the distance
     */
    private double boundingBoxDistanceBetweenEdgesSquared(@NotNull BoundingBox other) {
        return pow(max(
                        this.getMaxX() - other.getMinX(),
                        other.getMaxX() - this.getMinX()),
                2) +
                pow(max(
                                this.getMaxY() - other.getMinY(),
                                other.getMaxY() - this.getMinY()),
                        2) +
                pow(max(
                                this.getMaxZ() - other.getMinZ(),
                                other.getMaxZ() - this.getMinZ()),
                        2);
    }

    /**
     * function to get the center of the bounding box
     *
     * @return the Point in the middle of the bounding box
     */
    protected Point getBoundingBoxCenter() {

        return new Point(
                (getMaxX() + getMinX()) / 2,
                (getMaxY() + getMinY()) / 2,
                (getMaxZ() + getMinZ()) / 2
        );
    }


    /**
     * function to get the distance between the centers of two bounding boxes
     *
     * @param other - the other bounding box
     * @return the distance between the center of the boxes
     */
    private double boundingBoxDistanceBetweenCentersSquared(@NotNull BoundingBox other) {
        return this.getBoundingBoxCenter().distanceSquared(other.getBoundingBoxCenter());
    }

    //endregion

    /**
     * @return the static final variable x, y or z depends on who has the longest range
     */
    private char findLongest()
    {
        double x=getMaxX()-getMinX(),y=getMaxY()-getMinY(),z=getMaxZ()-getMinZ();
        if(x>y && x>z)
            return BoundingBox.X;
        if(y>z)
            return BoundingBox.Y;
        return BoundingBox.Z;
    }
    /**
     * Divides the bounding box along its longest axis and returns a list of two new bounding boxes.
     *
     * @return A list of two BoundingBox objects resulting from the division along the longest axis.
     */
    List<BoundingBox> divide() {
        char longest = findLongest();
        double average;

        return switch (longest) {
            case BoundingBox.X -> {
                average = (getMaxX() + getMinX()) / 2;
                yield List.of(
                        new BoundingBox(getMinX(), average, getMinY(), getMaxY(), getMinZ(), getMaxZ()),
                        new BoundingBox(average, getMaxX(), getMinY(), getMaxY(), getMinZ(), getMaxZ())
                );
            }
            case BoundingBox.Y -> {
                average = (getMaxY() + getMinY()) / 2;
                yield List.of(
                        new BoundingBox(getMinX(), getMaxX(), getMinY(), average, getMinZ(), getMaxZ()),
                        new BoundingBox(getMinX(), getMaxX(), average, getMaxY(), getMinZ(), getMaxZ())
                );
            }
            case BoundingBox.Z -> {
                average = (getMaxZ() + getMinZ()) / 2;
                yield List.of(
                        new BoundingBox(getMinX(), getMaxX(), getMinY(), getMaxY(), getMinZ(), average),
                        new BoundingBox(getMinX(), getMaxX(), getMinY(), getMaxY(), average, getMaxZ())
                );
            }
            default ->
                    throw new RuntimeException("the function longest returned something that is not BoundingBox.X, Y or Z ");
        };
    }

    /**
     *
     * @param other possibly smaller bounding box within this one
     * @return whether other is contained inside this one
     */
    public boolean contain(BoundingBox other){
        return other!=null&&
                getMinX()<=other.getMinX()&&
                getMaxX()>= other.getMaxX()&&
                getMinY()<=other.getMinY()&&
                getMaxY()>= other.getMaxY()&&
                getMinZ()<=other.getMinZ()&&
                getMaxZ()>= other.getMaxZ();

    }
}