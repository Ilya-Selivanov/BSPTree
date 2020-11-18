package bspTree;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
            JFrame myFrame = new JFrame();
            MyComponent myComponent = new MyComponent();
            myComponent.setSize(500, 500);
            myFrame.setSize(1000, 500);
            myFrame.add(myComponent);
            myFrame.setVisible(true);
            myFrame.setLocationRelativeTo(null);
            myFrame.setResizable(false);
            myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
