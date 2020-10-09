package com.amaurote.bookstore.repository;

public class ReviewAggregateResults {
    private final int upvotes;
    private final int downvotes;

    public ReviewAggregateResults(Integer upvotes, Integer downvotes) {
        this.upvotes = (upvotes == null) ? 0 : upvotes;
        this.downvotes = (downvotes == null) ? 0 : downvotes;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }
}
