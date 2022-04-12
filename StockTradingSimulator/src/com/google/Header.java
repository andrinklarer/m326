package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Header extends JPanel {
    private Searchbar searchbar;
    private JButton refreshButton;

    public Header() {
        searchbar = new Searchbar();
        setUpRefreshButton();
        setUpPanel();
    }

    private void setUpRefreshButton() {
        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        refreshButton.setFocusable(false);
    }

    private void setUpPanel() {
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new BorderLayout());
        this.add(refreshButton, BorderLayout.WEST);
        this.add(searchbar, BorderLayout.EAST);
        this.setBackground(new Color(0x2B0BDE));
    }
}
