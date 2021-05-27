import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;

public class pathFinder {
	public static void main(String args[]){
		long startTime = System.currentTimeMillis();
		
		StdDraw.setCanvasSize(1000, 1000);
		String m = "map5.txt";
		
		ShapeMap map = new ShapeMap(m);
	
		int hulls = 0;
		
		Point2D[][] convexHulls = new Point2D[map.amountofPolys()][];
		
		for (Polygon2D poly : map) {
			Polygon2D polygon = new Polygon2D(GrahamScan.findConvexHull(poly));
			Point2D points[] = polygon.asPointsArray();
			poly = GrahamScan.PreProcess(poly);
			StdDraw.setPenColor(Color.RED);
			poly.draw();
			//StdDraw.setPenColor(Color.LIGHT_GRAY);
			//polygon.drawFilled();
			//StdDraw.setPenColor(Color.GRAY);
			//poly.drawFilled();
			convexHulls[hulls] = points;
			StdDraw.setPenColor(Color.RED);
			poly.draw();
			hulls++;
		}
		
		Point2D startingPoint = map.sourcePoint();
		Point2D endPoint = map.destinationPoint();
		
		List<Graph.Edge> paths = new ArrayList<Graph.Edge>();
		paths = VisibilityGraph.generatePaths(convexHulls, startingPoint, endPoint);

		Graph.Edge[] graph = new Graph.Edge[paths.size()];
		graph = paths.toArray(graph);
		
		Graph g = new Graph(graph);
	    g.dijkstra(startingPoint);
	    g.printPath(endPoint);
	    
	    List<Point2D> route = new ArrayList<Point2D>();
	    route = g.getPath();
		
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledCircle(startingPoint.getX(), startingPoint.getY(), 0.001);
		StdDraw.filledCircle(endPoint.getX(), endPoint.getY(), 0.001);

		double totalDist = 0;
		
		StdDraw.setPenColor(Color.RED);
		StdDraw.setPenRadius(0.001);
	    for(int i=1; i<route.size();i++){
			StdDraw.line(route.get(i-1).getX(), route.get(i-1).getY(), route.get(i).getX(), route.get(i).getY());
			totalDist += VisibilityGraph.dist(route.get(i-1), route.get(i));
		}

		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Execution time (ms) = " + totalTime);
		System.out.println("Length of path found = " + totalDist);
	}	
}
