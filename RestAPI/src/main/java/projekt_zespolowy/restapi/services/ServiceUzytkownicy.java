package projekt_zespolowy.restapi.services;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import projekt_zespolowy.restapi.dao.UzytkownicyDao;
import projekt_zespolowy.restapi.model.Uzytkownicy;

@Path("/service/uzytkownicy")
public class ServiceUzytkownicy
{
    private UzytkownicyDao uzytkownicyDao = new UzytkownicyDao();

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Uzytkownicy> getAll() {
        return uzytkownicyDao.getAll();
    }

    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uzytkownicy getById(@PathParam("id") int id) {
        return uzytkownicyDao.getById(id);
    }

    @GET
    @Path("/getByNick/{nick}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uzytkownicy getByNick(@PathParam("nick") String nick) {
        return uzytkownicyDao.getByNick(nick);
    }

    @GET
    @Path("/getByEmail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uzytkownicy getByEmail(@PathParam("email") String email) {
        return uzytkownicyDao.getByEmail(email);
    }

    @GET
    @Path("/getWithAdminRights")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Uzytkownicy> getWithAdminRights() {
        return uzytkownicyDao.getWithAdminRights();
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();

        String nick;
        String email;
        String haslo;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("uzytkownicy");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            nick = json.getString("nick");
            email = json.getString("email");
            haslo = json.getString("haslo");
        } catch (JSONException ex) {
            return Response.ok("Niepoprawny format JSONa").build();
        }

        uzytkownicy.setNick(nick);
        uzytkownicy.setEmail(email);
        uzytkownicy.setHaslo(haslo);

        return uzytkownicyDao.postUzytkownicy(uzytkownicy);
    }

    @PUT
    @Path("/putEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmail(String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();

        int id_uzytkownika;
        String email;
        String haslo;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("uzytkownicy");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            id_uzytkownika = json.getInt("id_uzytkownika");
            email = json.getString("email");
            haslo = json.getString("haslo");
        } catch (JSONException ex) {
            return Response.ok("Niepoprawny format JSONa").build();
        }

        uzytkownicy.setId_uzytkownika(id_uzytkownika);
        uzytkownicy.setEmail(email);
        uzytkownicy.setHaslo(haslo);

        return uzytkownicyDao.updateEmail(uzytkownicy);
    }
}
