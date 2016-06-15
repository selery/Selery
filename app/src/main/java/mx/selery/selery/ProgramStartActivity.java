package mx.selery.selery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mx.selery.library.ui.ActivitySecure;
import mx.selery.library.utility.StringHelper;

public class ProgramStartActivity extends ActivitySecure {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            this.setShowmenu(true);
            if(!userIsLoggedIn)
            {
                finish();
                this.LoginRedirect();
                return;
            }
            setContentView(R.layout.activity_program_start);
            TextView text_message = (TextView) findViewById(R.id.text_message);
            String message = String.format ("%1$s %2$s%3$s",
                    StringHelper.getValueFromResourceCode("wkt_start_program_text1", getBaseContext()),
                    session.getUser().getCurrentProgram().getName(),
                    StringHelper.getValueFromResourceCode("misc_admiration", getBaseContext()));
            text_message.setText(message);

            TextView text_duration = (TextView) findViewById(R.id.text_duration);
            String duration= String.format ("%1$s %2$s %3$s",
                    StringHelper.getValueFromResourceCode("wkt_duration", getBaseContext()),
                    session.getUser().getCurrentProgram().getDuration(),
                    StringHelper.getValueFromResourceCode(session.getUser().getCurrentProgram().getUnitOfMeasureCode(), getBaseContext()));
            text_duration.setText(duration);

            final Button button_start_program = (Button) findViewById(R.id.button_start_program);
            button_start_program.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    Intent intenet = new Intent().setClass(getBaseContext(), PersonalInformationActivity.class);
                    startActivity(intenet);
                }
            });



        }
        catch(Exception ex)
        {
            handleException(ex,true);
        }

    }

    @Override
    public void onBackPressed()
    {
        Intent intenet = new Intent().setClass(getBaseContext(), ProgramListActivity.class);
        startActivity(intenet);

    }
}
