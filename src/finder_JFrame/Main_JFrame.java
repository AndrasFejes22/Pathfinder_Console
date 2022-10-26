package finder_JFrame;

import javax.swing.*;

public class Main_JFrame {

    public static void main(String[] args) {
        JFrame window = new JFrame("Pathfinder                 gCost = currentNode - startNode          hCost = currentNode - goalNode          fCost = gCost + hCost");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(new DemoPanel());

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        DemoPanel demoPanel = new DemoPanel();
        //demoPanel.autoSearch();



    }
}
