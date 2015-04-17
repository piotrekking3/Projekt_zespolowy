/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_zespolowy.restapi.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import projekt_zespolowy.restapi.dao.TestDao;
import projekt_zespolowy.restapi.model.Test;

/**
 *
 * @author guncda, Piotr, Kacper
 */
@Path("/service/test")
public class ServiceTest {
    
    TestDao testDao = new TestDao();
    
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Test>/*Response*/ getAll() {
        return testDao.getAll();
        //return Response.ok(testDao.getAll(), MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public /*List<Test>*/Response getById(@PathParam("id") int id) {
        //return testDao.getAll();
        return Response.ok(testDao.getById(id), MediaType.APPLICATION_JSON).build();
    }
    
    /*@PUT
    @Path("/update/{id_osoby}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String incomingData) throws Exception {
        //System.out.println("id_osoby: " + id_osoby);
        System.out.println("wszedlem");
        int id_osoby;
        String imie;
        String nazwisko;
        int http_code;
        String returnString = null;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        TestDao testDao = new TestDao();
        Test test = new Test();
        
        try {
            JSONObject partsData = new JSONObject(incomingData);
            /*test.setId_osoby(partsData.optInt("id_osoby"/*, 0*//*));
            test.setImie(partsData.optString("imie"/*, ""*//*));
            test.setNazwisko(partsData.optString("nazwisko"/*, ""*//*));*/
            /*id_osoby = partsData.optInt("id_osoby");
            imie = partsData.optString("imie");
            nazwisko = partsData.optString("nazwisko");
            System.out.println("id: " + id_osoby + ", imie: " + imie + ", nazwisko: " + nazwisko);
            
            test.setId_osoby(id_osoby);
            test.setImie(imie);
            test.setNazwisko(nazwisko);
            
            http_code = testDao.update(test);
            
            if(http_code == 200) {
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Item has been upadated succesfully");
            } else {
                return Response.status(500).entity("Server was not able to process your request").build();
            }
            
            returnString = jsonArray.put(jsonObject).toString();
        } catch(Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Server was not able to process your request").build();
        }
        
        return Response.ok(returnString).build();
    }
    
    /*@POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Test test) {
        String output = test.toString();
        return Response.status(200).entity(output).build();
    }*/
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(/*InputStream*/String incomingData) {
        StringBuilder txt = new StringBuilder();
        String line = null;
        Test test = new Test();
        int id_osoby;
        String imie, nazwisko;
        /*try {
         BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
         while ((line = in.readLine()) != null) {
         txt.append(line);
         }
         } catch (Exception ex) {
         System.out.println("Error: Nie udalo sie odczytac danych");
         }
         System.out.println(txt.toString());*/

        try {
            System.out.println(incomingData);
            test.buildByJSON(incomingData);
            testDao.add(test);
            //test.setTestByJSON(incomingData);
            /*JSONObject partsData = new JSONObject(incomingData);
             test.setId_osoby(id_osoby = partsData.optInt("id_osoby", 0));
             test.setImie(imie = partsData.optString("imie", ""));
             test.setNazwisko(nazwisko = partsData.optString("nazwisko", ""));*/

            //System.out.println("id_osoby: " + id_osoby + "\timie: " + imie + "\tnazwisko: " + nazwisko);
        } catch (Exception ex) {

        }

        //return Response.status(200).entity(txt.toString()).build();
        return Response.status(200).entity("Operacja dodania zostala zakonczona sukcesem").build();
    }
    
}
