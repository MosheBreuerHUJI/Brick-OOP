/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @author Moshe Breuer
 * @version 3.0
 * @since 2021-04-10
 * The class "Point" defines the object by x and y values as if on an axis-system.
 */
public class Point {
    private double xVal, yVal;

    /**
     * Constructor.
     *
     * @param x the value of the point on the x-axis
     * @param y the value of the point on the y-axis
     */
    public Point(double x, double y) {
        this.xVal = x;
        this.yVal = y;
    }

    /**
     * X value getter.
     *
     * @return the value of the point on the x-axis
     */
    public double getX() {
        return this.xVal;
    }

    /**
     * Y value getter.
     *
     * @return the value of the point on the y-axis
     */
    public double getY() {
        return this.yVal;
    }

    /**
     * A method comparing this point and another.
     *
     * @param other is the second point.
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        return Utilities.doubleEquals(this.getX(), other.getX()) && Utilities.doubleEquals(this.getY(), other.getY());
    }

    /**
     * This method measures the distance between this point and the other point.
     * It os based on Pythagoras' theorem.
     *
     * @param other is the point being compared to the one that the method is being called for.
     * @return the distance numeric value.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow((this.xVal - other.xVal), 2) + Math.pow((this.yVal - other.yVal), 2));
    }

    /**
     * A method to determine whether a point belongs to a segment or not.
     *
     * @param line represents a line-SEGMENT. The method assumes that the point belongs to the segment's line.
     * @return true if it is on the segment and false otherwise.
     */
    public boolean isIn(Line line) {
        double distancesSum = this.distance(line.start()) + this.distance(line.end());
        return Utilities.doubleEquals(distancesSum, line.length());
    }

    /**
     * "isInBoth": This method is a utility. It is an implementation of the method "isIn" for two lines.
     *
     * @param l1 is a line we check as described above.
     * @param l2 is a line we check as described above.
     * @return true if the point is on both the segments and false otherwise.
     */
    public boolean isInBoth(Line l1, Line l2) {
        return this.isIn(l1) && this.isIn(l2);
    }

    /**
     * Checking if the point is inside the surface of a given rectangle.
     *
     * @param rec is the given rectangle
     * @return true if the pint is in it, false otherwise
     */
    public boolean isIn(Rectangle rec) {
        double x = this.xVal;
        double y = this.yVal;
        return rec.getUpperLeft().xVal <= x && rec.getLowerRight().xVal >= x
                && rec.getUpperLeft().yVal <= y && rec.getLowerRight().yVal >= y;
    }

    /**
     * Checking if a point is on the perimeter of a given collidable object.
     *
     * @param object is given collidable object
     * @return true if it is, false otherwise
     */
    public boolean isOn(Collidable object) {
        Rectangle rect = object.getCollisionRectangle();
        Line[] lines = rect.getSides();
        for (Line line : lines) {
            if (this.isIn(line)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns the closest point from a given points collection, to our point.
     *
     * @param pointList is the given points collection
     * @return the closest point
     */
    public Point findClosest(List<Point> pointList) {
        if (pointList == null || pointList.size() == 0) {
            return null;
        }

        List<Double> distances = new ArrayList<Double>();
        for (Point currentPoint : pointList) {
            if (currentPoint != null) {
                distances.add(pointList.indexOf(currentPoint), this.distance(currentPoint));
            }
        }
        return pointList.get(distances.indexOf(Utilities.min(distances)));
    }

    @Override
    public String toString() {
        return "Point: (" + xVal + ", " + yVal + ")";
    }
}