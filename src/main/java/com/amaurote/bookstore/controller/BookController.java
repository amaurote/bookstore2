package com.amaurote.bookstore.controller;

import com.amaurote.bookstore.dto.BookDTO;
import com.amaurote.bookstore.service.BookService;
import com.amaurote.bookstore.service.DTOFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ConditionalOnExpression("${custom.controller.enabled}")
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    private final DTOFactory dtoFactory;

    public BookController(BookService bookService, DTOFactory dtoFactory) {
        this.bookService = bookService;
        this.dtoFactory = dtoFactory;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addBook(@RequestBody BookDTO dto) {
        bookService.saveNewBookFromDTO(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/addall")
    public ResponseEntity<?> addAllBooks(@RequestBody List<BookDTO> dtoList) {
        bookService.addAllBooks(dtoList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getBook(@RequestParam(value = "id", required = false) String id,
                                     @RequestParam(value = "isbn", required = false) String isbn) {
        if((id == null || id.trim().isEmpty()) && (isbn == null || isbn.trim().isEmpty()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        BookDTO dto = null;
        if(id != null && !id.isEmpty())
            dto = dtoFactory.getBookDTO(bookService.getBookByCatalogId(Integer.parseInt(id)));
        else if(isbn != null && !isbn.isEmpty())
            dto = dtoFactory.getBookDTO(bookService.getBookByIsbn(isbn));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
