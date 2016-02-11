package mx.selery.library.ui;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import mx.selery.library.utility.StringHelper;

/**
 * Created by htorres on 09/02/2016.
 */
public class ActivityBase extends AppCompatActivity {


    public String getEndpoint() {
        return StringHelper.getValueFromResourceCode("endpoint",getApplicationContext());
    }

    public void reportTransient(String message) {
        reportTransient(null, message);
    }

    public void reportTransient(String tag, String message) {
        String s;
        if (tag != null)
            s = String.format("%1$s:%2$s", tag, message);
        else
            s = message;

        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void handleException(Exception ex, Boolean show) {
        if (show) {
            String error = String.format("%1: %2", StringHelper.getValueFromResourceCode("registration_error", getApplicationContext()), ex.getMessage());
            reportTransient(error);
        }
        this.handleException(ex);
    }

    public void handleException(Exception ex) {

    }

    public void handleException(String ex, Boolean show) {
        if (show) {
            String error = String.format("%1$s : %2$s", StringHelper.getValueFromResourceCode("registration_error", getApplicationContext()), ex);
            reportTransient(error);
        }
        this.handleException(ex);
    }

    public void handleException(String ex) {

    }

}
