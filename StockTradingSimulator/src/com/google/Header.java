package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Header extends JPanel {
    private Searchbar searchbar;
    private JButton multiuseButton;
    private JButton switchDisplayButton;
    private JLabel titleLabel;
    private final Display parent;

    public Header(Display parent, String title, DisplayType type) {
        this.parent = parent;
        searchbar = new Searchbar();
        if (type == DisplayType.CHART) {
            setUpBackButton();
            setUpSwitchDisplayButton("Portfolio", 3);
        } else {
            setUpRefreshButton();
            setUpSwitchDisplayButton(DisplayType.PORTFOLIO == type ? "Overview" : "Portfolio", DisplayType.PORTFOLIO == type ? 1 : 3);
        }

        setUpTitleContainer(title);
        setUpPanel();
    }

    public void setUpTitleContainer(String title) {
        titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(DefaultValues.FONT_TITLE);
    }

    public void updateTitle(String title) {
        this.titleLabel.setText(title);
        repaint();
    }

    private void setUpRefreshButton() {
        multiuseButton = new JButton();
        multiuseButton.setText("Refresh");
        multiuseButton.setFocusable(false);
    }

    private void setUpSwitchDisplayButton(String text, int screenValue) {
        switchDisplayButton = new JButton();
        switchDisplayButton.setText(text);
        switchDisplayButton.setFocusable(false);
        switchDisplayButton.addActionListener(e -> parent.updateCurrentScreen(screenValue));
    }

    private void setUpBackButton() {
        multiuseButton = new JButton();
        multiuseButton.setText("Back");
        multiuseButton.setFocusable(false);
        multiuseButton.addActionListener(e -> parent.updateCurrentScreen(1));
    }

    private void setUpPanel() {
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(1, 4));
        this.add(multiuseButton);
        this.add(switchDisplayButton);
        //TODO: center the title
        this.add(titleLabel);
        this.add(searchbar);

        this.setBackground(DefaultValues.COLOR_BACKGROUND_DARK);
    }
}
