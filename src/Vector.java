import java.util.ArrayList;
import java.util.List;

public class Vector implements Comparable<Vector> {
 private final Point2D p0;
 private final Point2D p1;

 public Vector(Point2D p0, Point2D p1) {
  this.p0 = p0;
  this.p1 = p1;
 }

 public Point2D getPoint() {
  return p1;
 }

 public Point2D getP0() {
  return p0;
 }

 public Point2D getP1() {
  return p1;
 }

 public static Point2D[] removeColinear(Vector[] v) {
  List<Point2D> hullPoints = new ArrayList<Point2D>();
  for (int i = 0; i < v.length; i++) {
   double area = v[i].calcArea(v[(i + 1) % (v.length)]);
   if (area < 0.0001) {
   } else {
    hullPoints.add(v[i].getP1());
   }
  }

  Point2D[] hull = new Point2D[hullPoints.size()];
  hull = hullPoints.toArray(hull);
  return hull;
 }

 public double calcArea(Vector B) {
  Vector A = this;
  return A.p0.getX() * (A.p1.getY() - B.p1.getY()) + A.p1.getX() * (B.p1.getY() - A.p0.getY()) + B.p1.getX() * (A.p0.getY() - A.p1.getY());

 }

 public int compareTo(Vector B) {

  Vector A = this;
  if (A == B) return 0;
  double a1 = (A.p1.getX() - A.p0.getX());
  double a2 = (B.p1.getX() - A.p0.getX());
  double b1 = (A.p1.getY() - B.p0.getY());
  double b2 = (B.p1.getY() - B.p0.getY());
  double cross = (a1 * b2) - (b1 * a2);

  if (cross == 0) return 0;
  else if (cross > 0) return 1;
  else if (cross < 0) return -1;
  return 0;
 }

 @Override
 public String toString() {
  return ("\nEdge Point 1: (" + p0.getX() + "," + p0.getY() + ")" + "\tEdge Point 2: (" + p1.getX() + "," + p1.getY() + ")");
 }

 public static void main(String args[]) {
 }
}

