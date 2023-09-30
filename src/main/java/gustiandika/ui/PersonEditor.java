package gustiandika.ui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import gustiandika.Person;
import gustiandika.PersonRepository;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class PersonEditor extends VerticalLayout implements KeyNotifier {

	private final PersonRepository repository;

	/**
	 * The currently edited person
	 */
	private Person person;

	/* Fields to edit properties in Customer entity */
	TextField yearOfDeath = new TextField("Year of Death");
	TextField ageOfDeath = new TextField("Age of Death");

	/* Action buttons */
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<Person> binder = new Binder<>(Person.class);
	private ChangeHandler changeHandler;

	public PersonEditor(PersonRepository repository) {
		this.repository = repository;

		add(yearOfDeath, ageOfDeath, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);

		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

		addKeyPressListener(Key.ENTER, e -> save());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editPerson(person));
		setVisible(false);
	}

	void delete() {
		repository.delete(person);
		changeHandler.onChange();
	}

	void save() {
		repository.save(person);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editPerson(Person c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			// In a more complex app, you might want to load
			// the entity/DTO with lazy loaded relations for editing
			person = repository.findById(c.getId()).get();
		}
		else {
			person = c;
		}
		cancel.setVisible(persisted);

		// Bind person properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(person);

		setVisible(true);

		// Focus first field initially
		yearOfDeath.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}