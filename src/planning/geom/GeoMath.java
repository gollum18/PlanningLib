package planning.geom;

/**
 * Defines common mathematical functions used by the search agents.
 */
public class GeoMath {

    /**
     * Determines the euclidean distance between two coordinates.
     * @param x1 int: The x ordinal of the first point.
     * @param y1 int: The y ordinal of the first point.
     * @param x2 int: The x ordinal of the second point.
     * @param y2 int: The y ordinal of the second point.
     * @return double: The euclidean distance between two coordinates.
     */
    public static double euclideanDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Determines the euclidean distance between two points.
     * @param p1 Point: The origin point.
     * @param p2 Point: The destination point.
     * @return double: The euclidean distance between the two points.
     */
    public static double euclideanDistance(Point p1, Point p2) {
        return euclideanDistance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Determines the manhattan distance between two coordinates.
     * @param x1 int: The x ordinal of the first point.
     * @param y1 int: The y ordinal of the first point.
     * @param x2 int: The x ordinal of the second point.
     * @param y2 int: The y ordinal of the second point.
     * @return double: The manhattan distance between two coordinates.
     */
    public static double manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    /**
     * Determines the manhattan distance between two points.
     * @param p1 Point: The origin point.
     * @param p2 Point: The destination point.
     * @return double: The manhattan distance between two points.
     */
    public static double manhattanDistance(Point p1, Point p2) {
        return manhattanDistance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Determines the octile distance between the two coordinates.
     * @param x1 int: The x ordinal of the first point.
     * @param y1 int: The y ordinal of the first point.
     * @param x2 int: The x ordinal of the second point.
     * @param y2 int: The y ordinal of the second point.
     * @return double: The octile distance between two coordinates.
     */
    public static double octileDistance(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) +
                (Math.sqrt(2) - 2) * Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    /**
     * Determines the octile distance between two points.
     * @param p1 Point: The origin point.
     * @param p2 Point: The destination point.
     * @return double: The octile distance between two points.
     */
    public static double octileDistance(Point p1, Point p2) {
        return octileDistance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

}
