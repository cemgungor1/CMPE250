
public class Edge {
	// edge object having adjacent vertex and weight
	private Vertex nextV;
	private int weight;

	public Edge(Vertex nextV, int weight) {
		this.nextV = nextV;
		this.weight = weight;
	}

	public Vertex getNextVertex() {
		return this.nextV;
	}

	public int getWeight() {
		return this.weight;
	}

}
