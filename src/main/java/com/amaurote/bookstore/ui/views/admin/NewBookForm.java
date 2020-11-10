package com.amaurote.bookstore.ui.views.admin;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.domain.enums.Format;
import com.amaurote.bookstore.domain.enums.Language;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.Calendar;

public class NewBookForm extends FormLayout {

    IntegerField catalogId = new IntegerField("Catalog ID");
    TextField isbn = new TextField("ISBN 13");
    TextField name = new TextField("Name");
    TextField originalName = new TextField("Original name");
    TextField author = new TextField("Author");
    TextField publisher = new TextField("Publisher");
    //    IntegerField publication = new IntegerField("Publication Date");
//    IntegerField originalPublication = new IntegerField("Original Publication");
//    IntegerField futurePublication = new IntegerField("Future Publication");
    TextField publication = new TextField("Publication Date");
    TextField originalPublication = new TextField("Original Publication");
    TextField futurePublication = new TextField("Future Publication");
    ComboBox<Language> language = new ComboBox<>("Language");
    ComboBox<Format> format = new ComboBox<>("Format");
    IntegerField pages = new IntegerField("Pages");
    TextField dimensions = new TextField("Dimensions");
    IntegerField weight = new IntegerField("Weight");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    //    Binder<Book> binder = new BeanValidationBinder<>(Book.class);
    Binder<Book> binder = new Binder<>(Book.class);

    public NewBookForm() {
        addClassName("new-book-form");

        binder.bindInstanceFields(this);

        catalogId.setReadOnly(true);

        add(
                catalogId,
                isbn,
                name,
                originalName,
                author,
                publisher,
                createDatesLayout(),
                new Label(),
                createLanguageAndFormatLayout(),
                new Label(),
                createDimensionsLayout(),
                new Label(),
                createButtonsLayout()
        );
    }

    public void setBook(Book book) {
        binder.setBean(book);
    }

    private Component createDatesLayout() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int minYear = 1400;
        int maxYear = currentYear + 10;

//        publication.setMax(currentYear);
//        publication.setMin(minYear);
//        originalPublication.setMax(currentYear);
//        originalPublication.setMin(minYear);
//        futurePublication.setMax(maxYear);
//        futurePublication.setMin(currentYear);

        return new HorizontalLayout(publication, originalPublication, futurePublication);
    }

    private Component createLanguageAndFormatLayout() {
        language.setItems(Language.values());
        format.setItems(Format.values());

        return new HorizontalLayout(language, format);
    }

    private Component createDimensionsLayout() {
        return new HorizontalLayout(pages, dimensions, weight);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(click -> fireEvent(new CancelEvent(this)));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        fireEvent(new SaveEvent(this, binder.getBean()));
    }

    // EVENTS
    public static abstract class BookFormEvent extends ComponentEvent<NewBookForm> {
        private Book book;

        protected BookFormEvent(NewBookForm source, Book book) {
            super(source, false);
            this.book = book;
        }

        public Book getBook() {
            return book;
        }
    }

    public static class SaveEvent extends BookFormEvent {
        SaveEvent(NewBookForm source, Book book) {
            super(source, book);
        }
    }

    public static class DeleteEvent extends BookFormEvent {
        DeleteEvent(NewBookForm source, Book book) {
            super(source, book);
        }
    }

    public static class CancelEvent extends BookFormEvent {
        CancelEvent(NewBookForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
