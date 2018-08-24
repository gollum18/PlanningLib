package planning.agent;

import planning.geom.GeoMath;
import planning.geom.Grid;
import planning.geom.Point;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

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
     * @param sx int: The start x ordinal.
     * @param sy int: The start y ordinal.
     * @param gx int: The goal x ordinal.
     * @param gy int: The goal y ordinal.
     */
    public AStarAgent(int sx, int sy, int gx, int gy, Heuristic heuristic) {
        super(sx, sy, gx, gy);
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
        // Clear the lists
        mOpenList.clear();
        mClosedList.clear();
        // Add the start node to the open list
        mOpenList.add(new AStarNode(getStart()));
        // Stores the node on the current iteration
        AStarNode current = null;
        // Loop until we reach the goal
        while (!mOpenList.isEmpty()) {
            // Get the top node
            current = mOpenList.poll();
            mClosedList.add(current.getPosition());
            // Check if it is the goal, return the path if it is
            if (isGoalNode(current)) {
                return generatePath(current);
            }
            // Loop through the neighbors
            for (Point p : grid.generateNeighbors(current.getPosition().getX(),
                    current.getPosition().getY())) {
                // Skip neighbors that are already in the open list or have been evaluated
                if (mOpenList.contains(p) || mClosedList.contains(p)) {
                    continue;
                }
                // Calculate the f-score and add the node to the open list
                AStarNode node = new AStarNode(p, current);
                node.setGScore(current.getGScore() + 1);
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
        return null;
    }

}