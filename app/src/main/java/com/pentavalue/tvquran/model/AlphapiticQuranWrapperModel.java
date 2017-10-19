package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Passant on 8/10/2017.
 */

public class AlphapiticQuranWrapperModel implements Serializable {

    @Expose
    @SerializedName("eCode")
    int code;
    @Expose
    @SerializedName("eDesc")
    String desc;
    @SerializedName("eContent")
    @Expose
    HashMap<String,ArrayList<WrapperData>> alphabeticalHolyQuran;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public HashMap<String, ArrayList<WrapperData>> getAlphabeticalHolyQuran() {
        return alphabeticalHolyQuran;
    }

    public void setAlphabeticalHolyQuran(HashMap<String, ArrayList<WrapperData>> alphabeticalHolyQuran) {
        this.alphabeticalHolyQuran = alphabeticalHolyQuran;
    }
}
