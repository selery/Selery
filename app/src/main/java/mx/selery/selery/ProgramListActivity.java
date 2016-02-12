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

            // Obtener el Recycler
            recycler = (RecyclerView) findViewById(R.id.recycler);
            recycler.setHasFixedSize(true);

            // Usar un administrador para LinearLayout
            linearLayoutManager = new LinearLayoutManager(this);
            recycler.setLayoutManager(linearLayoutManager);

            RequestQueue queue = Volley.newRequestQueue(ProgramListActivity.this.getBaseContext());
            String url = String.format("%1$s%2$s",getEndpoint(),"/workout/getactiveprograms");
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST, url,new Response.Listener<JSONArray>() {
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
    protected void initializeFormFields()
    {

    }

}
