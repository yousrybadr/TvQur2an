package com.pentavalue.tvquran.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.pentavalue.tvquran.datasorage.database.DatabaseManager;
import com.pentavalue.tvquran.network.listeneres.ConnectivityReceiverListener;
import com.pentavalue.tvquran.network.listeneres.OnDownloadCompleteListner;
import com.pentavalue.tvquran.recivers.ConnectivityReceiver;
import com.pentavalue.tvquran.recivers.SoundDownloadReciver;
import com.pentavalue.tvquran.utils.TypefaceUtil;
import com.pentavalue.tvquran.utils.TypefaceUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


/**
 * @author Mahmoud Turki
 */
public class ApplicationController extends Application {
    private Handler mHandler;
    private ExecutorService mExecutorService;
    private static final int THREAD_POOL_SIZE = 3;
 /*   private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;*/
    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static ApplicationController app;
    public static boolean appEnterBackGround = false;

    /**
     * @return ApplicationController singleton instance
     */
    public synchronized static ApplicationController getInstance() {
        if (app == null)
            app = new ApplicationController();
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // initialize the singleton
        app = this;
        mHandler = new Handler();
        DatabaseManager.getInstance( ).initializeTables();
     //   sAnalytics = GoogleAnalytics.getInstance(this);
        // Thread pool init
        mExecutorService = Executors.newScheduledThreadPool(THREAD_POOL_SIZE, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "Background Executors");
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.setDaemon(true);
                return thread;
            }
        });

        TypefaceUtils.setDefaultFont(this, "MONOSPACE", "fonts/proximanova.ttf");
        TypefaceUtils.overrideFont(this, "MONOSPACE", "fonts/proximanova.ttf");
        TypefaceUtil.overrideFont(this, "MONOSPACE", "fonts/proximanova.ttf");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void runOnUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }
    public void runOnUiMainThread(Runnable runnable) {
        mHandler.post(runnable);

    }

    public void runInBackground(final Runnable runnable) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setConnectivityListener( ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public void setDownloadListner(OnDownloadCompleteListner listner){
        SoundDownloadReciver.onDownloadCompleteListner=listner;
    }

    /*synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }*/
}