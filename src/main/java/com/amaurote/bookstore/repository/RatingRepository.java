package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.Rating;
import com.amaurote.bookstore.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("select new com.amaurote.bookstore.repository.RatingAggregateResults(" +
            " avg(r.rating) as rating," +
            " count(r.rating) as totalRatings)" +
            " from Rating r" +
            " where r.book =:book")
    Double getAvgRatingByBook(@Param("book") Book book);

    Optional<Rating> findByBookAndAuthor(Book book, User author);

}
