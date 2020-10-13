package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entity.Role;
import com.amaurote.bookstore.domain.entity.User;
import com.amaurote.bookstore.dto.UserRegistrationDTO;
import com.amaurote.bookstore.exception.UserException;
import com.amaurote.bookstore.repository.RoleRepository;
import com.amaurote.bookstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User getUserByUsername(String userName) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if(userOptional.isPresent())
            return userOptional.get();
        else
            throw new UserException("User " + userName + " does not exist");
    }

    @Transactional
    public User save(UserRegistrationDTO dto) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));

        User user = new User();
        user.setUserName(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRoles(roles);
        user.setActive(true);
        user.setEnabled(true);

        return userRepository.save(user);
    }
}
