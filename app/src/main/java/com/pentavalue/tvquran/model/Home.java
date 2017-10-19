package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

import java.util.List;

/**
 * Created by OmniaGamil on 5/16/2017.
 */

public class Home extends BusinessObject {


    @SerializedName("sorted")
    @Expose
    public String sorted;
    @SerializedName("reciters")
    @Expose
    List<Reciters>reciterses;
    @SerializedName("entries")
    @Expose
    List<Entries>entries;
    /**
     * The server CRUD methods should be provided by the entity sub-classing {@linkplain BusinessObject}
     *
     * @param saveMethod
     * @param deleteMethod
     * @param getMethod
     */
    public Home(String saveMethod, String deleteMethod, String getMethod) {
        super(null,null,null);
    }

    public String getSorted() {
        return sorted;
    }

    public void setSorted(String sorted) {
        this.sorted = sorted;
    }

    public List<Reciters> getReciterses() {
        return reciterses;
    }

    public void setReciterses(List<Reciters> reciterses) {
        this.reciterses = reciterses;
    }

    public List<Entries> getEntries() {
        return entries;
    }

    public void setEntries(List<Entries> entries) {
        this.entries = entries;
    }
}
