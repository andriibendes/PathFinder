import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdDraw;

public class ShapeMap implements Iterable<Polygon2D> {

	private Point2D srcPoint;
	private Point2D destPoint;
	private ArrayList<Polygon2D> polygons;

	ShapeMap() {
		polygons = new ArrayList<Polygon2D>();
	}
	
	ShapeMap(Point2D src, Point2D dest) {
		polygons = new ArrayList<Polygon2D>();
		srcPoint = new Point2D(src);
		destPoint = new Point2D(dest);
	}
	
	ShapeMap(ArrayList<Polygon2D> pgs, Point2D src, Point2D dest) {
		polygons = new ArrayList<Polygon2D>();
		for (int i=0; i<pgs.size(); i++) {
			polygons.add( new Polygon2D(pgs.get(i)) );
		}
		srcPoint = new Point2D(src);
		destPoint = new Point2D(dest);
	}
	
	ShapeMap(String fileName) {
		MapFileReader mapReader = new MapFileReader(fileName);
		srcPoint = mapReader.getSourcePoint();
		destPoint = mapReader.getDestinationPoint();
		polygons = mapReader.parsePolygonData();
	}

	public void addPolygon(Polygon2D pg) {
		polygons.add(pg);
	}
	
	public int amountofPolys() {
		return polygons.size();
	}
	
	public void addPolygon(Point2D[] points) {
		Polygon2D pg = new Polygon2D();
		for (int i=0; i<points.length; i++) {
			pg.addPoint(points[i]);
		}
		addPolygon(pg);
	}

	public Point2D sourcePoint() {
		return srcPoint;
	}
	
	public Point2D destinationPoint () {
		return destPoint;
	}
	
	@Override
	public Iterator<Polygon2D> iterator() {
		Iterator<Polygon2D> it = new Iterator<Polygon2D>() {
			private int currentIndex = 0;
			@Override
			public boolean hasNext() { return (currentIndex < polygons.size()); }
			@Override
			public Polygon2D next() { return polygons.get(currentIndex++); }
			@Override
			public void remove() { throw new UnsupportedOperationException(); }
		};
		return it;
	}
	
	public void draw() {
		for (int i=0; i<polygons.size(); i++) {
			polygons.get(i).draw();
		}
	}
	
	public void drawFilled() {
		for (int i=0; i<polygons.size(); i++) {
			polygons.get(i).drawFilled();
		}
	}

	public static void main(String args[]) {

	}

}
