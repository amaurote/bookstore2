package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.models.user.MyUserDetails;
import com.amaurote.bookstore.domain.models.user.Role;
import com.amaurote.bookstore.domain.models.user.User;
import com.amaurote.bookstore.dto.UserRegistrationDTO;
import com.amaurote.bookstore.repository.RoleRepository;
import com.amaurote.bookstore.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public MyUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        User loaded = user.get();

        return new MyUserDetails(loaded.getUserName(), loaded.getPassword(), mapRolesToAuthorities(loaded.getRoles())); // todo pass active and enabled
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

    private Set<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }

//    public void test() {
//        User admin = new User();
//        admin.setUserName("admin");
//        admin.setPassword("pass");
//        admin.setActive(true);
//        admin.setEnabled(true);
//        admin.setRoles("ROLE_ADMIN");
//
//        User user = new User();
//        user.setUserName("user");
//        user.setPassword("pass");
//        user.setActive(true);
//        user.setEnabled(true);
//        user.setRoles("ROLE_USER");
//
//        User editor = new User();
//        editor.setUserName("editor");
//        editor.setPassword("pass");
//        editor.setActive(true);
//        editor.setEnabled(true);
//        editor.setRoles("ROLE_EDITOR");
//
//        userRepository.save(admin);
//        userRepository.save(user);
//        userRepository.save(editor);
//    }
}
