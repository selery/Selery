package mx.selery.selery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.selery.library.ui.ActivitySecure;

public class ProgramStartActivity extends ActivitySecure {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_start);
    }

    @Override
    protected void initializeFormFields()
    {

    }
}
