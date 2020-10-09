package com.amaurote.bookstore.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User author;

    @Column(name = "text", length = 512, nullable = false)
    private String text;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<UserVote> votes = new HashSet<>();

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public Set<UserVote> getVotes() {
//        return votes;
//    }
//
//    public void setVotes(Set<UserVote> votes) {
//        this.votes = votes;
//    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
