package com.pentavalue.tvquran.network.listeneres;

/**
 * Created by OmniaGamil on 5/16/2017.
 */

public interface OnEntityReceivedListener <T> extends UiListener{
    public void onSuccess(T obj);
}