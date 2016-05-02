package mx.selery.selery;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import mx.selery.entity.Activity;
import mx.selery.library.io.SeleryApiAdapter;
import mx.selery.library.ui.ActivitySecure;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PersonalInformation extends ActivitySecure {

    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private EditText weightEditText;
    private EditText heightIntEditText;
    private EditText heightDecEditText;
    private TextView birthDateTextView;
    private Date birthDate;
    private Spinner activitySpinner;
    private Button saveButton;
    int activityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            this.setShowmenu(true);
            if(!userIsLoggedIn)
            {
                finish();
                this.LoginRedirect();
                return;
            }
            setContentView(R.layout.activity_personal_information);
            birthDate= new Date();

            maleRadioButton = (RadioButton) findViewById(R.id.radiobutton_male);
            femaleRadioButton = (RadioButton) findViewById(R.id.radiobutton_female);
            weightEditText = (EditText) findViewById(R.id.edittext_weight);
            heightIntEditText = (EditText) findViewById(R.id.edittext_heightint);
            heightDecEditText = (EditText) findViewById(R.id.edittext_heightdect);
            birthDateTextView = (TextView) findViewById(R.id.text_birthdate);
            activitySpinner = (Spinner) findViewById(R.id.spinner_actividad);
            saveButton= (Button)findViewById(R.id.button_save);

            /*
                    session.getUser().setCurrentProgram(userProgram);
                    session.setUser(session.getUser());
            */
            Date bd = session.getUser().getBirthDate();

            //popular el spinner de actividades
            Callback callback = new Callback<List<Activity>>() {
                @Override
                public void success(List<Activity> programs, Response response) {
                    try
                    {
                        activitySpinner.setAdapter(new SpinAdapter(getBaseContext(), R.layout.spinner_item, programs));
                    }
                    catch(Exception e)
                    {
                        handleException(e,true);
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    handleException(retrofitError.toString());
                }
            };
            SeleryApiAdapter.getApiService().getActivity(callback);



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
}
