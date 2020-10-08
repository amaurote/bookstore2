package com.amaurote.bookstore.domain.entities;

import com.amaurote.bookstore.domain.entities.user.User;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User author;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    public Rating() {
    }

    public Rating(Book book, User author, Integer rating) {
        this.book = book;
        this.author = author;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
