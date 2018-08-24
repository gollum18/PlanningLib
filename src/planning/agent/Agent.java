package planning.agent;

import planning.enviro.Grid;
import planning.enviro.Point;

import java.util.LinkedList;

public abstract class Agent {

    /*
        Fields
     */

    private Point mStart;
    private Point mGoal;

    /*
        Constructors
     */

    /**
     * Creates an instance of an Agent with the given specifications.
     * @param sx The starting x ordinal.
     * @param sy The starting y ordinal.
     * @param gx The goal x ordinal.
     * @param gy The goal y ordinal.
     */
    public Agent(int sx, int sy, int gx, int gy) {
        mStart = new Point(sx, sy);
        mGoal = new Point(gx, gy);
    }

    /*
        Accessors/Mutators
     */

    /**
     * Returns the starting position.
     * @return Point: The starting position.
     */
    public Point getStart() {
        return mStart;
    }

    /**
     * Returns the goal position.
     * @return Point: The goal position.
     */
    public Point getGoal() {
        return mGoal;
    }

    /*
        Methods
     */

    /**
     * Generates a list containing the path.
     * @param grid The grid to path find across.
     * @return LinkedList: A list containing the path.
     */
    public abstract LinkedList<Point> traverse(Grid grid);

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
     * Determines the manhattan distance between the two points.
     * @param x1 int: The x ordinal of the first point.
     * @param y1 int: The y ordinal of the first point.
     * @param x2 int: The x ordinal of the second point.
     * @param y2 int: The y ordinal of the second point.
     * @return double: The manhattan distance between the two points.
     */
    public static double manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

}
