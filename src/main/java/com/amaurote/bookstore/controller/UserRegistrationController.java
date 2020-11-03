package com.amaurote.bookstore.controller;

import com.amaurote.bookstore.dto.UserRegistrationDTO;
import com.amaurote.bookstore.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String user() {
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return ("<h1>Welcome Admin</h1>");
    }

    @PostMapping("/registration")
    @PreAuthorize("permitAll()")
    public String registerUserAccount(@RequestBody UserRegistrationDTO registrationDto) {
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userService.save(registrationDto);
        return "redirect:/home";
    }

}
