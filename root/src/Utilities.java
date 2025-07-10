/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

/**
 * @author Moshe Breuer
 * @version 3.1
 * @since 2021-04-27
 * This is a class for different helpers such as technical methods or constant values.
 * It is only being used for project-wide purposes, cross-class ones.
 */
public class Utilities {
    public static final double A_LITTLE = 0.00001;
    private static final int RGB_SCALE_MAX = 255;
    public static final Color GREEN = new Color(112, 152, 89);
    public static final Color DARK_GREEN = new Color(112 / 2, 152 / 2, 89 / 2);
    public static final Color PURPLE = new Color(152, 118, 170);
    public static final Color DARK_PURPLE = new Color(90, 75, 100);
    public static final Color BRIGHT_PURPLE = new Color(182, 138, 190);

    /**
     * Generate a random color.
     *
     * @return the color.
     */
    public static Color randomColor() {
        Random random = new Random();
        int r = random.nextInt(RGB_SCALE_MAX + 1);
        int g = random.nextInt(RGB_SCALE_MAX + 1);
        int b = random.nextInt(RGB_SCALE_MAX + 1);
        return new Color(r, g, b);
    }

    /**
     * Check if two numbers are close enough.
     *
     * @param x is one number we compare to another
     * @param y is the other one
     * @return true if they are, false otherwise.
     */
    public static boolean doubleEquals(double x, double y) {
        if (isSpecialDouble(x)) {
            return x == y;
        }
        return Math.abs(x - y) <= A_LITTLE;
    }

    /**
     * A helper for "doubleEquals". it checks if a double variable doesn't have a regular numeral value.
     *
     * @param num is the number being checked
     * @return true if it not, false if it not.
     */
    private static boolean isSpecialDouble(double num) {
        return num == Double.POSITIVE_INFINITY || num == Double.NEGATIVE_INFINITY || num == Double.NaN;
    }

    /**
     * This method finds the minimum double value in a given unsorted NOT EMPTY list (using Collection).
     *
     * @param list is the list we search for the minimum value in.
     * @return the minimum value (the first value if there is no minimum)
     */
    public static double min(List<Double> list) {
        // sorting the list
        list = new ArrayList<>(list);
        Collections.sort(list);

        return list.get(0); // first element in the sorted list would be minimum
    }
}