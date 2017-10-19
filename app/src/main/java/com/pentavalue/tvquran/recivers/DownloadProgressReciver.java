package com.pentavalue.tvquran.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.pentavalue.tvquran.service.DownloadService;

/**
 * Created by Passant on 7/16/2017.
 */

public class DownloadProgressReciver extends BroadcastReceiver {

   Context mcontext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mcontext=context;
        if (intent.getAction().equals(
                DownloadService.PROGRESS_UPDATE_ACTION)) {
                final int progress = intent.getIntExtra("progress", -1);
                //final int position = intent.getIntExtra("position", -1);
               /* if (position == -1) {
                    return;
                }*/
                onProgressUpdate(progress);
            }
        }

    private void onProgressUpdate( int progress) {

        Toast.makeText(mcontext,"progress>>"+progress, Toast.LENGTH_SHORT).show();

    }


}
