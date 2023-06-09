public class DirectedEdge {
    public Point from;
    public Point to;
    public double cost;

    public DirectedEdge(Point from, Point to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}
