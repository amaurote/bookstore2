package com.amaurote.bookstore.service;

import com.amaurote.bookstore.BookstoreApplication;
import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.Review;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.repository.BookRepository;
import com.amaurote.bookstore.repository.VoteAggregateResults;
import com.amaurote.bookstore.repository.ReviewRepository;
import com.amaurote.bookstore.repository.UserRepository;
import com.amaurote.bookstore.repository.VoteRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BookstoreApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void mockData() {
        if (bookRepository.findAll().isEmpty()) {
            Book testBook1 = new Book();
            testBook1.setCatalogId(111111);
            testBook1.setIsbn("TEST-111111");
            testBook1.setName("Test Book 1");

            Book testBook2 = new Book();
            testBook2.setCatalogId(222222);
            testBook2.setIsbn("TEST-222222");
            testBook2.setName("Test Book 2");

            Book testBook3 = new Book();
            testBook3.setCatalogId(333333);
            testBook3.setIsbn("TEST-333333");
            testBook3.setName("Test Book 3");

            bookRepository.saveAll(Arrays.asList(testBook1, testBook2, testBook3));
        }

        if (userRepository.findAll().isEmpty()) {
            User testUser1 = new User();
            testUser1.setUserName("testUser1");
            testUser1.setEmail("test1@user.com");
            testUser1.setPassword("pass");
            testUser1.setActive(true);
            testUser1.setEnabled(true);

            User testUser2 = new User();
            testUser2.setUserName("testUser2");
            testUser2.setEmail("test2@user.com");
            testUser2.setPassword("pass");
            testUser2.setActive(true);
            testUser2.setEnabled(true);

            User testUser3 = new User();
            testUser3.setUserName("testUser3");
            testUser3.setEmail("test3@user.com");
            testUser3.setPassword("pass");
            testUser3.setActive(true);
            testUser3.setEnabled(true);

            User testUser4 = new User();
            testUser4.setUserName("testUser4");
            testUser4.setEmail("test4@user.com");
            testUser4.setPassword("pass");
            testUser4.setActive(true);
            testUser4.setEnabled(true);

            userRepository.saveAll(Arrays.asList(testUser1, testUser2, testUser3, testUser4));
        }
    }

    @Test
    public void reviewOrUpdate() {
        String text = "This is a test review.";

        Book book = bookRepository.findOneByCatalogId(111111).orElseThrow(() -> new RuntimeException("Book is null"));
        User author = userRepository.findByUserName("testUser1").orElseThrow(() -> new RuntimeException("User is null"));
        reviewService.reviewOrUpdate(book, author, text);

        Optional<Review> review = reviewRepository.findByBookAndAuthor(book, author);
        Assert.assertTrue("Review is not present", review.isPresent());
        Assert.assertEquals("Review text is incorrect", text, review.get().getText());
    }

    @Test
    public void voteOrUpdate() {
        Book book = bookRepository.findOneByCatalogId(111111).orElseThrow(() -> new RuntimeException("Book is null"));
        User author = userRepository.findByUserName("testUser4").orElseThrow(() -> new RuntimeException("User is null"));

        User commenter1 = userRepository.findByUserName("testUser1").orElseThrow(() -> new RuntimeException("User is null"));
        User commenter2 = userRepository.findByUserName("testUser2").orElseThrow(() -> new RuntimeException("User is null"));
        User commenter3 = userRepository.findByUserName("testUser3").orElseThrow(() -> new RuntimeException("User is null"));

        Review review = reviewService.reviewOrUpdate(book, author, "Second test review.");

        reviewService.voteOrUpdate(review, commenter1, 1);
        reviewService.voteOrUpdate(review, commenter2, 1);
        reviewService.voteOrUpdate(review, commenter3, 1);

        VoteAggregateResults votes = voteRepository.findUserReviewVotes(review);
        Assert.assertEquals("Incorrect VoteAggregateResults", votes.getScore(), 3);

        reviewService.voteOrUpdate(review, commenter1, -1);
        reviewService.voteOrUpdate(review, commenter2, -1);
        reviewService.voteOrUpdate(review, commenter3, -1);

        votes = voteRepository.findUserReviewVotes(review);
        Assert.assertEquals("Incorrect VoteAggregateResults", votes.getScore(), -3);

        reviewService.voteOrUpdate(review, commenter2, 1);
        reviewService.voteOrUpdate(review, commenter3, 1);

        votes = voteRepository.findUserReviewVotes(review);
        Assert.assertEquals("Incorrect VoteAggregateResults Score", votes.getScore(), 1);
        Assert.assertEquals("Incorrect VoteAggregateResults AllVotes", votes.getAllvotes(), 3);
        Assert.assertEquals("Incorrect VoteAggregateResults Upvotes", votes.getUpvotes(), 2);
        Assert.assertEquals("Incorrect VoteAggregateResults Downvotes", votes.getDownvotes(), 1);
    }
}