package planning.geom;

/**
 * Represents a point in cartesian space.
 */
public class Point {

    /*
        Fields
     */

    private int iX;     // The x ordinal for the point.
    private int iY;     // The y ordinal for the point.

    /*
        Constructors
     */

    /**
     * Creates an instance of a Point object.
     * @param x int: The x ordinal of the point.
     * @param y int: The y ordinal of the point.
     */
    public Point(int x, int y) {
        iX = x;
        iY = y;
    }

    /*
        Accessors/Mutators
     */

    /**
     * Gets the x ordinal of the point.
     * @return int: The x ordinal of the point.
     */
    public int getX() {
        return iX;
    }

    /**
     * Gets the y ordinal of the point.
     * @return int: The y ordinal of the point.
     */
    public int getY() {
        return iY;
    }

    /*
        Methods
     */

    /**
     * Determines if an object is equal to this point.
     * @param object The object to check.
     * @return boolean: Whether the object is equal to the point.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof Point)) {
            return false;
        }
        Point p = (Point)object;
        return iX == p.getX() && iY == p.getY();
    }

    /**
     * Generates a hascode for the point.
     * @return int: A hashcode for the point.
     */
    @Override
    public int hashCode() {
        int result = iX;
        result = 31 * result + iY;
        return result;
    }

    /**
     * Generates a string representation for thr point.
     * @return string: The string representation of the point.
     */
    @Override
    public String toString() {
        return String.format("Coordinates: (%d, %d)", iX, iY);
    }

}
