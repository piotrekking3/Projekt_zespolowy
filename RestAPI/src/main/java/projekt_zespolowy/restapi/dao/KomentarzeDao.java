package projekt_zespolowy.restapi.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import projekt_zespolowy.restapi.model.Komentarze;
import projekt_zespolowy.restapi.util.DatabaseConnection;

public class KomentarzeDao {

    private static DatabaseConnection connection = new DatabaseConnection();

    public List<Komentarze> getById(int id) {
        List<Komentarze> list = new ArrayList<>();
        Komentarze komentarze;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT id_zgloszenia,\n"
                    + "	id_komentarza,\n"
                    + "	email,\n"
                    + "	data_nadania,\n"
                    + "	komentarz\n"
                    + "FROM komentarze k\n"
                    + "WHERE id_zgloszenia = " + id);

            while (resultSet.next()) {
                komentarze = new Komentarze();
                komentarze.setId_zgloszenia(resultSet.getInt("id_zgloszenia"));
                komentarze.setId_komentarza(resultSet.getInt("id_komentarza"));
                String email = resultSet.getString("email");
                komentarze.setLogin(email.substring(0, email.indexOf("@")));
                komentarze.setData_nadania(resultSet.getString("data_nadania"));
                komentarze.setKomentarz(resultSet.getString("komentarz"));

                list.add(komentarze);
            }
        }
        catch (Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }

        connection.closeConnection();
        return list;
    }

    public Response post(Komentarze komentarze, String email, String token) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT addKomentarz("
                    + komentarze.getId_zgloszenia()
                    + ", '" + email + "', '"
                    + komentarze.getKomentarz() + "', '"
                    + token + "')");

            while (resultSet.next()) {
                switch (resultSet.getInt(1)) {
                    case 0:
                        return Response.ok("Dodano komentarz").build();
                    case 1:
                        return Response.serverError().entity("Blad autoryzacji - niepoprawny token").build();
                    case 2:
                        return Response.serverError().entity("Blad powiazania - nie znaleziono zgloszenia").build();
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);

            connection.closeConnection();
            return Response.serverError().entity("Wystapil nieznany blad").build();
        }
        connection.closeConnection();
        return Response.serverError().entity("Wystapil nieznany blad").build();
    }
}
