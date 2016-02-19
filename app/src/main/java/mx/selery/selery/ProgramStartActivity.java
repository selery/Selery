package mx.selery.selery;

import android.os.Bundle;

import mx.selery.library.ui.ActivitySecure;

public class ProgramStartActivity extends ActivitySecure {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(!userIsLoggedIn)
        {
            finish();
            this.LoginRedirect();
            return;
        }
        setContentView(R.layout.activity_program_start);
    }

    @Override
    protected void initializeFormFields()
    {

    }
}
