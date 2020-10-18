package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entity.Category;
import com.amaurote.bookstore.dto.CategoryDTO;
import com.amaurote.bookstore.exception.CategoryException;
import com.amaurote.bookstore.repository.CategoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createNewCategoryFromDTO(CategoryDTO dto) {
        if (dto == null)
            throw new CategoryException("DTO is null");

        String catName = dto.getName();

        // check category name
        if (StringUtils.isAllBlank(catName))
            throw new CategoryException("Invalid category name");

        // check category name characters
        if (!catName.matches("[a-z0-9_-]*"))
            throw new CategoryException("Invalid characters in category name");

        // get parent
        Category parent = null;
        if(dto.getParentId() != null && dto.getParentId() != 0) {
            parent = categoryRepository.findById(dto.getParentId()).orElseThrow(
                    () -> new CategoryException("Parent category doesn't exist"));

            // compare with subcategories names
            List<Category> children = categoryRepository.findAllByParent(parent);
            for (Category cat : children) {
                if (cat.getName().equals(catName))
                    throw new CategoryException("Category name " + catName + "already exists under parent " + parent.getName());
            }
        }

        // finally create new category
        Category newCategory = new Category();
        newCategory.setName(catName);
        newCategory.setCaption(dto.getCaption());
        newCategory.setParent(parent);
        return categoryRepository.save(newCategory);
    }

    @Transactional
    public Category createCategoryFromPath(String categoryStr) {
        // trim and check
        categoryStr = StringUtils.trimToEmpty(categoryStr).toLowerCase();
        checkCategoryString(categoryStr);

        // extract categories
        StringBuilder sb = new StringBuilder();
        Category parent = null;

        for (int i = 0; i < categoryStr.length(); i++) {
            char c = categoryStr.charAt(i);

            // last char
            if (i + 1 == categoryStr.length()) {
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

    public CategoryDTO getCategoryDTO(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryException("Category with id '" + id + "' does not exist"));
        return getCategoryDTO(category);
    }

    @Transactional
    public CategoryDTO getCategoryDTO(Category category) {
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
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setCaption(category.getCaption());

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

    public List<CategoryDTO> getSubcategories(Integer parentId) {
        List<Category> categories = categoryRepository.findAllByParent_Id(parentId);
        List<CategoryDTO> dtos = new ArrayList<>();

        for (Category c : categories) {
            dtos.add(getCategoryDTO(c));
        }

        return dtos;
    }

    @Transactional
    public String generateTree() {

        // todo load all categories to hashmap and create tree outside sql

        StringBuffer sb = new StringBuffer();
        List<Category> rootCategories = categoryRepository.findAllByParent(null);
        for (Category root : rootCategories) {
            growBranches(sb, root, 0);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void growBranches(StringBuffer sb, Category parent, int depth) {
        sb.append("\t".repeat(Math.max(0, depth)))
                .append(parent.getName())
                .append("\n");

        List<Category> children = categoryRepository.findAllByParent(parent);

        if (children.size() != 0) {
            for (Category child : children) {
                growBranches(sb, child, depth + 1);
            }
        }
    }
}
