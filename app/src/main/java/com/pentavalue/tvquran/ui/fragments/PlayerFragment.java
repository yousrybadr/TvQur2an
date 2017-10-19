package com.pentavalue.tvquran.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends BottomSheetDialogFragment  implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static  ProgressDialog mProgressDialog;
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
    Entries currentItem,nextItem;
    MediaPlayer mp;
    Handler mHandler;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

// instantiate it within the onCreate method
private Runnable mUpdateTimeTask = new Runnable() {
    @Override
    public void run() {
        if (getActivity() != null) {
            long totalDuration = ((ParentActivity) getActivity()).getDuration();
            long currentDuration = ((ParentActivity) getActivity()).getCurrentDuration();
            totalTime.setText("" + TimeUtilities.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            txtDuration.setText("" + TimeUtilities.milliSecondsToTimer(mp.getCurrentPosition()));
            //   Log.i("LOLO",""+TimeUtilities.milliSecondsToTimer(currentDuration));
            // Updating progress bar
            int progress = (int) (TimeUtilities.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            playerSeekBar.setProgress((int) mp.getCurrentPosition());
            mHandler.postDelayed(this, 100);
        }

        // Displaying Total Duration time

    }
};

    public PlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance(String param1, String param2) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_player, container, false);

        init(v);
        return v;
    }

    private void init(View rootView) {
        ButterKnife.bind(this, rootView);
        mHandler=new Handler();
        //outPlayer.setOnClickListener(this);
        if (HistoryTable.getInstance().getDownloadByID(currentItem.getId()) == null) {
            btnDownload.setImageDrawable( getResources().getDrawable(R.drawable.playerdownload));
        } else {
            btnDownload.setImageDrawable( getResources().getDrawable(R.drawable.playerdownloaded));
        }
        shikhName.setText(currentItem.getReciter_name());
        txt_suraName.setText(currentItem.getTitle());
        txtListn.setText(String.valueOf(currentItem.getViews_count()));
        txtLikes.setText(String.valueOf(currentItem.getUpVotes()));
        if (nextItem!=null) {
            next_suraName.setText(nextItem.getTitle());
            txtNextShikhName.setText(nextItem.getReciter_name());
         }
        long totalDuration = ((ParentActivity)getActivity()).getDuration();
        final long currentDuration = ((ParentActivity)getActivity()).getCurrentDuration();
        // Displaying Total Duration time
        totalTime.setText(""+ TimeUtilities.milliSecondsToTimer(totalDuration));
        // Displaying time completed playing
        txtDuration.setText(""+TimeUtilities.milliSecondsToTimer(currentDuration));
        Picasso.with(getActivity()).load(currentItem.getReciter_photo()).into(imgProfile);
        reportBtn.setOnClickListener(this);
        mp = ((ParentActivity) getActivity()).getMediaPlayer();
       /* mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (((ParentActivity)getActivity()).isRepeat()){
                    ((ParentActivity)getActivity()).playSound(currentItem.getSoundPath());
                }
            }
        });*/
        if (mp != null && mp.isPlaying())
            btnPlay.setBackgroundDrawable( getResources().getDrawable(R.drawable.playerpause));
        else
            btnPlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.playerplay));

        //seekbar implementation
        playerSeekBar.setClickable(false);
        playerSeekBar.setMax((int)totalDuration);
        playerSeekBar.setProgress((int)currentDuration);
        if (mp!=null && mp.isPlaying()){
            playerSeekBar.setProgress((int)currentDuration);
            updateSeekBar();

        }
        playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
              //  mHandler.removeCallbacks(mUpdateTimeTask);


                //mp.seekTo(progress);
                //updateSeekBar();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = ((ParentActivity)getActivity()).getDuration();
                int currentPosition = TimeUtilities.progressToTimer(seekBar.getProgress(), totalDuration);
                txtDuration.setText(""+TimeUtilities.milliSecondsToTimer(currentPosition));

                // forward or backward to certain seconds
                mp.seekTo(currentPosition);

                // update timer progress again
                updateSeekBar();
            }
        });

    }

    public void resetView(){

        btnPlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.playerplay));
        txtDuration.setText("0:00");
        totalTime.setText("0:00");
    }

    private void updateSeekBar() {

        mHandler.postDelayed(mUpdateTimeTask,100);

    }

    public void setData(Entries entiity,Entries nextEntity){
       currentItem=entiity;
        nextItem=nextEntity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp.isPlaying()){
            mp.stop();
            mp.reset();
        }

    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){

           case R.id.btn_report:
               sendReport();
               break;


       }
    }

    private void sendReport() {

        Intent intent = new Intent(getActivity(), ReportActivity.class);
        intent.putExtra("entity", currentItem);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
