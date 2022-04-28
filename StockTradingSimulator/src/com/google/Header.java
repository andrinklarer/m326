package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Header extends JPanel {
    private Searchbar searchbar;
    private JButton refreshButton;
    private JLabel titleLabel;

    public Header(String title) {
        searchbar = new Searchbar();
        setUpRefreshButton();
        setUpTitleContainer(title);
        setUpPanel();

    }

    public void setUpTitleContainer(String title){
        titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(DefaultValues.FONT_TITLE);
    }
    public void updateTitle(String title){
        this.titleLabel.setText(title);
        repaint();
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
        //TODO: center the title
        this.add(titleLabel, BorderLayout.CENTER);

        this.setBackground(DefaultValues.COLOR_BACKGROUND_DARK);
    }
}
