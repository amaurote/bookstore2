package com.amaurote.bookstore.controller;

import com.amaurote.bookstore.dto.CategoryDTO;
import com.amaurote.bookstore.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addBook(@RequestBody CategoryDTO dto) {
        categoryService.createCategoryFromString(dto.getCategoryStr());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCategory(@RequestParam(value = "id") String id) {
        long categoryId = Long.parseLong(id);
        return new ResponseEntity<CategoryDTO>(categoryService.getCategory(categoryId), HttpStatus.OK);
    }

    @GetMapping(value = "/subcat")
    public ResponseEntity<?> getSubcategories(@RequestParam(value = "id", required = false) String id) {
        Long categoryId;
        if(id == null || id.isEmpty())
            categoryId = null;
        else
            categoryId = Long.parseLong(id);

        return new ResponseEntity<List<CategoryDTO>>(categoryService.getSubcategories(categoryId), HttpStatus.OK);
    }
}
