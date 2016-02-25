package mx.selery.entity;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Horacio Torres on 28/01/2016.
 */

public class User {

    @SerializedName("UserID")
    private int userID;
    @SerializedName("FacebookID")
    private long facebookID;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("Email")
    private String email;
    @SerializedName("Password")
    private byte[] password;
    @SerializedName("ResetPasswordToken")
    private String resetPasswordToken;
    @SerializedName("ResetPasswordRequestDate")
    private Date resetPasswordRequestDate;
    @SerializedName("Gender")
    private int gender;
    @SerializedName("BirthDate")
    private Date birthDate;
    @SerializedName("Heigh")
    private Double heigh;
    @SerializedName("HeighInt")
    private int heighInt;
    @SerializedName("HeighDec")
    private int heighDec;
    @SerializedName("ActivityID")
    private int activityID;
    @SerializedName("Description")
    private String description;
    @SerializedName("ActivationDate")
    private Date activationDate;
    @SerializedName("TerminationDate")
    private Date terminationDate;
    @SerializedName("IsApproved")
    private Boolean isApproved;
    @SerializedName("IsLockedOut")
    private Boolean isLockedOut;
    @SerializedName("LastLoginDate")
    private Date lastLoginDate;
    @SerializedName("CreateDate")
    private Date createDate;
    @SerializedName("CreatedBy")
    private int createdBy;
    @SerializedName("LastUpdatedDate")
    private Date lastUpdatedDate;
    @SerializedName("LastUpdatedBy")
    private int lastUpdatedBy;
    @SerializedName("CurrentProgram")
    private UserProgram currentProgram;
    @SerializedName("IsProfileComplete")
    private Boolean isProfileComplete;
    @SerializedName("BMI")
    private Double bmi;
    @SerializedName("Calories")
    private Double calories;
    @SerializedName("Weight")
    private Double weight;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public long getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(long facebookID) {
        this.facebookID = facebookID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Date getResetPasswordRequestDate() {
        return resetPasswordRequestDate;
    }

    public void setResetPasswordRequestDate(Date resetPasswordRequestDate) {
        this.resetPasswordRequestDate = resetPasswordRequestDate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Double getHeigh() {
        return heigh;
    }

    public void setHeigh(Double heigh) {
        this.heigh = heigh;
    }

    public int getHeighInt() {
        return heighInt;
    }

    public void setHeighInt(int heighInt) {
        this.heighInt = heighInt;
    }

    public int getHeighDec() {
        return heighDec;
    }

    public void setHeighDec(int heighDec) {
        this.heighDec = heighDec;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsLockedOut() {
        return isLockedOut;
    }

    public void setIsLockedOut(Boolean isLockedOut) {
        this.isLockedOut = isLockedOut;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(int lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public UserProgram getCurrentProgram() {
        return currentProgram;
    }

    public void setCurrentProgram(UserProgram userProgram) {
        this.currentProgram = userProgram;
    }

    public Boolean getIsProfileComplete() {
        return isProfileComplete;
    }

    public void setIsProfileComplete(Boolean isProfileComplete) {
        this.isProfileComplete = isProfileComplete;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
