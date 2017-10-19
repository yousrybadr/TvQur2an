package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

/**
 * Created by OmniaGamil on 5/16/2017.
 */

public class filter extends BusinessObject {
    @SerializedName("en")
    @Expose
    String English;
    @SerializedName("ar")
    @Expose
    String Arabic;
    public filter(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
    }
}
