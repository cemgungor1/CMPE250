import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class project5 {
	public static void main(String[] args) {

		try {

			String inpPath = args[0];
			String outPath = args[1];
			Graph g = new Graph();
			FileReader fr = new FileReader(inpPath);
			BufferedReader br = new BufferedReader(fr);
			BufferedWriter writerObj = new BufferedWriter(new FileWriter(outPath));

			int cityCount = Integer.parseInt(br.readLine());
			String[] troops = br.readLine().split(" ");
			// added a starting vertex which is not given
			// so that I can run ford fulkerson from start to Kings landing
			Vertex start = new Vertex("start");
			g.addVertex(start);
			Vertex end = new Vertex("KL");
			g.addVertex(end);
			Vertex city = null;
			for (int i = 0; i < 6; i++) {
				// created the regions and added an edge from start to regions for each region
				String[] line = br.readLine().split(" ");
				Vertex region = new Vertex(line[0]);
				start.addAdjEdge(new Edge(start, region, 0, Integer.parseInt(troops[i])));
				g.addVertex(region);
				// creating regions and the cities they are connected
				for (int j = 1; j < line.length; j += 2) {
					if (g.checkVertex(line[j]) == null) {
						city = new Vertex(line[j]);
						g.addVertex(city);
					} else {
						city = g.checkVertex(line[j]);
					}
					region.addAdjEdge(new Edge(region, city, 0, Integer.parseInt(line[j + 1])));
				}
			}
			// created new cities if they are not created yet, and added given edges to the
			// city's adjList
			Vertex currentCity = null;
			for (int i = 0; i < cityCount; i++) {
				String[] line = br.readLine().split(" ");
				if (g.checkVertex(line[0]) == null) {
					currentCity = new Vertex(line[0]);
					g.addVertex(currentCity);
				} else {
					currentCity = g.checkVertex(line[0]);
				}
				for (int j = 1; j < line.length; j += 2) {
					if (g.checkVertex(line[j]) == null) {
						city = new Vertex(line[j]);
						g.addVertex(city);
					} else {
						city = g.checkVertex(line[j]);
					}
					currentCity.addAdjEdge(new Edge(currentCity, city, 0, Integer.parseInt(line[j + 1])));
				}
			}
			// with the graph and start, end vertex, created a ford fulkerson object
			// it starts the algorithm when created
			FordFulkerson f = new FordFulkerson(g, start, end);
			writerObj.write(String.valueOf(f.getValue()));

			br.close();
			writerObj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
