package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findByNameAndParent(String name, Category parent);

    Category findById(long id);

    boolean existsByName(String name);
}
