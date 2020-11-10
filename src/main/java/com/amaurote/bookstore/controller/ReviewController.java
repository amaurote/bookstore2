package com.amaurote.bookstore.controller;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.service.BookService;
import com.amaurote.bookstore.service.ReviewService;
import com.amaurote.bookstore.service.UserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@ConditionalOnExpression("${custom.controller.enabled}")
@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    private final UserService userService;

    private final BookService bookService;

    public ReviewController(ReviewService reviewService, UserService userService, BookService bookService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void test(Principal principal) {
        User user;
        if (principal != null) {
            user = userService.getUserByUsername(principal.getName());
            Book book = bookService.getBookByCatalogId(326617);
            reviewService.reviewOrUpdate(book, user, "This is my second review. Oh yeah...");
        }
    }

}
