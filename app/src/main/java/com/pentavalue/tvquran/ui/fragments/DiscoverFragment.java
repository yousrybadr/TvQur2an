package com.pentavalue.tvquran.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.NameAdapter;
import com.pentavalue.tvquran.adapter.SearchHistoryAdapter;
import com.pentavalue.tvquran.adapter.SearchResultSctionRecyclerAdapter;
import com.pentavalue.tvquran.data.constants.NetworkConstants;
import com.pentavalue.tvquran.datasorage.AppSharedPrefs;
import com.pentavalue.tvquran.datasorage.PrefsConstant;
import com.pentavalue.tvquran.datasorage.database.SearchHistoryTable;
import com.pentavalue.tvquran.model.AuthorWrapperModel;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.HeaderModel;
import com.pentavalue.tvquran.model.MapModel;
import com.pentavalue.tvquran.model.Reciters;
import com.pentavalue.tvquran.model.SearchName;
import com.pentavalue.tvquran.model.SearchResultModel;
import com.pentavalue.tvquran.network.listeneres.OnReaderClick;
import com.pentavalue.tvquran.network.listeneres.OnSearchItemClick;
import com.pentavalue.tvquran.network.listeneres.OnSearchRecitersListener;
import com.pentavalue.tvquran.network.listeneres.OnSearchResultListener;
import com.pentavalue.tvquran.network.managers.RecitersMangers;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.utils.ValidatorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiscoverFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends BaseFragment implements View.OnClickListener, OnReaderClick, OnSearchRecitersListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.BeautifulQuraan)
    TextView BeautifulQuraan;
    @Bind(R.id.RecitationByChildren)
    TextView RecitationByChildren;
    @Bind(R.id.Requa)
    TextView Requa;
    @Bind(R.id.DailyZekr)
    TextView DailyZekr;
    @Bind(R.id.DUA)
    TextView DUA;
    @Bind(R.id.AdanAndTakber)
    TextView AdanAndTakber;
    @Bind(R.id.AmazingQuran)
    TextView AmazingQuran;
    @Bind(R.id.VisitorRecitation)
    TextView VisitorRecitation;
    @Bind(R.id.RareRecitation)
    TextView RareRecitation;
    @Bind(R.id.et_Search)
    EditText et_Search;
    @Bind(R.id.recyclerView)
       RecyclerView recyclerView;
    @Bind(R.id.HolyQuraanLayout)
    LinearLayout HolyQuraan;
    @Bind(R.id.VidioAndBroadcast)
    LinearLayout VidioAndBroadcast;


    @Bind(R.id.searchLayout)
       LinearLayout searchLayout;
    Bundle bundle = new Bundle();
    OnReaderClick onReaderClick;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public LinearLayout getSearchLayout() {
        return searchLayout;
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init(view);
        VidioAndBroadcast.setVisibility(View.GONE);
        onReaderClick = this;
        ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.discoverTitle));
        ((ParentActivity) getActivity()).hideFilter(true);
        et_Search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (et_Search.getRight() - et_Search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Toast.makeText(getActivity(), "You reach me", Toast.LENGTH_SHORT).show();
                        //   getLocationPermission();
                        et_Search.getText().clear();
                        return true;
                    }
                }
                return false;
            }
        });
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        RecitersMangers.getInstance(getActivity()).removeListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        BeautifulQuraan.setOnClickListener(this);

        RecitationByChildren.setOnClickListener(this);

        Requa.setOnClickListener(this);

        DailyZekr.setOnClickListener(this);

        DUA.setOnClickListener(this);

        AdanAndTakber.setOnClickListener(this);

        AmazingQuran.setOnClickListener(this);

        VisitorRecitation.setOnClickListener(this);

        RareRecitation.setOnClickListener(this);

        HolyQuraan.setOnClickListener(this);

        VidioAndBroadcast.setOnClickListener(this);
        et_Search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    searchLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    ArrayList<MapModel> mlist = SearchHistoryTable.getInstance().GetHistoryList();
                    recyclerView.setAdapter(new SearchHistoryAdapter(getActivity(), mlist, new OnSearchItemClick() {
                        @Override
                        public void onClick(int Position, MapModel reciters) {

                            et_Search.setText(reciters.getValue());
                            performSearch();
                            // RecitersMangers.getInstance(getActivity()).performBrowseSearchResult(reciters.getValue(),"ar");
                        }
                    }));
                }
            }
        });
        et_Search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                RecitersMangers.getInstance(getActivity()).performBrowseSearchResult(s.toString(),
                        AppSharedPrefs.getStringVal(PrefsConstant.LANG));
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

                // AppSharedPrefs.saveStringPrefs(PrefsConstant.HISTORY,s.toString());
                Log.i("LOLO", s.toString());
            }
        });

        et_Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String uniqueId = UUID.randomUUID().toString();
                    SearchHistoryTable.getInstance().insertHistoryModel(new MapModel(uniqueId, et_Search.getText().toString()));

                    performSearch();
                    return true;
                }
                return false;
            }
        });

    }

    private void performSearch() {
        final List<HeaderModel> sectionList =new ArrayList<>()  ;
        showLoadingDialog();
        RecitersMangers.getInstance(getActivity()).getSearchResult(et_Search.getText().toString(),
                AppSharedPrefs.getStringVal(PrefsConstant.LANG));
        RecitersMangers.getInstance(getActivity()).addListener(new OnSearchResultListener() {
            @Override
            public void onSuccess(SearchResultModel searchResult) {
                if (isAdded()) {
                    hideLoadingDialog();

                    searchLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



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

                    recyclerView.setAdapter(new SearchResultSctionRecyclerAdapter(getActivity(),sectionList));

                    //  ((ParentActivity) getActivity()).replaceFragment(SearchResultFragment.newInstance(obj, et_Search.getText().toString()), false, false);
                }

            }

            @Override
            public void onException(Exception exception) {
                exception.printStackTrace();
                hideLoadingDialog();
            }
        });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BeautifulQuraan:
                bundle.putInt("category_id", NetworkConstants.categories.HolyQr2an);
                OpenSearchDetails();

                break;
            case R.id.RecitationByChildren:
                bundle.putInt("category_id", NetworkConstants.categories.RecitationByChildren);
                OpenSearchDetails();

                break;
            case R.id.Requa:
                bundle.putInt("category_id", NetworkConstants.categories.Ruqyah);
                OpenSearchDetails();

                break;
            case R.id.DailyZekr:
                bundle.putInt("category_id", NetworkConstants.categories.DailyDhikr);
                OpenSearchDetails();

                break;
            case R.id.DUA:
                bundle.putInt("category_id", NetworkConstants.categories.DUA);
                OpenSearchDetails();

                break;
            case R.id.AdanAndTakber:
                bundle.putInt("category_id", NetworkConstants.categories.AdanAndTakbier);
                OpenSearchDetails();

                break;
            case R.id.AmazingQuran:
                bundle.putInt("category_id", NetworkConstants.categories.amazinQuraan);
                OpenSearchDetails();

                break;
            case R.id.VisitorRecitation:
                bundle.putInt("category_id", NetworkConstants.categories.VisitorsRecitations);
                OpenSearchDetails();

                break;
            case R.id.RareRecitation:
                bundle.putInt("category_id", NetworkConstants.categories.RareRecitations);
                OpenSearchDetails();
                break;
            case R.id.HolyQuraanLayout:
                OpenSearchDetailsHolyQur2an();
                break;
            case R.id.VidioAndBroadcast:
                OpenVidioAndBroadcast();
                break;

        }
    }


    void OpenSearchDetails() {
        Fragment fragment = new SearchDetailsFragment();
        fragment.setArguments(bundle);
        ((ParentActivity) getActivity()).replaceFragment(fragment, false, false);
    }

    void OpenSearchDetailsHolyQur2an() {
        ((ParentActivity) getActivity()).replaceFragment(new AlphabeticalHolyQuranFragment(), false, false);
    }

    void OpenVidioAndBroadcast() {
        ((ParentActivity) getActivity()).replaceFragment(new LiveBroadcastFragment(), false, false);
    }

    @Override
    public void onException(Exception exception) {
        exception.printStackTrace();
//        Toast.makeText(getActivity(),exception.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(SearchName obj) {
        searchLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        HashMap<String, String> map = obj.getRecitersResult();
        Iterator it = map.entrySet().iterator();
        List<String> names = new ArrayList<>();
        ArrayList<MapModel> mapList = new ArrayList<>();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            // names.add(pair.getValue().toString());
            mapList.add(new MapModel(pair.getKey().toString(), pair.getValue().toString()));
            it.remove(); // avoids a ConcurrentModificationException
        }
        setupRecyclerView(recyclerView, mapList);
    }

    private void setupRecyclerView(RecyclerView recyclerView, ArrayList<MapModel> list) {
        //  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        setListItems(recyclerView, list);

    }

    private void setListItems(RecyclerView recyclerView, ArrayList<MapModel> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            NameAdapter adapter = new NameAdapter(getActivity(), list, onReaderClick);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(int Position, Reciters reciters) {

    }

    @Override
    public void onResume() {
        super.onResume();
        RecitersMangers.getInstance(getActivity()).addListener(this);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
