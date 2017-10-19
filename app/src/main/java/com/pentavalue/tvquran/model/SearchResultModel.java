package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

import java.util.ArrayList;

/**
 * Created by Passant on 6/12/2017.
 */

public class SearchResultModel extends BusinessObject {

    @Expose
    @SerializedName("quran")
    ArrayList<Entries> quraanList;

    @Expose
    @SerializedName("selections")
    ArrayList<Entries> quraanSelectionList;

    @Expose
    @SerializedName("authors")
    ArrayList<AuthorWrapperModel> authorList;


    public ArrayList<Entries> getQuraanList() {
        return quraanList;
    }

    public void setQuraanList(ArrayList<Entries> quraanList) {
        this.quraanList = quraanList;
    }

    public ArrayList<Entries> getQuraanSelectionList() {
        return quraanSelectionList;
    }

    public void setQuraanSelectionList(ArrayList<Entries> quraanSelectionList) {
        this.quraanSelectionList = quraanSelectionList;
    }

    public ArrayList<AuthorWrapperModel> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(ArrayList<AuthorWrapperModel> authorList) {
        this.authorList = authorList;
    }

    /**
     * The server CRUD methods should be provided by the entity sub-classing {@linkplain BusinessObject}
     *
     * @param saveMethod
     * @param deleteMethod
     * @param getMethod
     */
    public SearchResultModel(String saveMethod, String deleteMethod, String getMethod) {
        super(saveMethod, deleteMethod, getMethod);
    }
}
