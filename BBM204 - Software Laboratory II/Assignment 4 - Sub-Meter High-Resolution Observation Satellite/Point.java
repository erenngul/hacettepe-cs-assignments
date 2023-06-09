import java.util.ArrayList;
import java.util.List;

public class Point implements Comparable<Point> {
    public int x;
    public int y;
    public double distanceTo;
    public double cost;
    public ArrayList<DirectedEdge> adjacentEdges;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.distanceTo = Double.POSITIVE_INFINITY;
        this.cost = 0;
        this.adjacentEdges = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point o) {
        return Double.compare(this.distanceTo, o.distanceTo);
    }

    // You can add additional variables and methods if necessary.
}
