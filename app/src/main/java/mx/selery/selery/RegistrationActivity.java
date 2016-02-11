package mx.selery.selery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class RegistrationActivity extends AppCompatActivity {

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

    }
}
