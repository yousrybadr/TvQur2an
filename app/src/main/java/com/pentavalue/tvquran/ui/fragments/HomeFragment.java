package com.pentavalue.tvquran.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.RecitersAdapter;
import com.pentavalue.tvquran.adapter.SortedSorahAdapter;
import com.pentavalue.tvquran.data.constants.NetworkConstants;
import com.pentavalue.tvquran.data.parsing.ParsingManager;
import com.pentavalue.tvquran.datasorage.AppSharedPrefs;
import com.pentavalue.tvquran.datasorage.PrefsConstant;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.Home;
import com.pentavalue.tvquran.model.Reciters;
import com.pentavalue.tvquran.network.helper.DataHelper;
import com.pentavalue.tvquran.network.helper.VolleyNetworkHelper;
import com.pentavalue.tvquran.network.listeneres.OnAudioPlayListener;
import com.pentavalue.tvquran.network.listeneres.OnReaderClick;
import com.pentavalue.tvquran.network.response.BaseResponse;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.ui.activities.PlayerActivity;
import com.pentavalue.tvquran.utils.ValidatorUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, OnReaderClick, OnAudioPlayListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ArrayList<Entries> dataList;
    OnReaderClick onReaderClick;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.recyclerViewSorted)
    RecyclerView recyclerViewSorted;
    @Bind(R.id.typeTxt)
    TextView typeTxt;
    Dialog dialog;
    ImageView rightImg;
    List<Reciters> list = new ArrayList<>();
    int id = 1;
    FrameLayout outPlayer;
    OnAudioPlayListener onAudioPlayListener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        //  ((ParentActivity)getActivity()).checkConnection();
        onReaderClick = this;
        // outPlayer=((PerantActivity)getActivity()).getPlayerOutLayout();
        onAudioPlayListener = this;
        init(rootView);
        // if (ParentActivity.isConnected){
        rightImg = ((ParentActivity) getActivity()).getRightImg();
        //rightImg.setVisibility(View.VISIBLE);
        performBrowsHome(id);
        rightImg.setOnClickListener(this);


       /* }
        else {
            rightImg.setVisibility(View.GONE);
        }
*/

        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
        // RecitersMangers.getInstance(getActivity()).removeListener(this);
        //  ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.home));
        //   ((ParentActivity)getActivity()).hideFilter(false);
        //outPlayer=((ParentActivity)getActivity()).getPlayerOutLayout();
      /*  RecitersMangers.getInstance(getActivity()).addListener(this);
        RecitersMangers.getInstance(getActivity()).performBrowseHome(id);*/
    }

    private void performBrowsHome(int id) {

        showLoadingDialog();
        JSONObject json = new JSONObject();
        try {
            json.put("sort", id);
            json.put("locale", AppSharedPrefs.getStringVal(PrefsConstant.LANG));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, NetworkConstants.HOME_URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                BaseResponse baseResponse = null;
                try {
                    baseResponse = ParsingManager.parseServerResponse(response.toString());
                    int statusResponse = baseResponse.getStatus();
                    if (statusResponse == BaseResponse.STATUS_WEBSERVICE_SUCCES_WITH_DATA) {
                        Home reciters = DataHelper.deserialize(baseResponse.getData(), Home.class);
                        hideLoadingDialog();
                        //List<Reciters> list = handleObjectListServerResponseReciters(baseResponse, Reciters.class);
                        // List<Entries> list2 = handleObjectListServerResponseEntries(baseResponse, Entries.class);
                        typeTxt.setText(reciters.getSorted());
                        Log.i("SORT", reciters.getSorted());
                        List<Reciters> reciterses = reciters.getReciterses();
                        List<Entries> entries = reciters.getEntries();
                        setupRecyclerView(recyclerView, reciterses);
                        List<Entries> entriesById = new ArrayList<>();
                        for (int i = 0; i < entries.size(); i++) {
                            if (reciterses.get(1).getId() == entries.get(i).getAuthor_id()) {
                                entriesById.add(entries.get(i));
                            }
                        }
                        setupRecyclerViewSorted(recyclerViewSorted, entries);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hideLoadingDialog();
            }
        });

        VolleyNetworkHelper.getInstance(getActivity()).addToRequestQueue(req);


    }

    private void init(View rootView) {
        ButterKnife.bind(this, rootView);
        //outPlayer.setOnClickListener(this);

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
                    // ((ParentActivity)getActivity()).showPrepearDialog();
                    ((ParentActivity) getActivity()).showPlayerAndPlaySound(dataList, Postion, 1);
                }

                @Override
                public void OnPlayTrack(int Postion, ArrayList<Entries> entries) {
                    ((ParentActivity) getActivity()).showPlayerAndPlaySound(dataList, Postion, 1);

                }
            });
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if (AppSharedPrefs.getBooleanVal(PrefsConstant.AUTO_PLAY)) {
                if (dataList != null && dataList.size() > 0)
                    ((ParentActivity) getActivity()).showPlayerAndPlaySound(dataList, 0, 1);

            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rightImg:
                openDialog();
                // showLoadingDialog();
                // RecitersMangers.getInstance(getActivity()).performBrowseHome(id);

                break;
            /*case R.id.playerOut:
                openPlayer();
                break;*/
        }

    }


    public void openDialog() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_catogry);
        // dialog.setTitle(getString(R.string.title_filter));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        RadioGroup radioGB = (RadioGroup) dialog.findViewById(R.id.radioGB);
        final RadioButton rd_Random = (RadioButton) dialog.findViewById(R.id.rd_Random);
        final RadioButton rd_Recent = (RadioButton) dialog.findViewById(R.id.rd_Recent);
        final RadioButton rd_mostLiked = (RadioButton) dialog.findViewById(R.id.rd_mostLiked);
        final RadioButton rd_mostListened = (RadioButton) dialog.findViewById(R.id.rd_mostListened);
        final Button cancelBtn = (Button) dialog.findViewById(R.id.bt_cancel);
        radioGB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkID) {

                if (checkID == R.id.rd_Random) {
                    id = 1;
                } else if (checkID == R.id.rd_Recent) {
                    id = 3;
                } else if (checkID == R.id.rd_mostLiked) {
                    id = 2;
                } else if (checkID == R.id.rd_mostListened) {
                    id = 4;
                }

                dialog.hide();
                performBrowsHome(id);


            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing() && dialog != null)
                    dialog.dismiss();
            }
        });
        // now that the dialog is set up, it's time to show it


        dialog.show();
        // Toast.makeText(this,id+"",Toast.LENGTH_SHORT).show();
        //  return id;
    }

    private void openPlayer() {
        Intent i = new Intent(getActivity(), PlayerActivity.class);
        startActivity(i);
    }


 /*   @Override
    public void onException(Exception exception) {
        hideLoadingDialog();
        Toast.makeText(getActivity(),exception.getMessage(),Toast.LENGTH_SHORT);
       // AlertController
        exception.printStackTrace();
    }

    @Override
    public void onSuccess(Home obj) {
        hideLoadingDialog();
        typeTxt.setText(obj.getSorted());
        List<Reciters>reciterses=obj.getReciterses();
        List<Entries>entries=obj.getEntries();
        setupRecyclerView(recyclerView,reciterses);
        List<Entries>entriesById=new ArrayList<>();
        for(int i=0;i<entries.size();i++)
        {
            if(reciterses.get(1).getId()==entries.get(i).getAuthor_id())
            {
                entriesById.add(entries.get(i));
            }
        }
        setupRecyclerViewSorted(recyclerViewSorted,entries);
    }*/

    @Override
    public void onClick(int Position, Reciters reciters) {
       /* Fragment fragment = new readerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("reciter_id", reciters.getId());
        fragment.setArguments(bundle);*/
        ((ParentActivity) getActivity()).replaceFragment(ReaderProfileFragment.newInstance(reciters.getId()), false, false);
    }

    @Override
    public void OnPlayTrack(int Postion, Entries entries) {

    }

    @Override
    public void OnPlayTrack(int Postion, ArrayList<Entries> entries) {

    }

}
