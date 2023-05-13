import java.util.HashMap;

public class Graph {
	// just a graph object having vertices as a hashmap
	private HashMap<String, Vertex> vertices;
	
	public Graph() {
		this.vertices = new HashMap<String,Vertex>();
	}
	
	public HashMap<String,Vertex> getVertices(){
		return this.vertices;
	}
	
	public Vertex addVertex(Vertex v) {
		this.vertices.put(v.getName(), v);		
		return v;
	}
	
	public Vertex checkVertex(String nameVertex) {
		if (vertices.get(nameVertex) != null) {
			return vertices.get(nameVertex);
		}
		else {
			return null;
		}
	}

}























