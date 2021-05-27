import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisibilityGraph {

	public static Boolean lineOfSight(Vector v1, Vector v2) {
		double denominator = ((v1.getP1().getX() - v1.getP0().getX()) * (v2.getP1().getY() - v2.getP0().getY())) - ((v1.getP1().getY() - v1.getP0().getY()) * (v2.getP1().getX() - v2.getP0().getX()));
		if (denominator == 0) {
			return false;
		}
		double numerator1 = ((v1.getP0().getY() - v2.getP0().getY()) * (v2.getP1().getX() - v2.getP0().getX())) - ((v1.getP0().getX() - v2.getP0().getX()) * (v2.getP1().getY() - v2.getP0().getY()));
		double numerator2 = ((v1.getP0().getY() - v2.getP0().getY()) * (v1.getP1().getX() - v1.getP0().getX())) - ((v1.getP0().getX() - v2.getP0().getX()) * (v1.getP1().getY() - v1.getP0().getY()));
		if (numerator1 == 0 || numerator2 == 0) {
			return false;
		}
		double r = numerator1 / denominator;
		double s = numerator2 / denominator;
		return (r > 0 && r < 1) && (s > 0 && s < 1);
	}

	public static List<Vector> getEdges(Point2D[][] convexHulls) {
		List<Vector> edges = new ArrayList<Vector>();
		for (int i = 0; i < convexHulls.length; i++) {
			int k = 1;
			for (int j = 0; j < convexHulls[i].length; j++) {
				if (k == convexHulls[i].length) {
					k = 0;
				}
				edges.add(new Vector(convexHulls[i][j], convexHulls[i][k]));
				k++;
			}
		}
		return edges;
	}

	public static double dist(Point2D p1, Point2D p2) {
		double distance = Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
		return distance;
	}

	public static int indexOfArray(Point2D array[], Point2D key) {
		int returnvalue = -1;
		for (int i = 0; i < array.length; ++i) {
			if (key == array[i]) {
				returnvalue = i;
				break;
			}
		}
		return returnvalue;
	}

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public static List<Graph.Edge> generatePaths(Point2D[][] convexHulls, Point2D startingPoint, Point2D endPoint) {
		List<Vector> edges = getEdges(convexHulls);
		List<Vector> lines = new ArrayList<Vector>();
		List<Graph.Edge> paths = new ArrayList<Graph.Edge>();

		int s = 0;
		int intersections = 0;

		for (int i = 0; i < convexHulls.length; i++) {
			for (int j = 0; j < convexHulls[i].length; j++) {
				for (int k = 0; k < convexHulls.length; k++) {
					for (int l = 0; l < convexHulls[k].length; l++) {
						if (s == 0) {
							intersections = 0;
							lines.add(new Vector(startingPoint, convexHulls[k][l]));
							for (int e = 0; e < edges.size(); e++) {
								if (lineOfSight(lines.get(lines.size() - 1), edges.get(e)) == true)
									intersections++;
							}
							if (intersections < 1) {
								paths.add(new Graph.Edge(startingPoint, lines.get(lines.size() - 1).getP1(), dist(startingPoint, lines.get(lines.size() - 1).getP1())));
							}
						}
						if (i != k && s != 0) {
							intersections = 0;
							lines.add(new Vector(convexHulls[i][j], convexHulls[k][l]));
							for (int e = 0; e < edges.size(); e++) {
								if (lineOfSight(lines.get(lines.size() - 1), edges.get(e)) == true)
									intersections++;
							}
							if (intersections < 1) {
								paths.add(new Graph.Edge(lines.get(lines.size() - 1).getP0(), lines.get(lines.size() - 1).getP1(), dist(lines.get(lines.size() - 1).getP0(), lines.get(lines.size() - 1).getP1())));
							}
						}
					}
				}
				s++;
			}
		}
		for (int i = 0; i < convexHulls.length; i++) {
			for (int j = 0; j < convexHulls[i].length; j++) {
				intersections = 0;
				lines.add(new Vector(endPoint, convexHulls[i][j]));
				for (int e = 0; e < edges.size(); e++) {
					if (lineOfSight(lines.get(lines.size() - 1), edges.get(e)) == true)
						intersections++;
				}
				if (intersections < 1) {
					paths.add(new Graph.Edge(lines.get(lines.size() - 1).getP0(), lines.get(lines.size() - 1).getP1(), dist(lines.get(lines.size() - 1).getP0(), lines.get(lines.size() - 1).getP1())));
				}

			}
		}

		for (int i = 0; i < edges.size(); i++) {
			paths.add(new Graph.Edge(edges.get(i).getP0(), edges.get(i).getP1(), dist(edges.get(i).getP0(), edges.get(i).getP1())));
		}

		return paths;
	}

	public static void main(String args[]) {

	}
}