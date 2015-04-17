/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_zespolowy.restapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import projekt_zespolowy.restapi.model.Test;
import projekt_zespolowy.restapi.util.DatabaseConnection;

/**
 *
 * @author guncda
 */
public class TestDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    
    public List<Test> getAll() {
        List<Test> list = new ArrayList<Test>();
        Test test = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM osoby");
            
            while (resultSet.next()) {
                test = new Test();
                
                test.setId_osoby(resultSet.getInt("id_osoby"));
                test.setImie(resultSet.getString("imie"));
                test.setNazwisko(resultSet.getString("nazwisko"));
                
                list.add(test);
            }
                
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        
        return list;
    }
    
    public Test getById(int id) {
        Test test = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM osoby WHERE id_osoby = " + id);
            
            while (resultSet.next()) {
                test = new Test();
                
                test.setId_osoby(resultSet.getInt("id_osoby"));
                test.setImie(resultSet.getString("imie"));
                test.setNazwisko(resultSet.getString("nazwisko"));
                
            }
                
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        
        return test;
    }
    
    public int add(Test test) throws Exception {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery(
                    "INSERT INTO osoby "
                    + "VALUES(" + test.getId_osoby() + ", '" + test.getImie() + "', '" + test.getNazwisko() + "')");
           
            while (resultSet.next()) {
                
            }
        } catch (Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
            connection.closeConnection();
            return 500;
        }
        connection.closeConnection();
        
        return 200;
    }
    
}
