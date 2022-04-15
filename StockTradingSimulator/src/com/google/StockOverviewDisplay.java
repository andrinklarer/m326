package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class StockOverviewDisplay extends JPanel {
    private int LIST_ELEMENT_HEIGHT = 30;
    private JList<String> list;
    private JLabel label = new JLabel();
    private Display display;

    public StockOverviewDisplay(Display display) {
        this.display = display;

        this.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        if (StockMarket.getStocks().size() > 0) {
            list = new JList<>(StockMarket.getStocks().stream().map(Stock::getTicker).toArray(String[]::new));
            list.setBackground(DefaultValues.COLOR_BACKGROUND_LIGHT);
            list.setFixedCellHeight(LIST_ELEMENT_HEIGHT);
            list.setFixedCellWidth(600); // change to responsive code
            list.setBorder(new EmptyBorder(0, 10, 5, 10));

            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() >= 2 && e.getButton() == MouseEvent.BUTTON1) {
                        display.setScreenIdentifier(list.getSelectedValue());
                        display.updateCurrentScreen(2);
                    }
                }
            });

            list.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    JLabel cell = (JLabel) super.getListCellRendererComponent(list, value, index,
                            isSelected, cellHasFocus);
                    cell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, DefaultValues.COLOR_TEXT_MAIN));
                    return cell;
                }
            });
            this.add(list);
        } else {
            label.setText("No Stocks available");
        }
        this.add(label);
    }
}