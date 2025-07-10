/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import java.util.LinkedList;

/**
 * @author Moshe Breuer
 * @version 2.0
 * @since 2021-04-27
 * GameEnvironment is an object holds a collection of all collidable objects that exist in a certain game.
 * We use it to manage all of them in a concentrated way.
 */
public class GameEnvironment {

    private LinkedList<Collidable> collection;

    /**
     * A constructor.
     */
    public GameEnvironment() {
        this.collection = new LinkedList<Collidable>();
    }

    /**
     * Adding the given collidable to the environment.
     *
     * @param c is the given collidable.
     */
    public void addCollidable(Collidable c) {
        this.collection.add(c);
    }

    /**
     * Removing the given collidable from the environment.
     *
     * @param c is the given collidable.
     */
    public void remove(Collidable c) {
        this.collection.remove(c);
    }

    /**
     * A getter.
     *
     * @return the paddle object from the collidable collection of the game environment.
     */
    public Rectangle getPaddle() {
        for (Collidable c : this.collection) {
            // detecting it using the paddle's unique dimensions
            if (c.getCollisionRectangle().getWidth() == Game.PADDLE_WIDTH) {
                return c.getCollisionRectangle();
            }
        }
        return null; // this line won't ever be reached
    }

    /**
     * "getClosestCollision": Assuming an object is moving from line.start() to line.end().
     * If this object will not collide with any of the collidables in this collection, returning null.
     * Otherwise, returning the information about the closest collision that is going to occur.
     *
     * @param trajectory is the line the object moves on.
     * @return details of the closest collision (if any).
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (this.collection == null || this.collection.size() == 0) { // if there are no objects to collide with
            return null;
        }

        // finding ALL intersection points
        LinkedList<Point> collisionPoints = new LinkedList<>();
        for (Collidable object : this.collection) {
            collisionPoints.addAll(object.getCollisionRectangle().intersectionPoints(trajectory));
        }
        if (collisionPoints.isEmpty()) {
            return null;
        }
        // finding the closest of them:
        Point collisionPoint = trajectory.start().findClosest(collisionPoints);

        // getting the object being hit:
        Collidable collisionObject = null;
        for (Collidable object : this.collection) {
            if (collisionPoint.isOn(object)) {
                collisionObject = object;
                break;
            }
        }

        return new CollisionInfo(collisionPoint, collisionObject);
    }
}