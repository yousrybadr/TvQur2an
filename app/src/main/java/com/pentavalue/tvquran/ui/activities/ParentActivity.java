package com.pentavalue.tvquran.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.PlayerRecyclerAdapter;
import com.pentavalue.tvquran.application.ApplicationController;
import com.pentavalue.tvquran.data.constants.Params;
import com.pentavalue.tvquran.datasorage.database.HistoryTable;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.network.NetworkChecker;
import com.pentavalue.tvquran.network.listeneres.ConnectivityReceiverListener;
import com.pentavalue.tvquran.network.listeneres.OnDownloadCompleteListner;
import com.pentavalue.tvquran.recivers.ConnectivityReceiver;
import com.pentavalue.tvquran.recivers.DownloadProgressReciver;
import com.pentavalue.tvquran.recivers.DownloadReceiver;
import com.pentavalue.tvquran.recivers.SoundDownloadReciver;
import com.pentavalue.tvquran.service.DownloadService;
import com.pentavalue.tvquran.service.NotificationService;
import com.pentavalue.tvquran.ui.fragments.AlphabeticalHolyQuranFragment;
import com.pentavalue.tvquran.ui.fragments.CatogryFragment;
import com.pentavalue.tvquran.ui.fragments.DiscoverFragment;
import com.pentavalue.tvquran.ui.fragments.HomeFragment;
import com.pentavalue.tvquran.ui.fragments.LikeFragment;
import com.pentavalue.tvquran.ui.fragments.LiveBroadcastFragment;
import com.pentavalue.tvquran.ui.fragments.MoreFragment;
import com.pentavalue.tvquran.ui.fragments.ReaderProfileFragment;
import com.pentavalue.tvquran.ui.fragments.SearchDetailsFragment;
import com.pentavalue.tvquran.ui.fragments.VideoAndLiveDetailsFragment;
import com.pentavalue.tvquran.ui.fragments.WatchVidioFragment;
import com.pentavalue.tvquran.utils.DialogUtilit;
import com.pentavalue.tvquran.utils.TimeUtilities;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ParentActivity extends BaseActivity implements
        View.OnClickListener,
        LiveBroadcastFragment.OnFragmentInteractionListener,
        LikeFragment.OnFragmentInteractionListener,
        DiscoverFragment.OnFragmentInteractionListener,
        ReaderProfileFragment.OnFragmentInteractionListener,
        VideoAndLiveDetailsFragment.OnFragmentInteractionListener,
        WatchVidioFragment.OnFragmentInteractionListener,
        CatogryFragment.OnFragmentInteractionListener,
        AlphabeticalHolyQuranFragment.OnFragmentInteractionListener,
        SearchDetailsFragment.OnFragmentInteractionListener,
        ConnectivityReceiverListener,
        OnDownloadCompleteListner {

    private static final String Tag = ParentActivity.class.getSimpleName();
    private static final String mediaTag = MediaPlayer.class.getSimpleName() + "_" + Tag;
    private static final int REQUEST_ACCESS_STORAGE = 112;
    public static ArrayList<Entries> mList = new ArrayList<>();
    public static ParentActivity activity;
    public static ProgressBar progress;
    public static boolean isConnected;
    public FLAGS flags;
    public String suraURL, suraName_str, shikhName_str;
    public int duration, currentDuration;
    @Bind(R.id.leftImg)
    ImageView leftImg;
    @Bind(R.id.rightImg)
    ImageView rightImg;
    @Bind(R.id.titleTxt)
    TextView titleTxt;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.SorahName)
    TextView txt_souraName;
    @Bind(R.id.ReciterName)
    TextView txt_reciterName;
    @Bind(R.id.PauseImg)
    ImageView playPauseBtn;
    @Bind(R.id.content_frame)
    FrameLayout fragmentContainer;
    @Bind(R.id.connectionLayout)
    View connectionView;
    @Bind(R.id.playerLayout)
    LinearLayout playerLayout;
    @Bind(R.id.main)
    SlidingUpPanelLayout slidingLayout;
    @Bind(R.id.smallPlayerLayout)
    RelativeLayout playerSmallLayout;
    /*   @Bind(R.id.pager)
       ViewPager suraViewPager;*/
    @Bind(R.id.suraRV)
    RecyclerView suraRV;
    /* @Bind(R.id.loading_gif)
     GifImageView preLoadingGif;*/
    @Bind(R.id.connectionTxt)
    TextView connectionText;
    @Bind(R.id.txtGo_online)
    TextView txtGo_online;
    @Bind(R.id.img_connection)
    ImageView img_connection;
    @Bind(R.id.moreTopLayout)
    LinearLayout noConnection;
    boolean isPlayerShown = false;
    boolean isSoundPlay = false;
    MediaPlayer mp;
    Entries model;
    int position;
    boolean isRepeat = false;
    SoundDownloadReciver receiver;
    SoundDownloadReciver FailReciver;
    DownloadProgressReciver downloadProgressReciver;
    boolean isFromSlidePanal = false;
    int currentLenght = 0;
    boolean fromList = false;
    LinearLayoutManager linearLayoutManager;
    //Tracker mTracker;
    Handler mHandler;
    /*
        @Bind(R.id.viewpager)
        CustomViewPager viewPager;*/
    int id = 0;
    int[] tapImage =
            {
                    R.drawable.home_tab,
                    R.drawable.search_tab,
                    R.drawable.favorite_tab,
                    R.drawable.setting_tab
            };
    /* int[] tapImageSelected =
             {
                     R.drawable.homeblue,
                     R.drawable.searchblue,
                     R.drawable.likeblue,
                     R.drawable.catblue
             };*/
    Dialog dialog;
    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            View v = linearLayoutManager.findViewByPosition(position);
            if (v != null) {
                long totalDuration = getDuration();
                long currentDuration = getCurrentDuration();
                TextView totalTime = (TextView) v.findViewById(R.id.txt_time);
                totalTime.setText("" + TimeUtilities.milliSecondsToTimer(totalDuration));
                // Displaying time completed playing
                TextView txtDuration = (TextView) v.findViewById(R.id.txt_duration);
                txtDuration.setText("" + TimeUtilities.milliSecondsToTimer(mp.getCurrentPosition()));
                //   Log.i("LOLO",""+TimeUtilities.milliSecondsToTimer(currentDuration));
                // Updating progress bar
                int progress = (int) (TimeUtilities.getProgressPercentage(currentDuration, totalDuration));
                //Log.d("Progress", ""+progress);
                SeekBar playerSeekBar = (SeekBar) v.findViewById(R.id.seekbar);
                playerSeekBar.setProgress((int) mp.getCurrentPosition());
                mHandler.postDelayed(this, 100);
            }

        }
    };

    public static void showProgressBar(long total, int fileLength) {

        progress = new ProgressBar(activity, null,
                android.R.attr.progressBarStyleSmall);
        progress.setMax(100);
        progress.setIndeterminate(true);
        progress.setProgress((int) (total * 100 / fileLength));
        progress.setVisibility(View.VISIBLE);

    }

    public int getDuration() {
        return duration;
    }

    public int getCurrentDuration() {
        return currentLenght;
    }

    public ImageView getRightImg() {
        if (isConnected)
            rightImg.setVisibility(View.VISIBLE);
        else
            rightImg.setVisibility(View.GONE);
        return rightImg;
    }

    public void setRightImg(ImageView rightImg) {
        this.rightImg = rightImg;
    }

    public ImageView getLeftImg() {
        return leftImg;
    }

    public void setLeftImg(ImageView leftImg) {
        this.leftImg = leftImg;
    }

    private void stopMediaService() {
        checkConnection();
        Intent stopIntent = new Intent(this, NotificationService.class);
        stopIntent.setAction(Params.ACTIONS.ACTION_STOP);
        stopIntent.putExtra(Params.INTENT_PARAMS.INTENT_KEY_STOP, true);
        startService(stopIntent);
        //replaceFragment(LikeFragment.newInstance(), true, true);
    }

    private boolean checkPendingIntent(int flag) {
        Intent intent = getIntent();
        if (intent == null) return false;

        if (intent.hasExtra("NotClick")) {
            boolean isNotClick = intent.getBooleanExtra("NotClick", true);
            if (isNotClick) {
                if (flag == 1) {
                    stopMediaService();
                }
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);


        init();

        IntentFilter filter = new IntentFilter(SoundDownloadReciver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new SoundDownloadReciver();
        registerReceiver(receiver, filter);
        IntentFilter filterFail = new IntentFilter(SoundDownloadReciver.DOWNLOAD_FAIL);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        FailReciver = new SoundDownloadReciver();
        registerReceiver(FailReciver, filterFail);

        downloadProgressReciver = new DownloadProgressReciver();
        registerReceiver(downloadProgressReciver, filter);
        checkRunTimePermission();
        /*IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter
                .addAction(DownloadService.PROGRESS_UPDATE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                downloadProgressReciver, intentToReceiveFilter);*/
        //for google analitic
        //Tracker mTracker = ApplicationController.getInstance().getDefaultTracker();


    }

    private void init() {
        ButterKnife.bind(this);
        /*setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);*/
//        playerOutLayout.setOnClickListener(this);

        if (mp == null) {
            mp = new MediaPlayer();
        }

        addIconToTaps();
        setupSlidingPanelLayout();


        activity = this;
        mHandler = new Handler();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        suraRV.setLayoutManager(linearLayoutManager);
        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(suraRV);


        if (checkPendingIntent(1)) {
            ArrayList<Entries> history = HistoryTable.getInstance().GetHistoryList();
            currentLenght = getIntent().getIntExtra(Params.INTENT_PARAMS.INTENT_KEY_PROGRESS_MPLAYER, 0);
            Log.e(NotificationService.class.getSimpleName(), "Current Length is --> " + currentLenght);
            if (NotificationService.progress >= 0) {
                currentLenght = NotificationService.progress;
                Log.e(NotificationService.class.getSimpleName(), "Current Length is --> " + NotificationService.progress);
                NotificationService.progress = 0;
            }
            showPlayerAndPlaySound(history, getIntent().hasExtra(Params.INTENT_PARAMS.INTENT_KEY_POSITION) ?
                    getIntent().getIntExtra(Params.INTENT_PARAMS.INTENT_KEY_POSITION, -1) : 0, 0);
        }


        suraRV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // super.onScrollStateChanged(recyclerView, newState);
                //  if (newState==RecyclerView.SCROLL_STATE_IDLE) {
                currentLenght = 0;
                position = linearLayoutManager.findFirstVisibleItemPosition();
                Log.i(Tag, "after scroll>>>" + position);
                suraURL = mList.get(position).getSoundPath();
                model = mList.get(position);
                playSound(suraURL, model);
            }

            // }
        });
    }

    public int getPosition() {
        return position;
    }

    private void setupSlidingPanelLayout() {

        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(Tag, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(Tag, "onPanelStateChanged " + newState);
                Log.i(Tag, "onPanelStateChanged " + position);

                if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN && mp.isPlaying()) {
                    //  playerSmallLayout.setVisibility(View.GONE);
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    playerSmallLayout.setVisibility(View.GONE);
                    Log.i(Tag, "current expand>>" + currentLenght);
                   /* suraViewPager.setAdapter(new PlayerPagerAdapter(mList, activity));
                    suraViewPager.getAdapter().notifyDataSetChanged();*/
                    isFromSlidePanal = true;

                    suraRV.setAdapter(new PlayerRecyclerAdapter(mList, activity));
                    suraRV.getAdapter().notifyDataSetChanged();
                    suraRV.scrollToPosition(position);

                   /* View v = linearLayoutManager.findViewByPosition(position);
                    if (v!=null) {
                        SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekbar);
                        seekBar.setProgress(currentLenght);
                    }*/
                    Log.i(Tag, "from panal>>>>" + position);
                } else if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    Log.i(Tag, "current collapsed>>" + currentLenght);

                    if (mp != null && mp.isPlaying())
                        playPauseBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_outpause));
                    else
                        playPauseBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_outplay));

                    if (position >= 0 && position <= mList.size()) {
                        updateSmallPlayer(mList.get(position));

                        playerSmallLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        slidingLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    private void updateSmallPlayer(Entries entity) {
        if (entity != null) {


            txt_souraName.setText(entity.getTitle());
            txt_reciterName.setText(entity.getReciter_name());
        }

    }

    @Override
    public void onBackPressed() {
        if (slidingLayout != null &&
                (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            if (tabLayout.getSelectedTabPosition() == 1) {
                DiscoverFragment frg = new DiscoverFragment();
                if (frg.getRecyclerView() != null && frg.getRecyclerView().getVisibility() == View.VISIBLE) {
                    frg.getRecyclerView().setVisibility(View.GONE);
                    frg.getSearchLayout().setVisibility(View.VISIBLE);
                } else {
                    super.onBackPressed();
                }

            } else {

               /*   if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                    for (int i=0 ;i <getSupportFragmentManager().getBackStackEntryCount();i++){
                        getSupportFragmentManager().popBackStack();
                    }
                }*/
                super.onBackPressed();
            }
           /* if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                for (int i=0 ;i <getSupportFragmentManager().getBackStackEntryCount();i++){
                    getSupportFragmentManager().popBackStack();
                }
            }*/
        }
    }

    public void hideFilter(boolean isHiden) {
        if (isHiden)
            rightImg.setVisibility(View.GONE);
        else
            rightImg.setVisibility(View.VISIBLE);
    }

    public void replaceFragment(Fragment fragment, boolean clearBackStack, boolean isFirstFrag) {

        if (clearBackStack) {

            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                getSupportFragmentManager().popBackStackImmediate();
            }
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {

                getSupportFragmentManager().popBackStackImmediate();
            }

        }
        if (isFirstFrag)
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        else
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();


    }

    private void addIconToTaps() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                //addIconToTaps();
                //tabLayout.getTabAt(position).setIcon(tapImageSelected[position]);
                // checkConnection();
              /*  if (suraViewPager.getAdapter()!=null)
                {
                    suraViewPager.getAdapter().notifyDataSetChanged();

                }*/

                if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                    getSupportFragmentManager().popBackStackImmediate();
                }


                switch (position) {
                    case 0:
                        checkConnection();
                        replaceFragment(HomeFragment.newInstance(), false, true);

                        break;
                    case 1:
                        checkConnection();

                        replaceFragment(DiscoverFragment.newInstance(), true, true);
                        break;
                    case 2:
                        checkConnection();

                        replaceFragment(LikeFragment.newInstance(), true, true);
                        break;
                    case 3:
                        checkConnection();
                        replaceFragment(MoreFragment.newInstance(), true, true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        for (int i = 0; i < tapImage.length; i++) {
            if (i == 0)
                tabLayout.addTab(tabLayout.newTab().setIcon(tapImage[i]), true);
            else
                tabLayout.addTab(tabLayout.newTab().setIcon(tapImage[i]));


        }
     /*   if (tabLayout.getTabAt(0).isSelected())
            replaceFragment(HomeFragment.newInstance(), false, true);*/


    }

    @Override
    public void onClick(View v) {

    /*    switch (v.getId()) {
            case R.id.playerOut:
                //todo open player fragment
                //sheet fragment
                //hide the playerOut view

                PlayerViewPagerFragment sheetFragment = new PlayerViewPagerFragment();
                sheetFragment.setData(HomeFragment.dataList,0);
                sheetFragment.show(getSupportFragmentManager(), sheetFragment.getTag());
                //playerOutLayout.setVisibility(View.GONE);
                isPlayerShown=false;
                break;
        }*/
    }

    public void setTitleTxt(String title) {
        titleTxt.setText(title);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    //This part for play mp3

    public void showPlayerAndPlaySound(final ArrayList<Entries> dataList, int pos, int flag) {
        //mList.clear();
        fromList = true;
        final Entries model = dataList.get(pos);
        suraURL = model.getSoundPath();
        mList = dataList;
        position = pos;
        Log.i(mediaTag, "click  >> " + position);
        if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
            // slidingLayout.setPanelHeight(60);
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }

        /*  When Flag is 0 , this mean that pending Intent form Notification Service it had been started this activity
        *
        *   When Flag is 1, then this mean that Media Player will start from This Activity Not Service OK
        */
        if (flag == 0) {

        } else {
            saveToHistory(dataList.get(pos));
            currentLenght = 0;
        }

        suraRV.scrollToPosition(pos);
        playSound(suraURL, dataList.get(pos));
        playPauseBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_outpause));
        isSoundPlay = true;
        updateSmallPlayer(dataList.get(pos));
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create media player
                if (!isSoundPlay) {
                    playSound(suraURL, model);
                    playPauseBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_outpause));
                    isSoundPlay = true;
                } else {
                    isSoundPlay = false;
                    playPauseBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_outplay));
                    if (mp != null && mp.isPlaying()) {
                        currentLenght = mp.getCurrentPosition();
                        mp.seekTo(currentLenght);
                        mp.pause();
                    }

                }

            }
        });
    }

    public MediaPlayer getMediaPlayer() {
        return mp;
    }

    public void playSound(final String soundURL, Entries model) {
        Log.d(mediaTag, "from pager" + suraURL);

        if (mp != null && !mp.isPlaying()) {

            // try {
            if (currentLenght != 0) {
                try {
                    if (checkPendingIntent(0)) {
                        mp.reset();
                        mp.setDataSource(soundURL);
                        mp.prepareAsync();

                    } else {
                        mp.seekTo(currentLenght);
                        mp.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (currentLenght == 0) {
                ApplicationController.getInstance().runInBackground(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mp.reset();
                            mp.setDataSource(soundURL);
                            mp.prepareAsync();

                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                });


            }

        } else {

            ApplicationController.getInstance().runInBackground(new Runnable() {
                @Override
                public void run() {
                    try {
                        mp.reset();
                        mp.setDataSource(soundURL);

                        mp.prepareAsync();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (checkPendingIntent(0)) {
                    mp.seekTo(currentLenght);
                }
                mediaPlayer.start();

                duration = mp.getDuration();
                currentDuration = mp.getCurrentPosition();

                if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    if (mp.isPlaying()) {
                        playPauseBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.player_outpause));
                    }
                } else if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    View v = linearLayoutManager.findViewByPosition(position);
                    if (v != null) {
                        ImageView btnPlay = (ImageView) v.findViewById(R.id.img_play);
                        btnPlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.playerpause));
                        SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekbar);
                        seekBar.setProgress(currentLenght);
                        updateSeekBar(seekBar);
                    }
                }

                Log.i(mediaTag, "duration=" + duration);
                Log.i(mediaTag, "current=" + currentDuration);

            }
        });

        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            /*    DialogUtilit dialogUtilit=new DialogUtilit();
                final Dialog dialog = dialogUtilit.showCustomDialog(activity, getString(R.string.error_play_sound));
                // dialogUtilit.showCustomDialog(activity,getString(R.string.error_play_sound));
                //  e.printStackTrace();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 3000);*/
                return true;
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                currentLenght = 0;
                fromList = false;
                // position=position+1;
                if (isRepeat() == true) {

                } else {
                    mp.setLooping(false);
                    Log.v(Tag, "onComplete From Parent Activity");
                    if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        currentLenght = 0;
                        suraRV.getLayoutManager().scrollToPosition(position + 1);
                        //jumpToNext(null);
                    }

                }
                if (isRepeat()) {
                    playSound(mList.get(position).getSoundPath(), mList.get(position));
                }
                jumpToNext(null);
                Toast.makeText(getApplicationContext(), "Sura " + mList.get(position).getTitle() + " is finished", Toast.LENGTH_SHORT).show();

                mp.seekTo(0);

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ACCESS_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //000000003 doCapture();
                    //reload my activity with permission granted or use the features what required the permission
                } else {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void checkRunTimePermission() {

        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_ACCESS_STORAGE);
        } else {
            // doCapture();
        }
    }

    private void saveToHistory(Entries entries) {

        if (HistoryTable.getInstance().getHistoryByID(entries.getId()) == null) {
            entries.setIsHistory(1);
            entries.setIsDownloaded(0);
            HistoryTable.getInstance().insertHistoryModel(entries);
        } else {
            //  Toast.makeText(getApplicationContext(),"llll",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        //   Toast.makeText(getApplicationContext(),"disconnect",Toast.LENGTH_SHORT).show();
        this.isConnected = isConnected;

        if (isConnected) {
            noConnection.setVisibility(View.GONE);
            if (tabLayout.getSelectedTabPosition() == 3 || tabLayout.getSelectedTabPosition() == 2) {
                fragmentContainer.setVisibility(View.VISIBLE);
                connectionView.setVisibility(View.GONE);
//              //  img_connection.setVisibility(View.GONE);*/
              /* MoreFragment frag=new MoreFragment();
                frag.changeView(isConnected);*/

            } else {
                connectionView.setVisibility(View.VISIBLE);
                connectionText.setText(getString(R.string.InternetConnectionDetected));
               /* txtGo_online.setVisibility(View.VISIBLE);
                fragmentContainer.setVisibility(View.GONE);*/
                txtGo_online.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragmentContainer.setVisibility(View.VISIBLE);
                        connectionView.setVisibility(View.GONE);
                        if (tabLayout.getSelectedTabPosition() == 0) {
                            //perform home api
                            replaceFragment(HomeFragment.newInstance(), true, true);
                            rightImg.setVisibility(View.VISIBLE);
                        }
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    img_connection.setImageDrawable(getDrawable(R.drawable.emohappy));
                }
                //  fragmentContainer.setVisibility(View.VISIBLE);
            }

        } else {
            if (tabLayout.getSelectedTabPosition() == 3 || tabLayout.getSelectedTabPosition() == 2) {
                fragmentContainer.setVisibility(View.VISIBLE);
                connectionView.setVisibility(View.GONE);
                noConnection.setVisibility(View.VISIBLE);

               /* MoreFragment frag=new MoreFragment();
                frag.changeView(isConnected);*/

            } else {
                noConnection.setVisibility(View.GONE);
                connectionView.setVisibility(View.VISIBLE);
                txtGo_online.setVisibility(View.GONE);
                connectionText.setText(getString(R.string.str_noConnectionDetict));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    img_connection.setImageDrawable(getDrawable(R.drawable.emosad));
                }
                fragmentContainer.setVisibility(View.GONE);
//            if (rightImg.getVisibility() == View.VISIBLE)
                rightImg.setVisibility(View.GONE);

            }

        }
    }

    public void checkConnection() {
        isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            noConnection.setVisibility(View.GONE);
            connectionView.setVisibility(View.GONE);
            fragmentContainer.setVisibility(View.VISIBLE);
        } else {
            if (tabLayout.getSelectedTabPosition() == 0 || tabLayout.getSelectedTabPosition() == 1
                    ) {
                connectionView.setVisibility(View.VISIBLE);
                fragmentContainer.setVisibility(View.GONE);
               /* if (rightImg.getVisibility()==View.VISIBLE)
                    */
                rightImg.setVisibility(View.GONE);
            } else if (tabLayout.getSelectedTabPosition() == 3 || tabLayout.getSelectedTabPosition() == 2) {
                noConnection.setVisibility(View.VISIBLE);
                connectionView.setVisibility(View.GONE);
                /*noConnection.setVisibility(View.VISIBLE);
                img_connection.setVisibility(View.GONE);*/
               /* MoreFragment frag=new MoreFragment();
                frag.changeView(isConnected);*/
                fragmentContainer.setVisibility(View.VISIBLE);
            }

        }
    }

    private void showConnectionView(boolean isConnected) {
        //  Toast.makeText(this,"connection "+isConnected,Toast.LENGTH_SHORT).show();
        onNetworkConnectionChanged(isConnected);

    }

    @Override
    public void showDoneDialog() {
        // hideLoadingDialog();
    }

    @Override
    public void showFailDialog() {
        //  hideLoadingDialog();
        DialogUtilit dialogUtilit = new DialogUtilit();
        dialogUtilit.showCustomDialog(this, getString(R.string.fail));
    }

    public void jumpToNext(View view) {

        fromList = false;
        position = position + 1;
        Log.i(mediaTag, "from next>>>>" + position);
        if (position >= mList.size()) {

            position = mList.size() - 1;
        }
        //suraViewPager.setCurrentItem(position, true);
        else {
            linearLayoutManager.scrollToPosition(position);
            currentLenght = 0;
            playSound(mList.get(position).getSoundPath(), mList.get(position));
        }
    }

    public void jumptoPrev(View view) {
        fromList = false;
        position = position - 1;
        // suraViewPager.setCurrentItem(position, true);
        if (position < 0) {
            position = 0;
        } else {
            linearLayoutManager.scrollToPosition(position);
            currentLenght = 0;
            playSound(mList.get(position).getSoundPath(), mList.get(position));
        }

    }

    public void autoReplay(View view) {

        if (isRepeat) {
            isRepeat = false;
            mp.setLooping(false);

            Toast.makeText(getApplicationContext(), "is repeat false", Toast.LENGTH_SHORT).show();
        } else {
            isRepeat = true;
            mp.setLooping(true);
            Toast.makeText(getApplicationContext(), "is repeat true", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void showInitLoading() {
        //    preLoadingGif.setVisibility(View.VISIBLE);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.preloading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog.dismiss();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                showDoneDialog();
                final AlertDialog.Builder dialogAlert = new AlertDialog.Builder(activity);//, R.style.AppThemeNoActionBar
                dialogAlert.setTitle(getString(R.string.downLoadTitle));
                dialogAlert.setMessage(getString(R.string.downLoadMsg));
                dialogAlert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Todo go to the folder of TVQuran
                        //open downloaded list from app likeFragment

                        /*File file = new File(Environment.getExternalStorageDirectory().getPath() + "/TV-Quran/");
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "*file/mp3*");
                        startActivity(Intent.createChooser(intent, getString(R.string.action_downloaded)));*/
                        if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        }
                        tabLayout.getTabAt(2).select();
                        // HistoryTable.getInstance().insertHistoryModel();
                        Intent intent = new Intent(activity, DownloadService.class);
                        intent.putExtra("url", mList.get(position).getSoundPath());
                        intent.putExtra("fileName", mList.get(position).getTitle());
                        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
                        intent.putExtra("data", mList.get(position));
                        startService(intent);
                        // replaceFragment(LikeFragment.newInstance(),false,false);


                    }
                });
                dialogAlert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(activity, DownloadService.class);
                        intent.putExtra("url", mList.get(position).getSoundPath());
                        intent.putExtra("fileName", mList.get(position).getTitle());
                        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
                        intent.putExtra("data", mList.get(position));
                        startService(intent);
                    }
                });
                AlertDialog alert11 = dialogAlert.create();
                alert11.show();

            }
        }, 1000);

    }

    public void downLoadSura(View view) {

        if (NetworkChecker.isNetworkAvailable(this)) {
            if (tabLayout.getSelectedTabPosition() == 2) {
                tabLayout.getTabAt(1).select();
            }
            final int itemId = mList.get(position).getId();
            final Entries historyModel = HistoryTable.getInstance().getDownloadByID(itemId);
            if (historyModel == null) {
                //showLoadingDialog();
                mList.get(position).setIsDownloaded(1);
                //  mList.get(position).setIsHistory(0);
                HistoryTable.getInstance().insertDownLoadyModel(mList.get(position));
                Log.i(Tag, HistoryTable.getInstance().GetDownloadedList().size() + "");
                showInitLoading();
                //mList.get(position).setIsDownloaded(1);

            } else {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(
                        this);//, R.style.AppThemeNoActionBar
                dialog.setTitle(getString(R.string.title_removeDownload));
                dialog.setMessage(getString(R.string.remove_downLoadMsg));
                dialog.setPositiveButton(getString(R.string.remove), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //open downloaded list from app likeFragment
                        if (historyModel.getDownloadedPath() != null) {
                            File file = new File(historyModel.getDownloadedPath());
                            if (file != null) {
                                boolean deleted = file.delete();
                                HistoryTable.getInstance().deleteFromHistory(itemId);
                            }
                        }


                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert11 = dialog.create();
                alert11.show();

            }
        } else {
            final DialogUtilit du = new DialogUtilit();
            final Dialog dialog = du.showCustomDialog(this, getString(R.string.SorryButYouAreNotConnected));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 3000);
        }


    }

    public void shareSura(View view) {

        //share sura
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, suraURL);
        Log.i(mediaTag, "" + suraURL);
        startActivity(Intent.createChooser(shareIntent, "Share sura "));
    }

    public void playLargSura(final View view) {
        View v = linearLayoutManager.findViewByPosition(position);
        if (mp != null && !mp.isPlaying()) {
            //     ((ImageView) view).setBackgroundDrawable(getResources().getDrawable(R.drawable.playerpause));
            if (position < mList.size())

            {
                //  View v = linearLayoutManager.findViewByPosition(position);
                if (v != null) {
                    //SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekbar);
                    //seekBar.setProgress(currentLenght);
                    ImageView btnPlay = (ImageView) v.findViewById(R.id.img_play);
                    btnPlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.playerpause));
                    playSound(mList.get(position).getSoundPath(), mList.get(position));
                    isSoundPlay = true;

                    //updateSeekBar(seekBar);
                }
            }

        } else {
            if (mp != null && mp.isPlaying()) {
                currentLenght = mp.getCurrentPosition();
                if (v != null) {
                    //SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekbar);
                    //seekBar.setProgress(currentLenght);
                    ImageView btnPlay = (ImageView) v.findViewById(R.id.img_play);

                    isSoundPlay = false;
                    btnPlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.playerplay));
                }
                mp.pause();
            }
        }

    }

    public void updateSeekBar(SeekBar seekBar) {

        seekBar.setMax(getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                    seekBar.setProgress(progress);
                    currentLenght = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ApplicationController.getInstance().runInBackground(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(mUpdateTimeTask, 100);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent stopIntent = new Intent(this, NotificationService.class);
        stopIntent.setAction(Params.ACTIONS.ACTION_STOP);
        stopIntent.putExtra(Params.INTENT_PARAMS.INTENT_KEY_STOP, true);
        startService(stopIntent);
        if (mp != null) {
            mp.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //   addIconToTaps();
        ApplicationController.getInstance().setConnectivityListener(this);
        ApplicationController.getInstance().setDownloadListner(this);
        //checkConnection();
      /*  mTracker.setScreenName("Parent Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        startNotificationService();


    }

    @Override
    protected void onStop() {
        super.onStop();

        startNotificationService();

        mp.pause();

        //ApplicationController.getInstance().shutDownExecuterService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startNotificationService();
        // ButterKnife.unbind(this);
        unregisterReceiver(receiver);
        unregisterReceiver(FailReciver);
        unregisterReceiver(downloadProgressReciver);
   /*     LocalBroadcastManager.getInstance(this).unregisterReceiver(
                downloadProgressReciver);*/


    }

    void startNotificationService() {
        try {
            if (mp != null && mp.isPlaying()) {
                int progress = mp.getCurrentPosition();
                Intent intent = new Intent(getApplicationContext(), NotificationService.class);
                intent.setAction(Params.ACTIONS.ACTION_PLAY);
                intent.putExtra(Params.INTENT_PARAMS.INTENT_KEY_STOP, false);
                intent.putExtra(Params.INTENT_PARAMS.INTENT_KEY_PROGRESS, progress);
                Log.i(BaseActivity.class.getSimpleName(), "Current Length in start Notification is " + currentLenght + "\nThe Progress is " + progress);

                intent.putExtra(Params.INTENT_PARAMS.INTENT_KEY_POSITION, position);
                intent.putExtra(Params.INTENT_PARAMS.INTENT_KEY_MODEL, mList.get(position));
                if (position >= 0 && position < mList.size())
                    startService(intent);

                //ApplicationController.getInstance().shutDownExecuterService();

                mp.pause();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    enum FLAGS {
        FLAG_NOTIFICATION_PLAYER,
        FLAG_APPLICATION_PLAYER
    }


}
