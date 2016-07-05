package mx.selery.selery;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.selery.entity.User;
import mx.selery.library.security.UserSessionManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import mx.selery.entity.Activity;
import mx.selery.library.io.SeleryApiAdapter;
import mx.selery.library.ui.ActivitySecure;
import mx.selery.library.utility.StringHelper;
import mx.selery.library.utility.DateTimeHelper;


/**
 * Created by htorres on 04/07/2016.
 */
public class DiagnosticActivity extends ActivitySecure {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            this.setShowmenu(true);
            if (!userIsLoggedIn) {
                finish();
                this.LoginRedirect();
                return;
            }
            setContentView(R.layout.activity_diagnostic);

        }
        catch(Exception ex)
        {
            handleException(ex,true);
        }

    }

    @Override
    protected void initializeFormFields()
    {

    }

    @Override
    public void onBackPressed(){
        try
        {

            super.onBackPressed();
        }
        catch(Exception ex)
        {
            handleException(ex,true);
        }
    }
}
