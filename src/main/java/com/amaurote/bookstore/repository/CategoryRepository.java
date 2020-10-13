package com.amaurote.bookstore.repository;

import com.amaurote.bookstore.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByNameAndParent(String name, Category parent);

    Optional<Category> findById(long id);

    List<Category> findAllByParent_Id(Integer parentId);

    List<Category> findAllByParent(Category parent);
}
