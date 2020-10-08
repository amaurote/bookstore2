package com.amaurote.bookstore.service;

import com.amaurote.bookstore.dto.BookDTO;
import com.amaurote.bookstore.domain.entities.Book;
import org.springframework.stereotype.Service;

@Service
public class DTOFactory {

    BookDTO getBookDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setCatalogId(book.getCatalogId());
        dto.setIsbn(book.getIsbn());
        dto.setName(book.getName());
        dto.setOriginalName(book.getOriginalName());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setPublication(book.getPublicationYear());
        dto.setOriginalPublication(book.getOriginalPublication());
        dto.setDescription(book.getDescription());
        dto.setLanguage(book.getLanguage().name());

        if (book.getFormat() != null)
            dto.setFormat(book.getFormat().toString());
        else
            dto.setFormat(null);

        dto.setPages(book.getPages());
        dto.setDimensions(book.getDimensions());
        dto.setWeight(book.getWeight());
        return dto;
    }

}
