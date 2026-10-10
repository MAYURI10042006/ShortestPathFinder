import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {

    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    // Find a node by its name
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

        // LinkedHashMap keeps nodes in insertion order
        Map<Node, Integer> distances = new LinkedHashMap<>();

        Map<Node, Node> previous = new LinkedHashMap<>();

        // Initially set every node's distance to infinity
        for (Node node : nodes) {
            distances.put(node, Integer.MAX_VALUE);
        }

        // Distance from start node to itself is 0
        distances.put(start, 0);

        // PriorityQueue stores nodes according to smallest distance
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>();

        // Add starting node
        queue.add(new NodeDistance(start, 0));

        // Continue until queue becomes empty
        while (!queue.isEmpty()) {

            NodeDistance current = queue.poll();

            Node currentNode = current.getNode();
            int currentDistance = current.getDistance();

            // Skip outdated distance
            if (currentDistance > distances.get(currentNode)) {
                continue;
            }

            // Check every edge
            for (Edge edge : edges) {

                // Only consider edges starting from current node
                if (edge.getSource().equals(currentNode)) {

                    Node neighbor = edge.getDestination();
                    int weight = edge.getWeight();

                    // Calculate new possible distance
                    int newDistance = currentDistance + weight;

                    // Relaxation
                    if (newDistance < distances.get(neighbor)) {

                        distances.put(neighbor, newDistance);

                        // Remember previous node
                        previous.put(neighbor, currentNode);

                        // Add updated distance to queue
                        queue.add(
                                new NodeDistance(
                                        neighbor,
                                        newDistance
                                )
                        );
                    }
                }
            }
        }

        return new ShortestPathResult(distances, previous);
    }

    // Reconstruct the actual shortest path
    public List<Node> getShortestPath(
            Node start,
            Node destination,
            ShortestPathResult result) {

        List<Node> path = new ArrayList<>();

        Map<Node, Node> previous = result.getPrevious();

        // Check if destination is unreachable
        if (!result.getDistances().containsKey(destination)
                || result.getDistances().get(destination)
                == Integer.MAX_VALUE) {

            return new ArrayList<>();
        }

        Node current = destination;

        // Follow previous nodes backwards
        while (current != null) {

            path.add(current);

            // Stop when starting node is reached
            if (current.equals(start)) {
                break;
            }

            current = previous.get(current);
        }

        // Reverse path
        java.util.Collections.reverse(path);

        return path;
    }
}