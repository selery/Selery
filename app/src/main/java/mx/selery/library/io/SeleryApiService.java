package mx.selery.library.io;

import java.util.List;

import mx.selery.entity.AvailableProgram;
import mx.selery.entity.Credentials;
import mx.selery.entity.Program;
import mx.selery.entity.User;
import mx.selery.entity.UserProgram;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


/**
 * Created by htorres on 22/02/2016.
 */
public interface SeleryApiService {

    //registration
    @GET("/Selery/api/registration/")
    void getUserByEmail(@Query("email") String email,Callback<User> cb);

    @GET("/Selery/api/registration/{id}")
    void getUserByID(@Path("id") int id, Callback<User> cb);

    @GET("/Selery/api/registration/")
    void getUserByFacebookID(@Query("facebookID") Long id, Callback<User> cb);

    @POST("/Selery/api/registration/new")
    void createUser(@Body User user,Callback<User> cb);

    @POST("/Selery/api/registration/login")
    void login(@Body Credentials crdentials,Callback<User> cb);

    //workout
    @GET("/Selery/api/workout/availableprograms/")
    void getAvailablePrograms(@Query("userID") int userID,Callback<List<AvailableProgram>> cb );

    @POST("/Selery/api/workout/setprogram/")
    void setProgram(@Query("userID") int userID,@Body Program program, Callback<UserProgram> cb);


}
