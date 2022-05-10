package com.google;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UserProfileButton extends JButton {
    private static ImageIcon userIcon;

    private JLabel userBalanceLabel;
    private JLabel userIconLabel;

    public UserProfileButton() {
        if (userIcon == null) {
            try {
                userIcon = new ImageIcon(ImageIO.read(new File("userImage.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            } catch (IOException ignored) {
            }
        }
        userIconLabel = new JLabel(userIcon);
        userIconLabel.setMaximumSize(new Dimension(40, 40));

        userBalanceLabel = new JLabel("$ " + DefaultValues.DECIMAL_FORMAT.format(UserManager.currentUser.getPortfolio().getBalance()));
        this.setLayout(new GridLayout(2, 1));
        this.add(userIconLabel);
        this.add(userBalanceLabel);
    }

    /**
     * This method will update the balance of the user
     */
    public void updateBalance() {
        userBalanceLabel.setText("$ " + DefaultValues.DECIMAL_FORMAT.format(UserManager.currentUser.getPortfolio().getBalance()));
    }
}
