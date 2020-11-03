package com.amaurote.bookstore.service;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.entity.Category;
import com.amaurote.bookstore.domain.enums.Format;
import com.amaurote.bookstore.domain.enums.Language;
import com.amaurote.bookstore.dto.BookDTO;
import com.amaurote.bookstore.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> dtos = new ArrayList<>();
        for(Book b : books) {
            dtos.add(dtoFactory.getBookDTO(b));
        }

        return dtos;
    }

    public List<Book> getAllBooks(String filterText) {
        if(StringUtils.isAllBlank(filterText)) {
            return bookRepository.findAll();
        } else {
            return bookRepository.search(filterText);
        }
    }

    public Book save(Book book) {
        if(StringUtils.isAllBlank(book.getId()) && book.getCatalogId() == null) {
            book.setCatalogId(generateCatId());
        }

        return bookRepository.save(book);
    }

    private int generateCatId() {
        Random random = new Random();
        int low = 100000000;
        int high = 999999999;
        int cat = random.nextInt(high - low) + low;

        if(bookRepository.existsByCatalogId(cat))
            return generateCatId();
        else
            return cat;
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
        book.setPublication(dto.getPublication());
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

    public Book getBookByCatalogId(int catalogId) {   // todo change it to entity object
        return bookRepository.findOneByCatalogId(catalogId).orElse(null);
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findOneByIsbn(isbn).orElse(null);
    }
}
