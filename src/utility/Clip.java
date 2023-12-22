package utility;

import model.Point;
import model.Polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Clip {
    public Clip() {
    }

    public static Polygon clipPolygon(Polygon polygon, Polygon convexPolygon) {
        ArrayList<Point> inputList = new ArrayList<>(polygon.getPoints());
        ArrayList<Point> convexList = new ArrayList<>(convexPolygon.getPoints());

        if (isClockwise(inputList)) {
            Collections.reverse(inputList);
        }

        if (isClockwise(convexList)) {
            Collections.reverse(convexList);
        }

        ArrayList<Point> finalList = new ArrayList<>(inputList);

        int convexListLen = convexList.size();
        for (int i = 0; i < convexListLen; i++) {

            ArrayList<Point> nextList = new ArrayList<>(finalList);

            int len2 = finalList.size();
            finalList = new ArrayList<>(len2);

            Point a = convexList.get((i + convexListLen - 1) % convexListLen);
            Point b = convexList.get(i);

            for (int j = 0; j < len2; j++) {
                Point p = nextList.get((j + len2 - 1) % len2);
                Point q = nextList.get(j);

                if (isInside(a, b, q)) {
                    if (!isInside(a, b, p)) {
                        Point intersection = intersection(a, b, p, q);
                        finalList.add(intersection);
                    }
                    finalList.add(q);
                } else if (isInside(a, b, p))
                    finalList.add(intersection(a, b, p, q));
            }
        }

        Polygon finalPolygon = new Polygon();

        for (Point point : finalList) {
            finalPolygon.addPoint(point);
        }

        return finalPolygon;

    }

    private static boolean isInside(Point a, Point b, Point c) {
        return (a.getX() - c.getX()) * (b.getY() - c.getY()) > (a.getY() - c.getY()) * (b.getX() - c.getX());
    }

    private static Point intersection(Point a, Point b, Point p, Point q) {
        double A1 = b.getY() - a.getY();
        double B1 = a.getX() - b.getX();
        double C1 = A1 * a.getX() + B1 * a.getY();

        double A2 = q.getY() - p.getY();
        double B2 = p.getX() - q.getX();
        double C2 = A2 * p.getX() + B2 * p.getY();

        double det = A1 * B2 - A2 * B1;
        double x = (B2 * C1 - B1 * C2) / det;
        double y = (A1 * C2 - A2 * C1) / det;

        return new Point(x, y);

    }

    public static boolean isClockwise(ArrayList<Point> points) {
        if (points.size() < 3) {
            return true;
        }
        return isClockwiseOrder(points);
    }

    private static boolean isClockwiseOrder(List<Point> points) {
        int n = points.size();
        for (int i = 0; i < n - 2; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            Point p3 = points.get(i + 2);

            int crossProduct = (p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) -
                    (p2.getY() - p1.getY()) * (p3.getX() - p1.getX());

            if (crossProduct > 0) {
                return false;
            }
        }
        return true;
    }
}