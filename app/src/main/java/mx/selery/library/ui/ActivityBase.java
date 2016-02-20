package mx.selery.library.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import mx.selery.library.utility.StringHelper;

/**
 * Created by htorres on 09/02/2016.
 */
public class ActivityBase extends AppCompatActivity
{

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
        String s = !TextUtils.isEmpty(tag) ? String.format("%1$s:%2$s", tag, message): message;
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
