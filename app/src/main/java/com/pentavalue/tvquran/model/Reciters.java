package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by OmniaGamil on 5/2/2017.
 */

public class Reciters implements Serializable {
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("photo")
    @Expose
    public String reciterImage;
    @SerializedName("full_biography")
    @Expose
    public String reciterDetails;

    @SerializedName("entries")
    @Expose
    List<Entries>entries;

    @SerializedName("brief_biography")
    @Expose
    public String brief_biography;
    //recitations_count
    @SerializedName("recitations_count")
    @Expose
    public String recitations_count;
    @SerializedName("total_likes")
    @Expose
    public String total_likes;

    @SerializedName("total_listened")
    @Expose
    public String total_listened;
    @SerializedName("recitations")
    @Expose
    public List<Recitations>recitationses;

    public String getBrief_biography() {
        return brief_biography;
    }

    public void setBrief_biography(String brief_biography) {
        this.brief_biography = brief_biography;
    }

    public String getRecitations_count() {
        return recitations_count;
    }

    public void setRecitations_count(String recitations_count) {
        this.recitations_count = recitations_count;
    }

    public String getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(String total_likes) {
        this.total_likes = total_likes;
    }

    public String getTotal_listened() {
        return total_listened;
    }

    public void setTotal_listened(String total_listened) {
        this.total_listened = total_listened;
    }

    public List<Recitations> getRecitationses() {
        return recitationses;
    }

    public void setRecitationses(List<Recitations> recitationses) {
        this.recitationses = recitationses;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReciterDetails() {
        return reciterDetails;
    }

    public void setReciterDetails(String reciterDetails) {
        this.reciterDetails = reciterDetails;
    }

    public List<Entries> getEntries() {
        return entries;
    }

    public void setEntries(List<Entries> entries) {
        this.entries = entries;
    }

    public Reciters(String name, String reciterImage, String sorahName, String desc, String numOflisten , String numoflike)
    {

        this.name=name;
        this.reciterImage=reciterImage;
    }
    public Reciters(String name, String reciterImage)
    {      //  super(null,null,null);
        this.name=name;
        this.reciterImage=reciterImage;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getReciterImage() {
        return reciterImage;
    }

    public void setReciterImage(String reciterImage) {
        this.reciterImage = reciterImage;
    }

}
