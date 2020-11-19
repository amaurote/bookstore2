package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.Rating;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.exception.RatingException;
import com.amaurote.bookstore.repository.BookRepository;
import com.amaurote.bookstore.repository.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    private final BookRepository bookRepository;

    public RatingService(RatingRepository ratingRepository, BookRepository bookRepository) {
        this.ratingRepository = ratingRepository;
        this.bookRepository = bookRepository;
    }

    public Rating rateByCatalogId(int catalogId, User author, int rating) {
        return rateOrUpdate(bookRepository.findOneByCatalogId(catalogId).orElse(null), author, rating);
    }

    public Rating rateOrUpdate(Book book, User author, int rating){
        if(book == null || author == null)
            throw new RatingException("Unable to map rating");
        if(rating <= 0 || rating > 5)
            throw new RatingException("Invalid rating value");

        Rating ratingObj = ratingRepository.findByBookAndAuthor(book, author)
                .orElse(new Rating());
        ratingObj.setAuthor(author);
        ratingObj.setBook(book);
        ratingObj.setRating(rating);
        return ratingRepository.save(ratingObj);
    }

    public void removeRating(Book book, User author) {
        ratingRepository.findByBookAndAuthor(book, author).ifPresent(ratingRepository::delete);
    }


}
