package planning.enviro;

import java.util.LinkedList;

public class Grid {

    /*
    Fields
     */

    private GridState[][] mGrid;        // Stores map data
    private int iObstacleWidth;         // The obstacle width
    private int iObstacleHeight;        // The obstacle height
    private double dPercentObstacles;   // The percent of the map that is occupied

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
     */
    public Grid(int width, int height, int obsWidth,
                int obsHeight, double percentObs) {
        mGrid = new GridState[height][width];
        iObstacleWidth = obsWidth;
        iObstacleHeight = obsHeight;
        dPercentObstacles = percentObs;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mGrid[y][x] = GridState.EMPTY;
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

    /*
        Methods
     */

    /**
     * Sets all tiles on the grid to the GridState.EMPTY state.
     */
    public void clear() {
        for (int y = 0; y < mGrid.length; y++) {
            for (int x = 0; x < mGrid[0].length; x++) {
                setState(x, y, GridState.EMPTY);
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
                            getState(x + dx, y + dy) != GridState.PROTECTED) {
                        setState(x + dx, y + dy, GridState.OCCUPIED);
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
                if (!isInBounds(x + dx, y + dy)) {
                    continue;
                }
                if (getState(x + dx, y + dy) == GridState.OCCUPIED) {
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
     * @return GridState: The state of the tile on the grid.
     */
    public GridState getState(int x, int y) {
        if (isInBounds(x, y)) {
            return mGrid[y][x];
        }
        throw new IndexOutOfBoundsException("ERROR: Coordinates fall outside bounds of grid!");
    }

    /**
     * Gets a string representation of a GridState.
     * @param state The state to convert to a string.
     * @return A string representation of a state.
     */
    public static String gridStateToString(GridState state) {
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
        return getState(x, y) == GridState.OCCUPIED;
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
                    setState(x + dx, y + dy, GridState.OCCUPIED);
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
                        setState(x + dx, y + dy, GridState.PROTECTED);
                    }
                }
            }
        }
    }

    /**
     * Sets the state of a tile on the grid.
     * @param x int: The x ordinal of the tile.
     * @param y int: The y ordinal of the tile.
     * @param state GridState: A state to update the tile to.
     */
    public void setState(int x, int y, GridState state) {
        if (isInBounds(x, y)) {
            mGrid[y][x] = state;
        }
        throw new IndexOutOfBoundsException("ERROR: Coordinates fall outside bounds of grid!");
    }

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

}
