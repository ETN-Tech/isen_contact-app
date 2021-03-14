package isen.contactapp.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import isen.contactapp.App;
import isen.contactapp.model.Person;
import isen.contactapp.service.PersonService;
import isen.contactapp.util.ExportVCard;
import isen.contactapp.util.ImportVCard;
import isen.contactapp.util.PersonChangeListener;
import isen.contactapp.util.PersonValueFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class EditPersonController extends PersonController {

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
    @FXML
    protected AnchorPane personPane;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    @Override
    public void initController(Person person) {
        currentPerson = person;
        showPersonDetails(person);
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
        refreshList();
    }

    @FXML
    private void newContact() {
        refreshList();
    }

    @FXML
    private void saveContact() {
        try {
            LocalDate formattedBirthdate = LocalDate.parse(birthDate.getText(), formatter);

            // check if a person is selected
            if (currentPerson == null) {
                // add new person
                Person newPerson = personDao.addPerson(new Person(
                        null,
                        lastname.getText(),
                        firstname.getText(),
                        nickname.getText(),
                        phoneNumber.getText(),
                        address.getText(),
                        emailAddress.getText(),
                        LocalDate.parse(birthDate.getText(), formatter)
                ));

                currentPerson = newPerson;
                PersonService.addPerson(newPerson);
            }
            else {
                // modify person
                currentPerson.setFirstname(firstname.getText());
                currentPerson.setLastname(lastname.getText());
                currentPerson.setNickname(nickname.getText());
                currentPerson.setPhoneNumber(phoneNumber.getText());
                currentPerson.setAddress(address.getText());
                currentPerson.setEmailAddress(emailAddress.getText());
                currentPerson.setBirthDate(formattedBirthdate);

                personDao.updatePerson(currentPerson);
            }

            App.showView("DetailsPerson");
        }
        catch (DateTimeParseException e) {
            System.out.println("Invalid birthdate format");
        }
    }

    @FXML
    private void cancelContact() {
        App.showView("DetailsPerson");
    }
}
