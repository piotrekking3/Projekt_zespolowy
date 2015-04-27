package projekt_zespolowy.clienttest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.postgresql.geometric.PGpoint;

public class ClientTest
{
    private String dodajUzytkownika(String nick, String email, String haslo) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("nick", nick)
                    .put("email", email)
                    .put("haslo", haslo)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/post";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String dodajZgloszenie(int id_uzytkownika, int id_typu, int id_disqus, PGpoint punkt) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("zgloszenia", new JSONObject()
                    .put("id_uzytkownika", id_uzytkownika)
                    .put("id_typu", id_typu)
                    .put("id_disqus", id_disqus)
                    .put("wspolrzedne", punkt)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/zgloszenia/post";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String updateEmail (int id_uzytkownika, String email, String haslo) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("id_uzytkownika", id_uzytkownika)
                    .put("email", email)
                    .put("haslo", haslo)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/putEmail";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String dataTransfer(JSONObject json, String url_address) {
        String print_returned = "";

        try {
            URL url = new URL(url_address);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(json.toString());
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String input;
            while ((input = in.readLine()) != null) {
                print_returned += input + "\n";
            }

            in.close();
        } catch (IOException e) {
            if (e.toString().contains("java.net.ConnectException: Connection refused: connect")) {
                print_returned += "Zapomniales zalaczyc serwer" + "\n";
            }
            else {
                print_returned += e;
            }
        }

        return print_returned;
    }

    public static void main(String[] args) {
        ClientTest test = new ClientTest();

        String help;

        // TESTY - dokladniejsze informacje o bledach sa wypisywane w oknie serwera
        help = test.dodajUzytkownika("bob", "przyklad@email.com", "1234");
        //help = test.dodajZgloszenie(9, 1, 100, new PGpoint(51.094703, 17.021475));

        // TODO naprawic aktualizacje emaila
        //help = test.updateEmail(1, "email", "haslo");

        if (help.contains("OK")) {
            System.out.println(help);
        }
        else {
            System.out.println("+++++++BLAD+++++++\n" + help);
        }
    }
}
