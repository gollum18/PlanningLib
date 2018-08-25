package planning.agent;

import planning.geom.Point;

/**
 * Defines a generic node used by search agents.
 */
public class Node {

    /*
        Fields
     */

    private Point mPosition;    // The position of this node.
    private Node mParent;       // The parent of this node.

    /*
        Constructors
     */

    /**
     * Creates an instance of a Node with the specified parameters.
     * @param position Point: The position of this node.
     */
    public Node(Point position) {
        this(position, null);
    }

    /**
     * Creates an instance of a Node with the specified parameters.
     * @param position Point: The position of this node.
     * @param parent Node: The parent of this node.
     */
    public Node(Point position, Node parent) {
        mPosition = position;
        mParent = parent;
    }

    /*
        Accessors/Mutators
     */

    /**
     * Returns the position of this node.
     * @return Point: The position of the node.
     */
    public Point getPosition() {
        return mPosition;
    }

    /**
     * Gets the parent of this node.
     * @return Node: The parent of this node, may be null.
     */
    public Node getParent() {
        return mParent;
    }

    /**
     * Updates the parent of this node.
     * @param parent Node: A node object, may be null.
     */
    public void setParent(Node parent) {
        mParent = parent;
    }

}
