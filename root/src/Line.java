/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 3.0
 * @since 2021-04-10
 * The class "Line" implements a line-segment object,
 * based on two points on the axis-system and the properties of the line that derive from them.
 */
public class Line {

    private Point start;
    private Point end;
    private double slope; // the coefficient of X in the line equation that the segment is on
    private double offset; // the free number in the line equation that the segment is on

    /**
     * A constructor.
     *
     * @param start one of the edges of the line segment (represented as a "Point" object)
     * @param end   the other one
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.slope = this.calculateSlope();
        this.offset = this.calculateOffset();
    }

    /**
     * A constructor.
     * It uses the other constructor
     *
     * @param x1 the x value of line segment start point
     * @param y1 the y value of line segment start point
     * @param x2 the x value of line segment end point
     * @param y2 the y value of line segment end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * A method for finding the coefficient of X in the line equation that the segment is on.
     *
     * @return the slope
     */
    private double calculateSlope() {
        double deltaX = this.start.getX() - this.end.getX();
        double deltaY = this.start.getY() - this.end.getY();
        double calculatedSlope = deltaY / deltaX;

        if (calculatedSlope == Double.NEGATIVE_INFINITY) { // error fixation
            calculatedSlope = Double.POSITIVE_INFINITY;
        }

        return calculatedSlope;
    }

    /**
     * A method for finding the free number in the line equation that the segment is on.
     *
     * @return the offset
     */
    private double calculateOffset() {
        if (this.isVertical()) {
            return this.start.getX(); // A chosen way to represent the line equation of a vertical segment (x=c).
        }

        double x = this.start.getX(), y = this.start.getY();
        return y - this.slope * x; // according to arithmetics and the line equation
    }

    /**
     * A getter for the line segment start point.
     *
     * @return a Point object of it
     */
    public Point start() {
        return this.start;
    }

    /**
     * A getter for the line segment end point.
     *
     * @return a Point object of it
     */
    public Point end() {
        return this.end;
    }

    /**
     * Line segment middle point getter.
     *
     * @return the middle point values as a Point object
     */
    public Point middle() {
        double x = (this.start.getX() + this.end.getX()) / 2;
        double y = (this.start.getY() + this.end.getY()) / 2;
        return new Point(x, y);
    }

    /**
     * The method determines whether the line is parallel to the y-axis.
     *
     * @return true if it is, false otherwise
     */
    public boolean isVertical() {
        return this.slope == Double.POSITIVE_INFINITY;
    }

    /**
     * The length of the line segment.
     *
     * @return the length
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Comparing a line with another.
     *
     * @param other is the line being compared to ours
     * @return true if the lines are identical, false otherwise
     */
    public boolean equals(Line other) {
        this.sortLinesPoints(other); // in order to compare the lines in the same order
        return (this.start().equals(other.start()) && (this.end().equals(other.end())));
    }

    /**
     * A utility that swaps the points of the lines with each other.
     */
    private void swapPoints() {
        Point temp = this.start;
        this.start = this.end;
        this.end = temp;
    }

    /**
     * A utility that verifies that the start point of line segment "comes before" the end point on the axes-system.
     */
    public void sortPoints() {
        double startValue = this.start.getX();
        double endValue = this.end.getX();

        if (this.isVertical()) { // the x values are identical, so we shall sort according to the y values
            startValue = this.start.getY();
            endValue = this.end.getY();
        }

        if (startValue > endValue) {
            this.swapPoints();
        }
    }

    /**
     * A utility to implement the previous one on two line segments, helps comparing them later.
     *
     * @param other the second line being sorted
     */
    private void sortLinesPoints(Line other) {
        this.sortPoints();
        other.sortPoints();
    }

    /**
     * This method can assume that the line segments being sent to it have different slopes.
     *
     * @param other the line crosses ours
     * @return the common point of the line segments, null if there is not one.
     */
    private Point crossingLinesIntersection(Line other) {
        Point intersection = this.intersectionPoint(other); // the point where the lines cross
        if (intersection.isInBoth(this, other)) { // determining if the point is in the segments and not only the lines.
            return intersection;
        }
        return null; // if the point is outside one (or both) of the segments
    }

    /**
     * This method finds the common point for two infinite lines (not segments). It can assume they cross somewhere.
     *
     * @param other the line being compared to. Both the lines are being represented by segments that are on them.
     * @return the cross point, null if there is none.
     */
    private Point intersectionPoint(Line other) {
        // a different calculation for vertical line segments
        if (this.isVertical()) {
            return other.intersectionPointWithPerpendicularToXAxis(this);
        }
        if (other.isVertical()) {
            return this.intersectionPointWithPerpendicularToXAxis(other);
        }

        // the trivial case. calculated according to arithmetics of the line equation.
        double x, y;
        x = ((other.offset - this.offset) / (this.slope - other.slope));
        y = this.slope * x + this.offset;
        return new Point(x, y);
    }

    /**
     * This method receives this line and another only when this is not vertical and the other is.
     * It is a utility that extends the method "intersectionPoint".
     *
     * @param perpendicular the vertical line.
     * @return the cross point of the infinite lines.
     */
    private Point intersectionPointWithPerpendicularToXAxis(Line perpendicular) {
        double x = perpendicular.start.getX(); // any point on the line, the equation is from the form: x = c
        double y = this.slope * x + this.offset;
        return new Point(x, y);
    }

    /**
     * This method receives this line segment and another such that both are on the same infinite line.
     * (Meaning the slopes and the offsets equal).
     *
     * @param other the line that continues ours.
     * @return the point the lines share, if one of them starts right where the other ends. null otherwise.
     */
    private Point singleSharedPoint(Line other) {

        this.sortLinesPoints(other);

        Point start1 = this.start, end1 = this.end, start2 = other.start, end2 = other.end;
        if (start2.equals(end1)) {
            return start2;
        }
        if (start1.equals(end2)) {
            return start1;
        }
        return null;
    }

    /**
     * A method to find intersection of two segments, using all helper methods above.
     *
     * @param other is the line we check its crossing with ours.
     * @return the intersection point if the line segments intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (!Utilities.doubleEquals(this.slope, other.slope)) { // the infinite lines cross
            return this.crossingLinesIntersection(other); // the method that handles this case
        }
        // Now the infinite lines are parallel or congruent. Determine which case it is:
        if (!Utilities.doubleEquals(this.offset, other.offset)) {
            return null; // parallel lines never meet.
        }

        return this.singleSharedPoint(other); // using the method designed for segments that share a line equation
    }

    /**
     * A boolean method that determines whether this line intersect with another.
     *
     * @param other is the line we check the cross with ours.
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return this.intersectionWith(other) != null;
    }

    /**
     * Finding closest intersection (point) to the line start, given a rectangle the line might cross.
     *
     * @param rect is the given rectangle.
     * @return the point described above.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        return this.start.findClosest(rect.intersectionPoints(this));
    }

    @Override
    public String toString() {
        return "Line: {" + "start = " + start + ", end = " + end + ", slope = " + slope + ", offset = " + offset + "}";
    }
}