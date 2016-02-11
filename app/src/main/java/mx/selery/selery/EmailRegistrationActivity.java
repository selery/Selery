package mx.selery.selery;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import com.google.gson.Gson;

import mx.selery.entity.User;
import mx.selery.library.security.UserSessionManager;
import mx.selery.library.ui.ActivityBase;
import mx.selery.library.ui.ActivityFormBase;
import mx.selery.library.ui.EmailValidator;
import mx.selery.library.ui.Field;
import mx.selery.library.ui.IValidator;
import mx.selery.library.ui.PasswordFieldRule;
import mx.selery.library.utility.Encryption;
import mx.selery.library.utility.StringHelper;

public class EmailRegistrationActivity extends ActivityFormBase {

    //Form Fields
    private TextView firstName=null;
    private TextView lastName=null;
    private TextView password=null;
    private TextView confirmPassword=null;
    private TextView email=null;
    private UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_registration);


        this.session = new UserSessionManager(getApplicationContext());

        final Button registerButton = (Button) findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String formErrorLevel = null;
                TextView control = null;
                if (validateForm() == false)
                {
                    for (IValidator validator : ruleSet)
                    {
                        if (validator instanceof PasswordFieldRule && formErrorLevel==null)
                        {
                            String error = validator.getErrorMessage ();
                            if (!TextUtils.isEmpty(error))
                            {
                                formErrorLevel=error;
                            }
                        }
                        else if(validator instanceof Field && control==null)
                        {
                            if (!TextUtils.isEmpty(validator.getErrorMessage()))
                            {
                                control = validator.getControl ();
                                break;
                            }
                        }

                    }

                    if (control != null)
                    {
                        control.requestFocus ();//posicionarse en el primer control con error
                    }

                    if (!TextUtils.isEmpty(formErrorLevel)) {
                        reportTransient(formErrorLevel);//error a nivel de forma
                        password.requestFocus();//TODO: hay que tratar de no tener el control en hardcode
                    }

                    return;
                }

                RequestQueue queue = Volley.newRequestQueue(EmailRegistrationActivity.this.getBaseContext());
                final ProgressDialog dialog = ProgressDialog.show(EmailRegistrationActivity.this,
                        StringHelper.getValueFromResourceCode("app_name", EmailRegistrationActivity.this.getBaseContext()),
                        StringHelper.getValueFromResourceCode("misc_please_wait", EmailRegistrationActivity.this.getBaseContext()));

                //Validar que el email no exista
                String url = String.format("%1$s%2$s%3$s",getEndpoint(),"/registration/finduserbyemail?email=",email.getText());
                StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int userID = Integer.parseInt(response.toString());
                            if (userID == 0)
                            {
                                //el usuario no existe
                                registerUser(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),password.getText().toString());
                            }
                            else
                            {
                                reportTransient(StringHelper.getValueFromResourceCode("registration_user_exists", EmailRegistrationActivity.this.getBaseContext()));

                            }
                            dialog.cancel();
                        } catch (Exception e) {
                            handleException(e, true);
                        } finally {
                            dialog.cancel();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleException(error.toString(), true);
                        dialog.cancel();
                    }
                }

                );

                queue.add(req);

            }
        });
    }

    @Override
    protected void initializeFormFields()
    {
        this.firstName = (TextView) findViewById(R.id.text_first_name);
        this.lastName = (TextView) findViewById(R.id.text_last_name);
        this.email = (TextView) findViewById(R.id.text_email);
        this.password = (TextView) findViewById(R.id.text_password);
        this.confirmPassword = (TextView) findViewById(R.id.text_confirm_password);

        Field firstNamefd = new Field (this.firstName, true,EmailRegistrationActivity.this.getBaseContext());
        firstNamefd.setBackGroundResourceID(R.drawable.edittext_background);
        firstNamefd.setErrorBackGroundResourceID(R.drawable.edittext_background_error);

        Field lastNamefd = new Field (this.lastName, true,EmailRegistrationActivity.this.getBaseContext());
        lastNamefd.setBackGroundResourceID(R.drawable.edittext_background);
        lastNamefd.setErrorBackGroundResourceID(R.drawable.edittext_background_error);

        Field passwordfd = new Field (this.password, true,EmailRegistrationActivity.this.getBaseContext());
        passwordfd.setBackGroundResourceID(R.drawable.edittext_background);
        passwordfd.setErrorBackGroundResourceID(R.drawable.edittext_background_error);

        Field confirmPasswordfd = new Field (this.confirmPassword, true,EmailRegistrationActivity.this.getBaseContext());
        confirmPasswordfd.setBackGroundResourceID(R.drawable.edittext_background);
        confirmPasswordfd.setErrorBackGroundResourceID(R.drawable.edittext_background_error);

        Field emailfd = new Field (this.email, true, new EmailValidator(),EmailRegistrationActivity.this.getBaseContext());
        emailfd.setBackGroundResourceID(R.drawable.edittext_background);
        emailfd.setErrorBackGroundResourceID(R.drawable.edittext_background_error);

        addValidator(firstNamefd);
        addValidator(lastNamefd);
        addValidator(passwordfd);
        addValidator(confirmPasswordfd);
        addValidator(new PasswordFieldRule(this.password, this.confirmPassword,EmailRegistrationActivity.this.getBaseContext()));
        addValidator(emailfd);

    }

    private void registerUser(String firstName, String lastName, String email, String password)
    {
        RequestQueue queue = Volley.newRequestQueue(EmailRegistrationActivity.this.getBaseContext());
        String url = String.format("%1$s%2$s",getEndpoint(),"/registration/adduser");

        User user = null;
        try
        {
            user= new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(Encryption.EncryptToByteArray(password));
        }
        catch(Exception e)
        {
            this.handleException(e,true);
        }

        Gson gson = new Gson();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,gson.toJson(user),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    //inicializar la sesion
                    session.createUserLoginSession(String.format("%1$s %2$s", response.getString("FirstName"), response.getString("LastName")),
                            response.getString("UserID"));
                    reportTransient("Usuario registrado!");
                }
                catch(Exception e)
                {
                    handleException(e,true);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleException(error.toString(),true);
            }
        }

        );

        queue.add(req);

    }


}
