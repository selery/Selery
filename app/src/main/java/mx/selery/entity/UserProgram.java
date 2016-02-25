package mx.selery.entity;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by htorres on 25/02/2016.
 */
public class UserProgram extends Program {

    @SerializedName("IsCompleted")
    private Boolean IsCompleted;
    @SerializedName("StartDate")
    private Date StartDate;
    @SerializedName("EndDate")
    private Date EndDate;
    @SerializedName("CurrentDay")
    private int CurrentDay;
    @SerializedName("OnProgress")
    private Boolean OnProgress;

    public Boolean getIsCompleted() {
        return IsCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        IsCompleted = isCompleted;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public int getCurrentDay() {
        return CurrentDay;
    }

    public void setCurrentDay(int currentDay) {
        CurrentDay = currentDay;
    }

    public Boolean getOnProgress() {
        return OnProgress;
    }

    public void setOnProgress(Boolean onProgress) {
        OnProgress = onProgress;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public BigDecimal getBMIStart() {
        return BMIStart;
    }

    public void setBMIStart(BigDecimal BMIStart) {
        this.BMIStart = BMIStart;
    }

    private Date CreatedDate;
    private BigDecimal BMIStart;

}
