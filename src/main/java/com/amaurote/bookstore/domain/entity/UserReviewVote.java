package com.amaurote.bookstore.domain.entity;

import com.amaurote.bookstore.domain.enums.Vote;

import javax.persistence.*;

@Entity
@Table(name = "review_votes")
public class UserReviewVote {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne
    private User author;

    @ManyToOne
    private Review review;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "vote", nullable = false)
    private Vote vote;

    public UserReviewVote() {
    }

    public UserReviewVote(User author, Review review) {
        this.author = author;
        this.review = review;
    }

    public UserReviewVote(User author, Review review, Vote vote) {
        this.author = author;
        this.review = review;
        this.vote = vote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
