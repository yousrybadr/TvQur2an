package com.pentavalue.tvquran.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.application.ApplicationController;
import com.pentavalue.tvquran.data.constants.NetworkConstants;
import com.pentavalue.tvquran.data.parsing.ParsingManager;
import com.pentavalue.tvquran.datasorage.database.HistoryTable;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.Home;
import com.pentavalue.tvquran.model.Reciters;
import com.pentavalue.tvquran.network.helper.DataHelper;
import com.pentavalue.tvquran.network.helper.VolleyNetworkHelper;
import com.pentavalue.tvquran.network.response.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Passant on 6/27/2017.
 */

public class PlayerWidget extends AppWidgetProvider {

    TextView suraName, readerName, category;
    RemoteViews views;
    public static String PLAY_BUTTON = "com.pentavalue.tvquran.PLAY_BUTTON";
    public static String PREV_BUTTON = "com.pentavalue.tvquran.PREV_BUTTON";
    public static String NEXT_BUTTON = "com.pentavalue.tvquran.NEXT_BUTTON";
    public static String DOWNLOAD_BUTTON = "com.pentavalue.tvquran.DOWNLOAD_BUTTON";
    public static String AUTO_BUTTON = "com.pentavalue.tvquran.AUTO_BUTTON";
    static MediaPlayer mp;
    public static List<Entries> entries;
    public static int pos = 0;
    int total = 0;
    File file;
    public static int currentLenght ;
    public  static Bitmap bm  ;
    public static boolean isPlay=false;

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            final int currentWidgetId = appWidgetIds[i];
            views = new RemoteViews(context.getPackageName(), R.layout.player_widget_layout);
            views.setOnClickPendingIntent(R.id.img_play, getPendingSelfIntent(context, PLAY_BUTTON));
            views.setOnClickPendingIntent(R.id.img_next, getPendingSelfIntent(context, NEXT_BUTTON));
            views.setOnClickPendingIntent(R.id.img_prev, getPendingSelfIntent(context, PREV_BUTTON));
            views.setOnClickPendingIntent(R.id.btn_downLoad, getPendingSelfIntent(context, DOWNLOAD_BUTTON));
            views.setOnClickPendingIntent(R.id.autoPlay, getPendingSelfIntent(context, AUTO_BUTTON));
           // Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
            appWidgetManager.updateAppWidget(currentWidgetId, views);


          /*  final Handler handler = new Handler();
            Timer    timer = new Timer();
            TimerTask doAsynchronousTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @SuppressWarnings("unchecked")
                        public void run() {
                            try {
                                Log.i("LOL","in the timer Task");
                                callSuraListApi(context, appWidgetManager, currentWidgetId);

                            }
                            catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };
            timer.schedule(doAsynchronousTask, 0, 2000*60);*/
            callSuraListApi(context, appWidgetManager, currentWidgetId);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }


    private void callSuraListApi(Context context, final AppWidgetManager appWidgetManager, final int currentWidgetId) {
        JSONObject json = new JSONObject();
        try {
            json.put("sort", 1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.HOME_URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                BaseResponse baseResponse = null;
                try {
                    baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        Home reciters = DataHelper.deserialize(baseResponse.getData(), Home.class);
                        //List<Reciters> list = handleObjectListServerResponseReciters(baseResponse, Reciters.class);
                        // List<Entries> list2 = handleObjectListServerResponseEntries(baseResponse, Entries.class);
                        //  typeTxt.setText(reciters.getSorted());

                        List<Reciters> reciterses = reciters.getReciterses();
                        entries = reciters.getEntries();
                        List<Entries> entriesById = new ArrayList<>();
                        for (int i = 0; i < entries.size(); i++) {
                            if (reciterses.get(1).getId() == entries.get(i).getAuthor_id()) {
                                entriesById.add(entries.get(i));
                            }
                        }
                        pos = 0;
                        if (views!=null){
                            views.setTextViewText(R.id.txt_suraName, entries.get(pos).getTitle());
                            views.setTextViewText(R.id.txt_readerName, entries.get(pos).getReciter_name());
                            views.setTextViewText(R.id.txt_category, entries.get(pos).getCategory_name());
                            views.setImageViewUri(R.id.img_reader, Uri.parse(entries.get(pos).getReciter_photo()));
                            //  setupRecyclerViewSorted(recyclerViewSorted,entries);
                            appWidgetManager.updateAppWidget(currentWidgetId, views);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // hideLoadingDialog();
            }
        });

        VolleyNetworkHelper.getInstance(context).addToRequestQueue(req);

    }


    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private int widgetsInstalled(Context context) {
        ComponentName thisWidget = new ComponentName(context, PlayerWidget.class);
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        return mgr.getAppWidgetIds(thisWidget).length;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), PlayerWidget.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

        int widgetsInstalled = widgetsInstalled(context);
        if (widgetsInstalled != 0 && intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            startWidgetUpdateService(context, appWidgetManager, appWidgetIds);
        }

        if (PLAY_BUTTON.equals(intent.getAction())) {
            if (entries != null) {
                if (mp != null && mp.isPlaying()){
                    isPlay=false;
                    currentLenght = mp.getCurrentPosition();
                    Log.i("ZOZ","on pause "+currentLenght);
                    mp.pause();
                }
                   else{
                    isPlay=true;
                    doPlaySura();
                }
                updateAppWidget(context, appWidgetManager, appWidgetIds[0], entries.get(pos));


            }

        } else if (intent.getAction().equals(DOWNLOAD_BUTTON)) {

            //Todo call download service

            if (entries != null) {


                downloadSourah();
               /* Intent downIntent = new Intent(context, DownloadService.class);
                downIntent.putExtra("url", entries.get(pos).getSoundPath());
                downIntent.putExtra("fileName", entries.get(pos).getTitle());
                downIntent.putExtra("receiver", new DownloadReceiver(new Handler()));
                downIntent.putExtra("data", entries.get(pos));
                context.startService(intent);*/
                updateAppWidget(context, appWidgetManager, appWidgetIds[0], entries.get(pos));
            }


        } else if (intent.getAction().equals(PREV_BUTTON)) {

            //  goToPrev(pos);
            pos = pos - 1;
            currentLenght=0;
            if (entries != null)
                if (pos > 0) {
                    updateAppWidget(context, appWidgetManager, appWidgetIds[0], entries.get(pos));
                }

        } else if (intent.getAction().equals(NEXT_BUTTON)) {
            pos = pos + 1;
            currentLenght=0;
            if (entries != null)
                if (pos < entries.size()) {
                    updateAppWidget(context, appWidgetManager, appWidgetIds[0], entries.get(pos));

                }
        } else if (intent.getAction().equals(AUTO_BUTTON)) {

            if (mp != null) {
                mp.setLooping(true);
                updateAppWidget(context, appWidgetManager, appWidgetIds[0], entries.get(pos));
            }
        } else if (PLAY_BUTTON.equals(intent.getAction())) {

            doPlaySura();
            updateAppWidget(context, appWidgetManager, appWidgetIds[0], entries.get(pos));

        }
        super.onReceive(context, intent);

    }


    private void startWidgetUpdateService(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        callSuraListApi(context, appWidgetManager, appWidgetIds[0]);

    }

    private void downloadSourah() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                File sdFile = new File(Environment.getExternalStorageDirectory().getPath() + "/TV-Quran/");
                if (!sdFile.exists())
                    sdFile.mkdir();
                // ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
                try {
                    URL url = new URL(entries.get(pos).getSoundPath());
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    // this will be useful so that you can show a typical 0-100% progress bar
                    final int fileLength = connection.getContentLength();

                    // download the file

                    InputStream input = new BufferedInputStream(connection.getInputStream());
                    file = new File(sdFile, entries.get(pos).getTitle() + ".mp3");
                    entries.get(pos).setDownloadedPath(file.getPath());
                    int lenghtOfFile = connection.getContentLength();
                    HistoryTable.getInstance().insertHistoryModel(entries.get(pos));
                    OutputStream output = new FileOutputStream(file);

                    byte data[] = new byte[1024];
                    total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        Log.i("LOLO", "down>>" + total);
                        //  publishProgress(total);
                        Bundle dataB = new Bundle();


                        // here you are sending progress into ResultReceiver located in your Activity

                       /* int progress=(int)((total*100)/lenghtOfFile);
                        dataB.putInt("progress", progress);
                        receiver.send(Config.UPDATE_PROGRESS, dataB);*/
                        output.write(data, 0, count);


                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (IOException e) {
                    Log.i("EXXX", "inside exception from service");
                    e.printStackTrace();

                }
            }
        });
        thread.start();

    }


    private void doPlaySura() {


        if (mp != null && !mp.isPlaying()) {

            try {
                mp.reset();
                mp.setDataSource(entries.get(pos).getSoundPath());
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        if (currentLenght != 0)
                            mp.seekTo(currentLenght);
                        mediaPlayer.start();

                    }
                });
                mp.prepareAsync();

            } catch (IOException e) {
                e.printStackTrace();
              //  Toast.makeText(this, getString(R.string.error_play_sound), Toast.LENGTH_SHORT).show();
            }



        } else {
            if (mp==null)
                mp=new MediaPlayer();
            try {

                mp.reset();
                mp.setDataSource(entries.get(pos).getSoundPath());
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                        /*duration = mp.getDuration();
                        currentDuration = mp.getCurrentPosition();
                        Log.i("LOLO", "duration=" + duration);
                        Log.i("LOLO", "current=" + currentDuration);*/

                    }
                });
                mp.prepareAsync();

            } catch (IOException e) {
                e.printStackTrace();
               // Toast.makeText(this, getString(R.string.error_play_sound), Toast.LENGTH_SHORT).show();

            }
        }
    }

    public static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, Entries model) {


        final RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.player_widget_layout);
        updateViews.setTextViewText(R.id.txt_suraName, model.getTitle());
        updateViews.setTextViewText(R.id.txt_readerName, model.getReciter_name());
        updateViews.setTextViewText(R.id.txt_category, model.getCategory_name());

       Bitmap bm=getImageBitmap(entries.get(pos).getReciter_photo());

        if (bm!=null){
            Log.i("ZOZ","on bit map loaded");
            updateViews.setImageViewBitmap(R.id.img_reader, bm);

        }
       /* Picasso.with(context).load(entries.get(pos).getReciter_photo()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
             Log.i("ZOZ","on bit map loaded");
                updateViews.setImageViewBitmap(R.id.img_reader, bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

                Log.i("ZOZ", errorDrawable.toString());

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.i("ZOZ", placeHolderDrawable.toString());

            }
        });*/
     //   updateViews.setImageViewUri(R.id.img_reader, Uri.parse(entries.get(pos).getReciter_photo()));


        if (isPlay==true) {
            //  updateViews.set
            Log.i("ZOZ","on update view after play");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.player_outpause);
            updateViews.setImageViewBitmap(R.id.img_play, icon);

        } else {
            Log.i("ZOZ","on update view after play");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.player_outplay);
                    updateViews.setImageViewBitmap(R.id.img_play, icon);
                }

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);

        // Toast.makeText(context, "updateAppWidget(): " + String.valueOf(appWidgetId) + "\n" + strWidgetText, Toast.LENGTH_LONG).show();

    }




    private static Bitmap getImageBitmap(final String url) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                  //  Bitmap bm =null ;
                    URL aURL = new URL(url);
               final     URLConnection conn = aURL.openConnection();
                    conn.connect();
                    ApplicationController.getInstance().runInBackground(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                InputStream is = conn.getInputStream();
                                BufferedInputStream bis = new BufferedInputStream(is);
                                bm = BitmapFactory.decodeStream(bis);
                                //  bm=bm;
                                bis.close();
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    Log.e("ZOZ", "Error getting bitmap", e);
                }
            }
        }).run();

        return bm;
    }
}
