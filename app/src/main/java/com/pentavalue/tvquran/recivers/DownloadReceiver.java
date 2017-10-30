package com.pentavalue.tvquran.recivers;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.pentavalue.tvquran.service.DownloadService;
import com.pentavalue.tvquran.utils.Config;

/**
 * Created by Passant on 6/6/2017.
 */

public class DownloadReceiver extends ResultReceiver {

    public static int Down_progress=0;
    public DownloadReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (resultCode == Config.UPDATE_PROGRESS) {
            int progress = resultData.getInt("progress");
            Log.i("ZOZO","prog>>"+progress);

            Down_progress=progress;
            if (DownloadService.isDownloading && progress == 100) {
                DownloadService.isDownloading = false;
            }
           // mProgressDialog.setProgress(progress);

        }
        else
        {
            //Show faluir msg
        }

    }
}
