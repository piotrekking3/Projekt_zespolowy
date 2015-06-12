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
import projekt_zespolowy.restapi.dao.KomentarzeDao;
import projekt_zespolowy.restapi.model.Komentarze;

@Path("/service/komentarze")
public class ServiceKomentarze {

    private KomentarzeDao komentarzeDao = new KomentarzeDao();

    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Komentarze> getById(@PathParam("id") int id) {
        return komentarzeDao.getById(id);
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(String incomingData) {
        Komentarze komentarze = new Komentarze();

        int id_zgloszenia;
        String email;
        String komentarz;
        String token;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("komentarze");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            id_zgloszenia = json.getInt("id_zgloszenia");
            email = json.getString("email");
            komentarz = json.getString("komentarz");
            token = json.getString("token");
        } catch (JSONException ex) {
            return Response.serverError().entity("Niepoprawny format JSONa").build();
        }

        komentarze.setId_zgloszenia(id_zgloszenia);
        komentarze.setKomentarz(komentarz);

        return komentarzeDao.post(komentarze, email, token);
    }
}
