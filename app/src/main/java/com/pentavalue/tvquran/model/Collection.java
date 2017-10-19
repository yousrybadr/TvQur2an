package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

import java.util.List;

/**
 * Created by OmniaGamil on 5/22/2017.
 */

public class Collection extends BusinessObject {
    @SerializedName("collection_name")
    @Expose
    String collection_name;
    @SerializedName("authors")
    @Expose
    List<Reciters>authors;
    public Collection(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
    }

    public String getCollection_name() {
        return collection_name;
    }

    public void setCollection_name(String collection_name) {
        this.collection_name = collection_name;
    }

    public List<Reciters> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Reciters> authors) {
        this.authors = authors;
    }
}
