package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.function.Function;

public class UserDisplay extends JPanel {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private Display display;
    private JLabel balanceLabel;
    private JTable jtable;

    public UserDisplay(Display display) {
        this.display = display;
        this.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        balanceLabel = new JLabel("Balance: " + df.format(UserManager.currentUser.getPortfolio().getBalance()));
        this.add(balanceLabel);
        setComponent();
    }
    private void setComponent() {

        String[] columnNames = {"Stock", "Amount", "Bought for", "Value", "Profit/Loss", "+/-%"};

        String[][] data = UserManager.currentUser.getPortfolio().getShares().stream()
                .map(new Function<Share, String[]>() {
                    @Override
                    public String[] apply(Share share) {
                        int amount = share.getAmount();
                        double boughtFor = share.getBuyPrice() * amount;
                        double currentValue = share.getStock().getCurrentPrice() * amount;
                        return new String[]{
                                share.getStock().getName(),
                                amount + "",
                                df.format(boughtFor),
                                df.format (currentValue),
                                df.format(currentValue - boughtFor),
                                getPlusMinus(boughtFor/amount, share.getStock()),
                        };
                    }
                }).toArray(String[][]::new);

        jtable = new JTable(data, columnNames);

        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtable.setShowVerticalLines(false);

        jtable.setFont(DefaultValues.FONT_MAIN);
        jtable.setBackground(DefaultValues.COLOR_BACKGROUND_LIGHT);

        jtable.getTableHeader().setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        jtable.getTableHeader().setFont(DefaultValues.FONT_BOLD);
        jtable.getTableHeader().setBorder(new EmptyBorder(0, 0, 0, 0));
        jtable.getTableHeader().setReorderingAllowed(false);

        jtable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jtable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (((String) table.getValueAt(row, 5)).contains("+"))
                    component.setForeground(DefaultValues.COLOR_TEXT_POSITIVE);
                else if (((String) table.getValueAt(row, 5)).contains("-"))
                    component.setForeground(DefaultValues.COLOR_TEXT_ERROR);
                else component.setForeground(DefaultValues.COLOR_TEXT_MAIN);
                return component;
            }
        });
        JScrollPane pane = new JScrollPane(jtable);
        pane.getViewport().setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        pane.setBorder(new EmptyBorder(0, 0, 0, 0));

        this.add(pane);
    }
    private String getPlusMinus(double boughtFor, Stock stock) {
        //calculate the profit/loss
        double currentValue = stock.getCurrentPrice();
        double profitLoss = currentValue - boughtFor;
        double percent = profitLoss / boughtFor * 100;
        System.out.println(percent);

        if (percent > 0) return "+" + df.format(percent);
        else return df.format(percent);
    }
}
