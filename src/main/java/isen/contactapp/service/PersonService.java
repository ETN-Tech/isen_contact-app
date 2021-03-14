package isen.contactapp.service;

import isen.contactapp.daos.PersonDao;
import isen.contactapp.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonService {

    private static PersonDao personDao = new PersonDao();

    private ObservableList<Person> persons;

    private PersonService() {
        persons = FXCollections.observableArrayList();
        persons.addAll(personDao.listPersons());
    }

    public static ObservableList<Person> getPersons() {
        return PersonServiceHolder.INSTANCE.persons;
    }

    public static void addPerson(Person person) {
        //personDao.addPerson(person);
        PersonServiceHolder.INSTANCE.persons.add(person);
    }

    private static class PersonServiceHolder {
        private static final PersonService INSTANCE = new PersonService();
    }
}
