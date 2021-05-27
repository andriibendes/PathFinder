import java.util.*;
public class Dijkstra {
	
   public static void main(String[] args) {
   }
}
 
class Graph {
   private final Map<Point2D, Vertex> graph;
   
      public static class Edge {
      public final Point2D v1;
      public final Point2D v2;
      public final double dist;
      public Edge(Point2D v1, Point2D v2, double dist) {
         this.v1 = v1;
         this.v2 = v2;
         this.dist = dist;
      }
      public String toString() { return ("(" + v1 + "," + v2 + ")\n");}
      public Point2D getp0(){ //getters for points
    	  return v1;
      }
      public Point2D getp1(){
    	  return v2;
      }

   }
 
  public static class Vertex implements Comparable<Vertex>{
	public final Point2D name;
	public double dist = Double.MAX_VALUE;
	public Vertex previous = null; 
	public final Map<Vertex, Double> neighbours = new HashMap<>();
 
	public Vertex(Point2D name)
	{
		this.name = name;
	}

	private void printPath(List<Point2D> path)
	{
		if (this == this.previous)
		{
			System.out.print(this.name); 
			path.add(this.name);
		}
		else if (this.previous == null)
		{
			System.out.print(this.name + "(unreached)");
		}
		else
		{
			path.add(this.name);
			this.previous.printPath(path);
			System.out.print(" -> " + this.name);

		}
	}
	
	public Point2D getPoint(){ //getter
		return name;
	}
	
	public double getDist(){ //getter
		return dist;
	}
 
	public int compareTo(Vertex other)
	{
		if (dist == other.dist)
			return name.compareTo(other.name);
 
		return Double.compare(dist, other.dist);
	}
 
	@Override public String toString()
	{
		return "(" + name + ", " + dist + ")";
	}
}
 
   public Graph(Edge[] edges) { //edges array
      graph = new HashMap<>(edges.length);
 
      for (Edge e : edges) {
         if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
         if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
      }
 
      for (Edge e : edges) {
         graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
         graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); 
      }
   }
 
   public void dijkstra(Point2D startName) {
      final Vertex source = graph.get(startName);
      NavigableSet<Vertex> q = new TreeSet<>();
 
      for (Vertex v : graph.values()) {
         v.previous = v == source ? source : null;
         v.dist = v == source ? 0 : Double.MAX_VALUE;
         q.add(v);
      }
      dijkstra(q); 
   }
 
   private void dijkstra(final NavigableSet<Vertex> q) {      
      Vertex u, v;
      while (!q.isEmpty()) {
 
         u = q.pollFirst();
         if (u.dist == Double.MAX_VALUE) break; 
         
         for (Map.Entry<Vertex, Double> a : u.neighbours.entrySet()) {
            v = a.getKey(); 
 
            final double alternateDist = u.dist + a.getValue();
            if (alternateDist < v.dist) {
               q.remove(v);
               v.dist = alternateDist;
               v.previous = u;
               q.add(v);
            } 
         }
      } 
   }
   List<Point2D> path = new ArrayList<Point2D>();
   List<Point2D> routes = new ArrayList<Point2D>();
   
   public void printPath(Point2D endName) {
      graph.get(endName).printPath(path);
      System.out.println();
   }

   public List<Point2D> getPath(){
	   return path;
   }

   public List<Point2D> getRoutes() {
      for (Vertex v : graph.values()) {
         routes.add(v.getPoint());
      }
      return routes;
   }
}