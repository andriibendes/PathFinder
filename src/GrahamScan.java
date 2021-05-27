import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;

public class GrahamScan {

    public static void mergeSort(Comparable[] a) {
        Comparable[] tmp = new Comparable[a.length];
        mergeSort(a, tmp, 0, a.length - 1);
    }

    private static void mergeSort(Comparable[] a, Comparable[] tmp, int left, int right) //merge sort
    {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(a, tmp, left, center);
            mergeSort(a, tmp, center + 1, right);
            merge(a, tmp, left, center + 1, right);
        }
    }

    private static void merge(Comparable[] a, Comparable[] tmp, int left, int right, int rightEnd) {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while (left <= leftEnd && right <= rightEnd)
            if (a[left].compareTo(a[right]) <= 0)
                tmp[k++] = a[left++];
            else
                tmp[k++] = a[right++];

        while (left <= leftEnd)
            tmp[k++] = a[left++];

        while (right <= rightEnd)
            tmp[k++] = a[right++];

        for (int i = 0; i < num; i++, rightEnd--)
            a[rightEnd] = tmp[rightEnd];
    }

    public static Boolean pointIn(Point2D compPoint, Point2D points[][]) {
        boolean b = false;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                int k = j + 1;
                if (k == points[i].length) {
                    k = 0;
                }
                if (points[i][j].getY() < compPoint.getY() && points[i][k].getY() >= compPoint.getY() || points[i][k].getY() < compPoint.getY() && points[i][j].getY() >= compPoint.getY()) {
                    if (points[i][j].getX() + (compPoint.getY() - points[i][j].getY()) / (points[i][k].getY() - points[i][j].getY()) * (points[i][k].getX() - points[i][j].getX()) < compPoint.getX()) {
                        b = !b;
                    }
                }

            }
        }
        return b;
    }

    public static Polygon2D PreProcess(Polygon2D polygon) {
        Point2D[] points = new Point2D[1];
        points = polygon.asPointsArray();

        double maxX = points[0].getX();
        double maxY = points[0].getY();
        double minX = points[0].getX();
        double minY = points[0].getY();
        Point2D maxXp = points[0];
        Point2D maxYp = points[0];
        Point2D minXp = points[0];
        Point2D minYp = points[0];

        for (int i = 1; i < points.length; i++) {
            if (points[i].getX() > maxX) {
                maxXp = points[i];
            }
            if (points[i].getY() > maxY) {
                maxYp = points[i];
            }
            if (points[i].getX() < minX) {
                minXp = points[i];
            }
            if (points[i].getY() < minY) {
                minYp = points[i];
            }
        }

        Point2D[][] quad = new Point2D[2][2];
        quad[1][1] = maxXp;
        quad[0][1] = maxYp;
        quad[1][0] = minXp;
        quad[0][0] = minYp;

        List<Point2D> p = new ArrayList<Point2D>();
        for (int i = 0; i < points.length; i++) {
            if (!pointIn(points[i], quad)) {
                p.add(points[i]);
            }
        }
        if (!p.contains(minXp)) {
            p.add(maxXp);
        }
        if (!p.contains(minXp)) {
            p.add(maxYp);
        }
        if (!p.contains(minXp)) {
            p.add(minXp);
        }
        if (!p.contains(minXp)) {
            p.add(minYp);
        }

        Point2D[] newPoints = new Point2D[p.size()];
        newPoints = p.toArray(newPoints);
        polygon = new Polygon2D(newPoints);

        return polygon;
    }

    public static Polygon2D findConvexHull(Polygon2D polygon) {
        Point2D[] points = new Point2D[1];
        points = polygon.asPointsArray();
        Point2D lowest = lowestPoint(points);
        Vector[] vecs = new Vector[points.length];
        for (int i = 0; i < points.length; i++) {
            vecs[i] = new Vector(lowest, points[i]);
        }

        mergeSort(vecs);

        HullStack<Point2D> hull = new HullStack<Point2D>();
        hull.push(vecs[0].getPoint());
        hull.push(vecs[1].getPoint());
        for (int i = 2; i < points.length; i++) {
            while (Point2D.turningDirection(hull.sneeky_peek(), hull.peek(), vecs[i].getPoint()) == -1) {
                hull.pop();
            }
            hull.push(vecs[i].getPoint());
        }
        int size = hull.size();
        Point2D[] hullPoints = new Point2D[size];
        for (int i = 0; i < size; i++) {
            hullPoints[i] = hull.pop();
        }

        Vector[] testArray = new Vector[hullPoints.length];
        for (int i = 0; i < hullPoints.length; i++) {
            testArray[i] = new Vector(hullPoints[i], hullPoints[(i + 1) % (hullPoints.length)]);
        }
        StdDraw.setPenColor(Color.RED);

        Point2D[] test = Vector.removeColinear(testArray);

        Point2D[] h = new Point2D[test.length];

        for (int i = 0; i < test.length; i++) {
            h[i] = test[i];
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledCircle(h[i].getX(), h[i].getY(), 0.004);
        }
        polygon = new Polygon2D(test);

        return polygon;
    }


    public static Point2D lowestPoint(Point2D[] points) {
        Point2D lowest = points[0];
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].getY() <= lowest.getY()) {
                if (points[i].getY() == lowest.getY() && points[i].getX() < lowest.getX()) {
                    lowest = points[i];
                } else if (points[i].getY() != lowest.getY()) {
                    lowest = points[i];
                }
            }
        }
        swapLowest(points, lowest);
        return lowest;
    }

    public static void swapLowest(Point2D[] points, Point2D lowest) {
        Point2D temp = new Point2D(0, 0);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == lowest) {
                temp = points[0];
                points[0] = lowest;
                points[i] = temp;
            }
        }

    }

    public static void main(String args[]) {
    }

}