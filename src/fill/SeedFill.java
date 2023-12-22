package fill;

import model.Point;
import rasterize.Raster;

import java.awt.*;

public class SeedFill implements Filler {
    private final int seedFillColor = Color.GREEN.getRGB();
    private int backgroundColor;
    private final Raster raster;

    public SeedFill(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void fill() {

    }

    public void fill(Point point) {
        this.backgroundColor = raster.getPixel(point.getX(), point.getY());
        seedFill(point);
    }

    private void seedFill(Point point) {
        int pixelColor = raster.getPixel(point.getX(), point.getY());

        if (pixelColor == this.backgroundColor) {
            raster.setPixel(point.getX(), point.getY(), seedFillColor);
            seedFill(new Point(point.getX() + 1, point.getY()));
            seedFill(new Point(point.getX() - 1, point.getY()));
            seedFill(new Point(point.getX(), point.getY() + 1));
            seedFill(new Point(point.getX(), point.getY() - 1));
        }
    }
}
