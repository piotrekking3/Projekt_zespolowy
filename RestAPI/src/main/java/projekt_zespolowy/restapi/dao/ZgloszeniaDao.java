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
                zgloszenia.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                zgloszenia.setId_typu(resultSet.getInt("id_typu"));
                zgloszenia.setId_statusu(resultSet.getInt("id_statusu"));
                zgloszenia.setKalendarz(resultSet.getString("data"));
                zgloszenia.setId_disqus(resultSet.getInt("disqus_identifier"));
                zgloszenia.setWspolrzedne((PGpoint)resultSet.getObject("wspolrzedne"));

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

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM zgloszenia WHERE id_zgloszenia = " + id + ";");

            while (resultSet.next()) {
                zgloszenia = new Zgloszenia();

                zgloszenia.setId_zgloszenia(resultSet.getInt("id_zgloszenia"));
                zgloszenia.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                zgloszenia.setId_typu(resultSet.getInt("id_typu"));
                zgloszenia.setId_statusu(resultSet.getInt("id_statusu"));
                zgloszenia.setKalendarz(resultSet.getString("data"));
                zgloszenia.setId_disqus(resultSet.getInt("disqus_identifier"));
                zgloszenia.setWspolrzedne((PGpoint)resultSet.getObject("wspolrzedne"));
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
                zgloszenia.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                zgloszenia.setId_typu(resultSet.getInt("id_typu"));
                zgloszenia.setId_statusu(resultSet.getInt("id_statusu"));
                zgloszenia.setKalendarz(resultSet.getString("data"));
                zgloszenia.setId_disqus(resultSet.getInt("disqus_identifier"));
                zgloszenia.setWspolrzedne((PGpoint)resultSet.getObject("wspolrzedne"));

                list.add(zgloszenia);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }

    public Response postZgloszenia(Zgloszenia zgloszenia) {
        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("SELECT addZgloszenie(" + zgloszenia.getId_uzytkownika()
                    + ", " + zgloszenia.getId_typu() + ", " + zgloszenia.getId_disqus()
                    + ", POINT(" + zgloszenia.getWspolrzedne().x + ", " + zgloszenia.getWspolrzedne().y + "))");
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie użytkownikowi
            if (ex.toString().contains("(id_uzytkownika)=") && ex.toString().contains("nie występuje")) {
                return Response.serverError().entity("Podane id_uzytkownika nie wystepuje w bazie danych").build();
            }
            else if (ex.toString().contains("(id_typu)=") && ex.toString().contains("nie występuje")) {
                return Response.serverError().entity("Podane id_typu nie wystepuje w bazie danych").build();
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
}
