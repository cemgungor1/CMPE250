import java.util.ArrayList;
import java.util.Comparator;

public class Vertex {

	private String name;
	private int dist;
	private boolean known;
	private ArrayList<Edge> AdjList;
	private boolean inQueue;

	public Vertex(String name) {
		this.name = name;
		this.dist = 10000000;
		this.known = false;
		this.AdjList = new ArrayList<Edge>();
	}

	public void setInQueue(boolean b) {
		this.inQueue = b;
	}

	public boolean getInQueue() {
		return inQueue;
	}

	public int getDistance() {
		return this.dist;
	}

	public String getName() {
		return this.name;
	}

	public void setDistance(int distance) {
		this.dist = distance;
	}

	public ArrayList<Edge> getAdjList() {
		return this.AdjList;
	}

	public void addAdjEdge(Edge e) {
		this.AdjList.add(e);
	}

	public void setKnown(boolean known) {
		this.known = known;
	}

	public boolean getKnown() {
		return this.known;
	}

}

class VertexComparator implements Comparator<Vertex> {
	// just a comparator for priority queue, if distance is smaller then that vertex is at front.
	public int compare(Vertex vertex1, Vertex vertex2) {

		if (vertex1.getDistance() < vertex2.getDistance()) {
			return -1;
		} else if (vertex1.getDistance() < vertex2.getDistance()) {
			return 1;
		}
		return 1;
	}
}