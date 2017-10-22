package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUs {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("summary")
    @Expose
    private Object summary;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("views_count")
    @Expose
    private long viewsCount;
    @SerializedName("published_at")
    @Expose
    private String publishedAt;
    @SerializedName("is_published")
    @Expose
    private long isPublished;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getSummary() {
        return summary;
    }

    public void setSummary(Object summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public long getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(long isPublished) {
        this.isPublished = isPublished;
    }

    @Override
    public String toString() {
        return "AboutUs{" +
                "name='" + name + '\'' +
                ", summary=" + summary +
                ", content='" + content + '\'' +
                ", viewsCount=" + viewsCount +
                ", publishedAt='" + publishedAt + '\'' +
                ", isPublished=" + isPublished +
                '}';
    }
}