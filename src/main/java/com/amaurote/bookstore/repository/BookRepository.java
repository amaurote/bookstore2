package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findOneByCatalogId(int catalogId);

    Optional<Book> findOneByIsbn(String isbn);

    @Query("select b from Book b " +
            "where lower(b.name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(b.originalName) like lower(concat('%', :searchTerm, '%'))")
    List<Book> search(@Param("searchTerm") String searchTerm);

    boolean existsByCatalogId(int catalogId);
}