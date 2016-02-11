package mx.selery.library.ui;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by htorres on 09/02/2016.
 */
public class ActivityBase extends AppCompatActivity {

    public void reportTransient(String message)	{
        reportTransient(null,message);
    }

    public void reportTransient(String tag, String message)
    {
        String s;
        if (tag!=null)
            s = String.format ("%1$s:%2$s", tag, message);
        else
            s = message;

        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void handleException(Exception ex, Boolean show)
    {

    }

    public void handleException(String ex, Boolean show)
    {

    }

}
