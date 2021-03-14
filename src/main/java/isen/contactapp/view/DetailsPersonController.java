package isen.contactapp.view;

import isen.contactapp.App;
import isen.contactapp.model.Person;
import isen.contactapp.util.ExportVCard;
import isen.contactapp.util.PersonChangeListener;
import isen.contactapp.util.PersonValueFactory;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class DetailsPersonController extends PersonController {
    
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

    @Override
    public void initController(Person person) {
    }

    protected void showPersonDetails(Person person) {
        if (person == null) {
            personPane.setVisible(false);
        }
        else {
            personPane.setVisible(true);
            currentPerson = person;

            firstname.setText(person.getFirstname());
            lastname.setText(person.getLastname());
            nickname.setText(person.getNickname());
            phoneNumber.setText(person.getPhoneNumber());
            address.setText(person.getAddress());
            emailAddress.setText(person.getEmailAddress());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            birthDate.setText(person.getBirthDate().format(formatter));
        }
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
        resetView();
    }

    @FXML
    private void newContact() {
        App.showView("EditPerson");
    }

    @FXML
    private void editContact() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        App.showView("EditPerson", selectedPerson);
    }

    @FXML
    private void exportContact() throws IOException {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();

        ExportVCard exporter = new ExportVCard("backup/");
        exporter.exportToVcard(selectedPerson);
    }

    @FXML
    private void deleteContact() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
            personDao.deletePerson(selectedPerson.getId());
            resetView();
        }
        personTable.getSelectionModel().clearSelection();
    }

}