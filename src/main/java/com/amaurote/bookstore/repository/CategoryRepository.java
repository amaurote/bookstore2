package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findByNameAndParent(String name, Category parent);

    Category findById(long id);

    boolean existsByName(String name);

    List<Category> findAllByParent(Long parentId);
}
