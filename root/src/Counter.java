/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 1.0
 * @since 2021-05-30
 * Counter is a simple class that is used for counting things.
 */
public class Counter {

    private int counter;

    /**
     * A constructor. Starts at zero, obviously.
     */
    public Counter() {
        this.counter = 0;
    }

    /**
     * An alternative constructor.
     *
     * @param number is the initial value of our Counter.
     */
    public Counter(int number) {
        this.counter = number;
    }

    /**
     * Add a number to current count.
     *
     * @param number is the number to add.
     */
    void increase(int number) {
        counter += number;
    }

    /**
     * Subtract number from current count.
     *
     * @param number is the number to subtract.
     */
    void decrease(int number) {
        this.increase((-1) * number);
    }

    /**
     * A getter.
     *
     * @return the current count.
     */
    int getValue() {
        return this.counter;
    }
}
