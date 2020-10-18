package com.amaurote.bookstore.service;

import com.amaurote.bookstore.BookstoreApplication;
import com.amaurote.bookstore.domain.entity.Category;
import com.amaurote.bookstore.dto.CategoryDTO;
import com.amaurote.bookstore.exception.CategoryException;
import com.amaurote.bookstore.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BookstoreApplication.class})
public class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void createNewCategoryFromDTO() {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // DTO is null              ////////////////////////////////////////////////////////////////////////////////////
        Exception exception = assertThrows(CategoryException.class, () -> {
            categoryService.createNewCategoryFromDTO(null);
        });

        String expectedMessage = "Category error: DTO is null";
        assertEquals("CategoryException Message", expectedMessage, exception.getMessage());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Invalid category name    ////////////////////////////////////////////////////////////////////////////////////
        String expectedMessageName = "Category error: Invalid category name";
        CategoryDTO dtoName = new CategoryDTO();

        // test 1
        dtoName.setName(null);
        Exception exceptionName1 = assertThrows(CategoryException.class, () -> {
            categoryService.createNewCategoryFromDTO(dtoName);
        });
        assertEquals("CategoryException Message", expectedMessageName, exceptionName1.getMessage());

        // test 2
        dtoName.setName("");
        Exception exceptionName2 = assertThrows(CategoryException.class, () -> {
            categoryService.createNewCategoryFromDTO(dtoName);
        });
        assertEquals("CategoryException Message", expectedMessageName, exceptionName2.getMessage());

        // test 3
        dtoName.setName("     ");
        Exception exceptionName3 = assertThrows(CategoryException.class, () -> {
            categoryService.createNewCategoryFromDTO(dtoName);
        });
        assertEquals("CategoryException Message", expectedMessageName, exceptionName3.getMessage());

        // test 4
        expectedMessageName = "Category error: Invalid characters in category name";
        dtoName.setName("#$!*/");
        Exception exceptionName4 = assertThrows(CategoryException.class, () -> {
            categoryService.createNewCategoryFromDTO(dtoName);
        });
        assertEquals("CategoryException Message", expectedMessageName, exceptionName4.getMessage());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Invalid parent           ////////////////////////////////////////////////////////////////////////////////////
        CategoryDTO childDTO = new CategoryDTO();

        // test 1
        expectedMessageName = "Category error: Parent category doesn't exist";
        childDTO.setName("child");
        childDTO.setParentId(123);
        Exception exceptionParent = assertThrows(CategoryException.class, () -> {
            categoryService.createNewCategoryFromDTO(childDTO);
        });
        assertEquals("CategoryException Message", expectedMessageName, exceptionParent.getMessage());

        // test 2
        CategoryDTO parentDTO = new CategoryDTO();
        parentDTO.setName("parent");
        Category patentCategory = categoryService.createNewCategoryFromDTO(parentDTO);
        assertNotNull(patentCategory);

        childDTO.setParentId(patentCategory.getId());
        Category childCategory = categoryService.createNewCategoryFromDTO(childDTO);
        assertNotNull(childCategory);
    }

    @Test
    public void createCategoryFromPath() {
        // create categories and test
        Category category1 = categoryService.createCategoryFromPath("parent.child");
        assertNotNull(category1);

        // add categories to chain and test count
        Category category2 = categoryService.createCategoryFromPath("parent.child.sub1");
        Category category3 = categoryService.createCategoryFromPath("parent.child.sub2");
        assertEquals("Incorrect elements count", 4, categoryRepository.findAll().size());
    }

    @Test
    public void getCategoryDTO() {
        Category category = categoryService.createCategoryFromPath("parent.child.test");

        category.setCaption("Test Caption");
        categoryRepository.save(category);

        assertNotNull(categoryService.getCategoryDTO(category));
        assertNotNull(categoryService.getCategoryDTO(category.getId()));

        CategoryDTO dto = categoryService.getCategoryDTO(category);
        assertEquals("Incorrect category DTO path", "parent.child.test", dto.getPath());
        assertEquals("Incorrect category DTO pretty path", "parent -> child -> Test Caption", dto.getPathPretty());
    }

    @Test
    public void getSubcategories() {
        Category test = categoryService.createCategoryFromPath("parent.child.test");
        categoryService.createCategoryFromPath("parent.child.test.sub1");
        categoryService.createCategoryFromPath("parent.child.test.sub2");
        categoryService.createCategoryFromPath("parent.child.test.sub3");
        assertEquals("Incorrect subcategories count", 3, categoryService.getSubcategories(test.getId()).size());
    }

    @Test
    public void generateTree() {
        // TODO
    }
}