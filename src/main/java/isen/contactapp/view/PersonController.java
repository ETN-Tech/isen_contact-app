package isen.contactapp.view;

import isen.contactapp.daos.PersonDao;
import isen.contactapp.model.Person;
import isen.contactapp.service.PersonService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;

public abstract class PersonController {

    @FXML
    protected TableView<Person> personTable;
    @FXML
    protected TableColumn<Person, String> personColumn;
    @FXML
    protected AnchorPane personPane;
    
    protected PersonDao personDao = new PersonDao();

    protected Person currentPerson;


    public abstract void initController(Person person);

    protected abstract void showPersonDetails(Person person);

    protected void refreshList() {
        personTable.refresh();
        personTable.getSelectionModel().clearSelection();
    }

    protected void populateList() {
        personTable.setItems(PersonService.getPersons());
        refreshList();
    }

    protected void resetView() {
        showPersonDetails(null);
        refreshList();
    }
}
