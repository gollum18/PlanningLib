package planning.geom;

import planning.agent.Node;

import java.util.LinkedList;

public class Grid {

    /*
    Fields
     */

    private State[][] mGrid;            // Stores map data
    private int iObstacleWidth;         // The obstacle width
    private int iObstacleHeight;        // The obstacle height
    private double dPercentObstacles;   // The percent of the map that is occupied
    private Point mStart;               // The start point
    private Point mGoal;                // The goal point


    /*
        Constructors
     */

    /**
     * Creates an instance of a grid with the given specifications.
     * @param width int: The width of the grid.
     * @param height int: The height of the grid.
     * @param obsWidth int: The maximum width of obstacles on the grid.
     * @param obsHeight int: The maximum height of obstacles on the grid.
     * @param percentObs double: The percent of obstacles on the grid.
     * @param sx int: The x ordinal of the starting point.
     * @param sy int: The y ordinal of the starting point.
     * @param gx int: The x ordinal of the goal point.
     * @param gy int: The y ordinal of the goal point.
     */
    public Grid(int width, int height, int obsWidth,
                int obsHeight, double percentObs, int sx, int sy,
                int gx, int gy) {
        mGrid = new State[height][width];
        iObstacleWidth = obsWidth;
        iObstacleHeight = obsHeight;
        dPercentObstacles = percentObs;
        mStart = new Point(sx, sy);
        mGoal = new Point(gx, gy);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mGrid[y][x] = State.EMPTY;
            }
        }
    }

    /*
        Accessors & Mutators
     */

    /**
     * Gets the width of the grid.
     * @return int: The width of the grid.
     */
    public int getGridWidth() {
        return mGrid[0].length;
    }

    /**
     * Gets the height of the grid.
     * @return int: The height of the grid.
     */
    public int getGridHeight() {
        return mGrid.length;
    }

    /**
     * Gets the maximum width of obstacles on the grid.
     * @return int: The maximum width of obstacles.
     */
    public int getObstacleWidth() {
        return iObstacleWidth;
    }

    /**
     * Sets the maximum width of obstacles on the grid.
     * @param width int: Must be zero (0) or greater.
     */
    public void setObstacleWidth(int width) {
        if (width >= 0) {
            iObstacleWidth = width;
        }
    }

    /**
     * Gets the maximum height of obstacles on the grid.
     * @return int: The maximum height of obstacles.
     */
    public int getObstacleHeight() {
        return iObstacleHeight;
    }

    /**
     * Sets the maximum height of obstacles on the grid.
     * @param height int: Must be zero (0) or greater.
     */
    public void setObstacleHeight(int height) {
        if (height >= 0) {
            iObstacleHeight = height;
        }
    }

    /**
     * Gets the percent of all tiles of the grid that are occupied by obstacles.
     * @return double: The percent of tiles on the grid occupied by obstacles.
     */
    public double getPercentObstacles() {
        return dPercentObstacles;
    }

    /**
     * Sets the percent of all tiles of the grid to be occupied by obstacles.
     * @param percent double: The percent of tiles on the grid occupied by obstalces. Must be in range [0, .5]
     */
    public void setPercentObstacles(double percent) {
        if (dPercentObstacles >= 0 || dPercentObstacles <= .5) {
            dPercentObstacles = percent;
        }
    }

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
     * Sets all tiles on the grid to the State.EMPTY state.
     */
    public void clear() {
        for (int y = 0; y < mGrid.length; y++) {
            for (int x = 0; x < mGrid[0].length; x++) {
                setState(x, y, State.EMPTY);
            }
        }
    }

    /**
     * Creates obstacles on the map based on the max width and height as well as percent.
     */
    public void createObstacles() {
        for (int obs = 0; obs < (int)(dPercentObstacles * getGridWidth() * getGridHeight()); obs++) {
            int x = (int)(Math.random()*getGridWidth());
            int y = (int)(Math.random()*getGridHeight());
            for (int dy = 0; dy < (int)(Math.random()*getObstacleHeight()); dy++) {
                for (int dx = 0; dx < (int)(Math.random()*getObstacleWidth()); dx++) {
                    if (isInBounds(x + dx, y + dy) &&
                            getState(x + dx, y + dy) != State.PROTECTED) {
                        setState(x + dx, y + dy, State.OCCUPIED);
                    }
                }
            }
        }
    }

    /**
     * Generates a list of neighbors to a given point.
     * @param x int: The x ordinal of the point to generate neighbors from.
     * @param y int: The y ordinal of the point to generate neighbors from.
     * @return LinkedList: A list of neighbors.
     */
    public LinkedList<Point> generateNeighbors(int x, int y) {
        LinkedList<Point> neighbors = new LinkedList<>();
        for (int dy = -1; dy < 2; dy++) {
            for (int dx = -1; dx < 2; dx++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                if (!isValidCoordinates(x + dx, y + dy)) {
                    continue;
                }
                neighbors.add(new Point(x + dx, y + dy));
            }
        }
        return neighbors;
    }

    /**
     * Gets the state of a tile on the grid.
     * @param x int: The x ordinal of the tile.
     * @param y int: The y ordinal of the tile.
     * @return State: The state of the tile on the grid.
     */
    public State getState(int x, int y) {
        if (isInBounds(x, y)) {
            return mGrid[y][x];
        }
        throw new IndexOutOfBoundsException("ERROR: Coordinates fall outside bounds of grid!");
    }

    /**
     * Gets a string representation of a State.
     * @param state The state to convert to a string.
     * @return A string representation of a state.
     */
    public static String gridStateToString(State state) {
        switch (state) {
            case EMPTY:
                return "-";
            case OCCUPIED:
                return "O";
            case PATH:
                return "P";
            case PROTECTED:
                return "-";
            default:
                return "-";
        }
    }

    /**
     * Determines if a node is equal to the goal position.
     * @param node Node: The node to check.
     * @return boolean: Whether the position is equal to the goal or not.
     */
    public boolean isGoalNode(Node node) {
        return mGoal.getX() == node.getPosition().getX() && mGoal.getY() == node.getPosition().getY();
    }

    /**
     * Determines if a coordinate is in bounds of the grid.
     * @param x int: The x ordinal of the coordinate.
     * @param y int: The y ordinal of the coordinate.
     * @return boolean: Whether the specified coordinates are in bounds.
     */
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < mGrid[0].length && y >= 0 && y < mGrid.length;
    }

    /**
     * Determines if a tile at the specified coordinates is occupied by an obstacle or not.
     * @param x int: The x ordinal of the tile to check.
     * @param y int: The y ordinal of the tile to check.
     * @return boolean: Whether the tile is occupied or not.
     */
    private boolean isOccupied(int x, int y) {
        return getState(x, y) == State.OCCUPIED;
    }

    /**
     * Determines if a coordinate is valid.
     * @param x int: The x ordinal of the coordinate to check.
     * @param y int: The y ordinal of the coordinate to check.
     * @return boolean: Whether the coordinate is in bounds and not occupied.
     */
    public boolean isValidCoordinates(int x, int y) {
        return isInBounds(x, y) && !isOccupied(x, y);
    }

    /**
     * Determines if there is line of sight between two coordinates on the grid.
     * @param x1 int: The x ordinal of the origin point.
     * @param y1 int: The y ordinal of the origin point.
     * @param x2 int: The x ordinal of the destination point.
     * @param y2 int: The y ordinal of the destination point.
     * @return boolean: Whether there is line of sight between the two coordinates.
     * <div>This algorithm comes from Theta*: "Any-Angle Path Planning on Grids" by Daniel et al.
     * published in the Journal of Artificial Intelligence Resaech 39 (2010) pg. 533-579 and
     * retrieved from https(colon)//arxiv(dot)org/pdf/1401(dot)3843(dot)pdf<div/>
     */
    public boolean lineOfSight(int x1, int y1, int x2, int y2) {
        if (!isInBounds(x1, y1) || !isInBounds(x2, y2)) {
            return false;
        }
        int sx = 0;
        int sy = 0;
        int dy = y2 - y1;
        int dx = x2 - x1;
        int f = 0;
        if (dy < 0) {
            dx = -dx;
            sy = -1;
        } else {
            sy = 1;
        }
        if (dx < 0) {
            dx = -dx;
            sx = -1;
        } else {
            sx = 1;
        }
        if (dx >= dy) {
            while (x1 != x2) {
                f += dy;
                if (f >= dx) {
                    if (isOccupied(x1 + ((sx - 1)/2), y1 + ((sy - 1)/2))) {
                        return false;
                    }
                    y1 += sy;
                    f -= dx;
                }
                if (f != 0 &&
                        isOccupied(x1 + ((sx - 1)/2), y1 + ((sy - 1)/2))) {
                    return false;
                }
                if (dy == 0 &&
                        isOccupied(x1 + ((sx-1)/2), y1) &&
                        isOccupied(x1 + ((sx-1)/2), y1 - 1)) {
                    return false;
                }
                x1 += sx;
            }
        } else {
            while (y1 != y2) {
                f += dx;
                if (f >= dy) {
                    if (isOccupied(x1 + ((sx - 1)/2), y1 + ((sy - 1)/2))) {
                        return false;
                    }
                    x1 += sx;
                    f -= dy;
                }
                if (f != 0 &&
                        isOccupied(x1 + ((sx - 1)/2), y1 + ((sy - 1)/2))) {
                    return false;
                }
                if (dx == 0 &&
                        isOccupied(x1, y1 + ((sy-1)/2)) &&
                        isOccupied(x1 - 1, y1 + ((sy-1)/2))) {
                    return false;
                }
                y1 += sy;
            }
        }
        return true;
    }

    /**
     * Determines if there is line of sight between two points on the grid.
     * @param p1 Point: The origin point.
     * @param p2 Point: The destination point.
     * @return Whether there is line of sight between the two points on the grid.
     */
    public boolean lineOfSight(Point p1, Point p2) {
        return lineOfSight(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     *
     * @param x int: The x ordinal of the anchor for the obstacle.
     * @param y int: The y ordinal of the anchor for the obstacle.
     * @param width int: The width of the obstacle.
     * @param height int: The height of the obstacle.
     */
    public void placeObstacle(int x, int y, int width, int height) {
        if (isInBounds(x, y) && isInBounds(x + width, y + height)) {
            for (int dy = 0; dy < height; dy++) {
                for (int dx = 0; dx < width; dx++) {
                    setState(x + dx, y + dy, State.OCCUPIED);
                }
            }
        }
    }

    /**
     * Protects a tile so that it cannot be surrounded by obstacles.
     * @param x int: The x ordinal of the tile to protect.
     * @param y int: The y ordinal of the tile to protect.
     */
    public void protect(int x, int y) {
        if (isInBounds(x, y)) {
            for (int dy = -1; dy < 2; dy++) {
                for (int dx = -1; dx < 2; dx++) {
                    if (isInBounds(x + dx, y + dy)) {
                        setState(x + dx, y + dy, State.PROTECTED);
                    }
                }
            }
        }
    }

    /**
     * Generates a random point on the grid.
     * @return Point: A random point on the grid.
     */
    public Point random() {
        return new Point((int)Math.floor(Math.random()*getGridWidth()),
                (int)Math.floor(Math.random()*getGridHeight()));
    }

    /**
     * Sets the state of a tile on the grid.
     * @param x int: The x ordinal of the tile.
     * @param y int: The y ordinal of the tile.
     * @param state State: A state to update the tile to.
     */
    public void setState(int x, int y, State state) {
        if (isInBounds(x, y)) {
            mGrid[y][x] = state;
        }
        throw new IndexOutOfBoundsException("ERROR: Coordinates fall outside bounds of grid!");
    }

    /**
     * Generates a string representation of a grid based on its' current state.
     * @return String: A string representation of the grid.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < getGridHeight(); y++) {
            sb.append("|");
            for (int x = 0; x < getGridWidth(); x++) {
                sb.append(gridStateToString(getState(x, y))).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Updates the goal node to the specified coordinates.
     * @param x int: The x ordinal for the goal node.
     * @param y int: The y ordinal for the goal node.
     */
    public void updateGoal(int x, int y) {
        getStart().setX(x);
        getStart().setY(y);
    }

    /**
     * Updates the start node to the specified coordinates.
     * @param x int: THe x ordinal for the start node.
     * @param y int: The y ordinal for the start node.
     */
    public void updateStart(int x, int y) {
        getGoal().setX(x);
        getGoal().setY(y);
    }

}
