package mx.selery.selery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mx.selery.library.utility.HttpHelper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import mx.selery.entity.User;
import mx.selery.library.io.SeleryApiAdapter;
import mx.selery.library.security.UserSessionManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_email_registration);

            final Button registerButton = (Button) findViewById(R.id.button_register);

            registerButton.setOnClickListener(new View.OnClickListener()
            {

                public void onClick(View v)
                {

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

                        if (!TextUtils.isEmpty(formErrorLevel))
                        {
                            reportTransient(formErrorLevel,MessageType.Error);//error a nivel de forma
                            password.requestFocus();//TODO: hay que tratar de no tener el control en hardcode
                        }

                        return;
                    }

                    final ProgressDialog dialog = ProgressDialog.show(EmailRegistrationActivity.this,
                            StringHelper.getValueFromResourceCode("app_name", EmailRegistrationActivity.this.getBaseContext()),
                            StringHelper.getValueFromResourceCode("misc_please_wait", EmailRegistrationActivity.this.getBaseContext()));

                    //Validar que el email no exista
                    Callback callback = new Callback<User>()
                    {
                        @Override
                        public void success(User user, Response response) {
                            //el usuario existe no podemos continuar
                            dialog.cancel();
                            reportTransient(StringHelper.getValueFromResourceCode("reg_user_exists", EmailRegistrationActivity.this.getBaseContext()));
                        }

                        @Override
                        public void failure(RetrofitError retrofitError)
                        {
                            dialog.cancel();
                            if (retrofitError.getResponse().getStatus()== HttpHelper.HttpStatus.NotFound.getValue())
                            {
                                //el usuario no existe continuar con el registro
                                try
                                {
                                    registerUser(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),password.getText().toString());
                                }
                                catch(Exception ex)
                                {
                                    handleException(ex, true);
                                }

                            }
                            else
                            {
                                handleException(retrofitError.getMessage(),true);
                            }

                        }
                    };
                    SeleryApiAdapter.getApiService().getUserByEmail(email.getText().toString(),callback);
                }
            });
        }
        catch(Exception ex)
        {
            handleException(ex,true);
        }

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

    private void registerUser(String firstName, String lastName, String email, String password) throws Exception
    {

        Callback callback = new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                try
                {
                    UserSessionManager session= UserSessionManager.getSessionInstnce(getBaseContext());
                    session.setUser(user);

                    //ir a seleccionar el programa
                    Intent intenet = new Intent().setClass(getBaseContext(), ProgramListActivity.class);
                    startActivity(intenet);
                }
                catch(Exception ex)
                {
                    handleException(ex,true);
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                handleException(retrofitError.getMessage(),true);
            }
        };
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(Encryption.EncryptToByteArray(password));
        SeleryApiAdapter.getApiService().createUser(user,callback);

    }


}
