package com.google;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

public class Display extends JFrame {
    public static Header header;
    private JPanel screen;
    private JPanel currentDisplay;
    private String screenIdentifier;

    public Display() {
        UserManager.loadUsers();
        StockMarket.loadStocks();
        screen = new JPanel(new BorderLayout());
        header = new Header(this, "StockOverview", DisplayType.OVERVIEW);
        currentDisplay = new Login(this);
        screenIdentifier = "";



        screen.add(currentDisplay, BorderLayout.CENTER);

        setDefaultValues();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserManager.save();
                StockMarket.save();

                super.windowClosing(e);
            }
        });

    }

    /**
     * This method changes the current main screen to the new selected screen.
     * <p>
     * 0 -> LoginPage,
     * 1 -> StockOverviewDisplay,
     * 2 -> StockDisplay,
     * 3 -> UserDisplay
     *
     * @param screenValue value new screen
     */
    public void updateCurrentScreen(int screenValue) {
        screen.removeAll();
        switch (screenValue) {
            case 1 -> currentDisplay = new StockOverviewDisplay(this);
            case 2 -> currentDisplay = new StockDisplay(this, screenIdentifier);
            case 3 -> currentDisplay = new UserDisplay(this);
            default -> currentDisplay = new Login(this);
        }
        screen.add(currentDisplay, BorderLayout.CENTER);
        if(currentDisplay.getClass().equals(StockDisplay.class)) screen.add(header = new Header(this, StockMarket.getStockByTicker(screenIdentifier).getName(), DisplayType.CHART), BorderLayout.NORTH);
        if(currentDisplay.getClass().equals(StockOverviewDisplay.class)) screen.add((header = new Header(this, "StockOverview", DisplayType.OVERVIEW)), BorderLayout.NORTH);
        if(currentDisplay.getClass().equals(UserDisplay.class)) screen.add((header = new Header(this, UserManager.currentUser.getUsername(), DisplayType.PORTFOLIO)), BorderLayout.NORTH);
        screen.revalidate();
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
        this.setSize(1000, 700);
    }

    public String getScreenIdentifier() {
        return screenIdentifier;
    }


    public void setScreenIdentifier(String screenIdentifier) {
        this.screenIdentifier = screenIdentifier;
    }
}
