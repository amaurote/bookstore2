package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.Review;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.domain.entity.UserReviewVote;
import com.amaurote.bookstore.exception.ReviewException;
import com.amaurote.bookstore.repository.VoteAggregateResults;
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

    public UserReviewVote voteOrUpdate(Review review, User author, int vote) {
        if (review == null || author == null) {
            throw new ReviewException("Unable to map review");
        }
        if (vote == 0) {
            removeVote(review, author);
            return null;
        }

        int clamped = Math.max(-1, Math.min(1, vote));

        UserReviewVote reviewVote = voteRepository.findByReviewAndAuthor(review, author)
                .orElse(new UserReviewVote(author, review));

        reviewVote.setVote(clamped);

        return voteRepository.save(reviewVote);
    }

    public VoteAggregateResults getReviewVotes(Review review) {
        return voteRepository.findUserReviewVotes(review);
    }

    public void removeVote(Review review, User author) {
        // todo
    }
}
