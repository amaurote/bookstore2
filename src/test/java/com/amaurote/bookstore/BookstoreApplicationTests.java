package com.amaurote.bookstore;

import com.amaurote.bookstore.domain.entity.Category;
import com.amaurote.bookstore.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
public class BookstoreApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test() {
        Category category = new Category();
        category.setName("parent");
        Category category1 = categoryRepository.save(category);

        Category category2 = categoryRepository.findById(category1.getId()).get();

        assertEquals(category1.getName(), category2.getName());
    }

}
