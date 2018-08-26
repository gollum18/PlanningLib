package planning.realtime;

import planning.agent.Agent;
import planning.geom.Grid;
import planning.geom.Point;

import java.util.LinkedList;

/**
 * Represents a base abstract class real-time agents may extend from.
 */
public abstract class RealTimeAgent extends Agent {

    /*
        Constants
     */

    public static final long DEFAULT_PLANNING_TIME = 1000;
    public static final long MIN_PLANNING_TIME = 500;

    /*
        Fields
     */

    private long lPlanningTime;
    private boolean bPathFound;

    /*
        Constructors
     */

    /**
     * Base constructor for all real-time agents.
     * @param sx int: The x ordinal of the starting point.
     * @param sy int: The y ordinal of the starting point.
     * @param gx int: The x ordinal of the goal point.
     * @param gy int: The y ordinal of the goal point.
     * @param planningTime long: The planning time used for the agent.
     */
    public RealTimeAgent(int sx, int sy, int gx, int gy, long planningTime) {
        super(sx, sy, gx, gy);
        setPlanningTime(planningTime);
    }

    /**
     * Base constructor for all real-time agents.
     * @param start Point: The starting location.
     * @param goal Point: The goal location.
     * @param planningTime long: The planning time used for the agent.
     */
    public RealTimeAgent(Point start, Point goal, long planningTime) {
        this(start.getX(), start.getY(), goal.getX(), goal.getY(), planningTime);
    }

    /*
        Accessors/Mutators
     */

    public boolean isPathFound() {
        return bPathFound;
    }

    public void setPathFound(boolean pathFound) {
        bPathFound = pathFound;
    }

    /**
     * Gets the planning time the agent has for each planning iteration.
     * @return double: The time for each planning iteration.
     */
    public long getPlanningTime() {
        return lPlanningTime;
    }

    /**
     * Sets the planning time the agent has for each planning iteration.
     * @param planningTime long: The time for each planning iteration.
     */
    public void setPlanningTime(long planningTime) {
        if (planningTime < MIN_PLANNING_TIME) {
            lPlanningTime = DEFAULT_PLANNING_TIME;
        } else { lPlanningTime = planningTime; }
    }

    /*
        Methods
     */

    /**
     * Generates a path segment for a real-time planning agent. It may or may not contain the goal point.
     * @param grid The grid to path find across.
     * @return LinkedList: A path segment for a real-time planning agent.
     */
    public abstract LinkedList<Point> traverse(Grid grid);

}
