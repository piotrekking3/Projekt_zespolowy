package projekt_zespolowy.clienttest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ClientTest
{
    private String dodajUzytkownika(String email, String haslo, String facebook, String google, String typ) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("haslo", haslo)
                    .put("facebook", facebook)
                    .put("google", google)
                    .put("typ", typ)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/post";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/post";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String register(String email, String haslo) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("haslo", haslo)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/register";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/register";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }



    private String login(String email, String haslo) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("haslo", haslo)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/login";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/login";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String loginWithFacebook(String email, String facebook) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("facebook", facebook)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/loginWithFacebook";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/loginWithFacebook";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String loginWithGoogle(String email, String google) {
        JSONObject json = null;

        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("google", google)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/loginWithGoogle";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/loginWithGoogle";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String dodajZgloszenie(int id_typu, double x, double y, String opis, String adres, String email_uzytkownika, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("zgloszenia", new JSONObject()
                    .put("id_typu", id_typu)
                    .put("x", x)
                    .put("y", y)
                    .put("opis", opis)
                    .put("adres", adres)
                    .put("email_uzytkownika", email_uzytkownika)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/zgloszenia/post";
        String url_address = ("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zgloszenia/post");

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String updateStatusZgloszenia(String email, String token, int id_zgloszenia, int id_statusu) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("zgloszenia", new JSONObject()
                    .put("email", email)
                    .put("token", token)
                    .put("id_zgloszenia", id_zgloszenia)
                    .put("id_statusu", id_statusu)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/zgloszenia/updateStatusZgloszenia";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zgloszenia/updateStatusZgloszenia";

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

    public static void addZdjecie() {
        int id = 33;

        try {
            //URL url = new URL("http://localhost:8084/RestApi/service/zdjecia/addZdjecie");
            URL url = new URL("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zdjecia/addZdjecie");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            File file = new File("C:\\Users\\Sebastian\\Desktop\\test.jpg");

            //numer zgłoszenia zapisany jako ciąg bajtów
            byte[] id_zgloszenia = ByteBuffer.allocate(4).putInt(id).array();

            //zdjęcie w formie bajtów
            byte[] photo = new byte[(int)file.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(photo, 0, photo.length);

            //wyjściowy ciąg bajtów
            byte[] bytes = new byte[(int)file.length() + id_zgloszenia.length];

            //połącz numer zgłoszenia i zdjęcie w jeden, wyjściowy ciąg bajtów
            for (int i = 0; i < id_zgloszenia.length; i++) {
                bytes[i] = id_zgloszenia[i];
            }
            for (int i = id_zgloszenia.length; i < bytes.length; i++) {
                bytes[i] = photo[i - 4];
            }

            OutputStream os = connection.getOutputStream();
            os.write(bytes, 0, bytes.length);
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while (in.readLine() != null) {
            }
            System.out.println("\nSukces");
            in.close();
        } catch (Exception e) {
            System.out.println("\nNie przeszlo");
            System.out.println(e);
        }
    }

    private String updatePassword(String haslo, String email) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("haslo", haslo)
                    .put("email", email)

            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/postPassword";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/postPassword";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String updateUprawnienia(String admin_email, String token, String user_email, String uprawnienia) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("admin_email", admin_email)
                    .put("token", token)
                    .put("user_email", user_email)
                    .put("uprawnienia", uprawnienia)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/updateUprawnienia";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/updateUprawnienia";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String logout(String email, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/logout";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/logout";
        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String changeGoogle(String email, String google, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("google", google)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/changeGoogle";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/changeGoogle";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String activate(String email) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/activate";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/activate";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String dodajKomentarz(int id_zgloszenia, String email, String komentarz, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("komentarze", new JSONObject()
                    .put("id_zgloszenia", id_zgloszenia)
                    .put("email", email)
                    .put("komentarz", komentarz)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/komentarze/post";
        String url_address = ("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/komentarze/post");

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String valid(String email, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/valid";
        String url_address = ("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/valid");

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String deleteUzytkownik(String user_email, String admin_email, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("user_email", user_email)
                    .put("admin_email", admin_email)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/deleteUzytkownik";
        String url_address = ("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/deleteUzytkownik");

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String deleteZgloszenie(int id_zgloszenia, String admin_email, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("zgloszenia", new JSONObject()
                    .put("id_zgloszenia", id_zgloszenia)
                    .put("admin_email", admin_email)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8080/RestApi/service/zgloszenia/deleteZgloszenie";
        String url_address = ("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zgloszenia/deleteZgloszenie");

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    public static void main(String[] args) {
        ClientTest test = new ClientTest();

        String help = "";

        // TESTY - dokladniejsze informacje o bledach sa wypisywane w oknie serwera
        
        //help = test.dodajUzytkownika("emaijl5", "12934", "324312413", "1576657", "restapi");
        //help = test.register("kluski@makaron.pl", "1234");
        //help = test.login("superuser@a.pl", "202cb962ac59075b964b07152d234b70");
        //help = test.loginWithFacebook("srala@baba.pl", "99999999999999999999999999999999");
        //help = test.loginWithGoogle("z@mostu.pl", "99999999999999999999999999999999");
        //help = test.dodajZgloszenie(1, 100.094703, 127.021475, "opis", "adres", "kluski@makaron.pl", "a7a8f76db5659b4732560824f7a14129");
        //help = test.updatePassword("haslo", "kluski@makaron.pl");
        //help = test.updateUprawnienia("superuser@a.pl", "6a54f8c15047ab8b617849d15481fb88", "kluski@makaron.pl", "moderator");
        //help = test.updateStatusZgloszenia("superuser@a.pl", "6a54f8c15047ab8b617849d15481fb88", 86, 2);
        //help = test.logout("z@mostu.pl", "49d8a7398eab34195f48bae0249f4239");
        //help = test.changeGoogle("masa", "8123", "be0cb68524da194428020f1b76ad81a4");
        //help = test.activate("kluski@makaron.pl");
        //help = test.dodajKomentarz(86, "kluski@makaron.pl", "jebie mmnie to", "a7a8f76db5659b4732560824f7a14129");
        //help = test.valid("superuser@a.pl", "6a54f8c15047ab8b617849d15481fb88");
        //help = test.deleteUzytkownik("email2", "test@test.pl", "d71bd27be98bfa18d7cc0556acc7ee91");
        //help = test.deleteZgloszenie(44, "email", "853babe4628d53896fa08402a43d9d4a");

        System.out.println(help);
    }
}
