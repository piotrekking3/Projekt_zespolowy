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
    
}
