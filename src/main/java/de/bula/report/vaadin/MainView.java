package de.bula.report.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import de.bula.report.pattern.Pattern;
import de.bula.report.persisitence.PatternRepository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Route
public class MainView extends VerticalLayout {

    private final PatternRepository repo;

    private final PatternEditor editor;

    final Grid<Pattern> grid;

    final TextField filter;

    private final Button addNewBtn;

    public MainView(PatternRepository repo, PatternEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Pattern.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New pattern", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns(/*"id",*/ "name", "headers");
//        grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);

        filter.setPlaceholder("Filter by name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listPatterns(e.getValue()));

        // Connect selected Pattern to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editPattern(e.getValue());
        });

        // Instantiate and edit new Pattern the new button is clicked
        addNewBtn.addClickListener(e -> editor.editPattern(new Pattern(null, null, new ArrayList<>())));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listPatterns(filter.getValue());
        });

        // Initialize listing
        listPatterns(null);
    }

    // tag::listPatterns[]
    void listPatterns(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        }
        else {
            grid.setItems(repo.findAllByNameStartsWith(filterText));
        }
    }
    // end::listPatterns[]

}