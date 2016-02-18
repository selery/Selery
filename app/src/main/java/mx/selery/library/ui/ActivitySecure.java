package mx.selery.library.ui;

import android.content.Intent;
import android.os.Bundle;

import mx.selery.library.security.UserSessionManager;
import mx.selery.selery.ProgramListActivity;
import mx.selery.selery.RegistrationActivity;

/**
 * Created by htorres on 17/02/2016.
 */
public abstract class ActivitySecure extends ActivityFormBase
{
    protected UserSessionManager session;
    protected Boolean userIsLoggedIn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        session= new UserSessionManager(getBaseContext());
        if(this.session.getUser()!=null)
        {
            userIsLoggedIn=true;

        }
    }

    protected void LoginRedirect()
    {
        Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
