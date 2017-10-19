package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by OmniaGamil on 5/16/2017.
 */

public class Entries  implements Serializable{
    @SerializedName("id")
    @Expose
    int id;

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    @SerializedName("author_id")
    @Expose
    int author_id;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("content")
    @Expose
    String content;
    @SerializedName("views_count")
    @Expose
    int views_count;
    @SerializedName("reciter_name")
    @Expose
    String reciter_name;
    @SerializedName("reciter_photo")
    @Expose
    String reciter_photo;
    @SerializedName("category_name")
    @Expose
    String category_name;
    @SerializedName("category_desc")
    @Expose
    String category_desc;
    @Expose
    @SerializedName("path")
    String soundPath;
    @Expose
    @SerializedName("up_votes")
    int upVotes;

    int isHistory;
    int isDownloaded;
    String downloadedPath;

    public String getDownloadedPath() {
        return downloadedPath;
    }

    public void setDownloadedPath(String downloadedPath) {
        this.downloadedPath = downloadedPath;
    }


    public int getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(int isHistory) {
        this.isHistory = isHistory;
    }

    public int getIsDownloaded() {
        return isDownloaded;
    }

    public void setIsDownloaded(int isDownloaded) {
        this.isDownloaded = isDownloaded;
    }

    public Entries(int id, int author_id,
                   String title, String content, int views_count, String reciter_name,
                   String reciter_photo, String category_name, String category_desc, String soundPath, int upVotes
    , int isHistory, int isDownloaded,String downlaodedPath) {
       // super(null, null, null);
        this.id = id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
        this.views_count = views_count;
        this.reciter_name = reciter_name;
        this.reciter_photo = reciter_photo;
        this.category_name = category_name;
        this.category_desc = category_desc;
        this.soundPath = soundPath;
        this.upVotes = upVotes;
        this.isHistory=isHistory;
        this.isDownloaded=isDownloaded;
        this.downloadedPath=downlaodedPath;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViews_count() {
        return views_count;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public String getReciter_name() {
        return reciter_name;
    }

    public void setReciter_name(String reciter_name) {
        this.reciter_name = reciter_name;
    }

    public String getReciter_photo() {
        return reciter_photo;
    }

    public void setReciter_photo(String reciter_photo) {
        this.reciter_photo = reciter_photo;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_desc() {
        return category_desc;
    }

    public void setCategory_desc(String category_desc) {
        this.category_desc = category_desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
