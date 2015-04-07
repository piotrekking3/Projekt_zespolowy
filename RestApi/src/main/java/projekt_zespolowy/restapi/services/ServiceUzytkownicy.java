/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_zespolowy.restapi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import projekt_zespolowy.restapi.dao.UzytkownicyDao;
import projekt_zespolowy.restapi.model.Uzytkownicy;

/**
 *
 * @author guncda
 */
@Path("/service/uzytkownicy")
public class ServiceUzytkownicy {
    
    private static Map<Integer, Uzytkownicy> uzytkownicy = new HashMap<Integer, Uzytkownicy>();
    
    static {
        for(int i = 0; i < 10; i++) {
            Uzytkownicy uzytkownik = new Uzytkownicy();
            int id = i + 1;
            uzytkownik.setId(id);
            uzytkownik.setNick("Nick" + id);
            uzytkownik.setEmail(uzytkownik.getNick() + "@testmail.com");
            uzytkownik.setHaslo(uzytkownik.getNick());
            uzytkownik.setAdmin(false);
            
            uzytkownicy.put(id, uzytkownik);
        }
        uzytkownicy.get(10).setAdmin(true);
    }
    
    private UzytkownicyDao uzytkownicyDao = new UzytkownicyDao();
    
    @GET
    @Path("/getAllUzytkownicy")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Uzytkownicy> getAllUzytkownicy() {
        return uzytkownicyDao.getAllUzytkownicy();
    }
    
    @GET
    @Path("/getUzytkownikById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUzytkownikById(@PathParam("id") int id) {
        if (uzytkownicy.containsKey(id)) {
            return Response.ok(uzytkownicy.get(id), MediaType.APPLICATION_JSON).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("Nie znaleziono uzytkownika o podanym id: " + id).build();
        }
    }
    
        @GET
    @Path("/getUzytkownikByNick/{nick}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUzytkownikByNick(@PathParam("nick") String nick) {
        for (int i = 1; i <= uzytkownicy.size(); i++) {
            if (uzytkownicy.get(i).getNick().equals(nick)) {
                return Response.ok(uzytkownicy.get(i + 1), MediaType.APPLICATION_JSON).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Nie znaleziono uzytkownika o podanym nicku: " + nick).build();
    }
    
    @GET
    @Path("/getUzytkownikByEmail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUzytkownikByEmail(@PathParam("email") String email) {
        for (int i = 1; i <= uzytkownicy.size(); i++) {
            if (uzytkownicy.get(i).getEmail().equals(email)) {
                return Response.ok(uzytkownicy.get(i + 1), MediaType.APPLICATION_JSON).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Nie znaleziono uzytkownika o podanym adresie email: " + email).build();
    }
    
    @GET
    @Path("/getUzytkownikWithAdminRights")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Uzytkownicy> getUzytkownikWiyhAdminRights() {
        ArrayList<Uzytkownicy> array = new ArrayList<Uzytkownicy>();
        for (int i = 1; i <= uzytkownicy.size(); i++) {
            if (uzytkownicy.get(i).isAdmin()) {
                array.add(uzytkownicy.get(i));
            }
        }
        return array;
    }
    
}
