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

        // Ask user for number of nodes
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

        // Create graph
        Graph graph = new Graph();

        // Create nodes using user input
        for (int i = 1; i <= numberOfNodes; i++) {

            System.out.print("Enter name for node " + i + ": ");
            String nodeName = scanner.nextLine();

            // Check for empty node name
            if (nodeName.trim().isEmpty()) {

                System.out.println(
                        "Node name cannot be empty."
                );

                i--;
                continue;
            }

            // Check for duplicate node
            if (graph.findNode(nodeName) != null) {

                System.out.println(
                        "Node already exists. Please enter a different name."
                );

                i--;
                continue;
            }

            Node node = new Node(nodeName);

            graph.addNode(node);
        }

        // Ask user for number of edges
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

        // Create edges using user input
        for (int i = 1; i <= numberOfEdges; i++) {

            System.out.println();
            System.out.println("Enter details for edge " + i);

            System.out.print("Enter source node: ");
            String sourceName = scanner.nextLine();

            System.out.print("Enter destination node: ");
            String destinationName = scanner.nextLine();

            // Ask for weight safely
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

            // Find actual Node objects
            Node sourceNode = graph.findNode(sourceName);
            Node destinationNode = graph.findNode(destinationName);

            // Validate nodes
            if (sourceNode == null || destinationNode == null) {

                System.out.println(
                        "Invalid node name. Edge not added."
                );

                i--;

            // Check duplicate edge
            } else if (graph.edgeExists(sourceNode, destinationNode)) {

                System.out.println(
                        "This edge already exists. Edge not added."
                );

                i--;

            } else {

                // Create and add edge
                graph.addEdge(
                        new Edge(
                                sourceNode,
                                destinationNode,
                                weight
                        )
                );

                System.out.println(
                        "Edge added successfully."
                );
            }
        }

        // Display available nodes
        System.out.println();
        System.out.println("Available nodes:");

        for (Node node : graph.getNodes()) {
            System.out.print(node + " ");
        }

        System.out.println();

        // Display available edges
        System.out.println();
        System.out.println("Available edges:");

        if (graph.getEdges().isEmpty()) {

            System.out.println("No edges available.");

        } else {

            for (Edge edge : graph.getEdges()) {
                System.out.println(edge);
            }
        }

        System.out.println();

        // Ask user for source and destination
        System.out.print("Enter source node: ");
        String sourceName = scanner.nextLine();

        System.out.print("Enter destination node: ");
        String destinationName = scanner.nextLine();

        // Find actual Node objects
        Node source = graph.findNode(sourceName);
        Node destination = graph.findNode(destinationName);

        // Check source
        if (source == null) {

            System.out.println(
                    "Source node not found."
            );

            scanner.close();
            return;
        }

        // Check destination
        if (destination == null) {

            System.out.println(
                    "Destination node not found."
            );

            scanner.close();
            return;
        }

        // Run Dijkstra
        ShortestPathResult result =
                graph.findShortestPath(source);

        // Get distances
        Map<Node, Integer> distances =
                result.getDistances();

        // Find shortest path
        List<Node> path =
                graph.getShortestPath(
                        source,
                        destination,
                        result
                );

        // Display all shortest distances
        System.out.println();
        System.out.println(
                "Shortest distances from "
                + source
                + ":"
        );

        for (Map.Entry<Node, Integer> entry
                : distances.entrySet()) {

            if (entry.getValue() == Integer.MAX_VALUE) {

                System.out.println(
                        entry.getKey()
                        + " = Unreachable"
                );

            } else {

                System.out.println(
                        entry.getKey()
                        + " = "
                        + entry.getValue()
                );
            }
        }

        // Display final shortest path result
        System.out.println();
        System.out.println("========================================");
        System.out.println("          SHORTEST PATH RESULT");
        System.out.println("========================================");

        System.out.println("Source      : " + source);
        System.out.println("Destination : " + destination);
        System.out.println();

        // Check if path exists
        if (path.isEmpty()) {

            System.out.println(
                    "No path exists from "
                    + source
                    + " to "
                    + destination
                    + "."
            );

        } else {

            System.out.println("Shortest Path:");

            // Display path
            for (int i = 0; i < path.size(); i++) {

                System.out.print(path.get(i));

                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();
            System.out.println();

            // Display shortest distance
            System.out.println(
                    "Total Distance: "
                    + distances.get(destination)
            );
        }

        System.out.println("========================================");

        scanner.close();
    }
}




