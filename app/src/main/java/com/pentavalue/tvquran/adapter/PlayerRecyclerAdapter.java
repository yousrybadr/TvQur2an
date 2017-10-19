package com.pentavalue.tvquran.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.application.ApplicationController;
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

public class PlayerRecyclerAdapter extends RecyclerView.Adapter<PlayerRecyclerAdapter.DataViewHolder> {

    ArrayList<Entries> mList;
    Context mContext;
    MediaPlayer mp;
    ProgressDialog bar;
    Handler mHandler;
    DataViewHolder currentViewHolder;

    public PlayerRecyclerAdapter(ArrayList<Entries> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        mHandler=new Handler();
       // mp = new MediaPlayer();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_player, parent, false);

        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataViewHolder holder, final int position) {

        currentViewHolder=holder;
        if (HistoryTable.getInstance().getDownloadByID(mList.get(position).getId()) == null) {
          holder.  btnDownload.setImageDrawable(mContext.getResources().getDrawable(R.drawable.playerdownload));
        } else {
           holder. btnDownload.setImageDrawable(mContext.getResources().getDrawable(R.drawable.playerdownloaded));

        }
       holder.shikhName.setText(mList.get(position).getReciter_name());
       holder.txt_suraName.setText(mList.get(position).getTitle());
        holder.txtListn.setText(String.valueOf(mList.get(position).getViews_count()));
     holder.   txtLikes.setText(String.valueOf(mList.get(position).getUpVotes()));
        if (position != mList.size() - 1) {
          holder.  next_suraName.setText(mList.get(position + 1).getTitle());
           holder. txtNextShikhName.setText(mList.get(position + 1).getReciter_name());
        }
        long totalDuration = ((ParentActivity)mContext).getDuration();
        long currentDuration = ((ParentActivity)mContext).getCurrentDuration();
        // Displaying Total Duration time
      holder.  totalTime.setText(""+ TimeUtilities.milliSecondsToTimer(totalDuration));
        // Displaying time completed playing
      holder.  txtDuration.setText(""+TimeUtilities.milliSecondsToTimer(currentDuration));
        Picasso.with(mContext).load(mList.get(position).getReciter_photo()).into(holder.imgProfile);
      holder.  reportBtn.setOnClickListener(new View.OnClickListener() {
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
                    ((ParentActivity)mContext).playSound(mList.get(position).getSoundPath());
                }
            }
        });
        if (mp != null && mp.isPlaying())
         holder.   btnPlay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.playerpause));
        else
         holder.   btnPlay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.playerplay));

        holder.playerSeekBar.setClickable(false);
        holder.playerSeekBar.setMax((int)totalDuration);
       holder. playerSeekBar.setProgress((int)currentDuration);
        if (mp!=null && mp.isPlaying()){
          holder.  playerSeekBar.setProgress((int)currentDuration);
            updateSeekBar();

        }
        holder.playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                  mHandler.removeCallbacks(mUpdateTimeTask);

                mp.seekTo(progress);
                updateSeekBar();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = ((ParentActivity)mContext).getDuration();
                int currentPosition = TimeUtilities.progressToTimer(seekBar.getProgress(), totalDuration);
                holder.   txtDuration.setText(""+TimeUtilities.milliSecondsToTimer(currentPosition));

                // forward or backward to certain seconds
                mp.seekTo(currentPosition);

                // update timer progress again
                updateSeekBar();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class DataViewHolder extends RecyclerView.ViewHolder{

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
        public DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void updateSeekBar() {

        ApplicationController.getInstance().runInBackground(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(mUpdateTimeTask,100);

            }
        });

    }
    private Runnable mUpdateTimeTask=new Runnable() {
        @Override
        public void run() {
            long totalDuration = ((ParentActivity)mContext).getDuration();
            long currentDuration = ((ParentActivity)mContext).getCurrentDuration();
          currentViewHolder.  totalTime.setText(""+ TimeUtilities.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            currentViewHolder.txtDuration.setText(""+TimeUtilities.milliSecondsToTimer(mp.getCurrentPosition()));
            //   Log.i("LOLO",""+TimeUtilities.milliSecondsToTimer(currentDuration));
            // Updating progress bar
            int progress = (int)(TimeUtilities.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            currentViewHolder.playerSeekBar.setProgress((int)mp.getCurrentPosition());
            mHandler.postDelayed(this, 100);
        }
    };



}