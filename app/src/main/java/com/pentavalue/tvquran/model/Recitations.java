package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

import java.util.List;

/**
 * Created by OmniaGamil on 5/17/2017.
 */

public class Recitations extends BusinessObject {
    @SerializedName("collection")
    @Expose
    public String collection;

    @SerializedName("total_entries")
    @Expose
    public String total_entries;
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
    public Recitations(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getTotal_entries() {
        return total_entries;
    }

    public void setTotal_entries(String total_entries) {
        this.total_entries = total_entries;
    }

    public List<Entries> getEntries() {
        return entries;
    }

    public void setEntries(List<Entries> entries) {
        this.entries = entries;
    }
}
