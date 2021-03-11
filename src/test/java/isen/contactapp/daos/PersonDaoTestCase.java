package isen.contactapp.daos;

import isen.contactapp.model.Person;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static isen.contactapp.util.ConnectionFactory.getConnection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class PersonDaoTestCase {

    private PersonDao PersonsDao = new PersonDao();

    @Before
    public void initDb() throws Exception {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS person (\n" +
                        "    idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                        "    lastname VARCHAR(45) NOT NULL,  \n" +
                        "    firstname VARCHAR(45) NOT NULL,\n" +
                        "    nickname VARCHAR(45) NOT NULL,\n" +
                        "    phone_number VARCHAR(15) NULL,\n" +
                        "    address VARCHAR(200) NULL,\n" +
                        "    email_address VARCHAR(150) NULL,\n" +
                        "    birth_date DATE NULL);\n");
        stmt.executeUpdate("DELETE FROM person");
        stmt.executeUpdate("INSERT INTO person (idperson, lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
                + "VALUES (1, 'Tom', 'King', 'Tommy', '0001', '101, main street', 'tommy@cool.co', '1980-01-01 08:00:00.000')");
        stmt.executeUpdate("INSERT INTO person (idperson, lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
                + "VALUES (2, 'John', 'Kennedy', 'Jojo', '0002', '2, second street', 'jojo@cool.co', '1986-01-01 08:00:00.000')");
        stmt.executeUpdate("INSERT INTO person (idperson, lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
                + "VALUES (3, 'Bob', 'Dylan', 'Bobby', '0003', '1, high street', 'boby@high.co', '1990-01-01 08:00:00.000')");
        stmt.close();
        connection.close();
    }

    @Test
    public void shouldListPersons() {
        // WHEN
        List<Person> persons = PersonsDao.listPersons();
        // THEN
        assertThat(persons).hasSize(3);
        assertThat(persons).extracting("id", "lastname", "firstname", "nickname", "phoneNumber", "address", "emailAddress", "birthDate")
                .containsOnly(tuple(1, "Tom", "King", "Tommy", "0001", "101, main street", "tommy@cool.co", LocalDate.parse("1980-01-01")),
                        tuple(2, "John", "Kennedy", "Jojo", "0002", "2, second street", "jojo@cool.co", LocalDate.parse("1986-01-01")),
                        tuple(3, "Bob", "Dylan", "Bobby", "0003", "1, high street", "boby@high.co", LocalDate.parse("1990-01-01")));
    }

    @Test
    public void shouldGetPerson() throws Exception {
        //WHEN
        Person person = PersonsDao.getPerson(1);
        // THEN
        assertThat(person);
        // TODO test getPerson()
    }

    @Test
    public void shouldAddPerson() throws Exception {
        // GIVEN
        Person Person = new Person(null, "Daniel", "Boon", "Dany", "0004", "22, belgian street", "dany@cool.co", LocalDate.parse("1982-01-01"));
        // WHEN
        Person insertedPerson = PersonsDao.addPerson(Person);
        // THEN
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Person WHERE lastname = 'Daniel' AND firstname = 'Boon'");
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getInt("idPerson")).isEqualTo(insertedPerson.getId());
        assertThat(resultSet.next()).isFalse();
        resultSet.close();
        statement.close();
        connection.close();
    }
}

