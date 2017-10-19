package com.pentavalue.tvquran.ui.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.model.Entries;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerViewPagerFragment extends BottomSheetDialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   /* @Bind(R.id.pager)
    ViewPager suraViewPager;*/
    ArrayList<Entries> suraList;
    int pos;
    public PlayerViewPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerViewPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerViewPagerFragment newInstance(String param1, String param2) {
        PlayerViewPagerFragment fragment = new PlayerViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        /*View contentView = View.inflate(getContext(), R.layout.fragment_player_view_pager, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent())
                .getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(R.color.colorLightGray));*/
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
        View v=inflater.inflate(R.layout.fragment_player_view_pager, container, false);
        ButterKnife.bind(this,v);
       /* suraViewPager.setAdapter(new PlayerPagerAdapter(suraList,getActivity()));
        suraViewPager.setCurrentItem(pos);*/

        return v;

    }

    public void setData(ArrayList<Entries> dataList,int position){

        suraList=dataList;
        pos=position;
    }
}
