package com.pentavalue.tvquran.datasorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.pentavalue.tvquran.application.ApplicationController;

/**
 * Created by Passant on 5/4/2017.
 */

public class AppSharedPrefs {

    private static final String SHARED_PREFS_NAME = "appTVQuran";

    public static SharedPreferences getSharedPreferences() {

        return ApplicationController.getInstance().getSharedPreferences(
                SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * function to clear all data
     */
    public static void ClearSherdPrefs()
    {

        getSharedPreferences().edit().clear().commit();
    }


    public static void saveStringPrefs(String key,String val)
    {
        getSharedPreferences().edit().putString(key,val).commit();
    }

    public static String getStringVal(String key)
    {
        return getSharedPreferences().getString(key,"");
    }
    public static void saveBooleanPrefs(String key,boolean val){
        getSharedPreferences().edit().putBoolean(key,val).commit();

    }
    public static boolean getBooleanVal(String key)
    {
        return getSharedPreferences().getBoolean(key,false);
    }



}
