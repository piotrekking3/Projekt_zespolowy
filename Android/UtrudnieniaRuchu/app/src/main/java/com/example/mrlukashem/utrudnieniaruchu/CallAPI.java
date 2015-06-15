package com.example.mrlukashem.utrudnieniaruchu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;

/**
 * Created by mrlukashem on 08.04.15.
 */
public class CallAPI {
    //TODO: Opis!
    private static String usersString
            = "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/getAll";
    private static String usersByIdString
            = "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/getById";
    private static String problemsListString
            = "http://virt2.iiar.pwr.edu.pl/api/zgloszenia/getAll";
    private static String problemsByIdString
            = "http://virt2.iiar.pwr.edu.pl/api/zgloszenia/getById";
    private static String problemsByTypeString
            = "http://virt2.iiar.pwr.edu.pl/api/zgloszenia/getByType";
    private static String addNewUserString
            = "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/register";
    private static String addNewUserWithFB
            = "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/registerWithFacebook";
    private static String loginUser
            = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/login";
    private static String loginUserWithFB
            = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/loginWithFacebook";
    private static String addNewProblemString
            = "http://virt2.iiar.pwr.edu.pl/api/zgloszenia/post";
    private static String geoCodding
            = "https://maps.googleapis.com/maps/api/geocode/";
    private static String postComment
            = "http://virt2.iiar.pwr.edu.pl/api/komentarze/post";

    private CallAPI() { }

    private static CallAPI callAPIInstance =
            new CallAPI();

    public static CallAPI getInstance() {
        return callAPIInstance;
    }

    public enum Platform {
        app,
        facebook,
        google
    }

    public enum GetOperation {
        GET_USERS_LIST {
            @Override
            public String toString() {
                return usersString;
            }
        },
        GET_USER_BY_ID {
            @Override
            public String toString() {
                return usersByIdString;
            }
        },
        GET_PROBLEMS_LIST {
            @Override
            public String toString() {
                return problemsListString;
            }
        },
        GET_PROBLEM_BY_ID {
            @Override
            public String toString() {
                return problemsByIdString;
            }
        },
        GET_PROBLEMS_BY_TYPE {
            @Override
            public String toString() {
                return problemsByTypeString;
            }
        }
    }

    public AsyncTask getProblemsToHandler() {
        GetApi getApi = new GetApi(
                GetOperation.GET_PROBLEMS_LIST);
        getApi.execute();

        return getApi;
    }

    public AsyncTask getProblemsToHandlerWithRefreshBtn(RefreshSetup __setup) {
        GetApi getApi = new GetApi(
                GetOperation.GET_PROBLEMS_LIST,
                __setup);
        getApi.execute();

        return getApi;
    }

    public AsyncTask logInUser() {
        PostLoginUserApi loginUserApi = new PostLoginUserApi(Platform.app);
        loginUserApi.execute();

        return loginUserApi;
    }

    public AsyncTask logInUserWithFB() {
        PostLoginUserApi logInUserWithFBApi = new PostLoginUserApi(Platform.facebook);
        logInUserWithFBApi.execute();

        return logInUserWithFBApi;
    }

    public AsyncTask addProblem(ProblemInstance __problem) {
        PostAddProblemApi postApi = new PostAddProblemApi(__problem);
        postApi.execute(__problem);

        return postApi;
    }

    public AsyncTask addUser(String __email,  String __password, Context __con) {
        PostAddUserApi postApi = new PostAddUserApi(Platform.app, __con);
        postApi.execute(__email, __password);

        return postApi;
    }

    public AsyncTask addUserWithFB(String __email, String __id, Context __con) {
        PostAddUserApi postApi = new PostAddUserApi(Platform.facebook, __con);
        postApi.execute(__email, __id);

        return postApi;
    }

    public static class GetLocationByCords extends AsyncTask<LatLng, Void, Void> {

        private Context context;
        private String key;
        private ProblemInstance problemInstance;

        public GetLocationByCords(Context __context, ProblemInstance __problemInstance) {
            context = __context;
            problemInstance = __problemInstance;
        }

        @Override
        protected void onPreExecute() {
            key = context.getResources().getString(R.string.google_maps_key);
        }

        @Override
        protected Void doInBackground(LatLng... __params) {
            String _link = geoCodding;
            _link += "json?latlng=";
            _link += __params[0].latitude;
            _link += "," + __params[0].longitude + "&key=";
            _link += "AIzaSyCPc5w_ZvhKGmxfcn4pTeiS4sHVI-j5aHA";

            String _out_put = transferData(null, _link);
            if(_out_put.isEmpty()) {
                problemInstance.setAddress("Ulica nieokreślona");
                return null;
            }

            JSONObject _json;
            try {
                _json = new JSONObject(_out_put);
                JSONArray _arrray = _json.getJSONArray("results");
                JSONObject _address = _arrray.getJSONObject(0);
                problemInstance.setAddress(_address.getString("formatted_address"));

            } catch (JSONException __exc) {
                Log.e("json exc", __exc.toString());
            }

            return null;
        }
    }

    public class GetApi extends AsyncTask<String, String, String> {
        public GetApi(GetOperation __mode, RefreshSetup __rereshSetup) {
            mode = __mode;
            setup = __rereshSetup;
        }

        public GetApi(GetOperation __mode) {
            mode = __mode;
        }

        protected GetOperation mode;
        private RefreshSetup setup;

        @Override
        protected String doInBackground(String... __params) {
            String _json_result = null;
            InputStream _in;
            BufferedReader _reader = null;

            try {
                URL _url = new URL(mode.toString());
                HttpURLConnection _url_connection = (HttpURLConnection)_url.openConnection();
                _in = new BufferedInputStream(_url_connection.getInputStream());
                _reader = new BufferedReader(new InputStreamReader(_in));

            } catch(Exception __e) {
                Log.e("connection exc", "Nie mozna ustanowic polaczenia");
            }

            try {
                String _tmp = "";
                while((_json_result = _reader.readLine()) != null) {
                    _tmp += _json_result;
                }
                _json_result = _tmp;
            } catch(Exception __e) {
                Log.e("reader failed", "Blad podczas czytania strumienia");
            }

            return _json_result;
        }

        @Override
        protected void onPostExecute(String __json_result) {
            try {
                JSONArray _array = new JSONArray(__json_result);

                switch (mode) {
                    case GET_PROBLEMS_LIST:
                    for (int i = 0; i < _array.length(); i++) {
                        JSONObject _object = _array.getJSONObject(i).getJSONObject("zgloszenia");
                        ProblemInstance.ProblemData _data = ProblemInstance.createProblemData(
                                _object.getString("opis"),
                                "przykladowy_email@email.com",
                                _object.getString("kalendarz"),
                                _object.getString("adres"),
                                _object.getInt("id_typu"),
                                _object.getInt("id_zgloszenia"),
                                new LatLng(_object.getJSONObject("wspolrzedne").getDouble("x"),
                                        _object.getJSONObject("wspolrzedne").getDouble("y"))
                        );
                        ObjectsOnMapHandler.objectsOnMapHandler.addProblem(_data);
                    }
                    break;

                    case GET_PROBLEMS_BY_TYPE:
                    break;

                    case GET_USER_BY_ID:
                    break;

                    case GET_USERS_LIST:
                    break;
                }
            } catch(JSONException __jexc) {
                Log.e("json error", __jexc.toString());
            } catch(Exception __exc) {
                Log.e("problem object build", __exc.toString());
            }

            if(setup != null) {
                setup.resetUpdating();
            }
        }
    }

    public class PostAddUserApi extends AsyncTask<String, Integer, Integer> {

        private Platform mode;
        private Context context;

        public PostAddUserApi(Platform __mode, Context __con) {
            mode = __mode;
            context = __con;
        }

        @Override
        protected Integer doInBackground(String... __params) {
            JSONObject _json_out = null;

            if(mode == Platform.app) {
                try {
                    String _string = "{\"uzytkownicy\":\n"
                            + "{\"email\":\"" + __params[0] + "\",\n"
                            + "\"haslo\":\"" + __params[1] + "\"}\n"
                            + "}";

                    _json_out = new JSONObject(_string);
                } catch (JSONException __json_exc) {
                    Log.e("convertProblem error", __json_exc.toString());
                    UserManager.getInstance().setError(UserManager.ErrorsKinds.json_error);
                    return -1;
                }

                String result = transferData(_json_out, addNewUserString);
                if(result.equals("error")) {
                    return -1;
                } else {
                    return 0;
                }

            }
            else if(mode == Platform.facebook) {
                try {
                    _json_out = new JSONObject()
                            .put("uzytkownicy", new JSONObject()
                                            .put("email", __params[0])
                                            .put("facebook", __params[1])
                            );
                } catch(JSONException __json_exc) {
                    Log.e("convertProblem error", __json_exc.toString());
                    UserManager.getInstance().setError(UserManager.ErrorsKinds.json_error);
                    return -1;
                }

                String _output = transferData(_json_out, addNewUserWithFB);
                if(_output.equals("error")) {
                    return -1;
                } else {
                    return 0;
                }
            }

            return 0;
        }

        @Override
        public void onPostExecute(Integer __result) {
            if(__result == 0) {
                Toast.makeText(context, "Rejestracja przebiegła pomyślnie", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nie można się zarejstrować", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class PostLoginUserApi extends AsyncTask<Void, Void, Void> {

        private Platform mode;

        public PostLoginUserApi(Platform __mode) {
            mode = __mode;
        }

        @Override
        protected Void doInBackground(Void... __params) {

            JSONObject _json_out = null;

            if(mode == Platform.app) {
                try {
                    String _string = "{\"uzytkownicy\":\n"
                            + "{\"email\":\"" + UserManager.getInstance().getEmail() + "\",\n"
                            + "\"haslo\":\"" + UserManager.getInstance().getPassword() + "\"}\n"
                            + "}";

                    _json_out = new JSONObject(_string);
                } catch (JSONException __json_exc) {
                    Log.e("convertProblem error", __json_exc.toString());
                    UserManager.getInstance().setError(UserManager.ErrorsKinds.json_error);
                }

                String _out;
                if((_out = transferData(_json_out, loginUser)) != null) {
                    try {
                        JSONObject _json = new JSONObject(_out);
                        UserManager.getInstance().setToken(_json.getString("token"));
                    } catch(JSONException __json_exc) {
                        Log.e("convertProblem error", __json_exc.toString());
                        UserManager.getInstance().setError(UserManager.ErrorsKinds.json_error);
                    }
                }
            }
            else
            if(mode == Platform.facebook) {
                try {
                    _json_out = new JSONObject()
                            .put("uzytkownicy", new JSONObject()
                                            .put("email", UserManager.getInstance().getEmail())
                                            .put("facebook", UserManager.getInstance().getFBId())
                            );
                    Log.e("json", _json_out.toString());
                } catch (JSONException __json_exc) {
                    Log.e("convertProblem error", __json_exc.toString());
                    UserManager.getInstance().setError(UserManager.ErrorsKinds.json_error);
                }

                String _out;
                if((_out = transferData(_json_out, loginUserWithFB)) != null) {
                    try {
                        JSONObject _json = new JSONObject(_out);
                        UserManager.getInstance().setToken(_json.getString("token"));
                    } catch(JSONException __json_exc) {
                        Log.e("convertProblem error", __json_exc.toString());
                        UserManager.getInstance().setError(UserManager.ErrorsKinds.json_error);
                    }
                }
            }

            return null;
        }
    }

    public static String transferData(JSONObject __json, String __address) {
        try {
            URL _url = new URL(__address);
            URLConnection _url_connection = _url.openConnection();

            _url_connection.setDoOutput(true);
            _url_connection.setRequestProperty("Content-Type", "application/json");
            _url_connection.setConnectTimeout(5000);
            _url_connection.setReadTimeout(5000);

            if(__json != null) {
                OutputStreamWriter _out = new OutputStreamWriter(_url_connection.getOutputStream());

                _out.write(__json.toString());
                _out.close();
            }

            BufferedReader _reader = new BufferedReader(new InputStreamReader(_url_connection.getInputStream()));
            String _reader_out;
            String _result = "";
            while ((_reader_out = _reader.readLine()) != null) {
       //         Log.e("serwer msg:", _reader_out);
                _result += _reader_out;
            }
            _reader.close();

            return _result;
        } catch (Exception __e) {
            Log.e("connection exc", "Nie mozna ustanowic polaczenia" + __e.toString());
            UserManager.getInstance().setError(UserManager.ErrorsKinds.server_error);
            return "error";
        }
    }

    public class PostAddProblemApi extends AsyncTask<ProblemInstance, String, Integer> {

        private ProblemInstance problem;

        public PostAddProblemApi(ProblemInstance __problem) {
            problem = __problem;
        }

        @Override
        protected Integer doInBackground(ProblemInstance... __params) {
            JSONObject _json_out;
            int _id = -1;
            try {
                _json_out = new JSONObject()
                        .put("zgloszenia", new JSONObject()
                                        .put("id_typu", __params[0].getCategoryId() + 1)
                                        .put("x", __params[0].getCords().latitude)
                                        .put("y", __params[0].getCords().longitude)
                                        .put("opis", __params[0].getContent())
                                        .put("adres", __params[0].getAddress())
                                        .put("email_uzytkownika", UserManager.getInstance().getEmail())
                                        .put("token", UserManager.getInstance().getToken())
                        );
                Log.e("json", _json_out.toString());
            } catch (JSONException __json_exc) {
                Log.e("convertProblem error", __json_exc.toString());
                return _id;
            }

            OutputStreamWriter _out;
            BufferedReader _reader;

            try {
                URL _url = new URL(addNewProblemString);
                URLConnection _url_connection = _url.openConnection();
                _url_connection.setDoOutput(true);
                _url_connection.setRequestProperty("Content-Type", "application/json");
                _url_connection.setConnectTimeout(5000);
                _url_connection.setReadTimeout(5000);
                _out = new OutputStreamWriter(_url_connection.getOutputStream());
                _out.write(_json_out.toString());
                _out.close();


                _reader = new BufferedReader(new InputStreamReader(_url_connection.getInputStream()));
                String _reader_out;
                while ((_reader_out = _reader.readLine()) != null) {
                    Log.e("ADD_NEW_PROBLEM error", _reader_out);
                    JSONObject _json = new JSONObject(_reader_out);
                    _id = _json.getInt("id_zgloszenia");
                }
                _reader.close();

            } catch (Exception __e) {
                Log.e("connection exc", "Nie mozna ustanowic polaczenia" + __e.toString());
                return -2;
            }

            return _id;
        }

        @Override
        protected void onPostExecute(Integer __id) {
            if(__id <= -1) {
                ObjectsOnMapHandler.objectsOnMapHandler.removeProblem(problem);
            } else {
                problem.setId(__id);

                if(MainActivity.getImgResource() != null) {
                    Log.e("addProblemCallApionPost", "getImgResource() != null");
                    try {
                        new CallAPI.SendImg(
                                MainActivity.getImgResource(),
                                ObjectsOnMapHandler.objectsOnMapHandler.getContext()
                        ).execute(__id);
                    } catch (Exception __e) {
                        Log.e("AddProblemCallApi", __e.toString());
                        Toast.makeText(
                                ObjectsOnMapHandler.objectsOnMapHandler.getContext(),
                                "Zdjęcie nie mogło zostać dodane",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }
        }
    }

    public static class GetImgFromHttp extends AsyncTask<Integer, Bitmap, Bitmap> {

        private ImageView imageView;

        public GetImgFromHttp(ImageView __img_view) {
            imageView = __img_view;
        }

        @Override
        protected Bitmap doInBackground(Integer... __params) {
            try {
                Integer _id = __params[0];
                Log.e("GetImgFromHttp", "id: " + _id);
                String _url_string = "http://virt2.iiar.pwr.edu.pl/api/zdjecia/getById/" + String.valueOf(_id);
                URL url = new URL(_url_string);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                return bmp;
            } catch(Exception __e) {
                Log.e("GetImgFromHttp", __e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap __bit_map) {
            if((__bit_map == null) && (imageView == null)) {
                return;
            }

            imageView.setImageBitmap(__bit_map);
        }
    }

    public static class SendComment extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... __params) {
            int _id = Integer.valueOf(__params[0]);
            String _coment = __params[1];

            if(!UserManager.getInstance().isLoggedIn()) {
                return null;
            }

            try {
                JSONObject _json = new JSONObject();
                _json
                        .put("komentarze", new JSONObject()
                                .put("id_zgloszenia", _id)
                                .put("email", UserManager.getInstance().getEmail())
                                .put("komentarz", _coment)
                                .put("token", UserManager.getInstance().getToken()));

                String _out;
                if((_out = transferData(_json, postComment)) != null) {
                    try {
                        JSONObject _json_out = new JSONObject(_out);
                        Log.e("success_server", _json_out.toString());
                    } catch(JSONException __json_exc) {
                        Log.e("convertProblem error", __json_exc.toString());
                    }
                }

                Log.e("json_commen", _json.toString());
            } catch (JSONException __json_exc) {
                Log.e("json_exc", __json_exc.toString());
            }

            return null;
        }
    }

    public static class SendImg extends AsyncTask<Integer, Void, Void> {

        private File file;
        private Context context;

        public SendImg(File __file, Context __con) {
            file = __file;
            context = __con;
        }

        @Override
        protected Void doInBackground(Integer... __params) {
            if(__params == null) {
                return null;
            }

            int _id = __params[0];

            try {
                URL _url = new URL("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zdjecia/addZdjecie");
                URLConnection _connection = _url.openConnection();
                _connection.setDoOutput(true);
                _connection.setRequestProperty("Content-Type", "multipart/form-data");
                _connection.setConnectTimeout(5000);
                _connection.setReadTimeout(5000);

                //numer zgłoszenia zapisany jako cišg bajtów
                byte[] _id_zgloszenia = ByteBuffer.allocate(4).putInt(_id).array();

                //zdjęcie w formie bajtów
                byte[] _photo = new byte[(int)file.length()];
                BufferedInputStream _bis = new BufferedInputStream(new FileInputStream(file));
                _bis.read(_photo, 0, _photo.length);

                //wyjciowy cišg bajtów
                byte[] _bytes = new byte[(int)file.length() + _id_zgloszenia.length];

                //połšcz numer zgłoszenia i zdjęcie w jeden, wyjciowy cišg bajtów
                for (int i = 0; i < _id_zgloszenia.length; i++) {
                    _bytes[i] = _id_zgloszenia[i];
                }
                for (int i = _id_zgloszenia.length; i < _bytes.length; i++) {
                    _bytes[i] = _photo[i - 4];
                }

                OutputStream _os = _connection.getOutputStream();
                _os.write(_bytes, 0, _bytes.length);
                _os.flush();

                BufferedReader _in = new BufferedReader(new InputStreamReader(_connection.getInputStream()));

                while (_in.readLine() != null) {
                }
                System.out.println("\nSukces");
                _in.close();

            } catch (Exception e) {
                if(context != null) {
                    Toast.makeText(
                            context,
                            "Zdjęcie nie mogło zostać dodane",
                            Toast.LENGTH_LONG
                    ).show();
                }

                System.out.println("\nNie przeszlo");
                System.out.println(e);
            }

            return null;
        }
    }
}
