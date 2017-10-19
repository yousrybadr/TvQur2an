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

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.VideoAdapter;
import com.pentavalue.tvquran.network.listeneres.OnPressVidio;
import com.pentavalue.tvquran.model.Video;
import com.pentavalue.tvquran.model.VidioAndLiveBroadcast;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.utils.ValidatorUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoAndLiveDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoAndLiveDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoAndLiveDetailsFragment extends BaseFragment implements OnPressVidio {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    VidioAndLiveBroadcast vidioAndLiveBroadcast;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    OnPressVidio onPressVidio;
    boolean type;
    private OnFragmentInteractionListener mListener;

    public VideoAndLiveDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoAndLiveDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoAndLiveDetailsFragment newInstance(String param1, String param2) {
        VideoAndLiveDetailsFragment fragment = new VideoAndLiveDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_video_and_live_details, container, false);
        onPressVidio=this;
        Bundle bundle = getArguments();
        type=bundle.getBoolean("type");
        vidioAndLiveBroadcast = (VidioAndLiveBroadcast) bundle.getSerializable("Videos");
        init(view);
        if(type)
            setupRecyclerView(recyclerView,vidioAndLiveBroadcast.getVideos());
        else
            setupRecyclerView(recyclerView,vidioAndLiveBroadcast.getBroadcast());
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);

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
    private void setupRecyclerView(RecyclerView recyclerView, List<Video> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        setListItems(recyclerView, list);

    }

    private void setListItems(RecyclerView recyclerView, List<Video> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            VideoAdapter adapter = new VideoAdapter(getActivity(), list,onPressVidio);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnPressClick(int position, Video video) {
        Fragment fragment = new WatchVidioFragment();
        Bundle bundle=new Bundle();

        bundle.putBoolean("type",type);
        bundle.putSerializable("Videos",vidioAndLiveBroadcast);
        bundle.putSerializable("WatchingVideo",video);

        fragment.setArguments(bundle);
        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
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
