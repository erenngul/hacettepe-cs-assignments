import java.util.ArrayList;
import java.util.List;

public class Location {
    public String name;
    public int id;
    public boolean marked;
    public List<Trail> adj;

    public Location(String name, int id) {
        this.name = name;
        this.id = id;
        this.marked = false;
        this.adj = new ArrayList<>();
    }
}
