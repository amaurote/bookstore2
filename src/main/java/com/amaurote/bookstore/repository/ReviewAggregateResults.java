package com.amaurote.bookstore.repository;

public class ReviewAggregateResults {
    private final int upvotes;
    private final int downvotes;
    private final int allvotes;
    private final int score;

    public ReviewAggregateResults(Long upvotes, Long downvotes) {
        this.upvotes = (upvotes == null) ? 0 : upvotes.intValue();
        this.downvotes = (downvotes == null) ? 0 : downvotes.intValue();
        this.allvotes = this.upvotes + this.downvotes;
        this.score = this.upvotes - this.downvotes;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public int getAllvotes() {
        return allvotes;
    }

    public int getScore() {
        return score;
    }
}
