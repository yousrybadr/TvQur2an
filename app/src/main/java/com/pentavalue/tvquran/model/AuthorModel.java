package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.R;

import java.io.Serializable;

import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

/**
 * Created by Passant on 6/12/2017.
 */

public class AuthorModel extends DataBean implements Serializable {


    @SerializedName("id")
    @Expose
    int author_id;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("photo")
    @Expose
    String photoURL;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("full_biography")
    @Expose
    String full_biography;
    @SerializedName("updated_at")
    @Expose
    String updated_at;
    @SerializedName("brief_biography")
    @Expose
    String brief_biography;
    @SerializedName("country_id")
    @Expose
    String country_id;

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_biography() {
        return full_biography;
    }

    public void setFull_biography(String full_biography) {
        this.full_biography = full_biography;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getBrief_biography() {
        return brief_biography;
    }

    public void setBrief_biography(String brief_biography) {
        this.brief_biography = brief_biography;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter stickyHeaderViewAdapter) {
        return R.layout.list_item_alphabitcal_child;
    }
}
