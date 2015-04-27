package projekt_zespolowy.restapi.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
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

    public Response postUzytkownicy(Uzytkownicy uzytkownicy) {
        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("SELECT adduzytkownik('" + uzytkownicy.getNick()
                    + "', '" + uzytkownicy.getEmail() + "', '" + uzytkownicy.getHaslo() + "')");
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            if (ex.toString().contains("(email)=") && ex.toString().contains("już istnieje")) {
                return Response.serverError().entity("Podany email jest juz zajety").build();
            }
            else if (ex.toString().contains("(nick)=") && ex.toString().contains("już istnieje")) {
                return Response.serverError().entity("Podany nick jest juz zajety").build();
            }
            else if (ex.toString().contains("Brak funkcji")) {
                return Response.serverError().entity("Brakuje odpowiedniej funkcji w bazie danych").build();
            }

            connection.closeConnection();
            return Response.serverError().entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("OK").build();
    }

    public Response updateEmail(Uzytkownicy uzytkownicy) {
        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("SELECT updateEmail(" + uzytkownicy.getId_uzytkownika() + ", '"
                    + uzytkownicy.getEmail() + "', '" + uzytkownicy.getHaslo() + "')");

        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            connection.closeConnection();
            return Response.serverError().entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("OK").build();
    }
}
