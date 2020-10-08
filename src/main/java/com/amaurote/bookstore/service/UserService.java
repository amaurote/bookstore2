package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entities.user.User;
import com.amaurote.bookstore.exception.UserException;
import com.amaurote.bookstore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String userName) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if(userOptional.isPresent())
            return userOptional.get();
        else
            throw new UserException("User " + userName + " does not exist");
    }
}
