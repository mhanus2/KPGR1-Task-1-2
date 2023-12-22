package rasterize;

import model.Line;
import model.Point;

import java.awt.*;

public abstract class LineRasterizer {
    Raster raster;
    Color color;

    public LineRasterizer(Raster raster) {
        this.raster = raster;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int color) {
        this.color = new Color(color);
    }

    public void rasterize(Line line) {
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public void rasterize(Line line, Color color) {
        this.color = color;
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public void rasterize(Point pointA, Point pointB) {
        int x1 = pointA.getX();
        int y1 = pointA.getY();
        int x2 = pointB.getX();
        int y2 = pointB.getY();
        drawLine(x1, y1, x2, y2);
    }

    public void rasterize(Point pointA, Point pointB, Color color) {
        this.color = color;
        int x1 = pointA.getX();
        int y1 = pointA.getY();
        int x2 = pointB.getX();
        int y2 = pointB.getY();
        drawLine(x1, y1, x2, y2);
    }

    public void rasterize(int x1, int y1, int x2, int y2) {
        drawLine(x1, y1, x2, y2);
    }

    public void rasterize(int x1, int y1, int x2, int y2, Color color) {
        this.color = color;
        drawLine(x1, y1, x2, y2);
    }

    protected void drawLine(int x1, int y1, int x2, int y2) {

    }
}
