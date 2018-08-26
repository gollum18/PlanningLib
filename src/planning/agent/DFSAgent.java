package planning.agent;

import planning.geom.Grid;
import planning.geom.Point;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Defines a depth-first search agent used for finding paths.
 */
public class DFSAgent extends Agent {

    /*
        Fields
     */

    private Stack<Node> mOpenList;
    private HashSet<Point> mClosedList;

    /*
        Constructors
     */

    /**
     * Creates an instance of a DFSAgent with the specified parameters.
     */
    public DFSAgent(int sx, int sy, int gx, int gy) {
        super(sx, sy, gx, gy);
        mOpenList = new Stack<>();
        mClosedList = new HashSet<>();
    }

    public DFSAgent(Point start, Point goal) {
        this(start.getX(), start.getY(), goal.getX(), goal.getY());
    }

    /*
        Methods
     */

    /**
     * Generates a list containing a path through a grid if one is found. May return null if there is no path.
     * @param grid The grid to path find across.
     * @return LinkedList: A path through the grid if one is found. Otherwise, return null.
     */
    public LinkedList<Point> traverse(Grid grid) {
        mOpenList.clear();
        mClosedList.clear();
        mOpenList.add(new Node(getStart()));
        Node mCurrent = null;
        while (!mOpenList.isEmpty()) {
            mCurrent = mOpenList.pop();
            if (isGoalNode(mCurrent)) {
                return generatePath(mCurrent);
            }
            for (Point p : grid.generateNeighbors(mCurrent.getPosition().getX(), mCurrent.getPosition().getY())) {
                Node node = new Node(p, mCurrent);
                if (mOpenList.contains(node) || mClosedList.contains(p)) {
                    continue;
                }
                mOpenList.push(node);
            }
        }
        return null;
    }

}
