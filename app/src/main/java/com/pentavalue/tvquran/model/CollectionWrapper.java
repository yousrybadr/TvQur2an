package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

import java.util.List;

/**
 * Created by OmniaGamil on 5/22/2017.
 */

public class CollectionWrapper extends BusinessObject {

    @SerializedName("collections")
    @Expose
    List<Collection> collections;
    public CollectionWrapper(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }
}
