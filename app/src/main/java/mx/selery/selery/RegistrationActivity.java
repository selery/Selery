package mx.selery.selery;


import android.os.Bundle;

import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import mx.selery.library.ui.ActivityBase;

public class RegistrationActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Button emailRegistrationButton = (Button) findViewById(R.id.button_email_registration);
        emailRegistrationButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), EmailRegistrationActivity.class);
                startActivity(intent);
            }
        });

        final Button button_login= (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
