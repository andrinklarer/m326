package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

public class UserDisplay extends JPanel implements StockUpdateObserver {
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
        setHistory();
        StockMarket.getItemList().add(this);
    }

    private void setComponent() {

        String[] columnNames = {"Stock", "Amount", "Bought at", "Bought for", "Value", "Profit/Loss", "+/-%"};

        String[][] data = UserManager.currentUser.getPortfolio().getShares().stream().map(new Function<Share, String[]>() {
            @Override
            public String[] apply(Share share) {
                int amount = share.getAmount();
                double boughtFor = share.getBuyPrice() * amount;
                double currentValue = share.getStock().getCurrentPrice() * amount;
                return new String[]{share.getStock().getName(), amount + "", df.format(share.getBuyPrice()), df.format(boughtFor), df.format(currentValue), df.format(currentValue - boughtFor), getPlusMinus(boughtFor / amount, share.getStock()),};
            }
        }).toArray(String[][]::new);

        initTable(columnNames, data);
        jtable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (((String) table.getValueAt(row, 6)).contains("+"))
                    component.setForeground(DefaultValues.COLOR_TEXT_POSITIVE);
                else if (((String) table.getValueAt(row, 6)).contains("-"))
                    component.setForeground(DefaultValues.COLOR_TEXT_ERROR);
                else component.setForeground(DefaultValues.COLOR_TEXT_MAIN);
                return component;
            }
        });
        applyClickListener();
    }

    private void applyClickListener() {
        jtable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    display.setScreenIdentifier(StockMarket.getStockByName((String) target.getValueAt(target.getSelectedRow(), 0)).getTicker());
                    display.updateCurrentScreen(2);
                }
            }
        });
        JScrollPane pane = new JScrollPane(jtable);
        pane.getViewport().setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        pane.setBorder(new EmptyBorder(0, 0, 0, 0));

        this.add(pane);
    }


    private void setHistory() {

        String[] columnNames = {"Stock", "Amount", "Sold at", "Value"};

        String[][] data = UserManager.currentUser.getPortfolio().getSellHistory().stream().map(new Function<Share, String[]>() {
            @Override
            public String[] apply(Share share) {
                int amount = share.getAmount();
                double soldValue = share.getBuyPrice() * amount;
                return new String[]{share.getStock().getName(),
                        amount + "",
                        df.format(share.getBuyPrice()),
                        df.format(soldValue)};
            }
        }).toArray(String[][]::new);

        initTable(columnNames, data);

        applyClickListener();
    }

    private void initTable(String[] columnNames, String[][] data) {
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

    @Override
    public void update(List<Stock> stocks) {
        this.removeAll();
        this.add(balanceLabel);
        setComponent();
        setHistory();
        revalidate();
        repaint();
    }
}
