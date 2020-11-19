package com.amaurote.bookstore.repository;

public class VoteAggregateResults {
    private final int upvotes;
    private final int downvotes;
    private final int allvotes;
    private final int score;

    public VoteAggregateResults(Long upvotes, Long downvotes, Long allvotes, Long score) {
        this.upvotes = (upvotes == null) ? 0 : upvotes.intValue();
        this.downvotes = (downvotes == null) ? 0 : downvotes.intValue();
        this.allvotes = allvotes.intValue();
        this.score = score.intValue();
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
