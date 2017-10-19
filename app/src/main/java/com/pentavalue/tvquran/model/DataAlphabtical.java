package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

/**
 * Created by R on 5/21/2017.
 */

public class DataAlphabtical extends BusinessObject {

    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("photo")
    @Expose
    String photo;
    @SerializedName("full_biography")
    @Expose
    String full_biography;
    @SerializedName("brief_biography")
    @Expose
    String brief_biography;
    @SerializedName("recitations_count")
    @Expose
    int recitations_count;
    @SerializedName("total_likes")
    @Expose
    int total_likes;
    @SerializedName("total_listened")
    @Expose
    String total_listened;

    public DataAlphabtical(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFull_biography() {
        return full_biography;
    }

    public void setFull_biography(String full_biography) {
        this.full_biography = full_biography;
    }

    public String getBrief_biography() {
        return brief_biography;
    }

    public void setBrief_biography(String brief_biography) {
        this.brief_biography = brief_biography;
    }

    public int getRecitations_count() {
        return recitations_count;
    }

    public void setRecitations_count(int recitations_count) {
        this.recitations_count = recitations_count;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public String getTotal_listened() {
        return total_listened;
    }

    public void setTotal_listened(String total_listened) {
        this.total_listened = total_listened;
    }
}
