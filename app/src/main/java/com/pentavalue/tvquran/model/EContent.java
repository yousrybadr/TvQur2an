package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EContent {

    @SerializedName("about_us")
    @Expose
    private AboutUs aboutUs;

    public AboutUs getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(AboutUs aboutUs) {
        this.aboutUs = aboutUs;
    }

    @Override
    public String toString() {
        return "EContent{" +
                "aboutUs=" + aboutUs.toString() +
                '}';
    }
}