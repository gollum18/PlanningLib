package planning.agent;

import planning.geom.Point;

public class AStarNode extends Node implements Comparable<AStarNode> {

    /*
        Fields
     */

    private double dFScore; // The G-Score (path cost so far) and H-Score (remaining cost to goal)
    private double dGScore; // The remaining cost to the goal

    /*
        Constructors
     */

    /**
     * Creates an instance of a AStarNode with the indicated parameters.
     * @param position Point: The position of this node.
     */
    public AStarNode(Point position) {
        this(position, null);
    }

    /**
     * Creates an instance of a AStarNode with the indicated parameters.
     * @param position Point: The position of this node.
     * @param parent AStarNode: The parent of this node, may be null.
     */
    public AStarNode(Point position, AStarNode parent) {
        super(position, parent);
        dFScore = 0;
        dGScore = 0;
    }

    /*
        Accessors/Mutators
     */

    /**
     * Returns the f-score for this node.
     * @return double: The f-score for this node.
     */
    public double getFScore() {
        return dFScore;
    }

    /**
     * Updates the f-score for this node.
     * @param score double: The new f-score for the node.
     */
    public void setFScore(double score) {
        dFScore = score;
    }

    /**
     * Returns the g-score for this node.
     * @return double: The g-score for this node.
     */
    public double getGScore() {
        return dGScore;
    }

    /**
     * Updates the g-score for this node.
     * @param score double: The new g-score for the node.
     */
    public void setGScore(double score) {
        dGScore = score;
    }

    /*
        Methods
     */

    /**
     * Compares this nodes f-score to another nodes f-score.
     * @param node AStarNode: The node to compare against.
     * @return
     */
    @Override
    public int compareTo(AStarNode node) {
        if (dFScore < node.getFScore()) {
            return -1;
        } else if (dFScore > node.getFScore()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Determines if an object is equal to this AStarNode.
     * @param object Object: The object to compare against. Cannot be null. Must be a AStarNode object.
     * @return boolean: Whether the object is equal to this AStarNode.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof AStarNode)) {
            return false;
        }
        AStarNode n = (AStarNode)object;
        return getPosition().getX() == n.getPosition().getX() && getPosition().getY() == n.getPosition().getY();
    }

    /**
     * Calculates a hashCode for this AStarNode.
     * @return int: A hashCode representing this AStarNode.
     */
    @Override
    public int hashCode() {
        int result = getPosition().getX();
        result = 31 * result + getPosition().getY();
        return result;
    }

}
