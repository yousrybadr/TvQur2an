package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

/**
 * Created by OmniaGamil on 5/22/2017.
 */

public class Video extends BusinessObject {
    @SerializedName("id")
    @Expose
    int id;
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
    @SerializedName("up_votes")
    @Expose
    int up_votes;
    @SerializedName("down_votes")
    @Expose
    int down_votes;
    @SerializedName("shares")
    @Expose
    int shares;
    @SerializedName("created_at")
    @Expose
    String created_at;
    @SerializedName("updated_at")
    @Expose
    String updated_at;

    /**
     * The server CRUD methods should be provided by the entity sub-classing {@linkplain BusinessObject}
     *
     * @param saveMethod
     * @param deleteMethod
     * @param getMethod
     */
    public Video(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
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

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getUp_votes() {
        return up_votes;
    }

    public void setUp_votes(int up_votes) {
        this.up_votes = up_votes;
    }

    public int getDown_votes() {
        return down_votes;
    }

    public void setDown_votes(int down_votes) {
        this.down_votes = down_votes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
