package fill;


import model.Point;
import rasterize.Raster;

import java.awt.*;

public class SeedFillBorder implements Filler {
    private final int seedFillColor = Color.BLUE.getRGB();
    private final int borderColor = Color.YELLOW.getRGB();
    private final Raster raster;
    public SeedFillBorder(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void fill() {

    }

    public void fill(Point point) {
        seedFill(point);
    }

    private void seedFill(model.Point point) {
        int pixelColor = raster.getPixel(point.getX(),point.getY());

        if (pixelColor != this.borderColor && pixelColor != this.seedFillColor) {
            raster.setPixel(point.getX(), point.getY(), seedFillColor);
            seedFill(new model.Point(point.getX()+1,point.getY()));
            seedFill(new model.Point(point.getX()-1,point.getY()));
            seedFill(new model.Point(point.getX(),point.getY()+1));
            seedFill(new Point(point.getX(),point.getY()-1));
        }
    }
}
