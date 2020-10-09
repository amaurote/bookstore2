package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entity.UserReviewVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<UserReviewVote, Long> {
}
