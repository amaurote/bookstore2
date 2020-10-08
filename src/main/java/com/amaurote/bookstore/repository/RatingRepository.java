package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entities.Book;
import com.amaurote.bookstore.domain.entities.Rating;
import com.amaurote.bookstore.domain.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

//    @Query("SELECT new com.amaurote.bookstore.repository.AggregateResults( " +
//            "AVG(r.rating) as rating, " +
//            "COUNT(r.rating) as totalRatings) " +
//            "FROM Rating r " +
//            "WHERE Book = :book")
//    Double getAvgRatingByBook(@Param("book") Book book); // todo test this

    Optional<Rating> findByBookAndAuthor(Book book, User author);

}
