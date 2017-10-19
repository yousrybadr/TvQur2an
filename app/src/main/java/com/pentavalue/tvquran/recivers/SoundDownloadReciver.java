package com.pentavalue.tvquran.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pentavalue.tvquran.datasorage.database.HistoryTable;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.network.listeneres.OnDownloadCompleteListner;

/**
 * Created by Passant on 6/6/2017.
 */

public class SoundDownloadReciver extends BroadcastReceiver {

    public static OnDownloadCompleteListner onDownloadCompleteListner;
    public static String DOWNLOAD_RECIVE="done";
    public static String DOWNLOAD_FAIL="fail";
    public static final String ACTION_RESP =
            "com.pentavalue.tvquran.recivers.intent.action.MESSAGE_PROCESSED";
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction()==SoundDownloadReciver.ACTION_RESP){
            if (onDownloadCompleteListner!=null){
                onDownloadCompleteListner.showDoneDialog();


            }
        }
        else {
            if (onDownloadCompleteListner!=null)
                onDownloadCompleteListner.showFailDialog();
        }
    }
}
