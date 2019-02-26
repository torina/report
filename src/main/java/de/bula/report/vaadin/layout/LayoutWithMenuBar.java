package de.bula.report.vaadin.layout;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import de.bula.report.vaadin.MainView;
import de.bula.report.vaadin.UploadView;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ParentLayout(value = MainLayout.class)
public class LayoutWithMenuBar extends Composite<Div> implements RouterLayout {
    private final Div content = new Div();

    private final HorizontalLayout menuBar = new HorizontalLayout(
            new RouterLink("UploadFile" , UploadView.class),
            new RouterLink("Patterns" , MainView.class)
    );

    private final VerticalLayout root = new VerticalLayout(menuBar, content);

    public LayoutWithMenuBar() {
        getContent().add(root);
    }

    @Override
    public void showRouterLayoutContent(HasElement hasElement) {
        log.info("showRouterLayoutContent - LayoutWithMenuBar");
        Objects.requireNonNull(hasElement);
        Objects.requireNonNull(hasElement.getElement());
        content.removeAll();
        content.getElement().appendChild(hasElement.getElement());
    }
}
