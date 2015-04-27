package projekt_zespolowy.restapi.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection
{
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
            System.out.println("Wystapil problem z zamknieciem bazy danych");
        }
    }

    public Connection getConnection() {
        return databaseConnection;
    }
}
