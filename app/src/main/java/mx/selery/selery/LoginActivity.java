package mx.selery.selery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import mx.selery.entity.User;
import mx.selery.library.security.UserSessionManager;
import mx.selery.library.ui.ActivityFormBase;
import mx.selery.library.ui.EmailValidator;
import mx.selery.library.ui.Field;
import mx.selery.library.ui.IValidator;
import mx.selery.library.utility.Encryption;
import mx.selery.library.utility.StringHelper;


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
                        User user = new User();
                        user.setEmail(email.getText().toString());
                        user.setPassword(Encryption.EncryptToByteArray(password.getText().toString()));


                        RequestQueue queue = Volley.newRequestQueue(v.getContext());
                        String url = String.format("%1$s/registration/loginbyemail", getEndpoint());
                        Gson gson = new Gson();
                        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,gson.toJson(user),new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try
                                {
                                    UserSessionManager session;
                                    session = new UserSessionManager(getApplicationContext());
                                    session.setUser(response);

                                    Intent intenet = new Intent().setClass(getBaseContext(), ProgramListActivity.class);
                                    startActivity(intenet);

                                } catch (Exception e) {
                                    handleException(e, true);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.networkResponse!=null && error.networkResponse.statusCode==404)
                                     reportTransient(StringHelper.getValueFromResourceCode("reg_access_denied", getBaseContext()));
                                else
                                    handleException(String.format("Error:%1$s HttpStatusCode:%2$s", error.toString(), error.networkResponse!=null ? error.networkResponse.statusCode:null),true);
                            }
                        }

                        );

                        queue.add(req);

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
