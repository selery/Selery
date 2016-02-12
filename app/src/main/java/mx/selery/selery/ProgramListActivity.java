package mx.selery.selery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import mx.selery.library.security.UserSessionManager;
import mx.selery.library.ui.ActivityFormBase;
import mx.selery.library.utility.StringHelper;

public class ProgramListActivity extends ActivityFormBase {

    ListView listView=null;
    private UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_program_list);

            this.session = new UserSessionManager(getApplicationContext());

            String greeting = String.format ("%1$s%2$s%3$s",
                    StringHelper.getValueFromResourceCode("misc_hello", ProgramListActivity.this.getBaseContext()),
                    session.getUser().getString("FirstName"),
                    StringHelper.getValueFromResourceCode("misc_admiration", ProgramListActivity.this.getBaseContext()));

            ((TextView)findViewById(R.id.text_greeting)).setText(greeting);

            TextView selectProgramMesssageTextView = (TextView)findViewById(R.id.text_select_program);
            selectProgramMesssageTextView.setText(StringHelper.getValueFromResourceCode("registration_program_select", ProgramListActivity.this.getBaseContext()));

        }
        catch(Exception e)
        {
            handleException(e,true);
        }

    }

    @Override
    protected void initializeFormFields()
    {

    }

}
