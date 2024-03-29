package de.bula.report.vaadin;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.bula.report.pattern.Pattern;
import de.bula.report.persistance.PatternRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class PatternEditor extends VerticalLayout implements KeyNotifier {

    private final PatternRepository repository;

    /**
     * The currently edited pattern
     */
    private Pattern pattern;

    /* Fields to edit properties in Customer entity */
    TextField name = new TextField("Name");
    TextArea headers = new TextArea("Table Headers");

    /* Action buttons */
    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button reset = new Button("Reset");
    Button cancel = new Button("Cancel", VaadinIcon.BAN.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, reset, cancel, delete);

    Binder<Pattern> binder = new Binder<>(Pattern.class);
    private ChangeHandler changeHandler;

    @Autowired
    public PatternEditor(PatternRepository repository) {
        this.repository = repository;

        add(name, headers, actions);

        // bind using naming convention
//        binder.bindInstanceFields(this);

        binder.forField(headers)
                .withConverter(new TableHeaderConverter())
                .bind(Pattern::getHeaders, Pattern::setHeaders);

        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> savePattern());

        // wire action buttons to savePattern, deletePattern and reset
        save.addClickListener(e -> savePattern());
        delete.addClickListener(e -> deletePattern());
        reset.addClickListener(e -> editPattern(pattern));
        cancel.addClickListener(e -> closeEditor());
        setVisible(false);
    }

    private void closeEditor() {
        binder.removeBean();
        changeHandler.onChange();
    }

    private void deletePattern() {
        repository.delete(pattern);
        changeHandler.onChange();
    }

    private void savePattern() {
        repository.save(pattern);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editPattern(Pattern c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            pattern = repository.findById(c.getId()).get();
        } else {
            pattern = c;
        }
        reset.setVisible(persisted);

        // Bind pattern properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(pattern);

        setVisible(true);

        // Focus Name initially
        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either savePattern or deletePattern
        // is clicked
        changeHandler = h;
    }

}
