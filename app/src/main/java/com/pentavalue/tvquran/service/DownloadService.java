package com.pentavalue.tvquran.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pentavalue.tvquran.datasorage.database.HistoryTable;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.recivers.SoundDownloadReciver;
import com.pentavalue.tvquran.utils.Config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Passant on 6/6/2017.
 */

public class DownloadService extends IntentService {

    public static final String PARAM_OUT_MSG="outMsg";
    public static String PROGRESS_UPDATE_ACTION="progress";
    public static int NEW_PROGRESS=0;
  public static  long total;
    File file;
    Entries dataObject;
    public static boolean isDownloading=false;
    //private LocalBroadcastManager mBroadcastManager;
    public DownloadService( ) {
        super(null );
        //mBroadcastManager = LocalBroadcastManager.getInstance(ApplicationController.getInstance().getApplicationContext());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String urlToDownload = intent.getStringExtra("url");
        String fileName=intent.getStringExtra("fileName");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        dataObject= (Entries) intent.getSerializableExtra("data");
        File sdFile=new File(Environment.getExternalStorageDirectory().getPath()+"/TV-Quran/");
        if (!sdFile.exists())
            sdFile.mkdir();
       // ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {
            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();
            // this will be useful so that you can show a typical 0-100% progress bar
          final   int fileLength = connection.getContentLength();

            // download the file
            isDownloading=true;
            InputStream input = new BufferedInputStream(connection.getInputStream());
              file=new File(sdFile,fileName+".mp3");
            dataObject.setDownloadedPath(file.getPath());
            int lenghtOfFile = connection.getContentLength();
            HistoryTable.getInstance().updateDownloadItem(dataObject.getId(),file.getPath());
            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];
             total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                Log.i("LOLO","down>>"+total);
              //  publishProgress(total);
                Bundle dataB = new Bundle();


                // here you are sending progress into ResultReceiver located in your Activity

                int progress=(int)((total*100)/lenghtOfFile);
                dataB.putInt("progress", progress);
                receiver.send(Config.UPDATE_PROGRESS, dataB);
                output.write(data, 0, count);


            }

            output.flush();
            output.close();
            input.close();
          //  Log.i("PATH",file.getAbsolutePath());
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(SoundDownloadReciver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(PARAM_OUT_MSG, "All data retrive");
            broadcastIntent.putExtra("file",file.getPath());
            broadcastIntent.putExtra("data",dataObject);
           // isDownloading=false;
            sendBroadcast(broadcastIntent);
        } catch (IOException e) {
            Log.i("EXXX","inside exception from service");
            e.printStackTrace();
          //  Log.i("PATH",file.getAbsolutePath());
            //isDownloading=false;
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(SoundDownloadReciver.DOWNLOAD_FAIL);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(PARAM_OUT_MSG, "fail");
            if (file!=null)
            broadcastIntent.putExtra("file",file.getAbsolutePath());
            broadcastIntent.putExtra("data",dataObject);
            sendBroadcast(broadcastIntent);
        }

//        Bundle resultData = new Bundle();
//        resultData.putInt("progress" ,100);
//        receiver.send(Config.UPDATE_PROGRESS, resultData);

    }

    private void publishProgress(long total) {

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(PROGRESS_UPDATE_ACTION);
       // i.putExtra("position", positions);
        broadcastIntent.putExtra("progress", total);
       // i.putExtra("oneshot", true);
        sendBroadcast(broadcastIntent);
    }

}
