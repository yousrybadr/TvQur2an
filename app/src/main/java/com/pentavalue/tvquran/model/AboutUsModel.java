package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUsModel {

    @SerializedName("eCode")
    @Expose
    private long eCode;
    @SerializedName("eDesc")
    @Expose
    private String eDesc;
    @SerializedName("eContent")
    @Expose
    private EContent eContent;

    public long getECode() {
        return eCode;
    }

    public void setECode(long eCode) {
        this.eCode = eCode;
    }

    public String getEDesc() {
        return eDesc;
    }

    public void setEDesc(String eDesc) {
        this.eDesc = eDesc;
    }

    public EContent getEContent() {
        return eContent;
    }

    public void setEContent(EContent eContent) {
        this.eContent = eContent;
    }

    @Override
    public String toString() {
        return "AboutUsModel{" +
                "eCode=" + eCode +
                ", eDesc='" + eDesc + '\'' +
                ", eContent=" + eContent.toString() +
                '}';
    }
}