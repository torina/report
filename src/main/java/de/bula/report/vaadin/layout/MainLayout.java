package de.bula.report.vaadin.layout;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainLayout extends Composite<Div> implements RouterLayout {

    //Component to delegate content through.
    private Div content = new Div();

    private final VerticalLayout layout = new VerticalLayout(
//            new Span("from MainLayout top"),
            content
//            new Span("from MainLayout bottom")
    );

    public MainLayout() {
        getContent().add(layout);
    }

    @Override
    public void showRouterLayoutContent(HasElement hasElement) {
        log.info("showRouterLayoutContent - MainLayout");
        Objects.requireNonNull(hasElement);
        Objects.requireNonNull(hasElement.getElement());
        content.removeAll();
        content.getElement().appendChild(hasElement.getElement());
    }
}
