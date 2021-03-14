package isen.contactapp.daos;

import isen.contactapp.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static isen.contactapp.util.ConnectionFactory.getConnection;

public class PersonDao {

    /**
     * get all the persons from the database
     * @return a list of persons
     */
    public List<Person> listPersons() {
        List<Person> listOfPersons = new ArrayList<>();

        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet results = statement.executeQuery("SELECT * FROM person")) {
                    while (results.next()) {
                        listOfPersons.add(createPersonFromResult(results));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfPersons;
    }

    /**
     * get a specific person from the database with its id
     * @param personId Id of person to find
     * @return the Person found
     * @throws Exception if person not found
     */
    public Person getPerson(int personId) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE idperson = ?")) {
                statement.setInt(1, personId);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        return createPersonFromResult(result);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("No person with id " + personId + " in database.");
    }

    /**
     * add a new Person to the database
     * @param person to add
     * @return Person created with its id
     * @throws Exception if can not add person to database
     */
    public Person addPerson(Person person) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO person (lastname, firstname, nickname, phone_number, address, email_address, birth_date) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, person.getLastname());
                statement.setString(2, person.getFirstname());
                statement.setString(3, person.getNickname());
                statement.setString(4, person.getPhoneNumber());
                statement.setString(5, person.getAddress());
                statement.setString(6, person.getEmailAddress());
                statement.setDate(7, Date.valueOf(person.getBirthDate()));
                statement.executeUpdate();

                try (ResultSet ids = statement.getGeneratedKeys()) {
                    if (ids.next()) {
                        return new Person(
                                ids.getInt(1),
                                person.getLastname(),
                                person.getFirstname(),
                                person.getNickname(),
                                person.getPhoneNumber(),
                                person.getAddress(),
                                person.getEmailAddress(),
                                person.getBirthDate()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can not add Person to database");
    }

    /**
     * update a specific person in the database
     * @param person entity with new fields value
     */
    public void updatePerson(Person person) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE person SET lastname = ?, firstname = ?, nickname = ?, phone_number = ?, address = ?, email_address = ?, birth_date = ? WHERE idperson = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, person.getLastname());
                statement.setString(2, person.getFirstname());
                statement.setString(3, person.getNickname());
                statement.setString(4, person.getPhoneNumber());
                statement.setString(5, person.getAddress());
                statement.setString(6, person.getEmailAddress());
                statement.setDate(7, Date.valueOf(person.getBirthDate()));
                statement.setInt(8, person.getId());

                // check if update was successful
                if (statement.executeUpdate() == 1) {
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can not update person on database");
    }

    /**
     * delete a specific person from the database
     * @param personId of the person to delete
     * @throws Exception
     */
    public void deletePerson(int personId) {
        try (Connection connection = getConnection()) {
            String query = "DELETE FROM person WHERE idperson = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, personId);

                // check if delete was successful
                if (statement.executeUpdate() == 1) {
                    return;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can not delete person form database");
    }

    /**
     * create a Person entity from a query result
     * @param result query
     * @return the Person entity
     * @throws SQLException
     */
    private Person createPersonFromResult(ResultSet result) throws SQLException {
        return new Person(
                result.getInt("idperson"),
                result.getString("lastname"),
                result.getString("firstname"),
                result.getString("nickname"),
                result.getString("phone_number"),
                result.getString("address"),
                result.getString("email_address"),
                result.getDate("birth_date").toLocalDate()
        );
    }
}
