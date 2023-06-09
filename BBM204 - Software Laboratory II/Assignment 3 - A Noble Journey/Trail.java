public class Trail implements Comparable<Trail> {
    public Location source;
    public Location destination;
    public int danger;

    public Trail(Location source, Location destination, int danger) {
        this.source = source;
        this.destination = destination;
        this.danger = danger;
    }

    public int either() {
        return source.id;
    }

    public int other(int locationId) {
        if (locationId == source.id)
            return destination.id;
        else
            return source.id;
    }

    @Override
    public int compareTo(Trail o) {
        return Integer.compare(this.danger, o.danger);
    }
}
