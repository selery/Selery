package mx.selery.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htorres on 25/02/2016.
 */
public class Program {
    @SerializedName("ProgramID")
    private int ProgramID;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Description")
    private String Description;
    @SerializedName("LongDescription")
    private String LongDescription;
    @SerializedName("ProgramStatusID")
    private int ProgramStatusID;
    @SerializedName("ProgramStatusName")
    private String ProgramStatusName;
    @SerializedName("GoalID")
    private int GoalID;
    @SerializedName("GoalName")
    private String GoalName;
    @SerializedName("Raiting")
    private int Raiting;
    @SerializedName("ImageFile")
    private String ImageFile;
    @SerializedName("Duration")
    private int Duration;
    @SerializedName("UnitOfMeasureID")
    private int UnitOfMeasureID;

    public int getProgramID() {
        return ProgramID;
    }

    public void setProgramID(int programID) {
        ProgramID = programID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public int getProgramStatusID() {
        return ProgramStatusID;
    }

    public void setProgramStatusID(int programStatusID) {
        ProgramStatusID = programStatusID;
    }

    public String getProgramStatusName() {
        return ProgramStatusName;
    }

    public void setProgramStatusName(String programStatusName) {
        ProgramStatusName = programStatusName;
    }

    public int getGoalID() {
        return GoalID;
    }

    public void setGoalID(int goalID) {
        GoalID = goalID;
    }

    public String getGoalName() {
        return GoalName;
    }

    public void setGoalName(String goalName) {
        GoalName = goalName;
    }

    public int getRaiting() {
        return Raiting;
    }

    public void setRaiting(int raiting) {
        Raiting = raiting;
    }

    public String getImageFile() {
        return ImageFile;
    }

    public void setImageFile(String imageFile) {
        ImageFile = imageFile;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public int getUnitOfMeasureID() {
        return UnitOfMeasureID;
    }

    public void setUnitOfMeasureID(int unitOfMeasureID) {
        UnitOfMeasureID = unitOfMeasureID;
    }

    public String getUnitOfMeasureCode() {
        return UnitOfMeasureCode;
    }

    public void setUnitOfMeasureCode(String unitOfMeasureCode) {
        UnitOfMeasureCode = unitOfMeasureCode;
    }

    public String getUnitOfMeasureDescription() {
        return UnitOfMeasureDescription;
    }

    public void setUnitOfMeasureDescription(String unitOfMeasureDescription) {
        UnitOfMeasureDescription = unitOfMeasureDescription;
    }

    public int getUsersInProgram() {
        return UsersInProgram;
    }

    public void setUsersInProgram(int usersInProgram) {
        UsersInProgram = usersInProgram;
    }

    private String UnitOfMeasureCode;
    private String UnitOfMeasureDescription;
    private int UsersInProgram;
}
