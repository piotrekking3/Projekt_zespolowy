package com.example.mrlukashem.utrudnieniaruchu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by mrlukashem on 20.05.15.
 */
public class CommentsDialogFragment extends FragmentActivity {

    private CustomMarkerContentAdapter adapter;
    private int problemId;
    private static String getComments
            = "http://virt2.iiar.pwr.edu.pl/api/komentarze/getById/";
    private final int MIN_CONTENT_LENGTH = 10;

    @Override
    public void onCreate(Bundle __saved_instance_state) {
        super.onCreate(__saved_instance_state);
        setContentView(R.layout.dialog_comments);

        problemId = getIntent().getIntExtra("id", 0);

        adapter = new CustomMarkerContentAdapter(getApplicationContext(), R.id.commentsContainter);
        ListView _list = (ListView)findViewById(R.id.commentsContainter);
        _list.setAdapter(adapter);

        new GetComments().execute(problemId);
    }

    public void sendNewCommentOnClick(View __view) {
        EditText _comment = (EditText)findViewById(R.id.username);
        String _content = _comment.getText().toString();

        if(!UserManager.getInstance().isLoggedIn()) {
            Toast.makeText(this, "Trzeba być zalogowanym aby dodawać komentarze", Toast.LENGTH_LONG).show();
            return;
        }

        if(_content.length() < MIN_CONTENT_LENGTH) {
            _comment.setError("Komentarz musi mieć przynajmniej " + MIN_CONTENT_LENGTH + " znaków");
            return;
        }

        new CallAPI
                .SendComment()
                .execute(String.valueOf(problemId), _content);

        String _login = UserManager.getInstance().getEmail().split("@")[0];
        Calendar _cal = Calendar.getInstance();
        _cal.getTime();
        SimpleDateFormat _sdf = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
        adapter.add(Comment.getInstance(_login, _sdf.format(_cal.getTime()), _content));
        adapter.notifyDataSetChanged();
    }

    public class GetComments extends AsyncTask<Integer, String, String> {
        @Override
        protected String doInBackground(Integer... __params) {
            Integer _problem_id = __params[0];

            String _json_result = null;
            InputStream _in;
            BufferedReader _reader = null;

            try {
                String _link = getComments + String.valueOf(_problem_id);
                URL _url = new URL(_link);
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
        protected void onPostExecute(String __result) {
            try {
                JSONArray _array = new JSONArray(__result);
                for(int i = 0; i < _array.length(); i++) {
                    JSONObject _element = _array.getJSONObject(i);
                    JSONObject _single_comment = _element.getJSONObject("komentarze");
                    Log.e("json_array", _single_comment.toString());
                    String _login = _single_comment.getString("login");
                    String _comment = _single_comment.getString("komentarz");
                    String _date = _single_comment.getString("data_nadania");

                    adapter.add(Comment.getInstance(_login, _date, _comment));
                }

                adapter.notifyDataSetChanged();
            } catch (JSONException __json_exc) {
                Log.e("GetCommentOnPost", __json_exc.toString());
            } catch(Exception __exc) {
                Log.e("GetCommentOnPost", __exc.toString());
            }
        }
    }
}
