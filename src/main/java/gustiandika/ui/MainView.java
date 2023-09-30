package gustiandika.ui;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import gustiandika.Person;
import gustiandika.PersonRepository;
import gustiandika.SorcererService;

@SuppressWarnings("serial")
@Route
public class MainView extends VerticalLayout {

	private final Grid<Person> grid;

	private final Button addNewBtn;
	private final TextField avgNumVictimTxt;

	public MainView(PersonRepository repo, PersonEditor editor, SorcererService sorcerer) {
		this.grid = new Grid<>(Person.class);
		this.addNewBtn = new Button("New person", VaadinIcon.PLUS.create());
		this.avgNumVictimTxt = new TextField();
		this.avgNumVictimTxt.setReadOnly(true);
		this.avgNumVictimTxt.setValue("Average number of victims: -1");
		this.avgNumVictimTxt.setWidth("100%");
		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(addNewBtn);
		add(actions, grid, avgNumVictimTxt, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "yearOfDeath", "ageOfDeath", "yearOfBirth");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
		grid.addColumn(person -> sorcerer.numberOfVictim(person.getYearOfBirth()))
			.setHeader("Number of victim");

		
		// Connect selected Person to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editPerson(e.getValue());
		});

		// Instantiate and edit new Person the new button is clicked
		addNewBtn.addClickListener(e -> editor.editPerson(new Person(12, 10)));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			List<Person> persons = repo.findAll();
			grid.setItems(persons);
			
			double avgNumVictim = sorcerer.avgNumberOfVictim(persons.stream().toArray(Person[]::new));
			this.avgNumVictimTxt.setValue("Average number of victims: " + avgNumVictim);
		});

		// Initialize listing
		grid.setItems(repo.findAll());
	}
}