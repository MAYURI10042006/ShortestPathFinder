import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {

    public static Map<Node, Integer> findShortestPaths(
            Graph graph, Node start) {

        // Step 1: Create distance map
        Map<Node, Integer> distances = new HashMap<>();

        // Step 2: Set initial distance of every node to infinity
        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }

        // Step 3: Distance of starting node is 0
        distances.put(start, 0);

        // Step 4: Create PriorityQueue
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>();

        // Step 5: Add starting node to the queue
        queue.add(new NodeDistance(start, 0));

        // Step 6: Process nodes while queue is not empty
        while (!queue.isEmpty()) {

            NodeDistance current = queue.poll();

            Node currentNode = current.getNode();
            int currentDistance = current.getDistance();

            // Ignore outdated distance
            if (currentDistance > distances.get(currentNode)) {
                continue;
            }

            // Step 7: Check all edges
            for (Edge edge : graph.getEdges()) {

                // Check whether the edge starts from current node
                if (edge.getSource().equals(currentNode)) {

                    Node neighbor = edge.getDestination();

                    // Step 8: Calculate possible new distance
                    int newDistance =
                            currentDistance + edge.getWeight();

                    // Step 9: Relaxation
                    if (newDistance < distances.get(neighbor)) {

                        // Update shorter distance
                        distances.put(neighbor, newDistance);

                        // Add updated distance to PriorityQueue
                        queue.add(
                                new NodeDistance(neighbor, newDistance)
                        );
                    }
                }
            }
        }

        // Step 10: Return shortest distances
        return distances;
    }
}