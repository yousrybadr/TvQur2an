package com.pentavalue.tvquran.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.SearchResultSctionRecyclerAdapter;
import com.pentavalue.tvquran.model.AuthorWrapperModel;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.HeaderModel;
import com.pentavalue.tvquran.model.SearchResultModel;
import com.pentavalue.tvquran.ui.activities.ParentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private SearchResultModel searchResult;
    private String mParam2;

    RecyclerView searchResultRV;
    EditText edtSearch;
    List<HeaderModel> sectionList  ;
    public SearchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SearchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance(SearchResultModel searchResult ,String param2) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, searchResult);
         args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchResult = (SearchResultModel) getArguments().getSerializable(ARG_PARAM1);
             mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_search_result, container, false);
        searchResultRV=(RecyclerView)v.findViewById(R.id.recyclerView);
        edtSearch=(EditText) v.findViewById(R.id.et_Search);

        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Toast.makeText(getActivity(), "You reach me", Toast.LENGTH_SHORT).show();
                     //   getLocationPermission();
                        edtSearch.getText().clear();
                        return true;
                    }
                }
                return false;
            }
        });
        ((ParentActivity)getActivity()).setTitleTxt(getString(R.string.search_result));

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        edtSearch.setText(mParam2);
        sectionList = new ArrayList<>();
        searchResultRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (searchResult.getQuraanList()!=null && searchResult.getQuraanList().size() >0){
            ArrayList<Entries> temp=new ArrayList<>();
            for (int i=0 ;i <2; i++) {
                if (i< searchResult.getQuraanList().size())
                temp.add(searchResult.getQuraanList().get(i));
            }
            sectionList.add(new HeaderModel("Holy Quran",searchResult.getQuraanList().size(),temp,searchResult.getQuraanList()));

        }

        if (searchResult.getQuraanSelectionList()!=null && searchResult.getQuraanSelectionList().size() >0){
            ArrayList<Entries> temp=new ArrayList<>();
            for (int i=0 ;i <2; i++){
                temp.add(searchResult.getQuraanSelectionList().get(i));

            }
            sectionList.add(new HeaderModel("Collections",searchResult.getQuraanSelectionList().size(),temp,searchResult.getQuraanSelectionList()));

        }

        if (searchResult.getAuthorList() !=null && searchResult.getAuthorList().size()>0){
            ArrayList<AuthorWrapperModel> temp=new ArrayList<>();
             for (int i=0 ;i <2; i++){
                temp.add(searchResult.getAuthorList().get(i));


            }
           //sectionList.add(new HeaderModel("Authors",searchResult.getAuthorList(),temp,searchResult.getAuthorList().size()));

        }

        searchResultRV.setAdapter(new SearchResultSctionRecyclerAdapter(getActivity(),sectionList));


    }
}
