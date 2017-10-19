package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

/**
 * Created by R on 5/21/2017.
 */

public class WrapperData extends BusinessObject {

    @SerializedName("data")
    @Expose
    DataAlphabtical dataAlphabtical;

    public DataAlphabtical getDataAlphabtical() {
        return dataAlphabtical;
    }

    public void setDataAlphabtical(DataAlphabtical dataAlphabtical) {
        this.dataAlphabtical = dataAlphabtical;
    }

    public WrapperData(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
    }
}
