import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Bfs {
	public boolean bfs(Graph g, Vertex start, Vertex end, ArrayList<Vertex> path, int size) {
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		queue.add(start);
		visited.put(start.getName(), true);
		// should put a parent here
		path.add(start);

		while (queue.size() != 0) {
			Vertex v = queue.poll();
			for (Edge adjEdge : v.getAdjList()) {
				if (visited.get(adjEdge.getGoingV().getName()) == null && adjEdge.getCapacity() > 0) {
					if (adjEdge.getGoingV().getName().compareTo(end.getName()) == 0) {
						path.add(adjEdge.getGoingV());
						return true;
					}
					queue.add(adjEdge.getGoingV());
					path.add(adjEdge.getGoingV());
					visited.put(adjEdge.getGoingV().getName(), true);
				}
			}

		}
		return false;
	}


	
	
}
