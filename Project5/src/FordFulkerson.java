import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class FordFulkerson {
	private HashMap<String, Boolean> visited;
	private HashMap<Vertex, Edge> path;
	private int value;
	private int size;

	public FordFulkerson(Graph g, Vertex start, Vertex end) {
		// this.size = size;
		value = 0;
		while (hasAugmentingPath(g, start, end)) {
			// if there's augmenting path, using recursion we find the maximum flow of the capacity
			// we can send through that path and add that to the value.
			int bottleneck = Integer.MAX_VALUE;
			for (Vertex v = end; v != start; v = path.get(v).getOpposite(v)) {
				bottleneck = Math.min(bottleneck, path.get(v).residualCapacity(v));
			}
			for (Vertex v = end; v != start; v = path.get(v).getOpposite(v)) {
				path.get(v).addResidualFlow(v, bottleneck);
			}
			value += bottleneck;
		}
	}

	public boolean hasAugmentingPath(Graph g, Vertex start, Vertex end) {
		// this function checks for all the paths inside the graph using a queue and bfs
		// for each vertex, checks for its adjEdge's vertices and adds them to the path, visited list if they are not visited
		// and their residual capacity is greater than 0
		path = new HashMap<Vertex, Edge>();
		visited = new HashMap<String, Boolean>();
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		queue.add(start);
		visited.put(start.getName(), true);
		while (queue.size() != 0) {
			Vertex v = queue.poll();
			for (Edge adjEdge : v.getAdjList()) {
				Vertex oppositeV = adjEdge.getOpposite(v);
				if (visited.get(oppositeV.getName()) == null && adjEdge.residualCapacity(oppositeV) > 0) {
					path.put(oppositeV, adjEdge);
					visited.put(oppositeV.getName(), true);
					queue.add(oppositeV);
				}
			}
		}
		// if either all vertices are known or none of the vertices that are connected to KL have residual capacity > 0
		// we return false since no more augmented path is
		if (visited.get(end.getName()) == null) {
			return false;
		} else {
			return visited.get(end.getName());
		}
	}

	public int getValue() {
		return value;
	}

}
