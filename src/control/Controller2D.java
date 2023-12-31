package control;

import utility.Clip;
import fill.ScanLine;
import fill.SeedFill;
import fill.SeedFillBorder;
import model.*;
import rasterize.DottedLineRasterizer;
import rasterize.FilledLineRasterizer;
import rasterize.PolygonRasterizer;
import rasterize.Raster;
import view.Panel;

import javax.swing.*;
import java.awt.event.*;

public class Controller2D implements Controller {

    private final Panel panel;
    private DottedLineRasterizer dottedRasterizer;
    private PolygonRasterizer polygonRasterizer;
    private SeedFill seedFill;
    private SeedFillBorder seedFillBorder;
    private ScanLine scanLine;
    private Clip clip;
    private Point startPoint;
    private Point endPoint;
    private Polygon polygon;
    private Polygon convexPolygon;
    private Rectangle rectangle;
    private Ellipse ellipse;


    public Controller2D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRaster());
        initListeners(panel);
    }

    public void initObjects(Raster raster) {
        FilledLineRasterizer filledRasterizer = new FilledLineRasterizer(raster);
        dottedRasterizer = new DottedLineRasterizer(raster);
        polygonRasterizer = new PolygonRasterizer(filledRasterizer);
        seedFill = new SeedFill(raster);
        seedFillBorder = new SeedFillBorder(raster);
        scanLine = new ScanLine(raster);
        clip = new Clip();
        polygon = new Polygon();
        convexPolygon = new Polygon();
        rectangle = new Rectangle();
    }

    @Override
    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = new Point(e.getX(), e.getY());
                endPoint = startPoint;

                if (polygon.getPoints().isEmpty()) {
                    polygon.addPoint(startPoint);
                }

                if (e.isControlDown()) return;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isMiddleMouseButton(e)) {
                    seedFillBorder.fill(endPoint);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    seedFill.fill(endPoint);
                } else if (e.isShiftDown()) {
                    if (rectangle.getPoints().size() >= 2) {
                        polygonRasterizer.rasterize(rectangle);
                    } else {
                        rectangle.addPoint(startPoint);
                        if (rectangle.getPoints().size() >= 2) {
                            polygonRasterizer.rasterize(rectangle);
                            ellipse = new Ellipse(rectangle);
                            polygonRasterizer.rasterize(ellipse);
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                panel.clear();
                if (e.isControlDown()) {
                    convexPolygon.addPoint(endPoint);
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    polygon.addPoint(endPoint);
                }
                polygonRasterizer.rasterize(polygon);
                polygonRasterizer.rasterize(convexPolygon);
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();
                endPoint = new Point(e.getX(), e.getY());

                if (e.isControlDown()) return;

                if (e.isShiftDown()) {
                    Point lastPoint;
                    if (polygon.getPoints().isEmpty()) {
                        lastPoint = startPoint;
                    } else {
                        lastPoint = polygon.getPoints().getLast();
                    }

                    int dx = Math.abs(endPoint.getX() - lastPoint.getX());
                    int dy = Math.abs(endPoint.getY() - lastPoint.getY());

                    if (dx > dy) {
                        if (dx / 2 < dy) {
                            // Úhlopříčná úsečka
                            endPoint.setX(lastPoint.getX() + (endPoint.getX() > lastPoint.getX() ? dy : -dy));
                            endPoint.setY(lastPoint.getY() + (endPoint.getY() > lastPoint.getY() ? dy : -dy));
                        } else {
                            // Vodorovná úsečka
                            endPoint.setY(lastPoint.getY());
                        }
                    } else if (dy > dx) {
                        if (dy / 2 < dx) {
                            // Úhlopříčná úsečka
                            endPoint.setX(lastPoint.getX() + (endPoint.getX() > lastPoint.getX() ? dx : -dx));
                            endPoint.setY(lastPoint.getY() + (endPoint.getY() > lastPoint.getY() ? dx : -dx));
                        } else {
                            // Vodorovná úsečka
                            endPoint.setX(lastPoint.getX());
                        }
                    } else {
                        // Úhlopříčná úsečka
                        endPoint.setX(lastPoint.getX() + (endPoint.getX() > lastPoint.getX() ? dx : -dx));
                        endPoint.setY(lastPoint.getY() + (endPoint.getY() > lastPoint.getY() ? dy : -dy));
                    }

                    if (!polygon.getPoints().isEmpty()) {
                        polygonRasterizer.rasterize(polygon);
                        Line firstToCurrentline = new Line(polygon.getPoints().getFirst(), endPoint);
                        dottedRasterizer.rasterize(firstToCurrentline);
                    }
                    dottedRasterizer.rasterize(new Line(lastPoint, endPoint));


                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    if (polygon.getPoints().isEmpty()) {
                        Line newLine = new Line(startPoint, endPoint);
                        dottedRasterizer.rasterize(newLine);
                    } else {
                        polygonRasterizer.rasterize(polygon);

                        Line lastCurrentLine = new Line(polygon.getPoints().getLast(), endPoint);
                        Line firstCurrentline = new Line(polygon.getPoints().getFirst(), endPoint);

                        dottedRasterizer.rasterize(lastCurrentLine);
                        dottedRasterizer.rasterize(firstCurrentline);
                    }
                }
                update();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_C:
                        panel.clear();
                        polygon = new Polygon();
                        convexPolygon = new Polygon();
                        rectangle = new Rectangle();
                        break;
                    case KeyEvent.VK_X:
                        panel.clear();
                        polygon = clip.clipPolygon(polygon, convexPolygon);
                        convexPolygon = new Polygon();
                        scanLine.fill(polygon);
                        polygonRasterizer.rasterize(polygon);
                        panel.repaint();
                        break;
                }
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize();
                initObjects(panel.getRaster());
            }
        });
    }

    private void update() {
        panel.repaint();
    }
}
