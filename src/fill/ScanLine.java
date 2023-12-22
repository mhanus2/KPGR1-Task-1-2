package fill;

import model.Line;
import model.Polygon;
import rasterize.Raster;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class ScanLine implements Filler {
    private final Raster raster;
    private Polygon polygon;
    private final ArrayList<Line> pLines = new ArrayList<>();
    private final ArrayList<Integer> intersections = new ArrayList<>();
    private int yMin;
    private int yMax;
    private final int color = Color.BLUE.getRGB();

    public ScanLine(Raster raster) {
        this.raster = raster;
    }

    public void fill(Polygon polygon) {
        this.polygon = polygon;
        getPLines();

        for (int y = yMin; y <= yMax; y++) {
            intersections.clear();
            for (Line line : pLines) {
                if (line.isIntersection(y)) {
                    intersections.add(line.getIntersection(y));
                }
            }

            Collections.sort(intersections);

            int i = 0;

            while (i<intersections.size() -1) {
                int x1 = intersections.get(i);
                int x2 = intersections.get(i+1);

                for (int x = x1; x <= x2; x++) {
                    raster.setPixel(x, y, this.color);
                }

                i += 2;
            }
        }
    }

    private void getPLines() {
        ArrayList<Line> lines = this.polygon.getLines();

        for (Line line: lines) {
            if (line.getY1() != line.getY2()) {
                Line pLine = new Line(line.getX1(), line.getY1(), line.getX2(), line.getY2());

                pLine.changeDirection();
                pLine.calculateKQ();

                pLines.add(pLine);

                if (yMin > pLine.getY1()) yMin = pLine.getY1();
                if (yMax < pLine.getY2()) yMax = pLine.getY2();

            }
        }
    }

    @Override
    public void fill() {

    }
}
