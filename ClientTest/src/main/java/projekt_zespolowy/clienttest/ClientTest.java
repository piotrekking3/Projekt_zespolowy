package projekt_zespolowy.clienttest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.codehaus.jettison.json.JSONObject;

public class ClientTest
{
    public static void main(String[] args) {
        int i = 1;
        String input;
        String[] string = {
            "{\n"
            + "\"zgloszenia\": {\n"
            + " \"id_uzytkownika\": 6,\n"
            + " \"id_typu\": 1, \n"
            + " \"id_disqus\": 5, \n"
            + " \"wspolrzedne\": {\n"
            + "     \"type\":\"point\",\"value\":\"(51.094703, 17.021475)\", \"x\":51.094703, \"y\":17.021475\n"
            + " }\n"
            + "}\n"
            + "}",
            "{\n"
            + "\"uzytkownicy\": {\n"
            + " \"nick\": \"nick5\","
            + " \"email\": \"email5@email.com\","
            + " \"haslo\": \"haslo\""
            + " }\n"
            + "}"
        };

        try {
            JSONObject jsonObject = new JSONObject(string[i]);

            try {
                URL[] url = {
                    new URL("http://localhost:8080/RestApi/service/zgloszenia/post"),
                    new URL("http://localhost:8080/RestApi/service/uzytkownicy/post")
                };
                URLConnection connection = url[i].openConnection();

                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject.toString());
                out.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((input = in.readLine()) != null) {
                    System.out.println(input);
                }
                System.out.println("\nSukces");
                in.close();
            } catch (Exception e) {
                System.out.println("\nNie przeszlo");
                System.out.println(e);
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}