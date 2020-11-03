package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entity.Review;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.domain.entity.UserReviewVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<UserReviewVote, Long> {

    Optional<UserReviewVote> findByReviewAndAuthor(Review review, User author);

    @Query("select new com.amaurote.bookstore.repository.ReviewAggregateResults(" +
            " sum(case when urv.vote = 'UP' then 1 else 0 end) as upvotes," +
            " sum(case when urv.vote = 'DOWN' then 1 else 0 end) as downvotes)" +
            " from UserReviewVote urv" +
            " where urv.review =:review")
    ReviewAggregateResults findUserReviewVotes(Review review);
}
