package com.amaurote.bookstore.domain.entity;

import com.amaurote.bookstore.domain.enums.Vote;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "review_votes")
public class UserVote {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne
    private User author;

    @OneToOne
    private Review review;

    @Column(name = "vote")
    private Vote vote;

}
