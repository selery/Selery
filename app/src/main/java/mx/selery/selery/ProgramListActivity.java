package mx.selery.selery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.selery.library.security.UserSessionManager;
import mx.selery.library.ui.ActivityFormBase;
import mx.selery.library.utility.StringHelper;

public class ProgramListActivity extends ActivityFormBase {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;

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

            String greeting = String.format ("%1$s %2$s%3$s",
                    StringHelper.getValueFromResourceCode("misc_hello", ProgramListActivity.this.getBaseContext()),
                    session.getUser().getString("FirstName"),
                    StringHelper.getValueFromResourceCode("misc_admiration", ProgramListActivity.this.getBaseContext()));

            ((TextView)findViewById(R.id.text_greeting)).setText(greeting);

            TextView selectProgramMesssageTextView = (TextView) findViewById(R.id.text_select_program);
            selectProgramMesssageTextView.setText(StringHelper.getValueFromResourceCode("registration_program_select", ProgramListActivity.this.getBaseContext()));

            // Obtener el Recycler
            recycler = (RecyclerView) findViewById(R.id.recycler);
            recycler.setHasFixedSize(true);

            // Usar un administrador para LinearLayout
            linearLayoutManager = new LinearLayoutManager(this);
            recycler.setLayoutManager(linearLayoutManager);

            //TODO: no incluir el programa actiov del usuario que esta en progreso (si existiera)
            RequestQueue queue = Volley.newRequestQueue(ProgramListActivity.this.getBaseContext());
            String url = String.format("%1$s%2$s%3$s",getEndpoint(),"/workout/getavailableprograms?UserID=",this.session.getUser().getString("UserID"));
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try
                    {
                        // Crear un nuevo adaptador
                        adapter = new ProgramAdapter(response);

                        recycler.setAdapter(adapter);
                    }
                    catch(Exception e)
                    {
                        handleException(e,true);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    handleException(error.toString(),true);
                }
            }

            );

            queue.add(req);

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

    @Override
    protected void initializeFormFields()
    {

    }

}
