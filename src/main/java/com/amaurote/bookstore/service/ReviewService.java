package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.Review;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.domain.entity.UserReviewVote;
import com.amaurote.bookstore.domain.enums.Vote;
import com.amaurote.bookstore.exception.ReviewException;
import com.amaurote.bookstore.repository.ReviewRepository;
import com.amaurote.bookstore.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final VoteRepository voteRepository;

    public ReviewService(ReviewRepository reviewRepository, VoteRepository voteRepository) {
        this.reviewRepository = reviewRepository;
        this.voteRepository = voteRepository;
    }

    // TODO toto dat depretaced, vyriesit re-edit
    public Review reviewOrUpdate(Book book, User author, String text) {
        if (book == null || author == null) {
            throw new ReviewException("Unable to map review");
        }
        if (text == null || text.isEmpty() || text.isBlank() || text.length() > 500) {
            throw new ReviewException("Invalid string");
        }

        Review review = reviewRepository.findByBookAndAuthor(book, author)
                .orElse(new Review());

        review.setAuthor(author);
        review.setBook(book);
        review.setText(text);
        review.setDateCreated(new Date());

        // TODO remove all votes

        return reviewRepository.save(review);
    }

    public UserReviewVote voteOrUpdate(Review review, User author, Vote vote) {
        if (review == null || author == null) {
            throw new ReviewException("Unable to map review");
        }
        if (vote == null) {
            throw new ReviewException("Vote can't be null");
        }

        UserReviewVote reviewVote = voteRepository.findByReviewAndAuthor(review, author)
                .orElse(new UserReviewVote(author, review));

        reviewVote.setVote(vote);

        return voteRepository.save(reviewVote);
    }
}
