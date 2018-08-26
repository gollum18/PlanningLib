package planning.realtime;

import planning.agent.AStarNode;
import planning.agent.Heuristic;
import planning.geom.GeoMath;
import planning.geom.Grid;
import planning.geom.Point;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class RealTimeAStarAgent extends RealTimeAgent {

    /*
        Fields
     */

    private PriorityQueue<AStarNode> mOpenList;
    private HashSet<Point> mClosedList;
    private Heuristic eHeuristic;

    /*
        Constructors
     */

    /**
     * Creates an instance of a Real-Time A* agent with the specified parameters.
     * @param sx int: The x ordinal of the starting point.
     * @param sy int: The y ordinal of the starting point.
     * @param gx int: The x ordinal of the goal point.
     * @param gy int: The y ordinal of the goal point.
     * @param planningTime long: The planning time used for the agent.
     */
    public RealTimeAStarAgent(int sx, int sy, int gx, int gy, Heuristic heuristic, long planningTime) {
        super(sx, sy, gx, gy, planningTime);
        mOpenList = new PriorityQueue<>();
        mClosedList = new HashSet<>();
        eHeuristic = heuristic;
    }

    /**
     * Creates an instance of a Real-Time A* agent with the specified parameters.
     * @param start Point: The starting location.
     * @param goal Point: The goal location.
     * @param planningTime long: The planning time used for the agent.
     */
    public RealTimeAStarAgent(Point start, Point goal, Heuristic heuristic, long planningTime) {
        this(start.getX(), start.getY(), goal.getX(), goal.getY(), heuristic, planningTime);
    }

    /*
        Accessors/Mutators
     */

    /**
     * Gets the heuristic in use by the agent.
     * @return Heuristic: The heuristic in use by the agent.
     */
    public Heuristic getHeuristic () {
        return eHeuristic;
    }

    /**
     * Sets the heuristic in use by the agent.
     * @param heuristic Heuristic: The heuristic the agent should use.
     */
    public void setHeuristic(Heuristic heuristic) {
        eHeuristic = heuristic;
    }

    /*
        Methods
     */

    /**
     * Generates a path segment in real-time. The agents starting node is updated each time this method executes.
     * @param grid The grid to path find across.
     * @return LinkeList: A path segment.
     */
    public LinkedList<Point> traverse(Grid grid) {
        // Reset the path found flag
        if (isPathFound()) {
            mOpenList.clear();
            mClosedList.clear();
            setPathFound(false);
        }
        // Begin planning
        mOpenList.add(new AStarNode(getStart()));
        AStarNode current = null;
        // This initializes the timer
        long finishTime = System.currentTimeMillis() + getPlanningTime();
        while(System.currentTimeMillis() <= finishTime){
            current = mOpenList.poll();
            if (isGoalNode(current)) {
                return generatePath(current);
            }
            for (Point p : grid.generateNeighbors(current.getPosition().getX(), current.getPosition().getY())) {
                AStarNode node = new AStarNode(p);
                if (mOpenList.contains(node) || mClosedList.contains(p)) {
                    continue;
                }
                switch (eHeuristic) {
                    case EUCLIDEAN:
                        node.setFScore(node.getGScore() + GeoMath.euclideanDistance(current.getPosition().getX(),
                                current.getPosition().getY(), getGoal().getX(), getGoal().getY()));
                        break;
                    case MANHATTAN:
                        node.setFScore(node.getGScore() + GeoMath.manhattanDistance(current.getPosition().getX(),
                                current.getPosition().getY(), getGoal().getX(), getGoal().getY()));
                        break;
                    case OCTILE:
                        node.setFScore(node.getGScore() + GeoMath.octileDistance(current.getPosition().getX(),
                                current.getPosition().getY(), getGoal().getX(), getGoal().getY()));
                        break;
                    default:
                        node.setFScore(node.getGScore() + GeoMath.octileDistance(current.getPosition().getX(),
                                current.getPosition().getY(), getGoal().getX(), getGoal().getY()));
                        break;
                }
                mOpenList.add(node);
            }
        }
        setStart(current.getPosition());
        return generatePath(current);
    }

}
