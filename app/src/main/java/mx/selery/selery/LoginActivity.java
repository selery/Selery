package mx.selery.selery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mx.selery.entity.Credentials;
import mx.selery.entity.User;
import mx.selery.library.io.SeleryApiAdapter;
import mx.selery.library.security.UserSessionManager;
import mx.selery.library.ui.ActivityFormBase;
import mx.selery.library.ui.EmailValidator;
import mx.selery.library.ui.Field;
import mx.selery.library.ui.IValidator;
import mx.selery.library.utility.Encryption;

import mx.selery.library.utility.HttpHelper;
import mx.selery.library.utility.StringHelper;
import retrofit.Callback;
import retrofit.RetrofitError;


public class LoginActivity extends ActivityFormBase {

    private TextView email=null;
    private TextView password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            final Button button_login= (Button) findViewById(R.id.button_login);
            button_login.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    try
                    {
                        TextView control = null;
                        if (validateForm() == false) {
                            for (IValidator validator : ruleSet) {
                                if (validator instanceof Field && control == null) {
                                    if (!TextUtils.isEmpty(validator.getErrorMessage())) {
                                        control = validator.getControl();
                                        break;
                                    }
                                }

                            }

                            if (control != null)
                            {
                                control.requestFocus();//posicionarse en el primer control con error
                            }
                            return;
                        }

                        /*
                        ingresar
                        Caso 1. el usuario no tiene un programa -> Ir a ProgramListActivity
                        Caso 2. el progrqama tiene un programa seleccionado pero no esta inicializado -> ir a ProgramStartActivity
                        Caso 3. el usuario tiene un programa en progreso->ir a HomeActivity
                        */
                        Credentials credentials = new Credentials();
                        credentials.setEmail(email.getText().toString());
                        credentials.setPassword(Encryption.EncryptToByteArray(password.getText().toString()));

                        Callback callback = new Callback<User>() {
                            @Override
                            public void success(User user,retrofit.client.Response response) {
                                try
                                {
                                    UserSessionManager session =  UserSessionManager.getSessionInstnce(getApplicationContext());
                                    session.setUser(user);
                                    if (session.getUser().getCurrentProgram()==null)//Caso 1
                                    {
                                        Intent intenet = new Intent().setClass(getBaseContext(), ProgramListActivity.class);
                                        startActivity(intenet);
                                    }
                                    else if(!session.getUser().getCurrentProgram().getOnProgress())//Caso 2
                                    {
                                        Intent intenet = new Intent().setClass(getBaseContext(), ProgramStartActivity.class);
                                        startActivity(intenet);
                                    }
                                    else//Caso 3
                                    {
                                        //TODO: goto home
                                    }
                                }
                                catch(Exception ex)
                                {

                                    handleException(ex,true);
                                }

                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {
                                if (retrofitError.getResponse().getStatus()== HttpHelper.HttpStatus.NotFound.getValue())
                                    reportTransient(StringHelper.getValueFromResourceCode("reg_access_denied", getBaseContext()));
                                else
                                    handleException(retrofitError.getMessage(),true);

                            }
                        };
                        SeleryApiAdapter.getApiService().login(credentials, callback);

                    }
                    catch (Exception ex)
                    {
                        handleException(ex,true);
                    }
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

        this.email = (TextView) findViewById(R.id.text_email);
        this.password = (TextView) findViewById(R.id.text_password);

        Field emailfd = new Field (this.email, true, new EmailValidator(),getBaseContext());
        emailfd.setBackGroundResourceID(R.drawable.edittext_background);
        emailfd.setErrorBackGroundResourceID(R.drawable.edittext_background_error);

        Field passwordfd = new Field (this.password, true,getBaseContext());
        passwordfd.setBackGroundResourceID(R.drawable.edittext_background);
        passwordfd.setErrorBackGroundResourceID(R.drawable.edittext_background_error);

        addValidator(emailfd);
        addValidator(passwordfd);

    }

    private @Nullable
    View.OnClickListener OnClick(String email, String password)
    {
        return  null;
    }

}
