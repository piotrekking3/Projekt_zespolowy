/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_zespolowy.restapi.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author guncda
 */
public class DatabaseConnection {
    
    private static Connection databaseConnection = null;

    public void establishConnection() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        try {
            Class.forName("org.postgresql.Driver");
            databaseConnection = DriverManager.getConnection(url, "postgres", "1234");
        } catch(Exception ex) {
            System.out.println("Nie udalo sie ustanowic polaczenia do bazy danych");
        }
    }
    
    public void closeConnection() {
        try {
            if (databaseConnection != null) {
                databaseConnection.close();
            }
        } catch(Exception e) {
            System.out.println("Problem to close the connection to the database");
        }
    }
    
    public Connection getConnection() {
        return databaseConnection;
    }
    
}
