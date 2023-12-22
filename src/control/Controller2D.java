package control;

import model.Line;
import model.Point;
import model.Polygon;
import rasterize.*;
import view.Panel;

import javax.swing.*;
import java.awt.event.*;

public class Controller2D implements Controller {

    private final Panel panel;
    private FilledLineRasterizer filledRasterizer;

    private PolygonRasterizer polygonRasterizer;
    private Point startPoint;
    private Point endPoint;
    private Polygon polygon;


    private int x, y;

    public Controller2D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRaster());
        initListeners(panel);
    }

    public void initObjects(Raster raster) {
        filledRasterizer = new FilledLineRasterizer(raster);
        polygonRasterizer = new PolygonRasterizer(filledRasterizer);
        polygon = new Polygon();
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
                if (e.isControlDown()) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        //TODO
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        //TODO
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                panel.clear();
                if (e.isControlDown()) {

                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    polygon.addPoint(endPoint);
                }
                polygonRasterizer.rasterize(polygon);
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();
                endPoint = new Point(e.getX(), e.getY());

                if (e.isControlDown()) return;

                if (e.isShiftDown()) {
                    //TODO
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    if (polygon.getPoints().isEmpty()) {
                        Line newLine = new Line(startPoint, endPoint);
                        filledRasterizer.rasterize(newLine);
                    } else {
                        polygonRasterizer.rasterize(polygon);

                        Line lastCurrentLine = new Line(polygon.getPoints().getLast(), endPoint);
                        Line firstCurrentline = new Line(polygon.getPoints().getFirst(), endPoint);

                        filledRasterizer.rasterize(lastCurrentLine);
                        filledRasterizer.rasterize(firstCurrentline);

                    }
                    update();
                }
            }
        });

        panel.addKeyListener(new

                                     KeyAdapter() {
                                         @Override
                                         public void keyPressed(KeyEvent e) {
                                             // na klávesu C vymazat plátno
                                             if (e.getKeyCode() == KeyEvent.VK_C) {
                                                 //TODO
                                             }
                                         }
                                     });

        panel.addComponentListener(new

                                           ComponentAdapter() {
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

    private void hardClear() {
        panel.clear();
    }

}
