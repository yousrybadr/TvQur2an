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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.VideoAdapter;
import com.pentavalue.tvquran.network.listeneres.OnLiveBroadcastListener;
import com.pentavalue.tvquran.network.listeneres.OnPressVidio;
import com.pentavalue.tvquran.network.managers.RecitersMangers;
import com.pentavalue.tvquran.model.Video;
import com.pentavalue.tvquran.model.VidioAndLiveBroadcast;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.utils.ValidatorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LiveBroadcastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LiveBroadcastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiveBroadcastFragment extends BaseFragment implements OnLiveBroadcastListener, View.OnClickListener, OnPressVidio {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.LiveBroadcastLayout)
    LinearLayout LiveBroadcastLayout;
    @Bind(R.id.VideoLayout)
    LinearLayout VideoLayout;

    @Bind(R.id.numOfSorahSection)
    TextView numOfLiveVidio;

    @Bind(R.id.numOfVideosSection)
    TextView numOfVideosSection;

    @Bind(R.id.VideoName)
    TextView VideoName;

    @Bind(R.id.numOfLikes)
    TextView numOfLikes;
    @Bind(R.id.numOfListen)
    TextView numOfListen;
    @Bind(R.id.calender)
    TextView calender;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    Bundle bundle = new Bundle();
    Bundle bundleForWatch = new Bundle();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    VidioAndLiveBroadcast vidioAndLiveBroadcast;
    private OnFragmentInteractionListener mListener;
    OnPressVidio onPressVidio;
    boolean type;

    public LiveBroadcastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LiveBroadcastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LiveBroadcastFragment newInstance(String param1, String param2) {
        LiveBroadcastFragment fragment = new LiveBroadcastFragment();
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
        View view = inflater.inflate(R.layout.fragment_live_broadcast, container, false);
        init(view);
        onPressVidio = this;
        ((ParentActivity)getActivity()).setTitleTxt(getResources().getString(R.string.Videos)+"/"+getResources().getString(R.string.LiveBroadCast));
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        VideoLayout.setOnClickListener(this);
        LiveBroadcastLayout.setOnClickListener(this);

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
    public void onResume() {
        super.onResume();
        RecitersMangers.getInstance(getActivity()).addListener(this);
        showLoadingDialog();
        RecitersMangers.getInstance(getActivity()).performBrowseLiveBroadcast();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onException(Exception exception) {
        hideLoadingDialog();
    }

    @Override
    public void onSuccess(VidioAndLiveBroadcast obj) {
        hideLoadingDialog();
        numOfVideosSection.setText(obj.getVideos().size() + "");
        numOfLiveVidio.setText(obj.getBroadcast().size() + "");
        VideoName.setText(obj.getBroadcast().get(0).getTitle());
        if (obj.getBroadcast().size() > 0) {
            numOfListen.setText(obj.getBroadcast().get(0).getViews_count() + "");
            numOfLikes.setText("0");
            calender.setText(obj.getBroadcast().get(0).getCreated_at());
        }
        List<Video> vidios = new ArrayList<>();
        if (obj.getVideos().size() >= 3) {
            for (int i = 0; i < 3 && i < obj.getVideos().size(); i++) {
                vidios.add(obj.getVideos().get(i));
            }
            setupRecyclerView(recyclerView, vidios);
        } else {
            setupRecyclerView(recyclerView, obj.getVideos());
        }
        vidioAndLiveBroadcast = obj;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LiveBroadcastLayout:
                bundle.putBoolean("type", false);
                bundle.putSerializable("Videos", vidioAndLiveBroadcast);
                OpenVideoDetails();

                break;
            case R.id.VideoLayout:
                bundle.putBoolean("type", true);
                bundle.putSerializable("Videos", vidioAndLiveBroadcast);
                OpenVideoDetails();

                break;
        }
    }

    void OpenVideoDetails() {
        Fragment fragment = new VideoAndLiveDetailsFragment();

        fragment.setArguments(bundle);
        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //getFragmentManager().beginTransaction().replace(R.id.contanier_frame_layout,new readerFragment()).addToBackStack(null).commit();
        fragmentTransaction.replace(R.id.contanier_fram_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        ((ParentActivity)getActivity()).replaceFragment(fragment,false,false);
    }

    @Override
    public void OnPressClick(int position, Video video) {
        Fragment fragment = new WatchVidioFragment();
        Bundle bundle = new Bundle();
        if (video.getReciter_name() != null) {
            bundle.putBoolean("type", true);
        } else {
            bundle.putBoolean("type", false);

        }
        bundle.putSerializable("Videos", vidioAndLiveBroadcast);
        bundle.putSerializable("WatchingVideo", video);

        fragment.setArguments(bundle);
       /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //getFragmentManager().beginTransaction().replace(R.id.contanier_frame_layout,new readerFragment()).addToBackStack(null).commit();
        fragmentTransaction.replace(R.id.contanier_fram_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        ((ParentActivity)getActivity()).replaceFragment(fragment,false,false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
