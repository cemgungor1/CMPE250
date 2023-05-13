import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class project4 {

	public static void main(String[] args) {

		try {

			String inpPath = args[0];
			String outPath = args[1];

			// hash map to keep all flags since find o(1) delete o(1) add o(1)
			HashMap<String, Boolean> flagss = new HashMap<String, Boolean>();
			// priority queue to keep the queue
			// used 2 graphs since im changing, adding new paths etc
			PriorityQueue<Vertex> flagQueue = new PriorityQueue<Vertex>(new VertexComparator());
			Graph flagGraph = new Graph();

			PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(new VertexComparator());

			Graph g = new Graph();

			FileReader fr = new FileReader(inpPath);
			BufferedReader br = new BufferedReader(fr);
			BufferedWriter writerObj = new BufferedWriter(new FileWriter(outPath));

			// reading the input and declaring the variables
			int pointCount = Integer.parseInt(br.readLine());
			int flagCount = Integer.parseInt(br.readLine());
			String[] nextL = br.readLine().split(" ");

			String startP = nextL[0];
			String endP = nextL[1];
			int minDist = -1;

			nextL = br.readLine().split(" ");
			String firstFlag = nextL[0];
			for (String flag : nextL) {
				flagss.put(flag, true);
			}
			for (int i = 0; i < pointCount; i++) {

				nextL = br.readLine().split(" ");

				Vertex v = null;
				Vertex flagV = null;
				// if graph already has that vertex I skip, if it doesn't I add that vertex to
				// the graph
				v = g.checkVertex(nextL[0]);
				if (v == null) {
					v = g.addVertex(new Vertex(nextL[0]));
				}

				flagV = flagGraph.checkVertex(nextL[0]);
				if (flagV == null) {
					flagV = flagGraph.addVertex(new Vertex(nextL[0]));
				}
				// if its the first flag added to the initializer
				if (nextL[0].compareTo(firstFlag) == 0) {
					flagV.setDistance(0);
					flagQueue.add(flagV);
					firstFlag = flagV.getName();
				}
				// if the input is the starting point added to the queue as an initializer

				if (nextL[0].compareTo(startP) == 0) {
					v.setDistance(0);
					queue.add(v);
				}

				for (int j = 1; j < nextL.length; j += 2) {
					Vertex adjV = null;
					Vertex adjVflag = null;
					// adj vertexes are added here
					if (g.checkVertex(nextL[j]) != null) {
						adjV = g.checkVertex(nextL[j]);
					} else {
						adjV = new Vertex(nextL[j]);
						g.addVertex(adjV);
					}

					if (flagGraph.checkVertex(nextL[j]) != null) {
						adjVflag = flagGraph.checkVertex(nextL[j]);
					} else {
						adjVflag = new Vertex(nextL[j]);
						flagGraph.addVertex(adjVflag);
					}
					// using the adj Vertexes created edje objects and added to the list
					v.addAdjEdge(new Edge(adjV, Integer.parseInt(nextL[j + 1])));
					adjV.addAdjEdge(new Edge(v, Integer.parseInt(nextL[j + 1])));

					flagV.addAdjEdge(new Edge(adjVflag, Integer.parseInt(nextL[j + 1])));
					adjVflag.addAdjEdge(new Edge(flagV, Integer.parseInt(nextL[j + 1])));
				}

			}
			while (queue.size() != 0) {
				// two boolean fields, one to know if its in the queue already, false since we
				// poll it
				// used a field so that ý don't search the queue
				// and one to check if the queue processed that vertex already
				Vertex v = queue.poll();
				v.setKnown(true);
				v.setInQueue(false);

				if (v.getName().compareTo(endP) == 0) {
					// if we are at the end point, break out of for loop since its the minimum path
					minDist += (v.getDistance() + 1);
					// queue.clear();
					break;
				}

				for (Edge edge : v.getAdjList()) {
					Vertex nextV = edge.getNextVertex();
					if (v.getDistance() + edge.getWeight() < nextV.getDistance()) {
						// if the path from the vertex to its adj vertex is smaller than its adjVertex
						// normal distance
						// update it
						nextV.setDistance(v.getDistance() + edge.getWeight());
						// since the position is not updated when distance changes, remove and add the
						// vertex
						if (nextV.getInQueue()) {
							queue.remove(nextV);
							queue.add(nextV);
						}
					}
					// using both in queue and getknown, add to queue or not
					if (!nextV.getInQueue() & nextV.getKnown() == false) {
						nextV.setInQueue(true);
						queue.add(nextV);
					}
				}
			}

			writerObj.write(Integer.toString(minDist));
			writerObj.write("\n");

			int dist = 0;
			int realDist = -1;
			Vertex oldFlag = null;
			int length = 0;
			// two adt's holding the same things, but since timecomplexity of for loop in
			// hashmap is greater than arraylist
			// used arraylist to iterate
			HashMap<Vertex, Boolean> usedVerticess = new HashMap<Vertex, Boolean>();
			ArrayList<Vertex> used1 = new ArrayList<Vertex>();

			while (flagQueue.size() != 0) {
				// same shortest path algorithm as above
				Vertex v = flagQueue.poll();
				v.setKnown(true);
				v.setInQueue(false);
				// since polled vertex is used vertex, added to the list
				usedVerticess.put(v, true);
				used1.add(v);

				if (flagss.get(v.getName()) != null) {
					// if at a flag, change distance and remove that flag from flagss list
					flagss.remove(v.getName());
					dist += v.getDistance();

					if (flagss.size() == 0) {
						// if all the flags are removed break and increment dist by 1 since if we
						// couldn't finish looking for flags
						// we should print -1
						realDist += dist;
						realDist += 1;
						break;
					}
					if (oldFlag != null) {
						// if a flag is deleted before, create an edge between two having weight 0
						v.addAdjEdge(new Edge(oldFlag, 0));
						oldFlag.addAdjEdge(new Edge(v, 0));
					}
					// reset all vertexes used
					for (Vertex used : used1) {
						used.setKnown(false);
					}
					// start from v, last flag
					v.setDistance(0);
					usedVerticess = new HashMap<Vertex, Boolean>();
					used1 = new ArrayList<Vertex>();
					// change ýnQueue to false
					for (Vertex inQueue : flagQueue) {
						inQueue.setInQueue(false);
					}
					flagQueue.clear();
					oldFlag = v;
				}
				if (flagss.size() > 0) {
					// same algorithm as above
					for (Edge edge : v.getAdjList()) {
						Vertex nextV = edge.getNextVertex();
						length = v.getDistance() + edge.getWeight();
						if (length < nextV.getDistance()) {
							nextV.setDistance(length);
							flagQueue.add(nextV);
						}
						if (!nextV.getInQueue() & nextV.getKnown() == false) {
							nextV.setInQueue(true);
							flagQueue.add(nextV);
						}
					}
				} else {
					break;
				}
			}
			writerObj.write(Integer.toString(realDist));
			br.close();
			writerObj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
