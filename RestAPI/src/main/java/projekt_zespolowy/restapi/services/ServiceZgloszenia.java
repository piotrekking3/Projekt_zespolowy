package projekt_zespolowy.restapi.services;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.postgresql.geometric.PGpoint;
import projekt_zespolowy.restapi.dao.ZgloszeniaDao;
import projekt_zespolowy.restapi.model.Zgloszenia;

@Path("/service/zgloszenia")
public class ServiceZgloszenia
{
    private ZgloszeniaDao zgloszeniaDao = new ZgloszeniaDao();

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Zgloszenia> getAll() {
        return zgloszeniaDao.getAll();
    }

    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Zgloszenia getById(@PathParam("id") int id) {
        return zgloszeniaDao.getById(id);
    }

    @GET
    @Path("/getByType/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Zgloszenia> getByType(@PathParam("id") int id) {
        return zgloszeniaDao.getByType(id);
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(String incomingData) {
        Zgloszenia zgloszenia = new Zgloszenia();

        int id_typu;
        PGpoint wspolrzedne = new PGpoint();
        String opis;
        String email_uzytkownika;
        String adres;
        String token;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("zgloszenia");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            id_typu = json.getInt("id_typu");
            wspolrzedne.x = json.getDouble("x");
            wspolrzedne.y = json.getDouble("y");
            opis = json.getString("opis");
            adres = json.getString("adres");
            email_uzytkownika = json.getString("email_uzytkownika");
            token = json.getString("token");
        } catch (JSONException ex) {
            return Response.ok("Niepoprawny format JSONa").build();
        }

        zgloszenia.setId_typu(id_typu);
        zgloszenia.setWspolrzedne(wspolrzedne);
        zgloszenia.setOpis(opis);
        zgloszenia.setAdres(adres);
        zgloszenia.setEmail_uzytkownika(email_uzytkownika);

        return zgloszeniaDao.postZgloszenia(zgloszenia, token);
    }

    @POST
    @Path("/updateStatusZgloszenia")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStatusZgloszenia(String incomingData) {
        Zgloszenia zgloszenia = new Zgloszenia();

        String email;
        String token;
        int id_zgloszenia;
        int id_statusu;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("zgloszenia");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            email = json.getString("email");
            token = json.getString("token");
            id_zgloszenia = json.getInt("id_zgloszenia");
            id_statusu = json.getInt("id_statusu");

        } catch (JSONException ex) {
            return Response.ok("Niepoprawny format JSONa").build();
        }
        zgloszenia.setId_zgloszenia(id_zgloszenia);
        zgloszenia.setId_statusu(id_statusu);

        return zgloszeniaDao.updateStatusZgloszenia(zgloszenia, email, token);
    }

    @POST
    @Path("/deleteZgloszenie")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteZgloszenie(String incomingData) {
        int id_zgloszenia;
        String admin_email;
        String token;

        try {
            JSONObject json = new JSONObject(incomingData);

            json = json.getJSONObject("zgloszenia");

            id_zgloszenia = json.getInt("id_zgloszenia");
            admin_email = json.getString("admin_email");
            token = json.getString("token");
        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        }catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        return zgloszeniaDao.deleteZgloszenie(admin_email, token, id_zgloszenia);
    }
}
