package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Display extends JFrame {
    private JPanel currentDisplay;
    private Header header;
    private JPanel screen;

    public Display() {
        screen = new JPanel(new BorderLayout());
        header = new Header();

        currentDisplay = new StockOverviewDisplay();
//        System.out.println(currentDisplay.getClass());
        screen.add(currentDisplay, BorderLayout.CENTER);
        screen.add(header, BorderLayout.NORTH);
        setDefaultValues();
    }

    public void setDefaultValues() {
        this.setTitle("Stock Trading Simulator - Andy, Jannik");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.requestFocusInWindow();
        this.add(screen);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setSize(1000, 600);
    }
}
