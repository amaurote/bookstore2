package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.Category;
import com.amaurote.bookstore.domain.enums.Format;
import com.amaurote.bookstore.domain.enums.Language;
import com.amaurote.bookstore.dto.BookDTO;
import com.amaurote.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final CategoryService categoryService;

    private final DTOFactory dtoFactory;

    public BookService(BookRepository bookRepository, CategoryService categoryService, DTOFactory dtoFactory) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.dtoFactory = dtoFactory;
    }

    public void addAllBooks(List<BookDTO> dtoList) {
        List<Book> books = new ArrayList<>();
        for (BookDTO dto : dtoList) {
            saveNewBookFromDTO(dto);
        }
    }

    @Transactional
    public Book saveNewBookFromDTO(BookDTO dto) {
        List<String> categoryPath = dto.getCategoryPath();
        List<Category> categories = new ArrayList<>();
        if (categoryPath != null && categoryPath.size() > 0) {
            for (String cp : categoryPath) {
                categories.add(categoryService.createCategoryFromPath(cp));
            }
        }

        Book book = dtoToBook(dto);
        book.setCategories(categories);
        return bookRepository.save(book);
    }

    private Book dtoToBook(BookDTO dto) { // todo to dtofactory
        Book book = new Book();
        book.setCatalogId(dto.getCatalogId());
        book.setIsbn(dto.getIsbn());
        book.setName(dto.getName());
        book.setOriginalName(dto.getOriginalName());
        book.setFuturePublication(dto.getFuturePublication());
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
