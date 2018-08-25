package planning.agent;

import planning.geom.GeoMath;
import planning.geom.Grid;
import planning.geom.Point;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 */
public class RRTAgent extends Agent {

    /*
        Constants
     */
    public static final double DEFAULT_EPSILON = 7.0;   // The default max distance between nodes
    public static final int DEFAULT_MAX_NODES = 5000;   // The default max amount of nodes to generate
    public static final double MIN_EPSILON = 1.0;       // The min distance between nodes
    public static final int MIN_NODES = 100;            // The min amount of nodes to generate

    /*
        Fields
     */

    private ArrayList<Node> mNodes; // The nodes that are part of the RRT
    private double dEpsilon;        // The maximum distance allowed between nodes
    private int iMaxNodes;          // The maximum amount of nodes RRT may generate in a single traversal

    /*
        Constructors
     */

    /**
     * Creates an instance of an RRTAgent with the specified parameters..
     * @param epsilon double:
     * @param nodes int: The maximum amount of nodes to generate
     */
    public RRTAgent(double epsilon, int nodes) {
        super();
        if (epsilon < MIN_EPSILON) {
            epsilon = DEFAULT_EPSILON;
        }
        if (nodes < MIN_NODES) {
            nodes = DEFAULT_MAX_NODES;
        }
        mNodes = new ArrayList<>(nodes+1);
        dEpsilon = epsilon;
        iMaxNodes = nodes;
    }

    /*
        Accessors/Mutators
     */

    /**
     * Gets the maximum distance allowed between nodes.
     * @return
     */
    public double getMaxDistanceBetweenNodes() {
        return dEpsilon;
    }

    /**
     * Updates the maximum distance between nodes.
     * @param epsilon The maximum distance allowed between nodes. Must be >= 1.
     */
    public void setMaxDistanceBetweenNodes(Double epsilon) {
        if (epsilon >= MIN_EPSILON) {
            dEpsilon = epsilon;
        }
    }

    /**
     * Gets the maximum amount of nodes RRT is allowed to generate.
     * @return
     */
    public int getMaxNodes() {
        return iMaxNodes;
    }

    /**
     * Updates the maximum amount of nodes RRT is allowed to generate.
     * @param nodes The max amount of nodes for RRT. Must be >= 100.
     */
    public void setMaxNodes(int nodes) {
        if (nodes >= MIN_NODES) {
            iMaxNodes = nodes;
        }
    }

    /**
     * Returns a point along the line from p1 to p2.
     * @param p1 The first endpoint of the line.
     * @param p2 The second enpoint of the line.
     * @return Point: p2 if the distance between p1 and p2 is less than the max distance
     * between points. Otherwise, return a point on the line between p1 and p2.
     */
    private Point stepFromTo(Point p1, Point p2) {
        if (GeoMath.euclideanDistance(p1, p2) < dEpsilon) {
            return p2;
        } else {
            double theta = Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX());
            return new Point((int)Math.floor(p1.getX() + dEpsilon * Math.cos(theta)),
                    (int)Math.floor(p1.getY() + dEpsilon * Math.sin(theta)));
        }
    }

    /**
     * Generates a path along the grid using the RRT pathfinding algorithm.
     * @param grid The grid to path find across.
     * @return LinkedList: A path if one is found, null otherwise.
     */
    public LinkedList<Point> traverse(Grid grid) {
        mNodes.clear();
        mNodes.add(new Node(grid.getStart()));
        for (int i = 0; i < iMaxNodes; i++) {
            Point rand = grid.random();
            // Have to deal with the possibility of wasting a node on an obstacle
            if (!grid.isValidCoordinates(rand.getX(), rand.getY())) {
                i -= 1;
                continue;
            }
            // Find the nearest neighbor
            Node nn = mNodes.get(0);
            for (Node n : mNodes) {
                if (GeoMath.euclideanDistance(n.getPosition(), rand) <
                        GeoMath.euclideanDistance(nn.getPosition(), rand)) {
                    nn = n;
                }
            }
            // Generate a point along the nearest neighbor and the random point
            rand = stepFromTo(nn.getPosition(), rand);
            // Check visibility and goal state, add it to the list of nodes if necessary
            if (grid.lineOfSight(nn.getPosition(), rand)) {
                Node newnode = new Node(rand, nn);
                if (grid.isGoalNode(newnode)) {
                    return generatePath(newnode);
                }
                mNodes.add(newnode);
            }
            // Deals with the possibility that obstacles block the path from nearest neighbor to the new node
            else {
                i -= 1;
            }
        }
        return null;
    }

}
