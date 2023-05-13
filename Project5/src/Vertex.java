import java.util.ArrayList;
import java.util.Comparator;

public class Vertex {
	// just a vertex object having array list of edges
	private String name;
	private ArrayList<Edge> AdjList;

	public Vertex(String name) {
		this.name = name;
		this.AdjList = new ArrayList<Edge>();
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<Edge> getAdjList() {
		return this.AdjList;
	}

	public void addAdjEdge(Edge e) {
		this.AdjList.add(e);
	}

}