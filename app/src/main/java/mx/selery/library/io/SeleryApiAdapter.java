package mx.selery.library.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by htorres on 22/02/2016.
 */
public class SeleryApiAdapter {
    private static SeleryApiService API_SERVICE;

    public static SeleryApiService getApiService () {

        if(API_SERVICE == null){

            Gson gson= new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ApiConstants.BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setConverter(new GsonConverter(gson))
                    .build();

            API_SERVICE = adapter.create(SeleryApiService.class);
        }

        return API_SERVICE;

    }
}
