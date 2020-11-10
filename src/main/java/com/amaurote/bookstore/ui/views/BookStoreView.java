package com.amaurote.bookstore.ui.views;

import com.amaurote.bookstore.domain.entity.Book;
import com.amaurote.bookstore.service.BookService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "")
public class BookStoreView extends Div implements HasUrlParameter<String> {

    @Autowired
    private BookService bookService;

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
        if(StringUtils.isAllBlank(parameter)) {
            setText("Set Bookstore");
        } else if (StringUtils.isNumeric(parameter)) {
            Book book = bookService.getBookByCatalogId(Integer.parseInt(parameter));
            if(book == null)
                setText("Doesn't exist.");
            else
                setText(String.format("Book: %s", book.getName()));
        }
    }
}
