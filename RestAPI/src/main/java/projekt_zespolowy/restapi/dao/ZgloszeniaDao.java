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
import org.postgresql.geometric.PGpoint;
import projekt_zespolowy.restapi.model.Zgloszenia;
import projekt_zespolowy.restapi.util.DatabaseConnection;
/**
 *
 * @author guncda, Piotr, Kacper
 */
public class ZgloszeniaDao {
    
    private static DatabaseConnection connection = new DatabaseConnection();
    
    public List<Zgloszenia> getAll() {
        List<Zgloszenia> list = new ArrayList<Zgloszenia>();
        Zgloszenia zgloszenia = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM zgloszenia");
            
            while (resultSet.next()) {
                zgloszenia = new Zgloszenia();
                
                zgloszenia.setId_zgloszenia(resultSet.getInt("id_zgloszenia"));
                zgloszenia.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                zgloszenia.setId_typu(resultSet.getInt/*getObject*/("id_typu"));
                zgloszenia.setId_statusu(resultSet.getInt("id_statusu"));
                zgloszenia.setKalendarz(resultSet./*getDate*/getString("data"));
                zgloszenia.setId_disqus(resultSet.getInt("disqus_identifier"));
                zgloszenia.setWspolrzedne((PGpoint)resultSet.getObject("wspolrzedne"));
                
                list.add(zgloszenia);
            }
        }
        catch (Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }
    
    public Zgloszenia getById(int id) {
        Zgloszenia zgloszenia = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM zgloszenia WHERE id_zgloszenia = " + id + ";");
            
            while (resultSet.next()) {
                zgloszenia = new Zgloszenia();
                
                zgloszenia.setId_zgloszenia(resultSet.getInt("id_zgloszenia"));
                zgloszenia.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                zgloszenia.setId_typu(resultSet.getInt("id_typu"));
                zgloszenia.setId_statusu(resultSet.getInt("id_statusu"));
                zgloszenia.setKalendarz(resultSet.getString("data"));
                zgloszenia.setId_disqus(resultSet.getInt("disqus_identifier"));
                zgloszenia.setWspolrzedne((PGpoint)resultSet.getObject("wspolrzedne"));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return zgloszenia;
    }
    
    public List<Zgloszenia> getByType(int id) {
        List<Zgloszenia> list = new ArrayList<Zgloszenia>();
        Zgloszenia zgloszenia = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM zgloszenia WHERE id_typu = " + id + ";");
            
            while (resultSet.next()) {
                zgloszenia = new Zgloszenia();
                
                zgloszenia.setId_zgloszenia(resultSet.getInt("id_zgloszenia"));
                zgloszenia.setId_uzytkownika(resultSet.getInt("id_uzytkownika"));
                zgloszenia.setId_typu(resultSet.getInt("id_typu"));
                zgloszenia.setId_statusu(resultSet.getInt("id_statusu"));
                zgloszenia.setKalendarz(resultSet.getString("data"));
                zgloszenia.setId_disqus(resultSet.getInt("disqus_identifier"));
                zgloszenia.setWspolrzedne((PGpoint)resultSet.getObject("wspolrzedne"));
                
                list.add(zgloszenia);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }
    
    public int postZgloszenia(Zgloszenia zgloszenia) throws Exception {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery(
                    "INSERT INTO zgloszenia "
                    + "VALUES(" + zgloszenia.getId_zgloszenia() + ", " + zgloszenia.getId_uzytkownika()
                    + ", " + zgloszenia.getId_typu() + ", " + zgloszenia.getId_statusu() + ", '"
                    + zgloszenia.getKalendarz() + "', " + zgloszenia.getId_disqus() + ", POINT("
                    + zgloszenia.getWspolrzedne().x + ", " + zgloszenia.getWspolrzedne().y + "))");
           
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
