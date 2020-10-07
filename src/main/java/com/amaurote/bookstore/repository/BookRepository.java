package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Book findOneByCatalogId(String catalogId);
    Book findAllByIsbn(String isbn);
}