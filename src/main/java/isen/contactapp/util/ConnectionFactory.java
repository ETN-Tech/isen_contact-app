package isen.contactapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {

    private static DriverManager dataSource;
    private static String url = "jdbc:sqlite:sqlite.db";

    /**
     * @return a connection to the SQLite Database
     *
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
