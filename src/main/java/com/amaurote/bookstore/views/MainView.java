package com.amaurote.bookstore.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


@Route("")
@PageTitle("Black Books")
@PWA(name = "Black Books", shortName = "BlackBooks", enableInstallPrompt = false)
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends VerticalLayout implements HasUrlParameter<String> {

    public MainView() {
        Button button = new Button("Click me",
                event -> Notification.show("Clicked!"));
        add(button);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        

        if (parameter == null) {
            add(new H1("Welcome to Black Books"));
        } else {
            add(new H1(String.format("Welcome %s", parameter)));
        }
    }
}
