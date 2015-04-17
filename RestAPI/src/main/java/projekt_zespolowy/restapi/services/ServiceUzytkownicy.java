package projekt_zespolowy.restapi.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        //return Response.status(Response.Status.OK).entity(nick).build();
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
        int httpCode = 500, idx = 0, oldIdx = 0;

        try {
            if ((idx = uzytkownicy.setNickByJSON(incomingData, idx)) == oldIdx) {
                throw new Exception();
            }
            oldIdx = idx;
            if ((idx = uzytkownicy.setEmailByJSON(incomingData, idx)) == oldIdx) {
                throw new Exception();
            }
            oldIdx = idx;
            if (uzytkownicy.setHasloByJSON(incomingData, idx) == oldIdx) {
                throw new Exception();
            }
            uzytkownicy.setAdmin(false);
        } catch (Exception ex) {
            System.out.println("Niepowodzenie odczytania danych przychodzacych");
        }

        try {
            httpCode = uzytkownicyDao.postUzytkownicy(uzytkownicy);
        } catch (Exception ex) {
            Logger.getLogger(ServiceUzytkownicy.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (httpCode == 200) {
            return Response.status(httpCode).entity("Operacja dodania uzytkownika zostala zakonczona sukcesem").build();
        }

        return Response.status(200).entity("Operacja dodania uzytkownika nie powiodla sie").build();
    }

    @PUT
    @Path("/putEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmail(String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();
        int httpCode = 500, idx = 0, oldIdx = 0;

        try {
            if ((idx = uzytkownicy.setId_uzytkownikaByJSON(incomingData, idx)) == oldIdx) {
                throw new Exception();
            }
            oldIdx = idx;
            if ((idx = uzytkownicy.setEmailByJSON(incomingData, idx)) == oldIdx) {
                throw new Exception();
            }
            oldIdx = idx;
            if (uzytkownicy.setHasloByJSON(incomingData, idx) == oldIdx) {
                throw new Exception();
            }
        } catch (Exception ex) {
            System.out.println("Niepowodzenie odczytania danych przychodzacych");
        }

        try {
            httpCode = uzytkownicyDao.updateEmail(uzytkownicy);
        } catch (Exception ex) {
        }

        if (httpCode == 200) {
            return Response.status(httpCode).entity("Operacja aktualizacji adresu e-mail zakonczona sukcesem").build();
        }

        return Response.status(200).entity("Operacja aktualizacji adresu e-mail nie powiodla sie").build();
    }
}