package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entity.Category;
import com.amaurote.bookstore.dto.CategoryDTO;
import com.amaurote.bookstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category createCategoryFromString(String ctgStr) {
        // trim and check
        ctgStr = ctgStr.trim().toLowerCase();
        checkCategoryString(ctgStr);

        // extract categories
        StringBuilder sb = new StringBuilder();
        Category parent = null;

        for (int i = 0; i < ctgStr.length(); i++) {
            char c = ctgStr.charAt(i);

            // last char
            if (i + 1 == ctgStr.length()) {
                sb.append(c);
                return findOrCreate(sb.toString(), parent);
            }

            // period
            if (c == '.') {
                parent = findOrCreate(sb.toString(), parent);
                sb.setLength(0); // empty sb
            } else
                sb.append(c);
        }

        return null;
    }

    @Transactional
    public CategoryDTO getCategory(long id) {
        Category category = categoryRepository.findById(id);
        if (category == null)
            throw new RuntimeException("Category with id '" + id + "' does not exist");

        ArrayDeque<String> catPathStack = new ArrayDeque<>();
        ArrayDeque<String> catPrettyStack = new ArrayDeque<>();

        catPathStack.add(category.getName());
        if (category.getCaption() == null)
            catPrettyStack.add(category.getName());
        else
            catPrettyStack.add(category.getCaption());

        StringBuilder buffer = new StringBuilder();

        Category parent = category.getParent();
        while (parent != null) {
            // append path
            buffer.setLength(0);
            buffer.append(parent.getName());
            buffer.append('.');
            catPathStack.push(buffer.toString());

            // append pretty
            buffer.setLength(0);
            if (parent.getCaption() == null)
                buffer.append(parent.getName());
            else
                buffer.append(parent.getCaption());

            buffer.append(" -> ");
            catPrettyStack.push(buffer.toString());

            // update parent
            parent = parent.getParent();
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setName(category.getName());

        // pop the path
        buffer.setLength(0);
        while (!catPathStack.isEmpty()) {
            buffer.append(catPathStack.pop());
        }
        dto.setPath(buffer.toString());

        // pop the pretty
        buffer.setLength(0);
        while (!catPrettyStack.isEmpty()) {
            buffer.append(catPrettyStack.pop());
        }
        dto.setPathPretty(buffer.toString());

        return dto;
    }

    private void checkCategoryString(String ctgStr) {
        if (ctgStr.isEmpty())
            throw new RuntimeException("Invalid String: empty");

        if (!ctgStr.matches("[a-z0-9._-]*"))
            throw new RuntimeException("Invalid String: contains invalid characters");

        if (ctgStr.contains(".."))
            throw new RuntimeException("Invalid String: empty category");

        if (ctgStr.charAt(0) == '.')
            throw new RuntimeException("Invalid String: starts with '.'");

        if (ctgStr.charAt(ctgStr.length() - 1) == '.')
            throw new RuntimeException("Invalid String: ends with '.'");
    }

    private Category findOrCreate(String name, Category parent) {
        Category category = categoryRepository.findByNameAndParent(name, parent);

        if (category == null) {
            Category newCategory = new Category();
            newCategory.setName(name);
            newCategory.setParent(parent);
            return categoryRepository.save(newCategory);
        } else
            return category;
    }

    public List<CategoryDTO> getSubcategories(Long parentId) {
        List<Category> categories = categoryRepository.findAllByParent(parentId);
        return null;
    }
}
