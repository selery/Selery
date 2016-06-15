package mx.selery.library.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import mx.selery.library.security.UserSessionManager;
import mx.selery.library.utility.StringHelper;
import mx.selery.selery.ProgramListActivity;
import mx.selery.selery.R;

/**
 * Created by htorres on 09/02/2016.
 */
public class ActivityBase extends AppCompatActivity
{
    private Boolean showmenu=false;
    public Boolean getShowmenu() {
        return showmenu;
    }

    public void setShowmenu(Boolean showmenu) {
        this.showmenu = showmenu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_seleryheader);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(this.showmenu)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try
                {
                    String parent= NavUtils.getParentActivityName(this);
                    Intent intenet = new Intent().setClass(getBaseContext(), Class.forName(parent));
                    intenet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intenet);
                }
                catch(Exception ex)
                {
                    handleException(ex);
                }

               break;
            // action with ID action_refresh was selected
            case R.id.Logout:
                UserSessionManager session =UserSessionManager.getSessionInstnce(getApplicationContext());
                session.logoutUser();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public enum MessageType
    {
        Asterisk,Error,Exclamation,Hand,Information,None,Question,Stop,Warning
    }

    public String getEndpoint() {
        return StringHelper.getValueFromResourceCode("endpoint",getApplicationContext());
    }

    public void reportTransient(String message) {
        reportTransient(null, message);
    }

    public void reportTransient(String message, MessageType messageType)
    {
        reportTransient(null,message,messageType,getApplicationContext());
    }

    public void reportTransient(String tag, String message) {
        reportTransient(tag, message, null,getApplicationContext());
    }

    public static void reportTransient(String message, MessageType messageType, Context context)
    {
        reportTransient(null, message, messageType, context);
    }

    public static void reportTransient(String tag, String message, MessageType messageType, Context context)
    {
        String s = !TextUtils.isEmpty(tag) ? String.format("%1$s : %2$s", tag, message) : message;
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        toast.show();
    }

    public AlertDialog alertBox(String title,
                                String message,
                                String positiveButton,
                                String negativeButton,
                                DialogInterface.OnClickListener positiveListener,
                                DialogInterface.OnClickListener negativeListener,
                                MessageType messageType)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        if (positiveButton!= null) builder.setPositiveButton(positiveButton, positiveListener!=null ? positiveListener : null);
        if (negativeButton!= null) builder.setNegativeButton(negativeButton, negativeListener!=null ? negativeListener : null);
        return builder.create();
    }
    public AlertDialog alertBox(String title,String message,String positiveButton,MessageType messageType)
    {
        return alertBox(title, message, positiveButton, null, null, null, messageType);
    }

    public void handleException(Exception ex, Boolean show)
    {
        if (show) reportTransient(ex.getMessage(),MessageType.Error);
        handleException(ex);
    }

    public void handleException(String ex, Boolean show)
    {
        if (show) reportTransient(ex,MessageType.Error);
        handleException(ex);
    }

    public static void handleException(Exception ex, Boolean show, Context context)
    {
        if (show) reportTransient(ex.getMessage(),MessageType.Error,context);
        handleException(ex);
    }

    public static void handleException(Exception ex)
    {
        handleException(ex,false,null);
    }

    public static void handleException(String ex)
    {
        int x=0;
    }




}
