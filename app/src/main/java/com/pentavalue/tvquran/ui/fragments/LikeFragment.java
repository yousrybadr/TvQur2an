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
import android.widget.RelativeLayout;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.LikeSctionRecyclerAdapter;
import com.pentavalue.tvquran.datasorage.database.HistoryTable;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.HeaderModel;
import com.pentavalue.tvquran.ui.activities.ParentActivity;

import java.util.ArrayList;
import java.util.List;

import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LikeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LikeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView rv;
    StickyHeaderViewAdapter adapter;
    RelativeLayout noHistory;
    //  StickyHeaderView stickyHeaderView;
    List<HeaderModel> sectionList = new ArrayList<>();

    public LikeFragment() {
        // Required empty public constructor
    }

    public static LikeFragment newInstance() {
        LikeFragment fragment = new LikeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LikeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LikeFragment newInstance(String param1, String param2) {
        LikeFragment fragment = new LikeFragment();
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

        View v = inflater.inflate(R.layout.fragment_like, container, false);
        rv = (RecyclerView) v.findViewById(R.id.recyclerView);
        noHistory = (RelativeLayout) v.findViewById(R.id.noHistory);
        // stickyHeaderView=(StickyHeaderView) v.findViewById(R.id.stickyHeaderView);
        ((ParentActivity) getActivity()).hideFilter(true);
        ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.ti1tle_favorite));
        return v;
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
        sectionList=new ArrayList<>();
        if (HistoryTable.getInstance().GetHistoryList().size() > 0 || HistoryTable.getInstance().GetDownloadedList().size() > 0) {

            noHistory.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            //  final ArrayList<DataBean> userList = new ArrayList<>();
              ArrayList<Entries> historyList = HistoryTable.getInstance().GetHistoryList();
              ArrayList<Entries> downloadList = HistoryTable.getInstance().GetDownloadedList();
            if (ParentActivity.isConnected){
                if (historyList != null && historyList.size() > 0) {

                    ArrayList<Entries> temp=new ArrayList<>() ;
                    for (int i=0 ;i <2; i++){
                        if (i< historyList.size())
                            temp.add(historyList.get(i));
                    }

                    sectionList.add(new HeaderModel(getString(R.string.history_title), historyList.size(), temp,historyList));
                }
                if (downloadList !=null && downloadList.size() >0){

                    ArrayList<Entries> temp=new ArrayList<>() ;
                    for (int i=0 ;i <2; i++){
                        if (i< downloadList.size())
                            temp.add(downloadList.get(i));
                    }
                    sectionList.add(new HeaderModel(getString(R.string.downloaded_title),downloadList.size(),temp,downloadList));
                }
            }
           else{
                if (downloadList !=null && downloadList.size() >0){

                    ArrayList<Entries> temp=new ArrayList<>() ;
                    for (int i=0 ;i <2; i++){
                        if (i< downloadList.size())
                            temp.add(downloadList.get(i));
                    }
                    sectionList.add(new HeaderModel(getString(R.string.downloaded_title),downloadList.size(),temp,downloadList));
                }
            }
            LikeSctionRecyclerAdapter adapter=new LikeSctionRecyclerAdapter(getActivity(),sectionList);
            rv.setAdapter(adapter);
            rv.invalidate();
            adapter.notifyDataSetChanged();
           /* if (mlist!=null && mlist.size() >0){
                userList.add(new HeaderModel("History",mlist.size(),mlist));
                if (mlist!=null && mlist.size() >0)
                {
                    for (int i=0 ;i <2; i++){
                        if (i< mlist.size())
                            userList.add(mlist.get(i));
                    }
                }
            }

            final ArrayList<Entries> downloadList=HistoryTable.getInstance().GetDownloadedList();
            if (downloadList!=null && downloadList.size() >0){
                userList.add(new HeaderModel("Downloaded",downloadList.size(),downloadList));
                for (int i=0 ;i <2; i++){
                    if (i< downloadList.size())
                        userList.add(downloadList.get(i));
                    else {

                    }
                }
            }

            adapter = new StickyHeaderViewAdapter(userList)
                    .RegisterItemType(new HistoryItemViewType(new OnAudioPlayListener() {
                        @Override
                        public void OnPlayTrack(int Postion, Entries entries) {

                            Log.i("LOLO","downlaoded path "+ entries.getDownloadedPath()) ;
                            Log.i("LOLO","is hestory"+ entries.getIsHistory()) ;

                            if (entries.getDownloadedPath()==null)
                                ((ParentActivity)getActivity()).showPlayerAndPlaySound(mlist,Postion ); //+1 cause header
                            else
                                ((ParentActivity)getActivity()).showPlayerAndPlaySound(downloadList,Postion);
                        }
                    }))
                    .RegisterItemType(new ItemHeaderViewBinder(getActivity()));
            rv.setAdapter(adapter);
            ((SimpleItemAnimator) rv.getItemAnimator()).setSupportsChangeAnimations(false);*/
        } else if(HistoryTable.getInstance().GetHistoryList().size() == 0 && HistoryTable.getInstance().GetDownloadedList().size() == 0){
            noHistory.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);

        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
