package projekt_zespolowy.restapi.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import projekt_zespolowy.restapi.model.Uzytkownicy;
import projekt_zespolowy.restapi.util.DatabaseConnection;

public class UzytkownicyDao
{
    private static DatabaseConnection connection = new DatabaseConnection();

    public List<Uzytkownicy> getAll() {
        List<Uzytkownicy> list = new ArrayList<>();
        Uzytkownicy uzytkownicy;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy");

            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();

                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));

                list.add(uzytkownicy);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }

    public Uzytkownicy getById(int id) {
        Uzytkownicy uzytkownicy = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE id_uzytkownika = " + id);

            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();

                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return uzytkownicy;
    }

    public Uzytkownicy getByNick(String nick) {
        Uzytkownicy uzytkownicy = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE nick = '" + nick + "'");

            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();

                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return uzytkownicy;
    }

    public Uzytkownicy getByEmail(String email) {
        Uzytkownicy uzytkownicy = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE email = '" + email + "'");

            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();

                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return uzytkownicy;
    }

    public List<Uzytkownicy> getWithAdminRights() {
        List<Uzytkownicy> list = new ArrayList<>();
        Uzytkownicy uzytkownicy;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE admin = " + true);

            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();

                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));

                list.add(uzytkownicy);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }

    public int postUzytkownicy(Uzytkownicy uzytkownicy) throws Exception {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery(
                    "INSERT INTO uzytkownicy (nick, email, haslo, admin)"
                    + "VALUES('" + uzytkownicy.getNick() + "', '" + uzytkownicy.getEmail() + "', '"
                    + uzytkownicy.getHaslo() + "', '" + uzytkownicy.isAdmin() + "')");

            while (resultSet.next()) {

            }
        } catch (Exception ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników.")) {
                System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
                connection.closeConnection();
                return 500;
            }
        }
        connection.closeConnection();
        System.out.println("Zapytanie wykonane pomyslenie");

        return 200;
    }

    public int updateEmail(Uzytkownicy uzytkownicy) throws Exception {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT updateEmail(" + uzytkownicy.getId_uzytkownika() + ", '"
                    + uzytkownicy.getEmail() + "', '" + uzytkownicy.getHaslo() + "')");

            while (resultSet.next()) {

            }
        } catch (Exception ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników.")) {
                System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
                connection.closeConnection();
                return 500;
            }
        }
        connection.closeConnection();
        System.out.println("Zapytanie wykonane pomyslenie");

        return 200;
    }
}