package projekt_zespolowy.restapi.services;

import java.sql.SQLException;
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

        int id_uzytkownika;
        int id_typu;
        int id_disqus;
        PGpoint point;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("zgloszenia");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            id_uzytkownika = json.getInt("id_uzytkownika");
            id_typu = json.getInt("id_typu");
            id_disqus = json.getInt("id_disqus");
            point = new PGpoint((String)json.get("wspolrzedne"));
        } catch (JSONException | SQLException ex) {
            return Response.ok("Niepoprawny format JSONa").build();
        }

        zgloszenia.setId_uzytkownika(id_uzytkownika);
        zgloszenia.setId_typu(id_typu);
        zgloszenia.setId_disqus(id_disqus);
        zgloszenia.setWspolrzedne(point);

        return zgloszeniaDao.postZgloszenia(zgloszenia);
    }
}
