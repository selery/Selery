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

import mx.selery.library.security.UserSessionManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import mx.selery.entity.Activity;
import mx.selery.library.io.SeleryApiAdapter;
import mx.selery.library.ui.ActivitySecure;
import mx.selery.library.utility.StringHelper;
import mx.selery.library.utility.DateTimeHelper;

public class PersonalInformationActivity extends ActivitySecure {

    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private EditText weightEditText;
    private EditText heightIntEditText;
    private EditText heightDecEditText;
    private static TextView birthDateTextView;
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

            saveButton.setOnClickListener(new View.OnClickListener()
            {
                 public void onClick(View v)
                 {
                    try
                    {
                        if(TextUtils.isEmpty(birthDateTextView.getText().toString()))
                        {
                            reportTransient(StringHelper.getValueFromResourceCode("reg_personal_information_birthdateformat", v.getContext()));
                            return;
                        }

                        int age = UserSessionManager.UserAge(DateTimeHelper.toDate(birthDateTextView.getText().toString(),v.getContext()));
                        if (age<AppConstants.MIN_USER_AGE)
                        {
                            String msg1=StringHelper.getValueFromResourceCode("reg_min_user_age", v.getContext());
                            String msg2=StringHelper.getValueFromResourceCode("misc_years", v.getContext());
                            reportTransient(String.format("%1$s %2$s %3$s",msg1,msg2,AppConstants.MIN_USER_AGE));
                            return;
                        }
                        // TODO: update profile
                        //Intent intenet = new Intent().setClass(getBaseContext(), HomeActivity.class);
                        //startActivity(intenet);
                    }
                    catch(Exception ex)
                    {
                        handleException(ex,true);
                    }

                 }
            });

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
                        activitySpinner.setAdapter(new SpinAdapter(getBaseContext(), R.layout.support_simple_spinner_dropdown_item , programs));
                    }
                    catch(Exception ex)
                    {
                        handleException(ex,true);
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            int year=0;
            int month=0;;
            int day=0;
            try
            {
                final Calendar c = Calendar.getInstance();
                if(!TextUtils.isEmpty(birthDateTextView.getText().toString()))
                {
                    Date date = DateTimeHelper.toDate(birthDateTextView.getText().toString(),this.getContext());
                    c.setTime(date);
                }
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }
            catch(Exception ex)
            {
                handleException(ex,true,this.getContext());
            }

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day)
        {
           try
           {
               //el mes lo regresa 0:enero 1:febrero etc, etc
               String d = DateTimeHelper.toShortDateString(month+1,day,year,this.getContext());
               birthDateTextView.setText(d);
           }
           catch (Exception ex)
           {
               handleException(ex,true,this.getContext());
           }

        }

    }
}

