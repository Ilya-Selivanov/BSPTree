package bspTree;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BSPTree {
    private Node root = null;


    class Node {
        Polygon polygon;
        Node right;
        Node left;
        Color color;
        private int red;
        private int green;
        private int blue;

        Node(Polygon polygon) {
            this.polygon = polygon;
            red = (int) (Math.random() * 200 + 55);
            green = (int) (Math.random() * 200 + 55);
            blue = (int) (Math.random() * 200 + 55);
            color = new Color(red, green, blue);
            left = null;
            right = null;
        }

        public void addChildren(Node left, Node right) {
            this.left = left;
            this.right = right;
        }
    }

    public void createPlane(Polygon polygon) {
        root = new Node(polygon);
    }

    public void splitPlace(Line2D line) {
        if (root.left == null && root.right == null) split(root, line);
        else splitLeafOfTree(root, line);
    }

    public void leaf() {
        quelqueNode(root);
    }

    public void quelqueNode(Node node) {
        if (node == null) return;
        quelqueNode(node.left);
        quelqueNode(node.right);
        System.out.println(node.polygon);
    }

    private void splitLeafOfTree(Node node, Line2D line) {
        if (node == null) return;
        splitLeafOfTree(node.left, line);
        splitLeafOfTree(node.right, line);
        if (node.left == null && node.right == null) split(node, line);
    }

    public void drawTree(Graphics2D g2d) {
        drawNode(root, g2d);
    }

    private void drawNode(Node node, Graphics2D g2d) {
        if (node == null) return;
        drawNode(node.left, g2d);
        drawNode(node.right, g2d);
        if (node.left == null && node.right == null) {
            Color color = node.color;
            g2d.setPaint(color);
            g2d.fill(node.polygon);
        }
    }

    public void split(Node node, Line2D line) {
        if (intersectsLineRectangle(node.polygon, line)) {
            ArrayList<Point2D> pointsForBuilding = splitPlaneOnPoint(node.polygon, line);
            ArrayList<Point2D> pointFront = getPointFront(pointsForBuilding, line);
            ArrayList<Point2D> pointBack = getPointBack(pointsForBuilding, line);
            System.out.println("=================================");
            for (Point2D point2D : pointFront) {
                System.out.println(point2D.getX() + "-" + point2D.getY());
            }
            System.out.println("---------------------------");
            for (Point2D point2D : pointBack) {
                System.out.println(point2D.getX() + "-" + point2D.getY());
            }
            Node left = new Node(new Polygon(getPointXFromList(pointFront), getPointYFromList(pointFront), pointFront.size()));
            Node right = new Node(new Polygon(getPointXFromList(pointBack), getPointYFromList(pointBack), pointBack.size()));
            node.addChildren(left, right);
        }
    }

    private ArrayList<Point2D> splitPlaneOnPoint(Polygon polygon, Line2D line) {
        ArrayList<Point2D> points = getPointFromPolygon(polygon);
        ArrayList<Line2D> lines = getLineFromPoint(points);
        for (Point2D point2D : points) {
            System.out.println(point2D.getX() + "-" + point2D.getY());
        }
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).intersectsLine(line)) {
                for (int j = 0; j < points.size(); j++) {
                    if (points.get(j).equals(lines.get(i).getP1()))
                        points.add(j + 1, intersectionTwoLine(lines.get(i), line));
                }
            }
        }
        System.out.println("---------------------------");
        for (Point2D point2D : points) {
            System.out.println(point2D.getX() + "-" + point2D.getY());
        }
        return points;
    }

    private ArrayList<Point2D> getPointFront(ArrayList<Point2D> points, Line2D line) {
        ArrayList<Point2D> pointFront = new ArrayList<>();
        double A = line.getY1() - line.getY2();
        double B = line.getX2() - line.getX1();
        double C = line.getX1() * line.getY2() - line.getX2() * line.getY1();
        for (Point2D p : points) {
            if ((int) (A * p.getX() + B * p.getY() + C) >= 0) pointFront.add(p);
        }
        return pointFront;
    }

    private ArrayList<Point2D> getPointBack(ArrayList<Point2D> points, Line2D line) {
        ArrayList<Point2D> pointFront = new ArrayList<>();
        double A = line.getY1() - line.getY2();
        double B = line.getX2() - line.getX1();
        double C = line.getX1() * line.getY2() - line.getX2() * line.getY1();
        for (Point2D p : points) {
            if ((int) (A * p.getX() + B * p.getY() + C) <= 0) {
                System.out.println("Добавляю точку в массив. ");
                pointFront.add(p);
            }
        }
        return pointFront;
    }

    private int[] getPointXFromList(ArrayList<Point2D> arrayList) {
        int[] pointX = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            pointX[i] = (int) arrayList.get(i).getX();
        }
        return pointX;
    }

    private int[] getPointYFromList(ArrayList<Point2D> arrayList) {
        int[] pointY = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            pointY[i] = (int) arrayList.get(i).getY();
        }
        return pointY;
    }

    public boolean intersectsLineRectangle(Polygon polygon, Line2D line) {
        ArrayList<Point2D> points = getPointFromPolygon(polygon);
        ArrayList<Line2D> lines = getLineFromPoint(points);
        System.out.println(lines.size());
        boolean bool = false;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                if (lines.get(i).intersectsLine(line) && lines.get(j).intersectsLine(line) && j != i) bool = true;
            }
        }
        return bool;
    }


    private ArrayList<Point2D> getPointFromPolygon(Polygon polygon) {
        ArrayList<Point2D> arrayList = new ArrayList();
        for (int i = 0; i < polygon.npoints; i++) {
            arrayList.add(new Point2D.Double(polygon.xpoints[i], polygon.ypoints[i]));
        }
        return arrayList;
    }

    private ArrayList<Line2D> getLineFromPoint(ArrayList<Point2D> arrayList) {
        ArrayList<Line2D> lines = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == arrayList.size() - 1) {
                lines.add(new Line2D.Double(arrayList.get(arrayList.size() - 1), arrayList.get(0)));
            } else lines.add(new Line2D.Double(arrayList.get(i), arrayList.get(i + 1)));
        }
        return lines;
    }

    private Point2D intersectionTwoLine(Line2D line1, Line2D line2) {
        double A1 = line1.getY1() - line1.getY2();
        double B1 = line1.getX2() - line1.getX1();
        double C1 = line1.getX1() * line1.getY2() - line1.getX2() * line1.getY1();
        double A2 = line2.getY1() - line2.getY2();
        double B2 = line2.getX2() - line2.getX1();
        double C2 = line2.getX1() * line2.getY2() - line2.getX2() * line2.getY1();
        double x = (C2 * B1 - C1 * B2) / (A1 * B2 - A2 * B1);
        double y = (C2 * A1 - C1 * A2) / (A2 * B1 - A1 * B2);
        Point2D point = new Point2D.Double(x, y);
        return point;
    }

    public Node getRoot() {
        return root;
    }
}
