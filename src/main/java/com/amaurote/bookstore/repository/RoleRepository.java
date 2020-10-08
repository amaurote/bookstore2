package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByName(String name);
}
