package com.amaurote.bookstore.controller;

import com.amaurote.bookstore.dto.BookDTO;
import com.amaurote.bookstore.service.BookService;
import com.amaurote.bookstore.service.DTOFactory;
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
        bookService.addNewBook(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/addall")
    public ResponseEntity<?> addAllBooks(@RequestBody List<BookDTO> dtoList) {
        bookService.addAllBooks(dtoList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping(value = "/test")
//    public ResponseEntity<?> test() {
//        BookDTO dto = new BookDTO();
//
//        dto.setCatalogId("123456");
//        dto.setIsbn("ABCD-123456-789456");
//        dto.setName("Harry Potter a Kameň Mudrcov");
//        dto.setOriginalName("Harry Potter and Sorcerer Stone");
//        dto.setAuthor("J.K. Rowling");
//
//        bookService.addNewBook(dto);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<?> getBook(@RequestParam(value = "id", required = false) String id,
                                     @RequestParam(value = "isbn", required = false) String isbn) {
        if((id == null || id.trim().isEmpty()) && (isbn == null || isbn.trim().isEmpty()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        BookDTO dto = null;
        if(id != null && !id.isEmpty())
            dto = dtoFactory.getBookDTO(bookService.getBookByCatalogId(id));
        else if(isbn != null && !isbn.isEmpty())
            dto = dtoFactory.getBookDTO(bookService.getBookByIsbn(isbn));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
