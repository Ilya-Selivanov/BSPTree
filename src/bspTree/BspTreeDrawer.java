package bspTree;

import javax.swing.*;
import java.awt.*;

public class BspTreeDrawer extends JComponent {
    BSPTree bspTree;
    Graphics2D graphics2D;
    BspTreeDrawer(BSPTree bspTree, Graphics2D graphics2D){
        this.bspTree = bspTree;
        this.graphics2D = graphics2D;
    }

    public void draw(BSPTree.Node node, int x, int y, int spaceX, int spaceY, int r){
        if(node == null) return;
        drawCircle(x, y, r, node.color);
        draw(node.left, x - spaceX, y + spaceY, spaceX/2, spaceY, r - 2);
        draw(node.right, x + spaceX, y + spaceY, spaceX/2, spaceY,r - 2 );
    }

    public void drawCircle(int x, int y, int r, Color color){
        graphics2D.setPaint(color);
        graphics2D.fillOval(x - 10, y - 10, r, r);
    }
}
