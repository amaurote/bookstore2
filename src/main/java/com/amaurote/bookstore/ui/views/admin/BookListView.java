package com.amaurote.bookstore.ui.views.admin;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.service.BookService;
import com.amaurote.bookstore.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "admin/books", layout = MainLayout.class)
public class BookListView extends VerticalLayout {

    private BookService bookService;

    private final NewBookForm newBookForm;
    Grid<Book> grid = new Grid<>(Book.class);
    TextField filterText = new TextField();

    public BookListView(@Autowired BookService bookService) {
        this.bookService = bookService;

        addClassName("book-view");
        setSizeFull();
        configureGrid();

        newBookForm = new NewBookForm();
        newBookForm.addListener(NewBookForm.SaveEvent.class, this::saveBook);
        newBookForm.addListener(NewBookForm.CancelEvent.class, event -> closeEditor());

        Div content = new Div(grid, newBookForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void saveBook(NewBookForm.SaveEvent event) {
        bookService.save(event.getBook());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        newBookForm.setBook(null);
        newBookForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("book-grid");
        grid.setSizeFull();
        grid.setColumns("catalogId", "isbn", "name", "originalName", "author", "publisher", "publication",
                "originalPublication", "futurePublication", "language", "format", "pages", "dimensions", "weight");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editBook(event.getValue()));
    }

    private void editBook(Book book) {
        if(book == null) {
            closeEditor();
        } else {
            newBookForm.setBook(book);
            newBookForm.setVisible(true);
            addClassName("editing");
        }
    }

    private Component getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addBookButton = new Button("Add", click -> addBook());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addBookButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void addBook() {
        grid.asSingleSelect().clear();
        editBook(new Book());
    }

    private void updateList() {
        grid.setItems(bookService.getAllBooks(filterText.getValue()));
    }
}
