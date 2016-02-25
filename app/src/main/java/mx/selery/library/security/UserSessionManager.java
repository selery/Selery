package mx.selery.library.security;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.NotificationCompatSideChannelService;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.jar.JarEntry;

import mx.selery.entity.User;
import mx.selery.selery.RegistrationActivity;

/**
 * Created by Horacio Torres on 28/01/2016.
 */

public class UserSessionManager
{

    private static UserSessionManager SESSION; //singleton object
    private SharedPreferences pref;
    private Editor editor;
    private Context _context;
    private static final String USER_SESION ="USER_SESION";
    private int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "AndroidPref";
    private Gson gson;
    private User user;

    public static UserSessionManager getSessionInstnce(Context context)
    {
        if (SESSION!= null)
            return SESSION;

        SESSION = new UserSessionManager(context);
        return SESSION;
    }

    public User getUser() throws  Exception
    {
        if (this.user!=null) return this.user;
        if(pref.getString(USER_SESION, null)==null) return null;
        return gson.fromJson(pref.getString(USER_SESION, null),User.class);
    }

    public void setUser(User user) {
        this.user=user;
        editor.putString(USER_SESION, gson.toJson(user));
        editor.commit();
    }

    // Constructor
    public UserSessionManager(Context context){
        gson= new Gson();
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        user=null;

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, RegistrationActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

}
