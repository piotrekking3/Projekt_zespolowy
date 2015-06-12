package projekt_zespolowy.restapi.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import projekt_zespolowy.restapi.model.Zdjecia;
import projekt_zespolowy.restapi.util.DatabaseConnection;

public class ZdjeciaDao {

    private static DatabaseConnection connection = new DatabaseConnection();

    public Zdjecia getById(int id) throws Exception {
        Zdjecia zdjecia = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM zdjecia WHERE id_zgloszenia = " + id + ";");

            while (resultSet.next()) {
                zdjecia = new Zdjecia();

                zdjecia.setId_zgloszenia(resultSet.getInt("id_zgloszenia"));
                zdjecia.setZdjecie(resultSet.getBytes("zdjecie"));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return zdjecia;
    }

    public int postZdjecia(int id, File file) throws Exception {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection.establishConnection();

            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT deleteZdjecie(" + id + ");");

            FileInputStream fis = new FileInputStream(file);
            PreparedStatement ps = connection.getConnection().prepareStatement("INSERT INTO zdjecia (id_zgloszenia, zdjecie) VALUES (?, ?)");
            ps.setInt(1, id);
            ps.setBinaryStream(2, fis, (int) file.length());

            ps.executeUpdate();
            ps.close();
            fis.close();
        } catch (Exception ex) {
            if (!ex.toString().contains("Zapytanie nie zwróciło żadnych wyników.")) {
                System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
                connection.closeConnection();
                return 500;
            }
            System.out.println(ex);
        }
        connection.closeConnection();
        System.out.println("Zapytanie wykonane pomyslenie");

        return 200;
    }
}
