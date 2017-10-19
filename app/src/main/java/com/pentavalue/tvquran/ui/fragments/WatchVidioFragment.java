package com.pentavalue.tvquran.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.VideoAdapter;
import com.pentavalue.tvquran.network.listeneres.OnPressVidio;
import com.pentavalue.tvquran.model.Video;
import com.pentavalue.tvquran.model.VidioAndLiveBroadcast;
import com.pentavalue.tvquran.utils.ValidatorUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WatchVidioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WatchVidioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchVidioFragment extends Fragment implements OnPressVidio {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Boolean type;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.VideoName)
    TextView videoName;
    @Bind(R.id.VideoDetails)
    TextView videoDetails;
    @Bind(R.id.numOfLikes)
    TextView numOfLikes;
    @Bind(R.id.numOfListen)
    TextView numOfListen;
    @Bind(R.id.calender)
    TextView calender;

    VidioAndLiveBroadcast vidioAndLiveBroadcast;
    OnPressVidio onPressVidio;
    Video video;
    public WatchVidioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WatchVidioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WatchVidioFragment newInstance(String param1, String param2) {
        WatchVidioFragment fragment = new WatchVidioFragment();
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
        View view = inflater.inflate(R.layout.fragment_watch_vidio, container, false);
       onPressVidio =this;
        Bundle bundle = getArguments();
        type = bundle.getBoolean("type");
        vidioAndLiveBroadcast = (VidioAndLiveBroadcast) bundle.getSerializable("Videos");
        video=(Video) bundle.getSerializable("WatchingVideo");
        init(view);
        if (type)
            setupRecyclerView(recyclerView, vidioAndLiveBroadcast.getVideos());
        else
            setupRecyclerView(recyclerView, vidioAndLiveBroadcast.getBroadcast());
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        videoName.setText(video.getTitle());
        videoDetails.setText(video.getCategory_name());
        numOfListen.setText(video.getViews_count()+"");
        numOfLikes.setText(video.getUp_votes()+"");
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Video> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        setListItems(recyclerView, list);

    }

    private void setListItems(RecyclerView recyclerView, List<Video> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            VideoAdapter adapter = new VideoAdapter(getActivity(), list, onPressVidio);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void OnPressClick(int position, Video video) {
        videoName.setText(video.getTitle());
        videoDetails.setText(video.getCategory_name());
        numOfListen.setText(video.getViews_count()+"");
        numOfLikes.setText(video.getUp_votes()+"");
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
