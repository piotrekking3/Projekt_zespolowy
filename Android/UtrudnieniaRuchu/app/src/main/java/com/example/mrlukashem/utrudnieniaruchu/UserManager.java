package com.example.mrlukashem.utrudnieniaruchu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.util.Patterns;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by mrlukashem on 06.05.15.
 */
public class UserManager {

    private enum SessionKind {
        normal,
        facebook,
        google
    }

    public enum ErrorsKinds {
        no_error,
        json_error,
        server_error
    }

    private Context context;
    private String email;
    private String password;
    private String token;
    private String fbId;
    private SessionKind sessionKind;

    private ErrorsKinds error;
    private boolean isLogged = false;
    private final static Integer MIN_SDK = 19;
    private SharedPreferences sharedPreferences;

    private static UserManager userManager = new UserManager();
    private UserManager() { }

    public static UserManager getInstance() {
        return userManager;
    }

    public boolean isLoggedIn() {
        return isLogged;
    }

    public void setContext(Context __con) {
        context = __con;
    }

    public void logOut() {
        isLogged = false;
        token = null;
        email = null;
        saveStateToSP();
    }

    public void setError(ErrorsKinds __error) {
        error = __error;
    }

    public void resetErrors() {
        error = ErrorsKinds.no_error;
    }

    public boolean logInWithFB(String __email, String __id)
            throws IllegalArgumentException, NullPointerException, ClassNotFoundException {

        error = ErrorsKinds.no_error;

        if(Build.VERSION.SDK_INT >= MIN_SDK) {
            if(__email == null) {
                throw new NullPointerException();
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(__email).matches()) {
                throw new IllegalArgumentException("Wrong email format");
            }
            if(__id == null) {
                throw new NullPointerException();
            }

            email = __email;
            fbId = __id;
        } else {
            if(__email == null || __id == null) {
                throw new NullPointerException("Param cannot be null");
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(__email).matches()) {
                throw new IllegalArgumentException("Wrong email format");
            }

            email = __email;
            fbId = __id;
        }

        try {
            CallAPI.getInstance().logInUserWithFB().get();
        } catch(InterruptedException __exc) {
            Log.e("interrupted Exc", __exc.toString());
            return false;
        } catch(ExecutionException __exc) {
            Log.e("Execution Exc", __exc.toString());
            return false;
        } catch(Exception __exc) {
            Log.e("Exc", __exc.toString());
            return false;
        }

  //      if(error != ErrorsKinds.no_error) {
   //         return false;
   //     }

        sessionKind = SessionKind.facebook;
        isLogged = true;
        saveStateToSP();

        return true;
    }

    public boolean logIn(String __email, String __password)
            throws IllegalArgumentException, NullPointerException, ClassNotFoundException {

        error = ErrorsKinds.no_error;

        if(Build.VERSION.SDK_INT >= MIN_SDK) {
            if(__email == null) {
                throw new NullPointerException();
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(__email).matches()) {
                throw new IllegalArgumentException("Wrong email format");
            }
            if(__password == null) {
                throw new NullPointerException();
            }

            email = __email;
            password = getMD5(__password);
        } else {
            if(__email == null || __password == null) {
                throw new NullPointerException("Param cannot be null");
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(__email).matches()) {
                throw new IllegalArgumentException("Wrong email format");
            }

            email = __email;
            password = getMD5(__password);
        }

        try {
            CallAPI.getInstance().logInUser().get();
        } catch(InterruptedException __exc) {
            Log.e("interrupted Exc", __exc.toString());
        } catch(ExecutionException __exc) {
            Log.e("Execution Exc", __exc.toString());
        } catch(Exception __exc) {
            Log.e("Exc", __exc.toString());
        }

        password = null;

        if(error != ErrorsKinds.no_error) {
            saveStateToSP();
            return false;
        }

        sessionKind = SessionKind.normal;
        isLogged = true;
        saveStateToSP();

        return true;
    }

    public void adduser(String __email, String __password) throws IllegalArgumentException, NullPointerException {
        if(Build.VERSION.SDK_INT >= MIN_SDK) {
            if(__email == null) {
                throw new NullPointerException();
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(__email).matches()) {
                throw new IllegalArgumentException("Wrong email format");
            }
            if(__password == null) {
                throw new NullPointerException();
            }
            if(context == null) {
                throw new NullPointerException("Context is not set");
            }

            CallAPI.getInstance().addUser(
                    __email,
                    getMD5(__password),
                    context);
        } else {
            if(__email == null || __password == null) {
                throw new NullPointerException("Param cannot be null");
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(__email).matches()) {
                throw new IllegalArgumentException("Wrong email format");
            }
            if(context == null) {
                throw new NullPointerException("Context is not set");
            }

            CallAPI.getInstance().addUser(
                    __email,
                    getMD5(__password),
                    context
            );
        }
    }

    public void addUserWithFB(String __email, String __fb_id) throws IllegalArgumentException, NullPointerException {
        if(Build.VERSION.SDK_INT >= MIN_SDK) {
            if(__email == null) {
                throw new NullPointerException();
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(__email).matches()) {
                throw new IllegalArgumentException("Wrong email format");
            }
            if(__fb_id == null) {
                throw new NullPointerException();
            }

            CallAPI.getInstance().addUserWithFB(
                    __email,
                    __fb_id,
                    context);
        } else {
            if(__email == null || __fb_id == null) {
                throw new NullPointerException("Param cannot be null");
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(__email).matches()) {
                throw new IllegalArgumentException("Wrong email format");
            }

            CallAPI.getInstance().addUser(
                    __email,
                    __fb_id,
                    context
            );
        }
    }

    public String getEmail() {
        return email;
    }

    public void saveStateToSP() {
        if(sharedPreferences != null) {
            sharedPreferences.edit()
                    .putBoolean("isLogged", isLogged)
                    .putString("email", email)
                    .apply();
        }
    }

    public void loadLastStateFromSP() {
        if(sharedPreferences != null) {
            Boolean _value = sharedPreferences.getBoolean("isLogged", false);
            String _email = sharedPreferences.getString("email", "");
            isLogged = _value;
            email = _email;
        }
    }

    public void setSharedPreferences(SharedPreferences __sharedPreferences) {
        sharedPreferences = __sharedPreferences;
    }

    public void setToken(String __token) {
        token = __token;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    public String getFBId() {
        return fbId;
    }

    public boolean setFBId(String __id) {
        if(SessionKind.facebook == sessionKind) {
            fbId = __id;
            return true;
        } else {
            return false;
        }
    }

    public static String getMD5(String __input) {
        try {
            MessageDigest _md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = _md.digest(__input.getBytes());
            BigInteger _number = new BigInteger(1, messageDigest);
            String _hashtext = _number.toString(16);
            while (_hashtext.length() < 32) {
                _hashtext = "0" + _hashtext;
            }
            return _hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
