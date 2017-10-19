package com.pentavalue.tvquran.network.listeneres;

import java.util.List;

/**
 * Created by OmniaGamil on 5/16/2017.
 */

public interface OnEntityListReceivedListener <T> extends UiListener {
    public void onSuccess(List<T> obj);
}
