package com.google;

import java.util.List;

public interface StockUpdateObserver {
    void update(List<Stock> stocks);
}
