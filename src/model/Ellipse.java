package model;

public class Ellipse extends Polygon {

    public Ellipse(Rectangle rectangle) {
        calculateEllipsePoints(rectangle);
    }

    private void calculateEllipsePoints(Rectangle rectangle) {
        Point center = rectangle.getCenter();
        double semiMajorAxis = rectangle.getWidth() / 2.0;
        double semiMinorAxis = rectangle.getHeight() / 2.0;

        int numPoints = 360;

        for (int i = 0; i < numPoints; i++) {
            double theta = 2.0 * Math.PI * i / numPoints;

            int x = (int) (center.getX() + semiMajorAxis * Math.cos(theta));
            int y = (int) (center.getY() + semiMinorAxis * Math.sin(theta));

            addPoint(x, y);
        }
    }
}
