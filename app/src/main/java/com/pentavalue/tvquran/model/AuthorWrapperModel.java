package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Passant on 6/12/2017.
 */

public class AuthorWrapperModel implements Serializable {

    @Expose
    @SerializedName("reciter_data")
    AuthorModel authorModel;

    @Expose
    @SerializedName("recitationsCount")
    int recitationsCount;

    @Expose
    @SerializedName("totalLikes")
    int totalLikes;

    @Expose
    @SerializedName("totleListened")
    int totleListened;

    public AuthorModel getAuthorModel() {
        return authorModel;
    }

    public void setAuthorModel(AuthorModel authorModel) {
        this.authorModel = authorModel;
    }

    public int getRecitationsCount() {
        return recitationsCount;
    }

    public void setRecitationsCount(int recitationsCount) {
        this.recitationsCount = recitationsCount;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getTotleListened() {
        return totleListened;
    }

    public void setTotleListened(int totleListened) {
        this.totleListened = totleListened;
    }
}
