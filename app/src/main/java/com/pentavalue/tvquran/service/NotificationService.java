package com.pentavalue.tvquran.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.data.constants.Params;
import com.pentavalue.tvquran.datasorage.database.HistoryTable;
import com.pentavalue.tvquran.model.Entries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousry on 10/22/2017.
 */

public class NotificationService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {


    private static final String TAG = NotificationService.class.getSimpleName();
    private static MediaPlayer mMediaPlayer;
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
    private MediaSessionManager mManager;
    private MediaSession mSession;
    private MediaController mController;

    private List<Entries> mList = new ArrayList<>();

    private int postion = -1;
    private int progress = 0;
    private Entries model;

    /**
     * The service is starting, due to a call to startService()
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Done");
        mList = HistoryTable.getInstance().GetHistoryList();

        if (intent.hasExtra(Params.INTENT_PARAMS.INTENT_KEY_POSITION)) {

            postion = intent.getIntExtra(Params.INTENT_PARAMS.INTENT_KEY_POSITION, 0);
            model = (Entries) intent.getSerializableExtra(Params.INTENT_PARAMS.INTENT_KEY_MODEL);
            progress = intent.getIntExtra(Params.INTENT_PARAMS.INTENT_KEY_PROGRESS, 0);

        }


        if (mManager == null) {

            initMediaSessions(model);
        }
        handleIntent(intent);
        return START_STICKY;
    }

    /**
     * Called when The service is no longer used and is being destroyed
     */


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.release();
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
    private void buildNotification(Notification.Action action, @NonNull Entries model) {
        Notification.MediaStyle style = new Notification.MediaStyle();

        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        intent.setAction(Params.ACTIONS.ACTION_STOP);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.moshaficon)
                .setContentTitle(model.getTitle())
                .setContentText(model.getReciter_name())
                .setContentIntent(pendingIntent)
                .setStyle(style);

        builder.addAction(generateAction(android.R.drawable.ic_media_previous, "Previous", Params.ACTIONS.ACTION_PREVIOUS));
        builder.addAction(generateAction(android.R.drawable.ic_media_rew, "Rewind", Params.ACTIONS.ACTION_REWIND));
        builder.addAction(action);
        builder.addAction(generateAction(android.R.drawable.ic_media_ff, "Fast Foward", Params.ACTIONS.ACTION_FAST_FORWARD));
        builder.addAction(generateAction(android.R.drawable.ic_media_next, "Next", Params.ACTIONS.ACTION_NEXT));
        style.setShowActionsInCompactView(0, 1, 2, 3, 4);

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
    private void initMediaSessions(@NonNull final Entries model) {
        mMediaPlayer = new MediaPlayer();

        try {
            Log.i(TAG, model.getSoundPath());
            mMediaPlayer.setDataSource(model.getSoundPath());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnCompletionListener(this);

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }


        mSession = new MediaSession(getApplicationContext(), "simple player session");
        mController = new MediaController(getApplicationContext(), mSession.getSessionToken());

        mSession.setCallback(new MediaSession.Callback() {
                                 @Override
                                 public void onPlay() {
                                     super.onPlay();
                                     Log.e(TAG, "onPlay");
                                     //mMediaPlayer.start();
                                     mMediaPlayer.start();
                                     buildNotification(generateAction(android.R.drawable.ic_media_pause, "Pause", Params.ACTIONS.ACTION_PAUSE), model);
                                 }

                                 @Override
                                 public void onPause() {
                                     super.onPause();
                                     Log.e(TAG, "onPause  -->" + mMediaPlayer.isPlaying());
                                     if (mMediaPlayer.isPlaying()) {
                                         mMediaPlayer.pause();
                                     }
                                     //Toast.makeText(getApplicationContext(), "" + mMediaPlayer.isPlaying(), Toast.LENGTH_SHORT).show();
                                     buildNotification(generateAction(android.R.drawable.ic_media_play, "Play", Params.ACTIONS.ACTION_PLAY), model);
                                 }

                                 @Override
                                 public void onSkipToNext() {
                                     super.onSkipToNext();
                                     Log.e(TAG, "onSkipToNext");
                                     //Change media here
                                     //buildNotification(generateAction(android.R.drawable.ic_media_pause, "Pause", ACTION_PAUSE),model);
                                 }

                                 @Override
                                 public void onSkipToPrevious() {
                                     super.onSkipToPrevious();
                                     Log.e(TAG, "onSkipToPrevious");
                                     //Change media here
                                     //buildNotification(generateAction(android.R.drawable.ic_media_pause, "Pause", ACTION_PAUSE),model);
                                 }

                                 @Override
                                 public void onFastForward() {
                                     super.onFastForward();
                                     Log.e(TAG, "onFastForward");
                                     //Manipulate current media here
                                     //mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() + 10);
                                 }

                                 @Override
                                 public void onRewind() {
                                     super.onRewind();
                                     Log.e(TAG, "onRewind");
                                     //Manipulate current media here
                                 }

                                 @Override
                                 public void onStop() {
                                     super.onStop();
                                     Log.e(TAG, "onStop");

                                     stopForeground(true);
                                     //Stop media player here
                                     /*mMediaPlayer.stop();
                                     mMediaPlayer.release();
                                     NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                     notificationManager.cancel(1);
                                     Intent intent = new Intent(getApplicationContext(), NotificationService.class);
                                     stopService(intent);*/
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

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
