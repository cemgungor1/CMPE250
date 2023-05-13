
public class Edge {
	// edge object having from and to vertex and flow and capacity
	private Vertex comingV;
	private Vertex goingV;

	private int flow;
	private int capacity;

	public Edge(Vertex comingV, Vertex goingV, int flow, int capacity) {
		this.comingV = comingV;
		this.goingV = goingV;
		this.capacity = capacity;
	}

	public Vertex getComingV() {
		return comingV;
	}

	public void setComingV(Vertex comingV) {
		this.comingV = comingV;
	}

	public Vertex getGoingV() {
		return goingV;
	}

	public void setGoingV(Vertex goingV) {
		this.goingV = goingV;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	public int getCapacity() {
		return capacity;
	}
	// a getter to get the opposite vertex of the given side
	public Vertex getOpposite(Vertex v) {
		if (v.getName().compareTo(comingV.getName()) == 0) {
			return goingV;
		} else {
			return comingV;
		}
	}
	// a getter to get the residual capacity, if its the opposite side it returns capacity-flow
	public int residualCapacity(Vertex v) {
		if (v.getName().compareTo(comingV.getName()) == 0) {
			return flow;
		} else {
			return capacity - flow;
		}
	}
	// a setter to change the flow, if its going to opposite side, flow is decreased
	public void addResidualFlow(Vertex v, int change) {
		if (v.getName().compareTo(comingV.getName()) == 0) {
			this.flow -= change;
		} else {
			this.flow += change;
		}
	}

}
