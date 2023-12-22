package model;

import java.awt.*;

public class Line {

    private int x1, x2, y1, y2;
    private float k, q;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Line(Point p1, Point p2) {
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
    }

    public Line(Point p1, Point p2, Color color) {
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public void changeDirection() {
        if (this.y2 < this.y1 ) {
            int tmp = this.x1;
            this.x1 = this.x2;
            this.x2 = tmp;

            tmp = this.y1;
            this.y1 = this.y2;
            this.y2 = tmp;
        }
    }

    public void calculateKQ() {
        this.k = (float) (x2 - x1) / (y2 - y1);
        this.q = x1 - k * y1;
    }

    public boolean isIntersection(int y) {
        return (y >= y1) && (y < y2);
    }

    public int getIntersection(int y) {
        return (int) (k*y + q);
    }
}
