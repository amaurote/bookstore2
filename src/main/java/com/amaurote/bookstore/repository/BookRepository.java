package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findOneByCatalogId(String catalogId);
    Optional<Book> findAllByIsbn(String isbn);
}