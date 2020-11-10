package com.amaurote.bookstore.ui.views.admin;

import com.amaurote.bookstore.service.BookService;
import com.amaurote.bookstore.ui.MainLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Dashboard")
@Route(value = "admin/dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final BookService bookService;

    public DashboardView(@Autowired BookService bookService) {
        this.bookService = bookService;

        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(
                getStatus()
        );

    }

    private Span getStatus() {
        Span booksStats = new Span(Long.toString(bookService.getCount()) + " books");
        booksStats.addClassName("book-stats");

        return booksStats;
    }

}
