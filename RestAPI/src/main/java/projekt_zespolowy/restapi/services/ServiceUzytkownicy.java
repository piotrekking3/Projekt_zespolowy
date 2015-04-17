/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_zespolowy.restapi.services;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import projekt_zespolowy.restapi.dao.UzytkownicyDao;
import projekt_zespolowy.restapi.model.Uzytkownicy;

/**
 *
 * @author guncda, Piotr, Kacper
 */
@Path("/service/uzytkownicy")
public class ServiceUzytkownicy {
    
    private UzytkownicyDao uzytkownicyDao = new UzytkownicyDao();
    
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Uzytkownicy> getAll() {
        System.out.println(uzytkownicyDao.getAll().get(1).getEmail());
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
    
    @GET
    @Path("/id/{id}/pass/{haslo}")
    @Produces(MediaType.APPLICATION_JSON)
    public String putPass(@PathParam("id") int id, @PathParam("haslo") String haslo) {
        return uzytkownicyDao.putPass(id, haslo);
    }
    
    @GET
    @Path("/id/{id}/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String putEmail(@PathParam("id") int id, @PathParam("email") String email) {
        return uzytkownicyDao.putEmail(id, email);
    }   

    @GET
    @Path("/id/{id}/admin/{admin}")
    @Produces(MediaType.APPLICATION_JSON)
    public String putAdmin(@PathParam("id") int id, @PathParam("admin") boolean admin) {
        return uzytkownicyDao.putAdmin(id, admin);
    }  
    
    @GET
    @Path("/email/{email}/haslo/{haslo}/nick/{nick}")
    @Produces(MediaType.APPLICATION_JSON)
    public String postUser(@PathParam("email") String email, @PathParam("haslo") String haslo, @PathParam("nick") String nick) {
        return uzytkownicyDao.postUser(email, haslo, nick);
    }  
    
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("id") int id) {
        return uzytkownicyDao.deleteUser(id);
    }
    
}
