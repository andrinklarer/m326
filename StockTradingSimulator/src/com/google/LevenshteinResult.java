package com.google;

// would be used in the Searchbar
public class LevenshteinResult {
    private Stock source;
    private Integer distance;

    public LevenshteinResult(Stock source, Integer distance) {
        this.source = source;
        this.distance = distance;
    }

    public Stock getSource() {
        return source;
    }

    public void setSource(Stock source) {
        this.source = source;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
