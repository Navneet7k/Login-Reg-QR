package lyf.navneet.in.phpapisamp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sree on 04-09-2017.
 */

public class OuterModel {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @SerializedName("uid")

    @Expose
    private String uid;
    @SerializedName("user")
    @Expose
    private InnerModel user;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public InnerModel getInnerModel() {
        return user;
    }

    public void setInnerModel(InnerModel user) {
        this.user = user;
    }

}
