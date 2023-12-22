package model;

import java.util.ArrayList;

public class Rectangle extends Polygon {
    public Rectangle() {

    }

    @Override
    public void addPoint(Point point) {
        super.addPoint(point);
        if (this.getPoints().size() == 2) {
            calculatePoints();
        }

    }

    private void calculatePoints() {
        ArrayList<Point> newPoints = new ArrayList<Point>();
        Point pointA = this.points.getFirst();
        Point pointC = this.points.getLast();
        Point pointB = new Point(pointA.getX(), pointC.getY());
        Point pointD = new Point(pointC.getX(), pointA.getY());

        newPoints.add(pointA);
        newPoints.add(pointB);
        newPoints.add(pointC);
        newPoints.add(pointD);

        this.points = newPoints;
    }

    public Point getCenter() {
        int sumX = 0;
        int sumY = 0;

        for (Point point : points) {
            sumX += point.getX();
            sumY += point.getY();
        }

        int centerX = sumX / points.size();
        int centerY = sumY / points.size();

        return new Point(centerX, centerY);
    }

    public int getWidth() {
        Point firstPoint = points.get(0);
        Point secondPoint = points.get(2);

        return Math.abs(secondPoint.getX() - firstPoint.getX());
    }

    public int getHeight() {
        Point firstPoint = points.get(0);
        Point secondPoint = points.get(2);

        return Math.abs(secondPoint.getY() - firstPoint.getY());
    }
}
