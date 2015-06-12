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

                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setFacebook(resultSet.getString("facebook"));
                uzytkownicy.setGoogle(resultSet.getString("google"));
                uzytkownicy.setTyp(resultSet.getString("typ"));
                uzytkownicy.setToken(resultSet.getString("token"));
                uzytkownicy.setUprawnienia(resultSet.getString("uprawnienia"));
                uzytkownicy.setCzy_aktywowany(resultSet.getBoolean("czy_aktywowany"));
                uzytkownicy.setData_rejestracji(resultSet.getString("data_rejestracji"));

                list.add(uzytkownicy);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
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

                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setFacebook(resultSet.getString("facebook"));
                uzytkownicy.setGoogle(resultSet.getString("google"));
                uzytkownicy.setTyp(resultSet.getString("typ"));
                uzytkownicy.setToken(resultSet.getString("token"));
                uzytkownicy.setUprawnienia(resultSet.getString("uprawnienia"));
                uzytkownicy.setCzy_aktywowany(resultSet.getBoolean("czy_aktywowany"));
                uzytkownicy.setData_rejestracji(resultSet.getString("data_rejestracji"));
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
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE uprawnienia = 'admin'");

            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();

                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setFacebook(resultSet.getString("facebook"));
                uzytkownicy.setGoogle(resultSet.getString("google"));
                uzytkownicy.setTyp(resultSet.getString("typ"));
                uzytkownicy.setToken(resultSet.getString("token"));
                uzytkownicy.setUprawnienia(resultSet.getString("uprawnienia"));
                uzytkownicy.setCzy_aktywowany(resultSet.getBoolean("czy_aktywowany"));
                uzytkownicy.setData_rejestracji(resultSet.getString("data_rejestracji"));

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
            statement.executeQuery("SELECT adduzytkownik('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getHaslo() + "', '" + uzytkownicy.getFacebook()
                    + "', '" + uzytkownicy.getGoogle() + "', '" + uzytkownicy.getTyp() + "'" + ")");
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            if (ex.toString().contains("(email)=") && ex.toString().contains("już istnieje")) {
                return Response.ok("Podany email jest juz zajety").build();
            }
            else if (ex.toString().contains("(facebook)=") && ex.toString().contains("już istnieje")) {
                return Response.ok("Podane id facebooka jest juz zajete").build();
            }
            else if (ex.toString().contains("(google)=") && ex.toString().contains("już istnieje")) {
                return Response.ok("Podane if google jest juz zajete").build();
            }

            connection.closeConnection();
            return Response.ok("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("OK").build();
    }

    public Response register(Uzytkownicy uzytkownicy) {

        Statement statement0;
        ResultSet resultSet0;
        boolean pom_log=false;
        try
        {
           connection.establishConnection();
            statement0 = connection.getConnection().createStatement();
            resultSet0 = statement0.executeQuery("SELECT register('"+uzytkownicy.getEmail()+"','"+uzytkownicy.getHaslo()+"')");
        }
        catch(Exception ex){
            System.err.println(ex);
            connection.closeConnection();

            if (ex.toString().contains("(email)=") && ex.toString().contains("już istnieje")) {
                return Response.status(500).entity("Podany email jest juz zajety").build();
            }
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("OK").build();
    }

    public Response login(Uzytkownicy uzytkownicy) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT login('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getHaslo() + "')");

            while (resultSet.next()) {
                uzytkownicy.setToken(resultSet.getString(1));
            }
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        if (uzytkownicy.getToken() == null) {
            return Response.status(404).entity("Nie ma takiego uzytkownika").build();
        } else {
            return Response.ok("{\"token\":\"" + uzytkownicy.getToken() + "\"}").build();
        }
    }

    public Response loginWithFacebook(Uzytkownicy uzytkownicy) {

        Statement statement0;
        ResultSet resultSet0;
        boolean pom_log=false;
        try
        {
           connection.establishConnection();
            statement0 = connection.getConnection().createStatement();
            resultSet0 = statement0.executeQuery("SELECT checkfacebook('"+uzytkownicy.getFacebook()+"','"+uzytkownicy.getEmail()+"')");
            while(resultSet0.next())
            {
            if(resultSet0.getString(1).equals("t"))
            {
                System.out.println("podany uzytkownik znajduje sie w bazie wiec zostanie zalogowany");
                pom_log=true;
            }
            else
            {
                System.out.println("podany uzytkownik nie znajduje sie w bazie wiec zostanie zarejestrowany");
                pom_log=false;
            }
            }

        }
        catch(Exception ex){
            System.err.println(ex);
            connection.closeConnection();

        }
        //////////////////////////////////////////////////////////////////
        //uzytkownik znajduje sie w bazie wiec zostanie zalogowany
        if(pom_log==true)
        {
            Statement statement;
            ResultSet resultSet;

            try {
                connection.establishConnection();
                statement = connection.getConnection().createStatement();
                resultSet = statement.executeQuery("SELECT loginWithFacebook('" + uzytkownicy.getEmail()
                        + "', '" + uzytkownicy.getFacebook() + "')");

                while (resultSet.next()) {
                    uzytkownicy.setToken(resultSet.getString(1));
                }
            } catch (Exception ex) {
                // Wypisanie bledu na serwer
                System.err.println(ex);

                // Zwrocenie informacji o bledzie uzytkownikowi
                connection.closeConnection();
                return Response.status(500).entity("Wystapil nieznany blad").build();
            }

            connection.closeConnection();
            if (uzytkownicy.getToken() == null) {
                return Response.status(404).entity("Nie ma takiego uzytkownika").build();
            } else {
                return Response.ok("{\"token\":\"" + uzytkownicy.getToken() + "\"}").build();
            }
        }
        //uzytkownik nie jest w bazie wiec zostanie zarejestrownay
        else
        {
            Statement statement;
            try {
                connection.establishConnection();
                statement = connection.getConnection().createStatement();
                statement.executeQuery("SELECT registerWithFacebook('" + uzytkownicy.getEmail()
                        + "', '" + uzytkownicy.getFacebook() + "')");
            } catch (Exception ex) {
                // Wypisanie bledu na serwer
                System.err.println(ex);

                // Zwrocenie informacji o bledzie uzytkownikowi
                if (ex.toString().contains("(email)=") && ex.toString().contains("już istnieje")) {
                    return Response.status(500).entity("Podany email jest juz zajety").build();
                }
                else if (ex.toString().contains("(facebook)=") && ex.toString().contains("już istnieje")) {
                    return Response.status(500).entity("Podane id facebooka jest juz zajete").build();
                }
                connection.closeConnection();
                return Response.status(500).entity("Wystapil nieznany blad").build();
            }

            connection.closeConnection();
            return loginWithFacebook(uzytkownicy);
        }
    }

    public Response loginWithGoogle(Uzytkownicy uzytkownicy) {
        Statement statement0;
        ResultSet resultSet0;
        boolean pom_log=false;
        try
        {
           connection.establishConnection();
            statement0 = connection.getConnection().createStatement();
            resultSet0 = statement0.executeQuery("SELECT checkgoogle('"+uzytkownicy.getGoogle()+"','"+uzytkownicy.getEmail()+"')");
            while(resultSet0.next())
            {
            if(resultSet0.getString(1).equals("t"))
            {
                System.out.println("podany uzytkownik znajduje sie w bazie wiec zostanie zalogowany");
                pom_log=true;
            }
            else
            {
                System.out.println("podany uzytkownik nie znajduje sie w bazie wiec zostanie zarejestrowany");
                pom_log=false;
            }
            }

        }
        catch(Exception ex){
            System.err.println(ex);
            // Zwrocenie informacji o bledzie uzytkownikowi
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        if(pom_log==true)
        {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT loginWithGoogle('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getGoogle() + "')");

            while (resultSet.next()) {
                uzytkownicy.setToken(resultSet.getString(1));
            }
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        if (uzytkownicy.getToken() == null) {
            return Response.status(404).entity("Nie ma takiego uzytkownika").build();
        } else {
            return Response.ok("{\"token\":\"" + uzytkownicy.getToken() + "\"}").build();
        }
        }
        else
        {
        Statement statement1;

        try {
            connection.establishConnection();
            statement1 = connection.getConnection().createStatement();
            statement1.executeQuery("SELECT registerWithGoogle('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getGoogle() + "')");
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            if (ex.toString().contains("(email)=") && ex.toString().contains("już istnieje")) {
                return Response.status(500).entity("Podany email jest juz zajety").build();
            }
            else if (ex.toString().contains("(google)=") && ex.toString().contains("już istnieje")) {
                return Response.ok("Podane if google jest juz zajete").build();
            }
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return loginWithGoogle(uzytkownicy);
        }
    }

    public Response changeGoogle(Uzytkownicy uzytkownicy) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT changeGoogle('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getGoogle() + "', '" + uzytkownicy.getToken() + "')");

            while (resultSet.next()) {
                connection.closeConnection();
                if (resultSet.getBoolean(1)) System.out.println("tak");
                if (resultSet.getBoolean(1) == true) {
                    return Response.ok("Zmieniono id google").build();
                }
                else {
                    return Response.status(404).entity("Nie znaleziono takiego uzytkownika zalogowanego").build();
                }
            }
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }
        return Response.ok().build();
    }

    public Response updatePassword(Uzytkownicy uzytkownicy) throws Exception
    {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet=statement.executeQuery("UPDATE uzytkownicy set haslo='"+uzytkownicy.getHaslo()+"' WHERE email='"+
                    uzytkownicy.getEmail()+"'");

            while (resultSet.next()) {

            }
        } catch (Exception ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników.")
                    && !ex.toString().contains("No results were returned")) {
                System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
                connection.closeConnection();
                return Response.status(500).entity("wystapil nieznany blad").build();
            }
        }
        connection.closeConnection();
        System.out.println("Zapytanie wykonane pomyslenie");

        return Response.ok("ok").build();
    }

    public Response updateUprawnienia(Uzytkownicy uzytkownicy, String user_email) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet=statement.executeQuery("SELECT updateuprawnienia(" + "'" + uzytkownicy.getEmail()
                    + "','" + uzytkownicy.getToken() + "','" + user_email + "', '"
                    + uzytkownicy.getUprawnienia() + "');");

            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    System.out.println("Blad autoryzacji - uzytkownik nie ma odpowiednich praw");
                    return Response.serverError().entity("Blad autoryzacji - uzytkownik nie ma odpowiednich praw").build();
                }
            }
        } catch (Exception ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników.")
                    && !ex.toString().contains("No results were returned")) {
                System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
                connection.closeConnection();
                return Response.status(500).entity("wystapil nieznany blad").build();
            }
        }
        connection.closeConnection();
        System.out.println("Zapytanie wykonane pomyslenie");

        return Response.ok("ok").build();
    }

    public Response logout(Uzytkownicy uzytkownicy) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT logout(" + "'" + uzytkownicy.getEmail() + "','"
                    + uzytkownicy.getToken() + "'" + ");");

            while (resultSet.next()) {
                if (resultSet.getBoolean(1) == false) {
                    System.out.println("Nieprawidlowy email lub token, albo uzytkownik nie jest zalogowany :(.");
                    return Response.serverError().entity("Nieprawidlowy email lub token, albo uzytkownik nie jest zalogowany :(.").build();
                }
            }

        } catch (Exception ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników.")
                    && !ex.toString().contains("No results were returned")) {
                System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
                connection.closeConnection();
                return Response.serverError().entity("wystapil nieznany blad").build();
            }
        }
        connection.closeConnection();
        System.out.println("Zapytanie wykonane pomyslenie");

        return Response.ok("ok").build();
    }

    public Response activate(Uzytkownicy uzytkownicy) {
        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("SELECT activate(" + "'" + uzytkownicy.getEmail() + "'" + ");");
        } catch (Exception ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników.")
                    && !ex.toString().contains("No results were returned")) {
                System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
                connection.closeConnection();
                return Response.serverError().entity("wystapil nieznany blad").build();
            }
        }
        connection.closeConnection();
        System.out.println("Zapytanie wykonane pomyslenie");

        return Response.ok("ok").build();
    }

    public Response valid(Uzytkownicy uzytkownicy) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT checkUprawnienia(" + "'" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getToken() + "', 'admin');");

            while (resultSet.next()) {
                if (resultSet.getBoolean(1)) {
                    return Response.ok("{\"valid\":\"" + 1 + "\"}").build();
                }
            }
        } catch (Exception ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników.")
                    && !ex.toString().contains("No results were returned")) {
                System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
                connection.closeConnection();
                return Response.serverError().entity("wystapil nieznany blad").build();
            }
        }
        connection.closeConnection();
        System.out.println("Zapytanie wykonane pomyslenie");

        return Response.ok("{\"valid\":\"" + 0 + "\"}").build();
    }

    public Response deleteUzytkownik(Uzytkownicy uzytkownicy, String user_email) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT deleteUzytkownik(" + "'" + user_email
                    + "', '" + uzytkownicy.getEmail()+ "', '" + uzytkownicy.getToken() + "'" + ");");

            while (resultSet.next()) {
                if (resultSet.getInt(1) == 0) {
                    return Response.ok("{\"valid\":\"" + 1 + "\"}").build();
                }
            }
        } catch (Exception ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników.")
                    && !ex.toString().contains("No results were returned")) {
                System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
                connection.closeConnection();
                return Response.serverError().entity("wystapil nieznany blad").build();
            }
        }
        connection.closeConnection();
        System.out.println("Zapytanie wykonane pomyslenie");

        return Response.ok("{\"valid\":\"" + 0 + "\"}").build();
    }
}
