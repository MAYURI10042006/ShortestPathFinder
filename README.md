# Shortest Path Finder

A Java-based project that uses **Dijkstra's Algorithm** to find the shortest path between two nodes in a weighted graph.

## About the Project

Shortest Path Finder is a console-based application developed using Java. It allows users to create a graph by entering node names, connections, and edge weights. The application calculates the shortest distance from a selected source node and displays the shortest path to the destination node.

## Features

- Create nodes dynamically.
- Add weighted edges between nodes.
- Find the shortest path using Dijkstra's Algorithm.
- Display the shortest distances from the source node.
- Validate user inputs and prevent duplicate nodes and edges.
- Handle unreachable destinations.
- Display the final path and total distance.

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Data Structures: ArrayList, HashMap, and PriorityQueue
- Graphs and Dijkstra's Algorithm

## Project Structure

- `Main.java` — Handles user input and displays results.
- `Graph.java` — Manages the graph and shortest-path calculation.
- `Node.java` — Represents a graph node.
- `Edge.java` — Represents a weighted connection.
- `NodeDistance.java` — Supports priority-based processing.
- `ShortestPathResult.java` — Stores distances and previous nodes.

## How to Run

1. Install the Java Development Kit (JDK).
2. Clone or download this repository.
3. Open the project folder in VS Code or another Java IDE.
4. Compile the Java source files.
5. Run the `Main` class.

## Algorithm

Dijkstra's Algorithm finds the shortest distances from a source node to other reachable nodes in a graph with non-negative edge weights.

## Author

Mayuri Patil
