package com.pentavalue.tvquran.network.listeneres;

import java.util.List;

/**
 * Created by Passant on 1/2/2017.
 */

public interface OnEntityListReciveListner<T> extends UiListener {

    void onSuccess(List<T> objs);


}
