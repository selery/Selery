package mx.selery.library.ui;

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
        reportTransient(null,message,messageType);
    }

    public void reportTransient(String tag, String message) {
        reportTransient(tag,message,null);
    }

    public void reportTransient(String tag, String message, MessageType messageType)
    {
        String s = !TextUtils.isEmpty(tag) ? String.format("%1$s:%2$s", tag, message): message;
        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
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
        return alertBox(title,message,positiveButton,null,null,null,messageType);
    }

    public void handleException(Exception ex, Boolean show) {
        if (show) {
            String error = String.format("%1$s:%2$s", StringHelper.getValueFromResourceCode("misc_error", getApplicationContext()), ex.getMessage());
            reportTransient(error,MessageType.Error);
        }
        this.handleException(ex);
    }

    public void handleException(Exception ex) {

    }

    public void handleException(String ex, Boolean show) {
        if (show) {
            String error = String.format("%1$s : %2$s", StringHelper.getValueFromResourceCode("misc_error", getApplicationContext()), ex);
            reportTransient(error,MessageType.Error);
        }
        this.handleException(ex);
    }

    public void handleException(String ex) {

    }

}
