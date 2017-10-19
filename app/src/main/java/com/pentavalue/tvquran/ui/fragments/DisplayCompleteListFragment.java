package com.pentavalue.tvquran.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.SortedSorahAdapter;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.network.listeneres.OnAudioPlayListener;
import com.pentavalue.tvquran.ui.activities.ParentActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DisplayCompleteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayCompleteListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<Entries> mParam1;
    private String mParam2;


    RecyclerView displayRV;
    public DisplayCompleteListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *               * @return A new instance of fragment DisplayCompleteListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayCompleteListFragment newInstance(ArrayList<Entries> param1) {
        DisplayCompleteListFragment fragment = new DisplayCompleteListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (ArrayList<Entries>) getArguments().getSerializable(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_display_complete_list, container, false);
        displayRV=(RecyclerView) v.findViewById(R.id.displayRV);
        displayRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayRV.setAdapter(new SortedSorahAdapter(getActivity(), mParam1, new OnAudioPlayListener() {
            @Override
            public void OnPlayTrack(int Postion, Entries entries) {
               // ((ParentActivity)getActivity()).showPlayerAndPlaySound(mParam1,Postion);

            }

            @Override
            public void OnPlayTrack(int Postion, ArrayList<Entries> entries) {
                ((ParentActivity)getActivity()).showPlayerAndPlaySound(mParam1,Postion);
            }
        }));
    }
}
