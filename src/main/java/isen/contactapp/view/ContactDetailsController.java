package isen.contactapp.view;

import java.time.format.DateTimeFormatter;

import isen.contactapp.App;
import isen.contactapp.daos.PersonDao;
import isen.contactapp.model.Person;
import isen.contactapp.util.PersonChangeListener;
import isen.contactapp.service.PersonService;
import isen.contactapp.util.PersonValueFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ContactDetailsController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn personColumn;
    @FXML
    private AnchorPane personPane;
    @FXML
    private Text firstname;
    @FXML
    private Text lastname;
    @FXML
    private Text nickname;
    @FXML
    private Text phoneNumber;
    @FXML
    private Text address;
    @FXML
    private Text emailAddress;
    @FXML
    private Text birthDate;

    void showPersonDetails(Person person) {
        if (person == null) {
            personPane.setVisible(false);
        }
        else {
            personPane.setVisible(true);

            firstname.setText(person.getFirstname());
            lastname.setText(person.getLastname());
            nickname.setText(person.getNickname());
            phoneNumber.setText(person.getPhoneNumber());
            address.setText(person.getAddress());
            emailAddress.setText(person.getEmailAddress());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d LLLL yyyy");
            birthDate.setText(person.getBirthDate().format(formatter));
        }
    }

    private void refreshList() {
        personTable.refresh();
        personTable.getSelectionModel().clearSelection();
    }

    private void populateList() {
        personTable.setItems(PersonService.getPersons());
        refreshList();
    }

    public void resetView() {
        showPersonDetails(null);
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
    private void editContact() {
        // TODO
        App.showView("ContactEdit");
    }

    @FXML
    private void deleteContact() {
        // TODO
        App.showView("ContactEdit");
    }

}