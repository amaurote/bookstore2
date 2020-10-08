package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.enums.Language;
import com.amaurote.bookstore.dto.BookDTO;
import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.enums.Format;
import com.amaurote.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final DTOFactory dtoFactory;

    public BookService(BookRepository bookRepository, DTOFactory dtoFactory) {
        this.bookRepository = bookRepository;
        this.dtoFactory = dtoFactory;
    }

    public Book addNewBook(BookDTO dto) {
        Book book = dtoToBook(dto);
        return bookRepository.save(book);
    }

    public void addAllBooks(List<BookDTO> dtoList) {
        List<Book> books = new ArrayList<>();
        for(BookDTO bd : dtoList)
            books.add(dtoToBook(bd));
        bookRepository.saveAll(books);
    }

    private Book dtoToBook(BookDTO dto) {
        Book book = new Book();
        book.setCatalogId(dto.getCatalogId());
        book.setIsbn(dto.getIsbn());
        book.setName(dto.getName());
        book.setOriginalName(dto.getOriginalName());
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());
        book.setPublicationYear(dto.getPublication());
        book.setOriginalPublication(dto.getOriginalPublication());
        book.setDescription(dto.getDescription());
        book.setLanguage(Language.valueOf(dto.getLanguage().toUpperCase()));

        if (dto.getFormat() == null || dto.getFormat().trim().isEmpty())
            book.setFormat(null);
        else
            book.setFormat(Format.valueOf(dto.getFormat().trim().toUpperCase()));

        book.setPages(dto.getPages());
        book.setDimensions(dto.getDimensions());
        book.setWeight(dto.getWeight());

        return book;
    }

    public Book getBookByCatalogId(String catalogId) {   // todo change it to entity object
        return bookRepository.findOneByCatalogId(catalogId).orElse(null);
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findAllByIsbn(isbn).orElse(null);
    }
}
