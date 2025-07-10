/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 3.0
 * @since 2021-04-27
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {

    private double dx, dy;

    ///////////////// Constructors / Setters ///////////////////////////////////////////////////////////////////////////

    /**
     * A constructor.
     *
     * @param dx is the horizontal change of the ball's position.
     * @param dy is the vertical change of the ball's position.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * A setter that is calculating Δx and Δy according to the direction and size of the step.
     *
     * @param angle is for the ball's direction (in degrees).
     * @param speed is a scalar gives the size of steps wanted.
     * @return is the new velocity the arguments make.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        double dx = speed * Math.sin(angle);
        double dy = speed * Math.cos(angle);
        dy *= (-1); // since the positive direction of the y-axis on the screen is downwards.
        return new Velocity(dx, dy);
    }

    ///////////////// Velocity changers ////////////////////////////////////////////////////////////////////////////////

    /**
     * A method that causes the direction to be upside down horizontally.
     */
    public void negateDX() {
        this.dx *= -1;
    }

    /**
     * A method that causes the direction to be upside down vertically.
     */
    public void negateDY() {
        this.dy *= -1;
    }

    ///////////////// Getters //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * A getter.
     *
     * @return is the size of the horizontal change the velocity includes.
     */
    public double getDx() {
        return dx;
    }

    /**
     * A getter.
     *
     * @return is the size of the horizontal change the vertical includes.
     */
    public double getDy() {
        return dy;
    }

    /**
     * A getter that calculates the linear speed outcomes from the velocity.
     * It calculates it based on the Pythagoras formula.
     *
     * @return that.
     */
    public double getSpeed() {
        return Math.sqrt(Math.pow(this.getDx(), 2) + Math.pow(this.getDy(), 2));
    }

    @Override
    public String toString() {
        return "Velocity: " + "Δx = " + dx + ", Δy = " + dy + ".";
    }

    ///////////////// Using the velocity ///////////////////////////////////////////////////////////////////////////////

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     * This is what actually "moves" an object.
     *
     * @param p is is the current location of the object
     * @return is the location it will be after its step
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
}