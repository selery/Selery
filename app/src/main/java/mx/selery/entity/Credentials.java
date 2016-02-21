package mx.selery.entity;

/**
 * Created by htorres on 19/02/2016.
 */
public class Credentials {

    public String email;
    public byte[] password;

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
