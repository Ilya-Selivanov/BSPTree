package bspTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class MyComponent extends JComponent {
    private Line2D line = new Line2D.Double();
    private ArrayList<Polygon> polygons = new ArrayList<>();
    private Line2D currentShape = null;
    private BSPTree bspTree;
    private BspTreeDrawer treeDrawer;

    MyComponent() {
        bspTree = new BSPTree();
        int[] x = {50, 400, 400, 50};
        int[] y = {50, 50, 400, 400};
        Polygon polygon = new Polygon(x, y, 4);
        bspTree.createPlane(polygon);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                currentShape = new Line2D.Double(e.getPoint(), e.getPoint());
                line = currentShape;
                repaint();
            }

            public void mouseDragged(MouseEvent e) {
                Line2D shape = (Line2D) currentShape;
                shape.setLine(shape.getP1(), e.getPoint());
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                currentShape = null;
                bspTree.splitPlace(line);
                bspTree.leaf();
                System.out.println(bspTree.intersectsLineRectangle(polygon,line));
                repaint();
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        treeDrawer = new BspTreeDrawer(bspTree, g2d);
        treeDrawer.draw(bspTree.getRoot(), 725, 50, 125, 30, 30);
        bspTree.drawTree(g2d);
        g2d.setColor(Color.RED);
        g2d.draw(line);
    }
}

