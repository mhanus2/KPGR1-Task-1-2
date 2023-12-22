package rasterize;

import java.awt.*;

public class FilledLineRasterizer extends LineRasterizer {
    public FilledLineRasterizer(Raster raster) {
        super(raster);
        this.color = Color.YELLOW;
    }

    /*
    Bresenhamův algoritmus
    ----------------------
    Výhody:
        Rychlý inkrementální algoritmus.
        Používá pouze celočíselné výpočty.
    Nevýhody:
        Je určen pro základní čárový výkres.
        Anti-aliasing není součástí Bresenhamova algoritmu,
        takže pro kreslení hladkých čar by bylo třeba hledat jiný algoritmus.
     */
    @Override
    protected void drawLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) {
            raster.setPixel(x1, y1, this.color.getRGB());

            if (x1 == x2 && y1 == y2)
                break;

            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
    }
}
