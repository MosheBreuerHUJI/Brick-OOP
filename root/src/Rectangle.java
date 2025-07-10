/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @author Moshe Breuer
 * @version 2.0
 * @since 2021-04-27
 * A class for an object in the shape of a rectangle.
 */
public class Rectangle {
    private Point upperLeft, lowerRight;
    private double width, height;

    ///////////////// Constructors /////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor based on location and dimensions.
     *
     * @param upperLeft is the Rectangle's start point.
     * @param width     is the Rectangle's horizontal size.
     * @param height    is the Rectangle's vertical size.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.width = width;
        this.height = height;
        this.upperLeft = upperLeft;
        this.lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * Constructor based on the Rectangle's corners coordinates.
     *
     * @param upperLeft  is the closest corner to the origin.
     * @param lowerRight is the furthest corner from the origin.
     */
    public Rectangle(Point upperLeft, Point lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        this.width = lowerRight.getX() - upperLeft.getX();
        this.height = lowerRight.getY() - upperLeft.getY();
    }

    ///////////////// Getters //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter.
     *
     * @return the closest corner to the origin.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Getter.
     *
     * @return the furthest corner from the origin.
     */
    public Point getLowerRight() {
        return this.lowerRight;
    }

    /**
     * Getter.
     *
     * @return the Rectangle's horizontal size.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Getter.
     *
     * @return the Rectangle's vertical size.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * This method "converts" a rectangle to the four lines it is.
     *
     * @return an array of Line objects representing the Rectangle.
     */
    public Line[] getSides() {
        Point upperRightPoint = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point upperLeftPoint = this.upperLeft;
        Point lowerLeftPoint = new Point(this.lowerRight.getX() - this.width, this.lowerRight.getY());
        Point lowerRightPoint = this.lowerRight;

        Line[] sides = new Line[4];
        sides[0] = new Line(upperRightPoint, upperLeftPoint);
        sides[1] = new Line(upperLeftPoint, lowerLeftPoint);
        sides[2] = new Line(lowerLeftPoint, lowerRightPoint);
        sides[3] = new Line(lowerRightPoint, upperRightPoint);

        return sides;
    }

    ///////////////// Other ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Describing the relations of a given Line with our rectangle.
     *
     * @param line is the given line
     * @return a (possibly empty) List of intersection points with the line.
     */
    public List<Point> intersectionPoints(Line line) {
        Line[] rectangleSides = this.getSides();
        List<Point> intersectionPoints = new ArrayList<Point>();
        Point intersectionPoint;
        for (int i = 0; i < 4; i++) {
            intersectionPoint = rectangleSides[i].intersectionWith(line);
            if (intersectionPoint != null) {
                intersectionPoints.add(intersectionPoint);
            }
        }
        return intersectionPoints;
    }
}