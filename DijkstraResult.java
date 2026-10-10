import java.util.Map;

public class DijkstraResult {

    private Map<Node, Integer> distances;
    private Map<Node, Node> previous;

    public DijkstraResult(
            Map<Node, Integer> distances,
            Map<Node, Node> previous) {

        this.distances = distances;
        this.previous = previous;
    }

    public Map<Node, Integer> getDistances() {
        return distances;
    }

    public Map<Node, Node> getPrevious() {
        return previous;
    }
}
