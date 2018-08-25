package planning.agent;

import planning.geom.Grid;
import planning.geom.Point;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Defines a breadth-first search agent used for finding paths.
 */
public class BFSAgent extends Agent {

    /*
        Fields
     */

    private ArrayDeque<Node> mOpenList;
    private HashSet<Point> mClosedList;

    /*
        Constructors
     */

    /**
     * Creates an instance of a BFSAgent with the specified parameters.
     */
    public BFSAgent() {
        super();
        mOpenList = new ArrayDeque<>();
        mClosedList = new HashSet<>();
    }

    /*
        Methods
     */

    /**
     * Traverses a grid and returns a path if one is found. May return null if no path is found.
     * @param grid The grid to path find across.
     * @return LinkedList: A list containing the points in the path.
     */
    public LinkedList<Point> traverse(Grid grid) {
        mOpenList.clear();
        mClosedList.clear();
        mOpenList.add(new Node(grid.getStart()));
        Node mCurrent = null;
        while (!mOpenList.isEmpty()) {
            mCurrent = mOpenList.poll();
            if (grid.isGoalNode(mCurrent)) {
                return generatePath(mCurrent);
            }
            for (Point p : grid.generateNeighbors(mCurrent.getPosition().getX(), mCurrent.getPosition().getY())) {
                Node node = new Node(p, mCurrent);
                if (mOpenList.contains(node) || mClosedList.contains(p)) {
                    continue;
                }
                mOpenList.add(node);
            }
        }
        return null;
    }

}
