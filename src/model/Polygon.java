package model;

import java.util.ArrayList;

public class Polygon {

    protected ArrayList<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
    public ArrayList<Line> getLines() {
        ArrayList<Line> lineList = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            int nextIndex = (i + 1) % points.size();
            lineList.add(new Line(points.get(i), points.get(nextIndex)));
        }

        return lineList;
    }
}
