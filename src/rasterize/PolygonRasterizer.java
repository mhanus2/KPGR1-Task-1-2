package rasterize;

import model.Line;
import model.Point;
import model.Polygon;

import java.util.List;

public class PolygonRasterizer {

    private final FilledLineRasterizer lineRasterizer;

    public PolygonRasterizer(FilledLineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void rasterize(Polygon polygon) {
        List<Point> points = polygon.getPoints();

        for (int i = 0; i < points.size(); i++) {
            int nextIndex = (i + 1) % points.size();
            Line line = new Line(points.get(i), points.get(nextIndex));
            lineRasterizer.rasterize(line);
        }
    }

}
