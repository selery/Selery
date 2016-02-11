package mx.selery.bo;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mx.selery.entity.User;

/**
 * Created by htorres on 28/01/2016.
 */
public class Registration {

    Context context;

    public Registration(Context context)
    {
        this.context=context;
    }

    public int FindUserByEmail(String email)
    {
        int userID;
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url ="http://192.168.0.2/Selery.Web.Api/api/registration/finduserbyemail?email=horaciotg@hotmail.com";
        StringRequest req = new StringRequest( Request.Method.GET,url,new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                     Integer.parseInt(response.toString());

                }
                catch (Exception e)
                {
                    Log.e("Error onResponse:", e.getMessage());
                }
                finally
                {

                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e("Error onErrorResponse:", error.toString());

            }
        }

        );

        queue.add(req);

     return 0;
    }

    public static User loginByEmail(String email, String password)
    {
        return null;
    }
}
