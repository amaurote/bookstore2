package com.amaurote.bookstore.controller;

import com.amaurote.bookstore.dto.CategoryDTO;
import com.amaurote.bookstore.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR')")
    @PostMapping(value = "/addcat")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO dto) {
        categoryService.createNewCategoryFromDTO(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR')")
    @PostMapping(value = "/addpath")
    public ResponseEntity<?> createCategoryPath(@RequestBody String path) { // todo put to dto
        categoryService.createCategoryFromPath(path);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCategory(@RequestParam(value = "id") String id) {
        int categoryId = Integer.parseInt(id);
        return new ResponseEntity<>(categoryService.getCategoryDTO(categoryId), HttpStatus.OK);
    }

    @GetMapping(value = "/subcat")
    public ResponseEntity<?> getSubcategories(@RequestParam(value = "id", required = false) String id) {
        Integer categoryId;
        if(id == null || id.isEmpty())
            categoryId = null;
        else
            categoryId = Integer.parseInt(id);

        return new ResponseEntity<>(categoryService.getSubcategories(categoryId), HttpStatus.OK);
    }

    @GetMapping(value = "/tree")
    public String getCategoryTree() {
        return categoryService.generateTree();
    }
}
