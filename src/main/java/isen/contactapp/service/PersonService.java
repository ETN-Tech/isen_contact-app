package isen.contactapp.service;

import isen.contactapp.daos.PersonDao;
import isen.contactapp.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonService {

    private PersonDao personDao = new PersonDao();

    private ObservableList<Person> persons;

    private PersonService() {
        persons = FXCollections.observableArrayList();
        persons.addAll(personDao.listPersons());
    }

    public static ObservableList<Person> getPersons() {
        return PersonServiceHolder.INSTANCE.persons;
    }

    private static class PersonServiceHolder {
        private static final PersonService INSTANCE = new PersonService();
    }
}
