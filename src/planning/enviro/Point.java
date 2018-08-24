package planning.enviro;

public class Point {

    private int iX;
    private int iY;

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof Point)) {
            return false;
        }
        Point p = (Point)object;
        return iX == p.getX() && iY == p.getY();
    }

    @Override
    public int hashCode() {
        int result = iX;
        result = 31 * result + iY;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Coordinates: (%d, %d)", iX, iY);
    }

    public Point(int x, int y) {
        iX = x;
        iY = y;
    }

    public int getX() {
        return iX;
    }

    public void setX(int x) {
        iX = x;
    }

    public int getY() {
        return iY;
    }

    public void setY(int y) {
        iY = y;
    }

}
