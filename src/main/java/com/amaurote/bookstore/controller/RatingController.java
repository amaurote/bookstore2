package com.amaurote.bookstore.controller;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.service.BookService;
import com.amaurote.bookstore.service.RatingService;
import com.amaurote.bookstore.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final UserService userService;

    private final RatingService ratingService;

    private final BookService bookService;

    public RatingController(UserService userService, RatingService ratingService, BookService bookService) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.bookService = bookService;
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void test(Principal principal) {
        User user;
        if (principal != null) {
            user = userService.getUserByUsername(principal.getName());
            Book book = bookService.getBookByCatalogId("326617");
            ratingService.rate(book, user, 5);
        }
    }

}
