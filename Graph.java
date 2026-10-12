
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {

    private List<Node> nodes;
    private List<Edge> edges;
    private boolean directed;

    // Constructor
    public Graph(boolean directed) {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        this.directed = directed;
    }

    // Add a node
    public void addNode(Node node) {
        nodes.add(node);
    }

    // Add an edge
    public void addEdge(Edge edge) {
        edges.add(edge);

        // For an undirected graph, add the reverse connection
        if (!directed) {
            Edge reverseEdge = new Edge(
                edge.getDestination(),
                edge.getSource(),
                edge.getWeight()
            );

            edges.add(reverseEdge);
        }
    }

    // Get all nodes
    public List<Node> getNodes() {
        return nodes;
    }

    // Get all edges
    public List<Edge> getEdges() {
        return edges;
    }

    // Check graph type
    public boolean isDirected() {
        return directed;
    }

    // Find a node by name
    public Node findNode(String name) {
        for (Node node : nodes) {
            if (node.getName().equalsIgnoreCase(name)) {
                return node;
            }
        }
        return null;
    }

    // Check whether an edge already exists
    public boolean edgeExists(Node source, Node destination) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)
                    && edge.getDestination().equals(destination)) {
                return true;
            }
        }
        return false;
    }

    // Dijkstra's shortest path algorithm
    public ShortestPathResult findShortestPath(Node start) {

        Map<Node, Integer> distances = new LinkedHashMap<>();
        Map<Node, Node> previous = new LinkedHashMap<>();

        for (Node node : nodes) {
            distances.put(node, Integer.MAX_VALUE);
        }

        distances.put(start, 0);

        PriorityQueue<NodeDistance> queue = new PriorityQueue<>();
        queue.add(new NodeDistance(start, 0));

        while (!queue.isEmpty()) {

            NodeDistance current = queue.poll();

            Node currentNode = current.getNode();
            int currentDistance = current.getDistance();

            if (currentDistance > distances.get(currentNode)) {
                continue;
            }

            for (Edge edge : edges) {

                if (edge.getSource().equals(currentNode)) {

                    Node neighbor = edge.getDestination();
                    int weight = edge.getWeight();

                    // Avoid integer overflow
                    if (currentDistance > Integer.MAX_VALUE - weight) {
                        continue;
                    }

                    int newDistance = currentDistance + weight;

                    if (newDistance < distances.get(neighbor)) {

                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, currentNode);

                        queue.add(
                            new NodeDistance(neighbor, newDistance)
                        );
                    }
                }
            }
        }

        return new ShortestPathResult(distances, previous);
    }

    // Reconstruct the shortest path
    public List<Node> getShortestPath(
            Node start,
            Node destination,
            ShortestPathResult result) {

        List<Node> path = new ArrayList<>();

        Map<Node, Integer> distances = result.getDistances();
        Map<Node, Node> previous = result.getPrevious();

        if (!distances.containsKey(destination)
                || distances.get(destination) == Integer.MAX_VALUE) {
            return path;
        }

        Node current = destination;

        while (current != null) {
            path.add(current);

            if (current.equals(start)) {
                break;
            }

            current = previous.get(current);
        }

        // Verify that the path reaches the source
        if (path.isEmpty()
                || !path.get(path.size() - 1).equals(start)) {
            return new ArrayList<>();
        }

        Collections.reverse(path);

        return path;
    }
}
