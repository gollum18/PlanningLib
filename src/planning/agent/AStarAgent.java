package planning.agent;

import planning.geom.GeoMath;
import planning.geom.Grid;
import planning.geom.Point;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Defines an A* search agent for generating paths using various heuristics.
 */
public class AStarAgent extends Agent {

    /*
        Fields
     */

    private PriorityQueue<AStarNode> mOpenList; // The open list used for the agent
    private HashSet<Point> mClosedList;         // The closed list used for the agent
    private Heuristic eHeuristic;               // The heuristic used for the agent

    /*
        Constructors
     */

    /**
     * Creates an A* agent with the specified parameters.
     * @param heuristic: The heuristic to use for the A* agent.
     */
    public AStarAgent(Heuristic heuristic) {
        super();
        mOpenList = new PriorityQueue<>();
        mClosedList = new HashSet<>();
        eHeuristic = heuristic;
    }

    /*
        Accessors/Mutators
     */

    /**
     * Returns the heuristic currently in use by the agent.
     * @return Heuristic: The heuristic in use by the agent.
     */
    public Heuristic getHeuristic() {
        return eHeuristic;
    }

    /**
     * Changes the heuristic used by the agent.
     * @param heuristic: The new heuristic to use.
     */
    public void setHeuristic(Heuristic heuristic) {
        eHeuristic = heuristic;
    }

    /*
        Methods
     */

    /**
     * Traverses the given grid for a path.
     * @param grid The grid to path find across.
     * @return LinkedList: A list containing the path if it found. May return null if no path is found.
     */
    public LinkedList<Point> traverse(Grid grid) {
        mOpenList.clear();
        mClosedList.clear();
        mOpenList.add(new AStarNode(grid.getStart()));
        AStarNode current = null;
        while (!mOpenList.isEmpty()) {
            current = mOpenList.poll();
            mClosedList.add(current.getPosition());
            if (grid.isGoalNode(current)) {
                return generatePath(current);
            }
            for (Point p : grid.generateNeighbors(current.getPosition().getX(),
                    current.getPosition().getY())) {
                AStarNode node = new AStarNode(p, current);
                if (mOpenList.contains(node) || mClosedList.contains(p)) {
                    continue;
                }
                node.setGScore(current.getGScore() + 1);
                switch (eHeuristic) {
                    case EUCLIDEAN:
                        node.setFScore(node.getGScore() + GeoMath.euclideanDistance(current.getPosition().getX(),
                            current.getPosition().getY(), grid.getGoal().getX(), grid.getGoal().getY()));
                        break;
                    case MANHATTAN:
                        node.setFScore(node.getGScore() + GeoMath.manhattanDistance(current.getPosition().getX(),
                                current.getPosition().getY(), grid.getGoal().getX(), grid.getGoal().getY()));
                        break;
                    case OCTILE:
                        node.setFScore(node.getGScore() + GeoMath.octileDistance(current.getPosition().getX(),
                                current.getPosition().getY(), grid.getGoal().getX(), grid.getGoal().getY()));
                        break;
                    default:
                        node.setFScore(node.getGScore() + GeoMath.octileDistance(current.getPosition().getX(),
                                current.getPosition().getY(), grid.getGoal().getX(), grid.getGoal().getY()));
                        break;
                }
                mOpenList.add(node);
            }
        }
        return null;
    }

}
