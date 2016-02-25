package mx.selery.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htorres on 19/02/2016.
 */
public class Credentials {

    @SerializedName("Email")
    private String email;

    @SerializedName("Password")
    private byte[] password;

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
