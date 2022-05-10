package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Header extends JPanel {
    private final Display parent;

    private JLabel titleLabel;
    private UserProfileButton userProfileButton;
    private JButton overViewButton;

    public Header(Display parent, String title, DisplayType type) {
        this.parent = parent;
        if (type != DisplayType.OVERVIEW) setUpOverviewButton();
        setUpUserProfileButton();
        setUpTitleContainer(title);

        setUpPanel(type);
    }

    /**
     * A set up for the title, where the title will get it's styles and so on
     * @param title the text that will be displayed as the title
     */
    public void setUpTitleContainer(String title) {
        titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(DefaultValues.FONT_TITLE);
    }

    /**
     * A set up for the overview button, which will reroute the user to the stock overview page
     */
    private void setUpOverviewButton() {
        overViewButton = new JButton();
        overViewButton.setText("Overview");
        overViewButton.setFocusable(false);
        overViewButton.addActionListener(e -> parent.updateCurrentScreen(1));
        overViewButton.setBackground(DefaultValues.COLOR_BACKGROUND_LIGHT);
    }

    /**
     * A set up for the user profile button, which will reroute the user to the user page
     */
    private void setUpUserProfileButton() {
        userProfileButton = new UserProfileButton();
        userProfileButton.setMaximumSize(new Dimension(40, 40));
        userProfileButton.addActionListener(e -> parent.updateCurrentScreen(3));
        userProfileButton.setBackground(DefaultValues.COLOR_BACKGROUND_LIGHT);
    }

    /**
     * This method will call the user profile button update to update the users balance
     */
    public void update(){
        userProfileButton.updateBalance();

    }

    /**
     * This method will add all components to the panel
     * @param type the type of display, whereby the overview button will be displayed, type is not overview, or not
     */
    private void setUpPanel(DisplayType type) {
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new BorderLayout());

        if (type != DisplayType.OVERVIEW) this.add(overViewButton, BorderLayout.WEST);
        this.add(titleLabel, BorderLayout.CENTER);
        this.add(userProfileButton, BorderLayout.EAST);


        this.setBackground(DefaultValues.COLOR_BACKGROUND_DARK);
    }
}
