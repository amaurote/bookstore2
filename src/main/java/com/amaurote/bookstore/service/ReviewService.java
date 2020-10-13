package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.Review;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.exception.ReviewException;
import com.amaurote.bookstore.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

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

        return reviewRepository.save(review);
    }
}
