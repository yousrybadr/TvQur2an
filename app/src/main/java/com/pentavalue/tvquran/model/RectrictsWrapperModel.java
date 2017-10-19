package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Passant on 8/14/2017.
 */

public class RectrictsWrapperModel   implements Serializable{

    @Expose
    @SerializedName("reciters")
    ArrayList<Reciters> recitersesList;

    public ArrayList<Reciters> getRecitersesList() {
        return recitersesList;
    }

    public void setRecitersesList(ArrayList<Reciters> recitersesList) {
        this.recitersesList = recitersesList;
    }


}
