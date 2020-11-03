package com.amaurote.bookstore.domain.entity;

import com.amaurote.bookstore.domain.enums.Format;
import com.amaurote.bookstore.domain.enums.Language;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.bookstore.domain.utils.IdGenerator")
    @GeneratedValue(generator = "id")
    @Column(name = "id", length = 128, updatable = false, nullable = false, unique = true)
    private String id;

    @Column(name = "catalog_id", nullable = false, unique = true)
    private Integer catalogId;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publication_year")
    private String publication;

    @Column(name = "original_publication")
    private String originalPublication;

    @Column(name = "future_publication")
    private String futurePublication;

    @Column(name = "description")
    private String description;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "format")
    @Enumerated(EnumType.STRING)
    private Format format;

    @Column(name = "page_count")
    private Integer pages;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "weight")
    private Integer weight;

    @ManyToMany
    @JoinTable(inverseJoinColumns=@JoinColumn(name="category_id"))
    private List<Category> categories;

    // todo add relation to rating

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getOriginalPublication() {
        return originalPublication;
    }

    public void setOriginalPublication(String originalPublication) {
        this.originalPublication = originalPublication;
    }

    public String getFuturePublication() {
        return futurePublication;
    }

    public void setFuturePublication(String futurePublication) {
        this.futurePublication = futurePublication;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
