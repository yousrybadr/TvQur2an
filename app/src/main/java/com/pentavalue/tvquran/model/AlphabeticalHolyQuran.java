package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.network.managers.BusinessObject;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by OmniaGamil on 5/21/2017.
 */

public class AlphabeticalHolyQuran extends BusinessObject {



    @SerializedName("eContent")
    @Expose
    TreeMap<String,ArrayList<WrapperData>> alphabeticalHolyQuran;

    public AlphabeticalHolyQuran(String saveMethod, String deleteMethod, String getMethod) {
        super(null, null, null);
    }

    public TreeMap<String, ArrayList<WrapperData>> getAlphabeticalHolyQuran() {
        return alphabeticalHolyQuran;
    }

    public void setAlphabeticalHolyQuran(TreeMap<String, ArrayList<WrapperData>> alphabeticalHolyQuran) {
        this.alphabeticalHolyQuran = alphabeticalHolyQuran;
    }



}
