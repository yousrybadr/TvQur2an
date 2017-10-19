package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by OmniaGamil on 5/24/2017.
 */

public class SearchName extends BusinessObject {
    @SerializedName("reciters")
    @Expose
    HashMap<String,String>recitersResult;

     List<String> name;
    public SearchName(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
    }

    public HashMap<String, String> getRecitersResult() {
        return recitersResult;
    }

    public void setRecitersResult(HashMap<String, String> recitersResult) {
        this.recitersResult = recitersResult;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }
}
