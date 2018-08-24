package planning.geom;

public class GeoMath {

    /**
     * Determines the euclidean distance between two points.
     * @param x1 int: The x ordinal of the first point.
     * @param y1 int: The y ordinal of the first point.
     * @param x2 int: The x ordinal of the second point.
     * @param y2 int: The y ordinal of the second point.
     * @return double: The euclidean distance between two points.
     */
    public static double euclideanDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Determines the manhattan distance between two points.
     * @param x1 int: The x ordinal of the first point.
     * @param y1 int: The y ordinal of the first point.
     * @param x2 int: The x ordinal of the second point.
     * @param y2 int: The y ordinal of the second point.
     * @return double: The manhattan distance between two points.
     */
    public static double manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    /**
     * Determines the octile distance between the two points.
     * @param x1 int: The x ordinal of the first point.
     * @param y1 int: The y ordinal of the first point.
     * @param x2 int: The x ordinal of the second point.
     * @param y2 int: The y ordinal of the second point.
     * @return double: The octile distance between two points.
     */
    public static double octileDistance(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) +
                (Math.sqrt(2) - 2) * Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

}
