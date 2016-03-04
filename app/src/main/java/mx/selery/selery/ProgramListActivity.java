package mx.selery.selery;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.List;

import mx.selery.entity.AvailableProgram;
import mx.selery.entity.Program;
import mx.selery.entity.User;
import mx.selery.library.io.SeleryApiAdapter;
import mx.selery.library.io.SeleryApiService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import mx.selery.library.ui.ActivitySecure;
import mx.selery.library.utility.StringHelper;

public class ProgramListActivity extends ActivitySecure {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;

    ListView listView=null;

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
            setContentView(R.layout.activity_program_list);
            //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            //setSupportActionBar(myToolbar);
            String greeting = String.format ("%1$s %2$s%3$s",
                    StringHelper.getValueFromResourceCode("misc_hello", ProgramListActivity.this.getBaseContext()),
                    session.getUser().getFirstName(),
                    StringHelper.getValueFromResourceCode("misc_admiration", ProgramListActivity.this.getBaseContext()));

            ((TextView)findViewById(R.id.text_greeting)).setText(greeting);

            TextView selectProgramMesssageTextView = (TextView) findViewById(R.id.text_select_program);
            selectProgramMesssageTextView.setText(StringHelper.getValueFromResourceCode("reg_program_select", ProgramListActivity.this.getBaseContext()));

            // Obtener el Recycler
            recycler = (RecyclerView) findViewById(R.id.recycler);
            recycler.setHasFixedSize(true);

            // Usar un administrador para LinearLayout
            linearLayoutManager = new LinearLayoutManager(this);
            recycler.setLayoutManager(linearLayoutManager);

            Callback callback = new Callback<List<AvailableProgram>>() {
                @Override
                public void success(List<AvailableProgram> programs, Response response) {
                    try
                    {
                        // Crear un nuevo adaptador
                        adapter = new ProgramAdapter(programs);
                        recycler.setAdapter(adapter);
                    }
                    catch(Exception e)
                    {
                        handleException(e,true);
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    handleException(retrofitError.toString());
                }
            };
            SeleryApiAdapter.getApiService().getAvailablePrograms(this.session.getUser().getUserID(), callback);

        }
        catch(Exception e)
        {
            handleException(e,true);
        }

    }

    @Override
    public void onBackPressed()
    {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startMain);

    }




}
