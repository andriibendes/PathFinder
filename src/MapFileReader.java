import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class MapFileReader {

	private ArrayList<String> lines = new ArrayList<String>();

	protected MapFileReader(String fileName) {
		try {
			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			String line;
			while ((line = bufferReader.readLine()) != null)
				lines.add(line);
			bufferReader.close();
		}
		catch(Exception e) {
			System.out.println(e.toString() + fileName);
		}
	}

	protected ArrayList<Polygon2D> parsePolygonData() {
		if (lines.size() < 7) return null;
		if (!lines.get(3).equals("POLYGON")) return null;

		ArrayList<Polygon2D> polygons = new ArrayList<Polygon2D>();
		String[] tokens;
		for (int i=3; i<lines.size(); i++) {
			tokens = lines.get(i).split(",");
			if (tokens[0].equals("END")) break;
			else if (tokens[0].equals("POLYGON"))  {
				Polygon2D pg = new Polygon2D();
				polygons.add(pg);
			}
			else {
				if (tokens.length != 2) return null;
				Polygon2D pg = polygons.get(polygons.size()-1);
				double x = Double.parseDouble(tokens[0]);
				double y = Double.parseDouble(tokens[1]);
				pg.addPoint(new Point2D(x,y));
			}
		}
		return polygons;
	}

	protected Point2D getSourcePoint() {
		if (lines.size() < 7) return null;
		if (!lines.get(0).equals("SRC-DST")) return null;
		String[] tokens = lines.get(1).split(",");
		if (tokens.length != 2) return null;
		double x = Double.parseDouble(tokens[0]);
		double y = Double.parseDouble(tokens[1]);
		return new Point2D(x,y);
	}

	protected Point2D getDestinationPoint() {
		if (lines.size() < 7) return null;
		if (!lines.get(0).equals("SRC-DST")) return null;
		String[] tokens = lines.get(2).split(",");
		if (tokens.length != 2) return null;
		double x = Double.parseDouble(tokens[0]);
		double y = Double.parseDouble(tokens[1]);
		return new Point2D(x,y);
	}
	
	protected void printFileData() {
		for (String s : lines) {
			System.out.println(s);
		}
	}
	
	protected void printPolygons() {
		ArrayList<Polygon2D> polys = parsePolygonData();
		if (polys == null) return;
		System.out.println("Number of polygons: " +  polys.size());
		for (Polygon2D pg : polys) {
			System.out.print("Polygon Points: ");
			System.out.println(pg);
		}
	}
	
	public static void main(String args[]) {

	}
}
