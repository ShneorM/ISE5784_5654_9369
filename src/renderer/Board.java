package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

public class Board {

    /**
     * The center point of the board.
     */
    private Point center;

    /**
     * The up vector defining the orientation of the board.
     */
    private Vector VUp;

    /**
     * The right vector defining the orientation of the board.
     */
    private Vector VRight;

    /**
     * The size of the board.
     */
    private double size;

    /**
     * Indicates whether the board should be treated as a circle.
     */
    private boolean circle = false;

    /**
     * Constructs a Board with the specified center point, up vector, right vector, and size.
     *
     * @param center the center point of the board
     * @param VUp the up vector defining the orientation of the board
     * @param VRight the right vector defining the orientation of the board
     * @param size the size of the board
     */
    public Board(Point center, Vector VUp, Vector VRight, double size) {
        this.center = center;
        this.VUp = VUp;
        this.VRight = VRight;
        this.size = size;
    }

    /**
     * Sets whether the board should be treated as a circle and returns the updated Board object.
     *
     * @param circle true if the board should be treated as a circle, false otherwise
     * @return the updated Board object with the circle property set
     */
    public Board setCircle(boolean circle) {
        this.circle = circle;
        return this;
    }

    /**
     * Generates a list of points evenly distributed in a square pattern on the board.
     *
     * @param numberOfSamplesInRow the number of samples per row
     * @return a list of points in a square pattern
     */
    private List<Point> getPointsSquare(int numberOfSamplesInRow) {
        double subPixelSize = size / numberOfSamplesInRow;
        List<Point> points = new ArrayList<>(numberOfSamplesInRow * numberOfSamplesInRow);
        Point point;
        double x, y;
        for (int i = 0; i < numberOfSamplesInRow; i++) {
            for (int j = 0; j < numberOfSamplesInRow; j++) {
                y = (-(i - (numberOfSamplesInRow - 1.0) / 2.0) * subPixelSize) + (Math.random() - 0.5) * subPixelSize;
                x = ((j - (numberOfSamplesInRow - 1.0) / 2.0) * subPixelSize) + (Math.random() - 0.5) * subPixelSize;
                point = center;

                if (!isZero(x)) {
                    point = point.add(VRight.scale(x));
                }
                if (!isZero(y)) {
                    point = point.add(VUp.scale(y));
                }
                points.add(point);
            }
        }
        return points;
    }

    /**
     * Generates a list of points evenly distributed in a circular pattern on the board.
     *
     * @param numberOfSamplesPerRow the number of samples per row
     * @return a list of points in a circular pattern
     */
    private List<Point> getPointsCircle(int numberOfSamplesPerRow) {
        List<Point> pointsSquare = getPointsSquare(numberOfSamplesPerRow);
        List<Point> pointsCircled = new ArrayList<>((int) Math.ceil(numberOfSamplesPerRow * numberOfSamplesPerRow * Math.PI));
        double radiusSquared = (size / 2) * (size / 2);
        for (Point point : pointsSquare)
            if (point.distanceSquared(center) < radiusSquared)
                pointsCircled.add(point);

        return pointsCircled;
    }

    /**
     * Generates a list of points based on the number of samples per row.
     * The pattern can be either square or circular depending on the circle property.
     *
     * @param numberOfSamplesPerRow the number of samples per row
     * @return a list of points in either a square or circular pattern
     */
    public List<Point> getPoints(int numberOfSamplesPerRow) {
        if (!circle)
            return getPointsSquare(numberOfSamplesPerRow);
        else
            return getPointsCircle(numberOfSamplesPerRow);
    }

}
