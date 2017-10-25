package com.pentavalue.tvquran.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.application.ApplicationController;
import com.pentavalue.tvquran.data.constants.Params;
import com.pentavalue.tvquran.datasorage.database.HistoryTable;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.ui.activities.ParentActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousry on 10/22/2017.
 */

public class NotificationService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {


    private static final String TAG = NotificationService.class.getSimpleName();
    public static int progress = 0;
    private static MediaPlayer mMediaPlayer;
    private static int postion = 0;
    private static Entries model;
    /**
     * indicates whether onRebind should be used
     */
    boolean mAllowRebind;
    /**
     * indicates how to behave if the service is killed
     */
    int mStartMode;
    /**
     * interface for clients that bind
     */
    IBinder mBinder;
    boolean playFromNotification = false;
    private MediaSessionManager mManager;
    private MediaSession mSession;
    private MediaController mController;
    private List<Entries> mList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private Runnable mUpdatedProgress = new Runnable() {
        @Override
        public void run() {
            try {
                if (mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {
                        progress = mMediaPlayer.getCurrentPosition();
                        Log.i("PornMovie", "" + progress);
                        mHandler.postDelayed(this, 100);
                    }
                }
            } catch (IllegalStateException | NullPointerException e) {
                Log.e(TAG, " " + e.getMessage());

            }
        }
    };

    /**
     * The service is starting, due to a call to startService()
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Done");
        mList = HistoryTable.getInstance().GetHistoryList();


        if (intent == null) {
            return Service.START_NOT_STICKY;
        }
        try {
            if (intent.getBooleanExtra(Params.INTENT_PARAMS.INTENT_KEY_STOP, false)) {
                if (mMediaPlayer != null) {
                    mController.getTransportControls().stop();
                }

            }
        } catch (RuntimeException ex) {
            //Log.e(TAG, ex.getMessage());
            ex.printStackTrace();
        }


        initMediaSessions();
        handleIntent(intent);
        return START_STICKY;
    }

    /**
     * Called when The service is no longer used and is being destroyed
     */


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSession.release();
        }
        Log.v(TAG, "onDestroy");
    }

    /**
     * A client is binding to the service with bindService()
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "DoneDone");
        return null;

    }

    /**
     * Called when all clients have unbound with unbindService()
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onUnbind(Intent intent) {
        mSession.release();
        return mAllowRebind;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null)
            return;

        String action = intent.getAction();

        if (action.equalsIgnoreCase(Params.ACTIONS.ACTION_PLAY)) {
            if (intent.hasExtra(Params.INTENT_PARAMS.INTENT_KEY_POSITION)) {


                //postion = intent.getIntExtra(Params.INTENT_PARAMS.INTENT_KEY_POSITION, 0);
                model = (Entries) intent.getSerializableExtra(Params.INTENT_PARAMS.INTENT_KEY_MODEL);
                progress = intent.getIntExtra(Params.INTENT_PARAMS.INTENT_KEY_PROGRESS, 0);
                Log.e(TAG, "Progress that coming from Parent Activity is " + progress);
                if (model != null) {
                    Entries entries = HistoryTable.getInstance().getHistoryByID(model.getId());

                    if (entries != null) {
                        model = entries;
                    }
                } else {
                    Log.e(TAG, "There are a problem in this sura!");
                    return;
                }
            }


            if (mMediaPlayer == null) {

                initMediaPlayer(model);
            }


            Log.v(TAG, "onAction Play");
            mController.getTransportControls().play();
        } else if (action.equalsIgnoreCase(Params.ACTIONS.ACTION_PAUSE)) {
            Log.v(TAG, "onAction Pause");
            mController.getTransportControls().pause();
        } else if (action.equalsIgnoreCase(Params.ACTIONS.ACTION_FAST_FORWARD)) {
            Log.v(TAG, "onAction Fast_Forward");
            mController.getTransportControls().fastForward();
        } else if (action.equalsIgnoreCase(Params.ACTIONS.ACTION_REWIND)) {
            Log.v(TAG, "onAction Rewind");
            mController.getTransportControls().rewind();
        } else if (action.equalsIgnoreCase(Params.ACTIONS.ACTION_PREVIOUS)) {
            Log.v(TAG, "onAction Previous");
            mController.getTransportControls().skipToPrevious();
        } else if (action.equalsIgnoreCase(Params.ACTIONS.ACTION_NEXT)) {
            Log.v(TAG, "onAction Next");
            mController.getTransportControls().skipToNext();
        } else if (action.equalsIgnoreCase(Params.ACTIONS.ACTION_STOP)) {
            Log.v(TAG, "onAction Stop");
            mController.getTransportControls().stop();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void buildNotification(Notification.Action action, @NonNull Entries model, int postion) {
        Notification.MediaStyle style = new Notification.MediaStyle();

        //int progress = mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : NotificationService.progress;
        Log.e(TAG, "Progress from Notification Builder is " + progress);
        Intent intent1 = new Intent(getApplicationContext(), ParentActivity.class);
        intent1.putExtra("NotClick", true);
        intent1.putExtra(Params.INTENT_PARAMS.INTENT_KEY_PROGRESS_MPLAYER, progress);
        intent1.putExtra(Params.INTENT_PARAMS.INTENT_KEY_POSITION, postion);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent mainIntent = PendingIntent.getActivity(getApplicationContext(), Params.NOTIFICATION_ID.FOREGROUND_SERVICE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        intent.setAction(Params.ACTIONS.ACTION_STOP);

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.homeblue)
                .setContentIntent(mainIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.moshaficon))
                .setContentTitle(model.getTitle())
                .setContentText(model.getReciter_name())
                .setOngoing(true)
                .setDeleteIntent(pendingIntent)
                .setSubText("Tap to open Tv Quraan.")
                .setStyle(style);

        builder.addAction(generateAction(android.R.drawable.ic_media_previous, "Previous", Params.ACTIONS.ACTION_PREVIOUS));
        builder.addAction(action);
        builder.addAction(generateAction(android.R.drawable.ic_media_next, "Next", Params.ACTIONS.ACTION_NEXT));
        style.setShowActionsInCompactView(0, 1, 2);

        //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        startForeground(Params.NOTIFICATION_ID.FOREGROUND_SERVICE,
                builder.build());
        //notificationManager.notify(Params.NOTIFICATION_ID.FOREGROUND_SERVICE, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private Notification.Action generateAction(int icon, String title, String intentAction) {
        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        intent.setAction(intentAction);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), Params.NOTIFICATION_ID.FOREGROUND_SERVICE, intent, 0);
        return new Notification.Action.Builder(icon, title, pendingIntent).build();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initMediaSessions() {
        mSession = new MediaSession(getApplicationContext(), "simple player session");
        mSession.setActive(true);
        mController = new MediaController(getApplicationContext(), mSession.getSessionToken());

        mSession.setCallback(new MediaSession.Callback() {
                                 @Override
                                 public void onPlay() {
                                     super.onPlay();
                                     Log.i(TAG, "onPlay");
                                     try {
                                         if (mMediaPlayer == null) {
                                             initMediaPlayer(model);
                                         }
                                         if (progress != 0) {
                                             Log.i(TAG, "Progress is --> " + progress);

                                             mMediaPlayer.seekTo(progress);
                                         }
                                         updateProgressMediaPlayer();

                                         mMediaPlayer.start();
                                         Toast.makeText(getApplicationContext(), "" + mMediaPlayer.isPlaying(), Toast.LENGTH_SHORT).show();
                                         buildNotification(generateAction(android.R.drawable.ic_media_pause, "Pause", Params.ACTIONS.ACTION_PAUSE), model, postion);
                                     } catch (RuntimeException e) {
                                         Log.e(TAG, "Exception ---> " + e.getMessage());
                                         if (progress != 0) {
                                             Log.i(TAG, "Progress is --> " + progress);

                                             try {
                                                 mMediaPlayer.seekTo(progress);
                                             } catch (IllegalStateException ex) {
                                                 ex.printStackTrace();
                                             }
                                         }
                                         initMediaPlayer(model);
                                         mMediaPlayer.start();
                                         buildNotification(generateAction(android.R.drawable.ic_media_pause, "Pause", Params.ACTIONS.ACTION_PAUSE), model, postion);
                                     }
                                     //mMediaPlayer.start();
                                 }

                                 @Override
                                 public void onPause() {
                                     super.onPause();
                                     if (mMediaPlayer == null) {
                                         onStop();
                                         return;
                                     }
                                     progress = mMediaPlayer.getCurrentPosition();
                                     Log.e(TAG, "onPause  -->" + mMediaPlayer.isPlaying());
                                     mMediaPlayer.pause();
                                     Toast.makeText(getApplicationContext(), "" + mMediaPlayer.isPlaying(), Toast.LENGTH_SHORT).show();
                                     buildNotification(generateAction(android.R.drawable.ic_media_play, "Play", Params.ACTIONS.ACTION_PLAY), model, postion);
                                 }

                                 @Override
                                 public void onSkipToNext() {
                                     super.onSkipToNext();
                                     Log.e(TAG, "onSkipToNext");
                                     progress = 0;
                                     if (postion >= 0 && postion < mList.size() && postion != mList.size() - 1) {
                                         postion += 1;
                                     }
                                     try {
                                         mMediaPlayer.reset();
                                         model = mList.get(postion);
                                         initMediaPlayer(mList.get(postion));
                                         onPlay();
                                     } catch (Exception e) {
                                         Log.e(TAG, e.getMessage());
                                     }
                                     //Change media here
                                     //buildNotification(generateAction(android.R.drawable.ic_media_pause, "Pause", ACTION_PAUSE),model);
                                 }

                                 @Override
                                 public void onSkipToPrevious() {
                                     super.onSkipToPrevious();
                                     Log.e(TAG, "onSkipToPrevious");
                                     progress = 0;
                                     if (postion > 0 && postion < mList.size() && postion != 0) {
                                         postion -= 1;
                                     }
                                     try {
                                         mMediaPlayer.reset();
                                         model = mList.get(postion);

                                         initMediaPlayer(mList.get(postion));
                                         mController.getTransportControls().play();
                                     } catch (Exception e) {
                                         Log.e(TAG, e.getMessage());
                                     }


                                     //Change media here
                                     //buildNotification(generateAction(android.R.drawable.ic_media_pause, "Pause", ACTION_PAUSE),model);
                                 }

                                 @Override
                                 public void onFastForward() {
                                     super.onFastForward();
                                     Log.e(TAG, "onFastForward");
                                     progress += 10;
                                     //Manipulate current media here
                                     //mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() + 10);
                                 }

                                 @Override
                                 public void onRewind() {
                                     super.onRewind();
                                     Log.e(TAG, "onRewind");
                                     progress -= 10;
                                     //Manipulate current media here
                                 }

                                 @Override
                                 public void onStop() {
                                     super.onStop();
                                     Log.e(TAG, "onStop");
                                     try {
                                         progress = 0;
                                         if (mMediaPlayer != null) {
                                             mMediaPlayer.stop();
                                             mMediaPlayer.release();
                                         }
                                         stopForeground(true);
                                     } catch (IllegalStateException e) {
                                         stopForeground(true);
                                     }

                                 }

                                 @Override
                                 public void onSeekTo(long pos) {
                                     super.onSeekTo(pos);

                                 }

                                 @Override
                                 public void onSetRating(Rating rating) {
                                     super.onSetRating(rating);
                                 }
                             }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initMediaPlayer(Entries model) {
        mMediaPlayer = new MediaPlayer();
        try {
            Log.i(TAG, model.getSoundPath());
            mMediaPlayer.setDataSource(model.getSoundPath());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
        } catch (IOException | NullPointerException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCompletion(MediaPlayer mp) {
        //buildNotification(generateAction(android.R.drawable.ic_media_play, "Play", Params.ACTIONS.ACTION_PLAY), model);
        mController.getTransportControls().rewind();

    }

    public void updateProgressMediaPlayer() {
        ApplicationController.getInstance().runInBackground(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(mUpdatedProgress, 100);
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        //mMediaPlayer.seekTo(progress);
        //mMediaPlayer.start();
    }
}
