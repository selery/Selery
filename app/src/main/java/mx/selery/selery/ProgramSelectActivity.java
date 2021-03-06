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

import com.google.gson.Gson;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import mx.selery.library.ui.ActivityBase;
import mx.selery.entity.UserProgram;
import mx.selery.library.io.SeleryApiAdapter;
import mx.selery.entity.Program;
import mx.selery.library.ui.ActivitySecure;
import mx.selery.library.utility.StringHelper;

public class ProgramSelectActivity extends ActivitySecure {

    private int position=0;
    private Program program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            setContentView(R.layout.activity_program_select);
            position = getIntent().getIntExtra("Position",0);

            Gson gson = new Gson();
            program =  gson.fromJson(getIntent().getStringExtra("Program"),Program.class);
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
                    program.getUsersInProgram(),
                    StringHelper.getValueFromResourceCode("wkt_select_program_text2", this.getBaseContext())));

            TextView text_program_name = (TextView) findViewById(R.id.text_program_name);
            text_program_name.setText(program.getName());

            TextView text_description = (TextView) findViewById(R.id.text_description);
            text_description.setText(program.getDescription());

            TextView text_long_description = (TextView) findViewById(R.id.text_long_description);
            text_long_description.setText(program.getLongDescription());

            TextView text_duration = (TextView) findViewById(R.id.text_duration);
            String duration = String.format("%1$s %2$s %3$s",
                    StringHelper.getValueFromResourceCode("wkt_duration", this.getBaseContext()),
                    program.getDuration(),
                    StringHelper.getValueFromResourceCode(program.getUnitOfMeasureCode(), this.getBaseContext()));
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

    private void selectProgramButtonClick(View v)  throws Exception
    {
        //Si el usuario cambiar de programa hay que confirmar si lo quiere hacer
        UserProgram currentProgram = session.getUser().getCurrentProgram();
        if (currentProgram != null)
        {
            //si esta  cambiando de programa
            if(currentProgram.getProgramID()!=program.getProgramID())
            {
                //alertar dle cambkio
                AlertDialog dialog = alertBox(StringHelper.getValueFromResourceCode("misc_Alert", this.getBaseContext()),
                        StringHelper.getValueFromResourceCode("reg_program_change_question", this.getBaseContext()),
                        StringHelper.getValueFromResourceCode("misc_Yes", this.getBaseContext()),
                        StringHelper.getValueFromResourceCode("misc_No", this.getBaseContext()),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try
                                {
                                    //YES - cambiar el programa
                                    //ir a ProgramStart
                                    setProgram(session.getUser().getUserID(), program);
                                    dialog.dismiss();
                                }
                                catch(Exception ex)
                                {
                                    handleException(ex,true);
                                }


                            }
                        }
                        ,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //ir a ProgramStart sin cambiarlo
                                dialog.dismiss();
                                Intent intenet = new Intent().setClass(getBaseContext(), ProgramStartActivity.class);
                                startActivity(intenet);
                            }
                        },
                        MessageType.Question);
                dialog.show();
            }
            else
            {
                //ir directo a ProgramStartActivity
                Intent intenet = new Intent().setClass(getBaseContext(), ProgramStartActivity.class);
                startActivity(intenet);
            }

        }
        else
        {
            setProgram(session.getUser().getUserID(), this.program);
        }

    }

    private void setProgram(int userID, Program program)
    {
        Callback callback = new Callback<UserProgram>() {
            @Override
            public void success(UserProgram userProgram, Response response) {
                try
                {
                    //actualizar la sesion con el programa del usuario
                    session.getUser().setCurrentProgram(userProgram);
                    session.setUser(session.getUser());
                    Intent intenet = new Intent().setClass(getBaseContext() , ProgramStartActivity.class);
                    startActivity(intenet);
                }
                catch(Exception ex)
                {
                    handleException(ex,true);
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                ActivityBase.handleException(retrofitError.toString());

            }
        };
        SeleryApiAdapter.getApiService().setProgram(userID,program,callback);
    }


}
