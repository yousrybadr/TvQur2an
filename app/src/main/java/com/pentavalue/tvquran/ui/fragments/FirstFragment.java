package com.pentavalue.tvquran.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.RecitersAdapter;
import com.pentavalue.tvquran.adapter.SortedSorahAdapter;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.Home;
import com.pentavalue.tvquran.model.Reciters;
import com.pentavalue.tvquran.network.listeneres.OnAudioPlayListener;
import com.pentavalue.tvquran.network.listeneres.OnBrowseHome;
import com.pentavalue.tvquran.network.listeneres.OnReaderClick;
import com.pentavalue.tvquran.network.managers.RecitersMangers;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.utils.ValidatorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ArrayList<Entries> dataList;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.recyclerViewSorted)
    RecyclerView recyclerViewSorted;
    @Bind(R.id.typeTxt)
    TextView typeTxt;
    OnReaderClick onReaderClick;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onPause() {
        super.onPause();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        RecitersMangers.getInstance(getActivity()).performBrowseHome(1);
        RecitersMangers.getInstance(getActivity()).addListener(new OnBrowseHome() {
            @Override
            public void onSuccess(Home obj) {
                typeTxt.setText(obj.getSorted());
                List<Reciters> reciterses = obj.getReciterses();
                List<Entries> entries = obj.getEntries();
                setupRecyclerView(recyclerView, reciterses);
                List<Entries> entriesById = new ArrayList<>();
                for (int i = 0; i < entries.size(); i++) {
                    if (reciterses.get(1).getId() == entries.get(i).getAuthor_id()) {
                        entriesById.add(entries.get(i));
                    }
                }
                setupRecyclerViewSorted(recyclerViewSorted, entries);
            }

            @Override
            public void onException(Exception exception) {

            }
        });
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Reciters> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        setListItems(recyclerView, list);

    }

    private void setupRecyclerViewSorted(RecyclerView recyclerView, List<Entries> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setListItemsSorted(recyclerView, list);

    }

    private void setListItems(RecyclerView recyclerView, List<Reciters> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            RecitersAdapter adapter = new RecitersAdapter(getActivity(), list, onReaderClick);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void setListItemsSorted(RecyclerView recyclerView, List<Entries> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            dataList = (ArrayList<Entries>) list;
            SortedSorahAdapter adapter = new SortedSorahAdapter(getActivity(), list, new OnAudioPlayListener() {
                @Override
                public void OnPlayTrack(int Postion, Entries entries) {
                    //((ParentActivity)getActivity()).playSound(entries.getSoundPath(),entries.getTitle(),entries.getReciter_name(),false);
                    ((ParentActivity) getActivity()).showPlayerAndPlaySound(dataList, Postion, 1);
                }

                @Override
                public void OnPlayTrack(int Postion, ArrayList<Entries> entries) {

                }
            });
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
