package rasterize;

import java.awt.*;

public class DottedLineRasterizer extends LineRasterizer {
    public DottedLineRasterizer(Raster raster) {
        super(raster);
        this.color = Color.RED;
    }

    @Override
    protected void drawLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;
        int e2;

        int dotCounter = 0;

        while (true) {
            if (dotCounter == 2) {
                raster.setPixel(x1, y1, this.color.getRGB());
                dotCounter = 0;
            } else {
                dotCounter++;
            }

            if (x1 == x2 && y1 == y2) {
                break;
            }

            e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }

        }
    }
}
