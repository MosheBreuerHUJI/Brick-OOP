/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 2.0
 * @since 2021-04-27
 * An object we use to learn about collisions.
 * It holds the collision's location on the frame and a reference to the object that is hit.
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * A constructor.
     *
     * @param collisionPoint  is the collision's location on the frame.
     * @param collisionObject is a reference to the object that is hit.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * A getter.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * A getter.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}