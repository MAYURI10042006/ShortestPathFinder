
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("        SHORTEST PATH FINDER");
        System.out.println("          Dijkstra Algorithm");
        System.out.println("========================================");
        System.out.println();

        // Select graph type
        int graphChoice;

        while (true) {
            try {
                System.out.println("Select Graph Type:");
                System.out.println("1. Directed Graph");
                System.out.println("2. Undirected Graph");
                System.out.print("Enter your choice (1 or 2): ");

                graphChoice = scanner.nextInt();
                scanner.nextLine();

                if (graphChoice == 1 || graphChoice == 2) {
                    break;
                }

                System.out.println(
                        "Invalid choice. Please enter 1 or 2."
                );

            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input. Please enter a number."
                );
                scanner.nextLine();
            }
        }

        boolean directed = (graphChoice == 1);

        // Create graph with selected type
        Graph graph = new Graph(directed);

        System.out.println();
        System.out.println("Graph Type: "
                + (directed ? "Directed" : "Undirected"));
        System.out.println();

        // Ask for number of nodes
        int numberOfNodes;

        while (true) {
            try {
                System.out.print("Enter number of nodes: ");
                numberOfNodes = scanner.nextInt();
                scanner.nextLine();

                if (numberOfNodes < 2) {
                    System.out.println(
                            "Number of nodes must be at least 2."
                    );
                    continue;
                }

                break;

            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input. Please enter a number."
                );
                scanner.nextLine();
            }
        }

        // Create nodes
        for (int i = 1; i <= numberOfNodes; i++) {

            System.out.print("Enter name for node " + i + ": ");
            String nodeName = scanner.nextLine().trim();

            if (nodeName.isEmpty()) {
                System.out.println(
                        "Node name cannot be empty."
                );
                i--;
                continue;
            }

            if (graph.findNode(nodeName) != null) {
                System.out.println(
                        "Node already exists. Please enter a different name."
                );
                i--;
                continue;
            }

            graph.addNode(new Node(nodeName));
        }

        // Ask for number of edges
        int numberOfEdges;

        while (true) {
            try {
                System.out.print("Enter number of edges: ");
                numberOfEdges = scanner.nextInt();
                scanner.nextLine();

                if (numberOfEdges < 0) {
                    System.out.println(
                            "Number of edges cannot be negative."
                    );
                    continue;
                }

                break;

            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input. Please enter a number."
                );
                scanner.nextLine();
            }
        }

        // Create edges
        for (int i = 1; i <= numberOfEdges; i++) {

            System.out.println();
            System.out.println("Enter details for edge " + i);

            System.out.print("Enter source node: ");
            String sourceName = scanner.nextLine().trim();

            System.out.print("Enter destination node: ");
            String destinationName = scanner.nextLine().trim();

            Node sourceNode = graph.findNode(sourceName);
            Node destinationNode = graph.findNode(destinationName);

            // Validate nodes before asking for weight
            if (sourceNode == null || destinationNode == null) {
                System.out.println(
                        "Invalid node name. Edge not added."
                );
                i--;
                continue;
            }

            if (sourceNode.equals(destinationNode)) {
                System.out.println(
                        "Self-connections are not allowed."
                );
                i--;
                continue;
            }

            // Check duplicate connection
            if (graph.edgeExists(sourceNode, destinationNode)) {
                System.out.println(
                        "This edge already exists. Edge not added."
                );
                i--;
                continue;
            }

            // Read edge weight
            int weight;

            while (true) {
                try {
                    System.out.print("Enter weight: ");
                    weight = scanner.nextInt();
                    scanner.nextLine();

                    if (weight < 0) {
                        System.out.println(
                                "Weight cannot be negative."
                        );
                        continue;
                    }

                    break;

                } catch (InputMismatchException e) {
                    System.out.println(
                            "Invalid input. Please enter a number."
                    );
                    scanner.nextLine();
                }
            }

            // Add edge
            graph.addEdge(
                    new Edge(sourceNode, destinationNode, weight)
            );

            System.out.println("Edge added successfully.");
        }

        // Display available nodes
        System.out.println();
        System.out.println("Available nodes:");

        for (Node node : graph.getNodes()) {
            System.out.print(node + " ");
        }

        System.out.println();

        // Display edges
        System.out.println();
        System.out.println("Available edges:");

        if (graph.getEdges().isEmpty()) {
            System.out.println("No edges available.");
        } else {
            for (Edge edge : graph.getEdges()) {
                System.out.println(edge);
            }
        }

        // Ask for source and destination
        System.out.println();
        System.out.print("Enter source node: ");
        String sourceName = scanner.nextLine().trim();

        System.out.print("Enter destination node: ");
        String destinationName = scanner.nextLine().trim();

        Node source = graph.findNode(sourceName);
        Node destination = graph.findNode(destinationName);

        if (source == null) {
            System.out.println("Source node not found.");
            scanner.close();
            return;
        }

        if (destination == null) {
            System.out.println("Destination node not found.");
            scanner.close();
            return;
        }

        // Run Dijkstra's Algorithm
        ShortestPathResult result =
                graph.findShortestPath(source);

        Map<Node, Integer> distances =
                result.getDistances();

        List<Node> path =
                graph.getShortestPath(
                        source,
                        destination,
                        result
                );

        // Display shortest distances
        System.out.println();
        System.out.println(
                "Shortest distances from " + source + ":"
        );

        for (Map.Entry<Node, Integer> entry
                : distances.entrySet()) {

            if (entry.getValue() == Integer.MAX_VALUE) {
                System.out.println(
                        entry.getKey() + " = Unreachable"
                );
            } else {
                System.out.println(
                        entry.getKey() + " = " + entry.getValue()
                );
            }
        }

        // Display final result
        System.out.println();
        System.out.println("========================================");
        System.out.println("          SHORTEST PATH RESULT");
        System.out.println("========================================");

        System.out.println("Graph Type  : "
                + (directed ? "Directed" : "Undirected"));
        System.out.println("Source      : " + source);
        System.out.println("Destination : " + destination);
        System.out.println();

        if (path.isEmpty()) {
            System.out.println(
                    "No path exists from " + source
                    + " to " + destination + "."
            );
        } else {
            System.out.println("Shortest Path:");

            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));

                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();
            System.out.println();
            System.out.println(
                    "Total Distance: " + distances.get(destination)
            );
        }

        System.out.println("========================================");

        scanner.close();
    }
}





