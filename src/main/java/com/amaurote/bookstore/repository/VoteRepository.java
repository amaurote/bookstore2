package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entity.Review;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.domain.entity.UserReviewVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<UserReviewVote, Long> {

    Optional<UserReviewVote> findByReviewAndAuthor(Review review, User author);

    @Query("select new com.amaurote.bookstore.repository.VoteAggregateResults(" +
            " sum(case when urv.vote = 1 then 1 else 0 end) as upvotes," +
            " sum(case when urv.vote = -1 then 1 else 0 end) as downvotes," +
            " count(urv.vote) as allvotes," +
            " sum(urv.vote) as score )" +
            " from UserReviewVote urv" +
            " where urv.review =:review")
    VoteAggregateResults findUserReviewVotes(@Param("review") Review review);
}
