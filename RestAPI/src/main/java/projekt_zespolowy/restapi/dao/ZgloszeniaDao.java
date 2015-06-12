package projekt_zespolowy.restapi.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.postgresql.geometric.PGpoint;
import projekt_zespolowy.restapi.model.Zgloszenia;
import projekt_zespolowy.restapi.util.DatabaseConnection;

public class ZgloszeniaDao
{
    private static DatabaseConnection connection = new DatabaseConnection();

    public List<Zgloszenia> getAll() {
        List<Zgloszenia> list = new ArrayList<>();
        Zgloszenia zgloszenia;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM zgloszenia");

            while (resultSet.next()) {
                zgloszenia = new Zgloszenia();
                zgloszenia.setId_zgloszenia(resultSet.getInt("id_zgloszenia"));
                zgloszenia.setId_typu(resultSet.getInt("id_typu"));
                zgloszenia.setId_statusu(resultSet.getInt("id_statusu"));
                zgloszenia.setKalendarz(resultSet.getString("data"));
                zgloszenia.setWspolrzedne((PGpoint)resultSet.getObject("wspolrzedne"));
                zgloszenia.setOpis(resultSet.getString("opis"));
                zgloszenia.cutOpis(128);
                zgloszenia.setEmail_uzytkownika(resultSet.getString("email_uzytkownika"));
                zgloszenia.setAdres(resultSet.getString("adres"));

                list.add(zgloszenia);
            }
        }
        catch (Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }

    public Zgloszenia getById(int id) {
        Zgloszenia zgloszenia = null;
        Statement statement;
        ResultSet resultSet;
        KomentarzeDao komentarzeDao = new KomentarzeDao();

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM zgloszenia WHERE id_zgloszenia = " + id + ";");

            while (resultSet.next()) {
                zgloszenia = new Zgloszenia();

                zgloszenia.setId_zgloszenia(resultSet.getInt("id_zgloszenia"));
                zgloszenia.setId_typu(resultSet.getInt("id_typu"));
                zgloszenia.setId_statusu(resultSet.getInt("id_statusu"));
                zgloszenia.setKalendarz(resultSet.getString("data"));
                zgloszenia.setWspolrzedne((PGpoint)resultSet.getObject("wspolrzedne"));
                zgloszenia.setOpis(resultSet.getString("opis"));
                zgloszenia.setEmail_uzytkownika(resultSet.getString("email_uzytkownika"));
                zgloszenia.setAdres(resultSet.getString("adres"));
                zgloszenia.setKomentarze(komentarzeDao.getById(id));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return zgloszenia;
    }

    public List<Zgloszenia> getByType(int id) {
        List<Zgloszenia> list = new ArrayList<>();
        Zgloszenia zgloszenia;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM zgloszenia WHERE id_typu = " + id + ";");

            while (resultSet.next()) {
                zgloszenia = new Zgloszenia();

                zgloszenia.setId_zgloszenia(resultSet.getInt("id_zgloszenia"));
                zgloszenia.setId_typu(resultSet.getInt("id_typu"));
                zgloszenia.setId_statusu(resultSet.getInt("id_statusu"));
                zgloszenia.setKalendarz(resultSet.getString("data"));
                zgloszenia.setWspolrzedne((PGpoint)resultSet.getObject("wspolrzedne"));
                zgloszenia.setOpis(resultSet.getString("opis"));
                zgloszenia.setEmail_uzytkownika(resultSet.getString("email_uzytkownika"));
                zgloszenia.setAdres(resultSet.getString("adres"));

                list.add(zgloszenia);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }

    public Response postZgloszenia(Zgloszenia zgloszenia, String token) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT addZgloszenie(" + zgloszenia.getId_typu()
                    + ", POINT(" + zgloszenia.getWspolrzedne().x + ", " + zgloszenia.getWspolrzedne().y
                    + "), '" + zgloszenia.getOpis() + "', '" + zgloszenia.getAdres()
                    + "', '" + zgloszenia.getEmail_uzytkownika() + "', '" + token + "'" + ")");

            while (resultSet.next()) {
                zgloszenia.setId_zgloszenia(resultSet.getInt(1));
                if (zgloszenia.getId_zgloszenia() == 0) {
                    System.out.println("Funkcja z bazy zwrocila false :(");
                    return Response.serverError().entity("Funkcja z bazy zwrocila false :(").build();
                }
            }
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie użytkownikowi
            if (ex.toString().contains("(email_uzytkownika)=") && ex.toString().contains("nie występuje")) {
                return Response.serverError().entity("Podany email_uzytkownika nie wystepuje w bazie danych").build();
            }
            else if (ex.toString().contains("(id_typu)=") && ex.toString().contains("nie występuje")) {
                return Response.serverError().entity("Podane id_typu nie wystepuje w bazie danych").build();
            }

            connection.closeConnection();
            return Response.ok("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("{\"id_zgloszenia\":" + zgloszenia.getId_zgloszenia() + "}").build();
    }

     public Response updateStatusZgloszenia(Zgloszenia zgloszenia, String email, String token)
    {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT updatestatuszgloszenia(" + "'" + email
                    + "','" + token + "'," + zgloszenia.getId_zgloszenia() + "," + zgloszenia.getId_statusu() + ");");

            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    System.out.println("Blad autoryzacji - uzytkownik nie ma odpowiednich praw");
                    return Response.serverError().entity("Blad autoryzacji - uzytkownik nie ma odpowiednich praw").build();
                }
            }
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            connection.closeConnection();
            return Response.serverError().entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("OK").build();
    }

    public Response deleteZgloszenie(String admin_email, String token, int id_zgloszenia) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT deleteZgloszenie(" + id_zgloszenia
                    + ", '" + admin_email+ "', '" + token + "'" + ");");

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
