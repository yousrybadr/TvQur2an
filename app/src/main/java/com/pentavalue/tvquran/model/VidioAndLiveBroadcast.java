package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

import java.util.List;

/**
 * Created by OmniaGamil on 5/22/2017.
 */

public class VidioAndLiveBroadcast extends BusinessObject{
    @SerializedName("broadcast")
    @Expose
    List<Video> broadcast;
    @SerializedName("videos")
    @Expose
    List<Video> videos;


    public VidioAndLiveBroadcast(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
    }

    public List<Video> getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(List<Video> broadcast) {
        this.broadcast = broadcast;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
