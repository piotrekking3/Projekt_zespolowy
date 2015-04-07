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
 * @author guncda
 */
public class UzytkownicyDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    
    public List<Uzytkownicy> getAllUzytkownicy() {
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
                
                uzytkownicy.setId(resultSet.getInt("id_uzytkownika"));
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
    
}
