/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_zespolowy.clienttest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author guncda
 */
public class ClientTest {
    
    public static void main(String[] args) {
        try { 
            String string = "{\n"
                    + "    \"zgloszenia\": {\n"
                    + "        \"id_zgloszenia\": 6,\n"
                    + "        \"id_uzytkownika\": 1,\n"
                    + "        \"id_typu\": 1,\n"
                    + "        \"id_statusu\": 1,\n"
                    + "        \"kalendarz\": \"2015-04-17\",\n"
                    + "        \"id_disqus\": 5,\n"
                    + "        \"wspolrzedne\": {\n"
                    + "            \"type\":\"point\",\"value\":\"(51.094703, 17.021475)\",\"x\":51.094703, \"y\":17.021475\n"
                    + "        }\n"
                    + "    }\n"
                    + "}";
            JSONObject jsonObject = new JSONObject(string);

            try {
                URL url = new URL("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zgloszenia/post");
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject.toString());
                out.close();
 
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
                while (in.readLine() != null) {
                }
                System.out.println("\nSukces");
                in.close();
            } catch (Exception e) {
                System.out.println("\nNie przeszlo");
                System.out.println(e);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
