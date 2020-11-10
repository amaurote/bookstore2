package com.amaurote.bookstore.controller;

import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.service.RatingService;
import com.amaurote.bookstore.service.UserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@ConditionalOnExpression("${custom.controller.enabled}")
@RestController
@RequestMapping("/rating")
public class RatingController {

    private final UserService userService;

    private final RatingService ratingService;

    public RatingController(UserService userService, RatingService ratingService) {
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void test(Principal principal) {
        User user;
        if (principal != null) {
            user = userService.getUserByUsername(principal.getName());
            ratingService.rateByCatalogId(326617, user, 2);
        }
    }

}
