package com.pentavalue.tvquran.model;

/**
 * Created by Passant on 6/14/2017.
 */

public class MapModel  {

    String Key;
    String value;



    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MapModel(String key, String value) {
        Key = key;
        this.value = value;
    }
}
