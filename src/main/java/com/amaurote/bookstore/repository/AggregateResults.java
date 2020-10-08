package com.amaurote.bookstore.repository;

public class AggregateResults {
    private final double rating;
    private final int totalRatings;

    public AggregateResults(Double rating, Long totalRatings) {
        this.rating = (rating == null) ? 0 : rating;
        this.totalRatings = (totalRatings == null) ? 0 : totalRatings.intValue();
    }

    public double getRating() {
        return rating;
    }

    public int getTotalRatings() {
        return totalRatings;
    }
}
