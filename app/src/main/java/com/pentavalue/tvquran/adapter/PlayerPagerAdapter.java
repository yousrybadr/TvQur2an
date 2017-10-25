package com.pentavalue.tvquran.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.datasorage.database.HistoryTable;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.ui.activities.ReportActivity;
import com.pentavalue.tvquran.utils.TimeUtilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Passant on 6/6/2017.
 */

public class PlayerPagerAdapter extends PagerAdapter {

    ArrayList<Entries> mList;
    Context mContext;
    @Bind(R.id.txt_duration)
    TextView txtDuration;
    @Bind(R.id.txt_time)
    TextView totalTime;
    @Bind(R.id.txt_suraName)
    TextView txt_suraName;
    @Bind(R.id.txt_nextSuraName)
    TextView next_suraName;
    @Bind(R.id.txt_hint)
    TextView hint;
    @Bind(R.id.txt_nextShikhName)
    TextView txtNextShikhName;
    @Bind(R.id.txt_shikhName)
    TextView shikhName;
    @Bind(R.id.btn_downLoad)
    ImageButton btnDownload;
    @Bind(R.id.txt_numOfLikes)
    TextView txtLikes;
    @Bind(R.id.txt_numOfListen)
    TextView txtListn;
    @Bind(R.id.profile_image)
    CircleImageView imgProfile;
    @Bind(R.id.img_play)
    ImageView btnPlay;
    @Bind(R.id.seekbar)
    SeekBar playerSeekBar;
    @Bind(R.id.btn_report)
    ImageButton reportBtn;
    MediaPlayer mp;
    ProgressDialog bar;
    Handler mHandler;
    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = ((ParentActivity) mContext).getDuration();
            long currentDuration = ((ParentActivity) mContext).getCurrentDuration();
            // Displaying Total Duration time
            totalTime.setText("" + TimeUtilities.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            txtDuration.setText("" + TimeUtilities.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (TimeUtilities.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            //playerSeekBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };



    public PlayerPagerAdapter(ArrayList<Entries> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        mHandler=new Handler();
       // mp = new MediaPlayer();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_player,
                container, false);

        ButterKnife.bind(this, view);

        if (HistoryTable.getInstance().getDownloadByID(mList.get(position).getId()) == null) {
            btnDownload.setImageDrawable(mContext.getResources().getDrawable(R.drawable.playerdownload));
        } else {
            btnDownload.setImageDrawable(mContext.getResources().getDrawable(R.drawable.playerdownloaded));

        }
        shikhName.setText(mList.get(position).getReciter_name());
        txt_suraName.setText(mList.get(position).getTitle());
        txtListn.setText(String.valueOf(mList.get(position).getViews_count()));
        txtLikes.setText(String.valueOf(mList.get(position).getUpVotes()));
        if (position != mList.size() - 1) {
            next_suraName.setText(mList.get(position + 1).getTitle());
            txtNextShikhName.setText(mList.get(position + 1).getReciter_name());
        }
        long totalDuration = ((ParentActivity)mContext).getDuration();
        long currentDuration = ((ParentActivity)mContext).getCurrentDuration();
        // Displaying Total Duration time
        totalTime.setText(""+ TimeUtilities.milliSecondsToTimer(totalDuration));
        // Displaying time completed playing
        txtDuration.setText(""+TimeUtilities.milliSecondsToTimer(currentDuration));
        Picasso.with(mContext).load(mList.get(position).getReciter_photo()).into(imgProfile);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ReportActivity.class);
                intent.putExtra("entity", mList.get(position));
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        mp = ((ParentActivity) mContext).getMediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (((ParentActivity)mContext).isRepeat()){
                    ((ParentActivity) mContext).playSound(mList.get(position).getSoundPath(), mList.get(position));
                }
            }
        });
        if (mp != null && mp.isPlaying())
            btnPlay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.playerpause));
        else
            btnPlay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.playerplay));
/*
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mp!=null && !mp.isPlaying()) {
                    ApplicationController.getInstance().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnPlay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.playerpause));
                        }
                    });
                    ((ParentActivity)mContext). playSound(mList.get(position).getSoundPath());
                    //                    isSoundPlay = true;
                    playerSeekBar.setProgress(0);
                    playerSeekBar.setMax(100);
                    updateSeekBar();
                } else {
                  //  isSoundPlay = false;
                    btnPlay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.playerplay));
                    if (mp != null && mp.isPlaying())
                        mp.stop();
                }
            }


        });*/
/*
        playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //mHandler.removeCallbacks(mUpdateTimeTask);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = ((ParentActivity)mContext).getDuration();
                int currentPosition = TimeUtilities.progressToTimer(seekBar.getProgress(), totalDuration);

                // forward or backward to certain seconds
                mp.seekTo(currentPosition);

                // update timer progress again
                //updateSeekBar();
            }
        });
*/
        ((ViewPager) container).addView(view, 0);
        return view;
    }

    private void updateSeekBar() {

        mHandler.postDelayed(mUpdateTimeTask,100);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //  super.destroyItem(container, position, object);
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
