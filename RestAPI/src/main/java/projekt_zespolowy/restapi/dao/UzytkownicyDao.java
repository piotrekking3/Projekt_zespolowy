/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_zespolowy.restapi.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import projekt_zespolowy.restapi.model.Uzytkownicy;
import projekt_zespolowy.restapi.util.DatabaseConnection;

/**
 *
 * @author guncda, Piotr, Kacper
 */
public class UzytkownicyDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    
    public List<Uzytkownicy> getAll() {
        List<Uzytkownicy> list = new ArrayList<Uzytkownicy>();
        Uzytkownicy uzytkownicy = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy");
            
            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();
                
                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));
                
                list.add(uzytkownicy);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }
    
    public Uzytkownicy getById(int id) {
        Uzytkownicy uzytkownicy = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE id_uzytkownika = " + id);
            
            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();
                
                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return uzytkownicy;
    }
    
    public Uzytkownicy getByNick(String nick) {
        Uzytkownicy uzytkownicy = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE nick = '" + nick + "'");
            
            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();
                
                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return uzytkownicy;
    }
    
    public Uzytkownicy getByEmail(String email) {
        Uzytkownicy uzytkownicy = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE email = '" + email + "'");
            
            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();
                
                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return uzytkownicy;
    }
    
    public List<Uzytkownicy> getWithAdminRights() {
        List<Uzytkownicy> list = new ArrayList<Uzytkownicy>();
        Uzytkownicy uzytkownicy = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE admin = " + true);
            
            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();
                
                uzytkownicy.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                uzytkownicy.setAdmin(resultSet.getBoolean("admin"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setNick(resultSet.getString("nick"));
                
                list.add(uzytkownicy);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }
 
    public String putPass(int id, String haslo) {
        try {
            connection.establishConnection();
            Statement statement = connection.getConnection().createStatement();
            statement.executeQuery("UPDATE uzytkownicy SET haslo='" + haslo + "' WHERE id_uzytkownika=" + id + ";");
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        
        connection.closeConnection();
        return "{\"status\":\"ok\"}";
    }
    
    public String putEmail(int id, String email) {
        try {
            connection.establishConnection();
            Statement statement = connection.getConnection().createStatement();
            statement.executeQuery("UPDATE uzytkownicy SET email='" + email + "' WHERE id_uzytkownika=" + id + ";");
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        
        connection.closeConnection();
        return "{\"status\":\"ok\"}";
    }

    public String putAdmin(int id, boolean admin) {
        try {
            connection.establishConnection();
            Statement statement = connection.getConnection().createStatement();
            statement.executeQuery("UPDATE uzytkownicy SET admin='" + admin + "' WHERE id_uzytkownika=" + id + ";");
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        
        connection.closeConnection();
        return "{\"status\":\"ok\"}";
    }
    
    public String postUser(String email, String haslo, String nick) {
        try {
            connection.establishConnection();
            Statement statement = connection.getConnection().createStatement();
            statement.executeQuery("INSERT INTO uzytkownicy VALUES(DEFAULT, false, '" + haslo + "', '" + email + "', '" + nick + "');");
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        
        connection.closeConnection();
        return "{\"status\":\"ok\"}";
    }

    public String deleteUser(int id) {
        try {
            connection.establishConnection();
            Statement statement = connection.getConnection().createStatement();
            statement.executeQuery("DELETE FROM uzytkownicy WHERE id_uzytkownika=" + id + ";");
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        
        connection.closeConnection();
        return "{\"status\":\"ok\"}";
    }
    
}
