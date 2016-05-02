package mx.selery.entity;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by htorres on 05/03/2016.
 */
public class Activity {

    @SerializedName("ActivityID")
    public int ActivityID;
    @SerializedName("ActivityName")
    public String ActivityName;

    public BigDecimal getFactor() {
        return Factor;
    }

    public void setFactor(BigDecimal factor) {
        Factor = factor;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public int getActivityID() {
        return ActivityID;
    }

    public void setActivityID(int activityID) {
        ActivityID = activityID;
    }

    @SerializedName("Factor")
    public BigDecimal Factor;

}
