package com.example.mrlukashem.utrudnieniaruchu;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

public class LogInActivity extends ActionBarActivity implements Runnable{
    private LoginButton FbloginButton;
    private CallbackManager callbackManager;
    private android.support.v7.app.ActionBar aBar;
    private static AccessToken accessToken = null;
    private AccessTokenTracker accessTokenTracker;
    private LoginManager loginManager;
    private String email;
    private String password;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private Toast toast;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        toast = Toast.makeText(this, "Logowanie przebiegło pomyślnie", Toast.LENGTH_LONG);

        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message __msg) {
                progressDialog.dismiss();

                if(__msg.what == 0) {
                    showCantLogInDialog();
                } else {
                    toast.show();
                    Button _log_in_button = (Button) findViewById(R.id.loginActivityLogInButton);
                    _log_in_button.setText(getResources().getString(R.string.logged_out));
                    finish();
                }

                return true;
            }
        });

        if(getIntent().getBooleanExtra("logout", false)) {
            LoginManager.getInstance().logOut();
            UserManager.getInstance().logOut();
            finish();
        }

        setContentView(R.layout.activity_log_in);
        setActionBar();
        loadLogInButtonState();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken __oldAccessToken, AccessToken __currentAccessToken) {
                if (__oldAccessToken == null) {
                    // Log in Logic
                } else if (__currentAccessToken == null) {
                    UserManager.getInstance().logOut();
                }
            }
        };

        FbloginButton = (LoginButton)findViewById(R.id.connectWithFbButton);
        FbloginButton.setReadPermissions(Arrays.asList("email"));
        FbloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View __v) {
            }
        });

        FbloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                loginManager = LoginManager.getInstance();

                GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String _id = object.getString("id");
                            String _email = object.getString("email");

                            if(!UserManager.getInstance().logInWithFB(_email, _id)) {
                                loginManager.logOut();
                            }

                            finish();
                        } catch (JSONException __json_exc) {
                            Log.e("Json exception-facebook", __json_exc.toString());
                        } catch(ClassNotFoundException __cnf_exc) {
                            Log.e("login button error", __cnf_exc.toString());
                        }
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {
                Log.e("buttn", "wylogowanie");
                accessToken = null;
            }

            @Override
            public void onError(FacebookException exception) {
                accessToken = null;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else
        if(id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finish() {
        finishActivity();
        super.finish();
    }

    private void setLogInButtonState() {
        if(UserManager.getInstance().isLoggedIn()) {
            sharedPreferences.edit().putBoolean("login_button_state", true).apply();
        } else {
            sharedPreferences.edit().putBoolean("login_button_state", false).apply();
        }
    }

    private void loadLogInButtonState() {
        Boolean _value = sharedPreferences.getBoolean("login_button_state", false);
        Button _log_in_button = (Button) findViewById(R.id.loginActivityLogInButton);
        if(_value) {
            _log_in_button.setText(getResources().getString(R.string.logged_out));
        } else {
            _log_in_button.setText(getResources().getString(R.string.logged_in));
        }
    }

    private void setActionBar() {
        if(getSupportActionBar() == null) {
            return;
        }

        aBar = getSupportActionBar();
        aBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));
        aBar.setHomeButtonEnabled(true);
        aBar.setDisplayHomeAsUpEnabled(true);
    }

    private void finishActivity() {
        Bundle _bundle = new Bundle();
        Intent _intent = new Intent();

        if(accessToken == null) {
            _bundle.putBoolean("log_in_status", false);
        }
        else {
            _bundle.putBoolean("log_in_status", true);
        }

        _intent.putExtras(_bundle);
        setResult(RESULT_OK, _intent);
    }

    public void registerButtonOnClick(View __view) {
        try {
            getFragmentManager().beginTransaction()
                    .add(RegistrationDialogFragment.newInstance(), "registrationDialog")
                    .commit();
        } catch(Exception __exc) {
            Toast.makeText(getApplicationContext(), "Nie można się zarejstrować", Toast.LENGTH_LONG).show();
            Log.e("failed commit", __exc.toString());
        }
    }

    public void loginButtonOnClick(View __view) {
        Button _log_in_button = (Button) findViewById(R.id.loginActivityLogInButton);

        if(UserManager.getInstance().isLoggedIn()) {
            UserManager.getInstance().logOut();
            _log_in_button.setText(getResources().getString(R.string.logged_in));
            setLogInButtonState();
        } else {
            EditText _email_field = (EditText) findViewById(R.id.logInActivityEmailEditText);
            EditText _password_field = (EditText) findViewById(R.id.logInActivityPasswordEditText);
            email = _email_field.getText().toString();
            password = _password_field.getText().toString();
            _email_field.setText("");
            _password_field.setText("");

            progressDialog =
                    ProgressDialog.show(LogInActivity.this, "Proszę czekać ...", "Trwa logowanie ...", true);

            handler.post(this);
        }
    }

    public void showCantLogInDialog() {
        CantLogInDialogFragment _frag = new CantLogInDialogFragment();

        getFragmentManager().beginTransaction()
                            .add(_frag, "cantLogInDialog")
                            .commit();
    }

    @Override
    public void run() {
        try {
            if(!UserManager.getInstance().logIn(email, password)) {
                handler.sendEmptyMessage(0);
            } else {
                handler.sendEmptyMessage(1);
            }
        } catch(Exception __exc) {
            handler.sendEmptyMessage(0);
            Log.e("userManagerExc", __exc.toString());
        }
    }
}
