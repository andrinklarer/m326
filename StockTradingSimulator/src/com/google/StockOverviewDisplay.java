package com.google;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;


public class StockOverviewDisplay extends JPanel {

    private JList<String> list;
    JLabel label = new JLabel();

    public StockOverviewDisplay() {
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(33, 222, 161));

        if (StockMarket.getStocks().size() > 0) {
            list = new JList<>(StockMarket.getStocks().stream().map(Stock::getTicker).toArray(String[]::new));
        } else {
            list = new JList<>();
        }
        this.add(label);
        this.add(list);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String SelectedFruit = list.getSelectedValue();
                label.setText(SelectedFruit);
            }
        });
    }
}
//        list = new JList<>();
//        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//        for (int i = 0; i < 30; i++) {
//            list.add(new JButton(i + ""));
//        }
//        this.setBackground(Color.GRAY);
//        this.add(list);
//        list.add(new JButton("Element"));
