package mx.selery.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htorres on 02/03/2016.
 */
public class AvailableProgram extends Program{

    @SerializedName("UserID")
    private int UserID;

    @SerializedName("IsCurrent")
    private boolean IsCurrent;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public boolean getIsCurrent() {
        return IsCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        IsCurrent = isCurrent;
    }


}
