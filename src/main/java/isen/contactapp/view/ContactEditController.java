package isen.contactapp.view;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import isen.contactapp.App;
import isen.contactapp.model.Person;
import isen.contactapp.service.PersonService;
import isen.contactapp.util.PersonChangeListener;
import isen.contactapp.util.PersonValueFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ContactEditController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn personColumn;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField nickname;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField emailAddress;
    @FXML
    private TextField birthDate;

    void showPersonDetails(Person person) {
        firstname.setText(person.getFirstname());
        lastname.setText(person.getLastname());
        nickname.setText(person.getNickname());
        phoneNumber.setText(person.getPhoneNumber());
        address.setText(person.getAddress());
        emailAddress.setText(person.getEmailAddress());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d LLLL yyyy");
        birthDate.setText(person.getBirthDate().format(formatter));
    }

    private void refreshList() {
        personTable.refresh();
        personTable.getSelectionModel().clearSelection();
    }

    private void populateList() {
        personTable.setItems(PersonService.getPersons());
        refreshList();
    }

    @FXML
    public void initialize() {
        personColumn.setCellValueFactory(new PersonValueFactory());
        populateList();

        personTable.getSelectionModel().selectedItemProperty().addListener(new PersonChangeListener() {

            @Override
            public void handleNewValue(Person newValue) {
                showPersonDetails(newValue);
            }
        });
        refreshList();
    }

    @FXML
    private void newContact() {
        // TODO
        App.showView("ContactEdit");
    }

    @FXML
    private void saveContact() {
        // TODO
        App.showView("ContactDetails");
    }

    @FXML
    private void cancelContact() {
        App.showView("ContactDetails");
    }
}
