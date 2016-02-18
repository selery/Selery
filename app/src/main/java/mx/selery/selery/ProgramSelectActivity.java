package mx.selery.selery;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import mx.selery.library.security.UserSessionManager;
import mx.selery.library.ui.ActivityFormBase;
import mx.selery.library.ui.ActivitySecure;
import mx.selery.library.utility.StringHelper;

public class ProgramSelectActivity extends ActivitySecure {

    private int position=0;
    private JSONObject program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            if(!userIsLoggedIn)
            {
                this.LoginRedirect();
                return;
            }
            setContentView(R.layout.activity_program_select);

            this.session = new UserSessionManager(getApplicationContext());
            position = getIntent().getIntExtra("Position",0);
            program=new JSONObject(getIntent().getStringExtra("Program"));
            if(position>0)
            {
                LinearLayout layout_program = (LinearLayout) findViewById(R.id.layout_program);
                if (position % 2 == 0)
                    layout_program.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.wkt_program_item_BGColor));
                else
                    layout_program.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.wkt_program_item_BGAlternateColor));
            }

            TextView text_users_in_program = (TextView) findViewById(R.id.text_users_in_program);
            text_users_in_program.setText(String.format("%1$s %2$s %3$s",
                    StringHelper.getValueFromResourceCode("wkt_select_program_text1", this.getBaseContext()),
                    program.getString("UsersInProgram"),
                    StringHelper.getValueFromResourceCode("wkt_select_program_text2", this.getBaseContext())));

            TextView text_program_name = (TextView) findViewById(R.id.text_program_name);
            text_program_name.setText(program.getString("Name"));

            TextView text_description = (TextView) findViewById(R.id.text_description);
            text_description.setText(program.getString("Description"));

            TextView text_long_description = (TextView) findViewById(R.id.text_long_description);
            text_long_description.setText(program.getString("LongDescription"));

            TextView text_duration = (TextView) findViewById(R.id.text_duration);
            String duration=String.format("%1$s %2$s %3$s",
                    StringHelper.getValueFromResourceCode("wkt_duration", this.getBaseContext()),
                    program.getString("Duration"),
                    StringHelper.getValueFromResourceCode(program.getString("UnitOfMeasureCode"), this.getBaseContext()));
            text_duration.setText(duration);

            final Button button_select_program = (Button) findViewById(R.id.button_select_program);
            button_select_program.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    try
                    {
                        selectProgramButtonClick(v);
                    }
                    catch (Exception ex)
                    {
                        handleException(ex,true);
                    }

                }
            }
            );

        }
        catch(Exception ex)
        {
            handleException(ex,true);
        }
    }

    @Override
    protected void initializeFormFields()
    {

    }

    private void selectProgramButtonClick(View v)  throws Exception
    {
        //si el usuario ya tiene un programa que no esta en progreso y es diferente al que selecciono
        //hay que confirmar si quiere cambiarlo
        JSONObject program = !this.session.getUser().isNull("Program") ? this.session.getUser().getJSONObject("Program") : null;
        if (program != null && program.getString("ProgramID") != this.program.getString("ProgramID") && !program.getBoolean("OnProgress"))
        {

            AlertDialog dialog = alertBox(StringHelper.getValueFromResourceCode("misc_Alert", this.getBaseContext()),
                    StringHelper.getValueFromResourceCode("reg_program_change_question", this.getBaseContext()),
                    StringHelper.getValueFromResourceCode("misc_Yes", this.getBaseContext()),
                    StringHelper.getValueFromResourceCode("misc_No", this.getBaseContext()),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //ir a ProgramStart
                            dialog.dismiss();
                        }
                    }
                    ,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //ir a ProgramStart
                            dialog.dismiss();
                        }
                    },
                    MessageType.Question);
            dialog.show();
        }
        else
        {
            //registrar el programa
            RequestQueue queue = Volley.newRequestQueue(this.getBaseContext());
            String url = String.format("%1$s/workout/userprograminsert?userID=%2$s",
                    getEndpoint(),
                    this.session.getUser().getString("UserID"));
            //final String programID = this.program.getString("ProgramID");
            final JSONObject selectedProgram = this.program;
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Intent intenet = new Intent().setClass(getBaseContext(), ProgramStartActivity.class);
                        startActivity(intenet);
                    } catch (Exception e) {
                        handleException(e, true);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    handleException(error.toString(), true);

                }
            }

            ) {
                @Override
                public byte[] getBody() {
                    try {
                        return selectedProgram.toString().getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };

            queue.add(req);
        }


    }

}
