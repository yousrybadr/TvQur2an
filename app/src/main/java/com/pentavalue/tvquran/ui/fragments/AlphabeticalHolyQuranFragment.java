package com.pentavalue.tvquran.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.RecitersSearchAdapter;
import com.pentavalue.tvquran.adapter.SectionAlphabiticalRecyclerAdapter;
import com.pentavalue.tvquran.adapter.SectionCollectionRecyclerAdapter;
import com.pentavalue.tvquran.model.AlphabeticalHolyQuran;
import com.pentavalue.tvquran.model.CollectionWrapper;
import com.pentavalue.tvquran.model.DataAlphabtical;
import com.pentavalue.tvquran.model.Reciters;
import com.pentavalue.tvquran.model.RectrictsWrapperModel;
import com.pentavalue.tvquran.model.SectionHeaderAlphabetical;
import com.pentavalue.tvquran.model.SectionHeaderRewayatOrder;
import com.pentavalue.tvquran.model.WrapperData;
import com.pentavalue.tvquran.network.listeneres.OnHolyQuranListener;
import com.pentavalue.tvquran.network.listeneres.OnReaderClick;
import com.pentavalue.tvquran.network.managers.RecitersMangers;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.utils.ValidatorUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlphabeticalHolyQuranFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlphabeticalHolyQuranFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlphabeticalHolyQuranFragment extends BaseFragment implements OnReaderClick, View.OnClickListener, OnHolyQuranListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<SectionHeaderAlphabetical> sectionHeaders;
    List<SectionHeaderRewayatOrder> sectionHeadersRewayat;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    ImageView rightImg;
    Dialog dialog;
    int flag = 0;
    OnReaderClick onReaderClick;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AlphabeticalHolyQuranFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlphabeticalHolyQuranFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlphabeticalHolyQuranFragment newInstance(String param1, String param2) {
        AlphabeticalHolyQuranFragment fragment = new AlphabeticalHolyQuranFragment();
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
        View view = inflater.inflate(R.layout.fragment_alphabetical_holy_quran, container, false);
        rightImg = ((ParentActivity) getActivity()).getRightImg();
        init(view);
        onReaderClick = this;
        ((ParentActivity)getActivity()).hideFilter(false);
        ((ParentActivity)getActivity()).setTitleTxt(getString(R.string.HolyQr2an));
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        sectionHeaders = new ArrayList<>();
        sectionHeadersRewayat = new ArrayList<>();
        rightImg.setOnClickListener(this);
      //  ((ParentActivity)getActivity()).setTitleTxt(getString(R.string.discoverTitle));


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
    public void onResume() {
        super.onResume();
        RecitersMangers.getInstance(getActivity()).addListener(this);

        showLoadingDialog();
        RecitersMangers.getInstance(getActivity()).performBrowseAlphabeticalHolyQuran();
        flag = 1;
    }

    @Override
    public void onException(Exception exception) {
        hideLoadingDialog();
        exception.printStackTrace();
        Toast.makeText(getActivity(),getString(R.string.error),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        hideLoadingDialog();
        if (flag == 1) {
            AlphabeticalHolyQuran obj = RecitersMangers.getInstance(getActivity()).getAlphabeticalHolyQuran();
            alphapiticalHoly(obj);
            flag = 0;
        } else if (flag == 2) {
            CollectionWrapper obj = RecitersMangers.getInstance(getActivity()).getCollectionWrapper();
            collectionData(obj);
            flag = 0;
        } else if (flag == 3) {
           RectrictsWrapperModel obj = RecitersMangers.getInstance(getActivity()).getReciterses();
            recentData(obj);
            flag = 0;
        }
        else if(flag == 4)
        {
            RectrictsWrapperModel obj = RecitersMangers.getInstance(getActivity()).getReciterses();
            recentData(obj);
            flag = 0;
        }

    }

    private void collectionData(CollectionWrapper obj) {
        for (int i = 0; i < obj.getCollections().size(); i++) {
            sectionHeadersRewayat.add(new SectionHeaderRewayatOrder(obj.getCollections().get(i).getAuthors()
                    , obj.getCollections().get(i).getCollection_name(), obj.getCollections().get(i).getAuthors().size()));
        }
        setupRecyclerViewRewayat(recyclerView, sectionHeadersRewayat);
    }

    private void recentData(RectrictsWrapperModel obj) {

        setupRecyclerViewRecent(recyclerView, obj.getRecitersesList());
    }

    void alphapiticalHoly(AlphabeticalHolyQuran obj) {
        TreeMap<String, ArrayList<WrapperData>> map = obj.getAlphabeticalHolyQuran();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
           Log.i("LOLO",pair.getKey() + " = " + pair.getValue());
            List<WrapperData> wrapperDatas = (List<WrapperData>) pair.getValue();
            List<DataAlphabtical> dataAlphabticals = new ArrayList<>();
            for (int i = 0; i < wrapperDatas.size(); i++) {
                dataAlphabticals.add(wrapperDatas.get(i).getDataAlphabtical());
            }
            sectionHeaders.add(new SectionHeaderAlphabetical(dataAlphabticals, pair.getKey().toString(), 1));
            it.remove(); // avoids a ConcurrentModificationException
        }
        setupRecyclerView(recyclerView, sectionHeaders);
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<SectionHeaderAlphabetical> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        setListItems(recyclerView, list);

    }

    private void setListItems(RecyclerView recyclerView, List<SectionHeaderAlphabetical> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            SectionAlphabiticalRecyclerAdapter adapter = new SectionAlphabiticalRecyclerAdapter(getActivity(), list, this);
            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }
    }

    private void setupRecyclerViewRewayat(RecyclerView recyclerView, List<SectionHeaderRewayatOrder> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        setListItemsRewayat(recyclerView, list);

    }

    private void setListItemsRewayat(RecyclerView recyclerView, List<SectionHeaderRewayatOrder> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            SectionCollectionRecyclerAdapter adapter = new SectionCollectionRecyclerAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void setupRecyclerViewRecent(RecyclerView recyclerView, List<Reciters> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        setListItemsRecent(recyclerView, list);

    }

    private void setListItemsRecent(RecyclerView recyclerView, List<Reciters> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            RecitersSearchAdapter adapter = new RecitersSearchAdapter(getActivity(), list, onReaderClick);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rightImg:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_catogry);
        // dialog.setTitle(getString(R.string.title_filter));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        final RadioButton rd_Alphabitical = (RadioButton) dialog.findViewById(R.id.rd_Random);
        final RadioButton rd_Recent = (RadioButton) dialog.findViewById(R.id.rd_Recent);
        final RadioButton rd_Rowayat = (RadioButton) dialog.findViewById(R.id.rd_mostLiked);
        final RadioButton rd_mostListened = (RadioButton) dialog.findViewById(R.id.rd_mostListened);
        TextView button = (TextView) dialog.findViewById(R.id.bt_cancel);
        rd_Alphabitical.setText(R.string.AlphabeticalOrder);
        rd_Rowayat.setText(R.string.RewayatOrder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        rd_Alphabitical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd_Alphabitical.isChecked()) {
                    if (rd_Recent.isChecked()) {
                        rd_Recent.setChecked(false);
                    }
                    if (rd_Rowayat.isChecked()) {
                        rd_Rowayat.setChecked(false);
                    }
                    if (rd_mostListened.isChecked()) {
                        rd_mostListened.setChecked(false);
                    }
                    rd_Alphabitical.setChecked(true);
                }
                RecitersMangers.getInstance(getActivity()).performBrowseAlphabeticalHolyQuran();
                flag = 1;
                dialog.hide();
            }
        });
        rd_Recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd_Recent.isChecked()) {
                    if (rd_Alphabitical.isChecked()) {
                        rd_Alphabitical.setChecked(false);
                    }
                    if (rd_Rowayat.isChecked()) {
                        rd_Rowayat.setChecked(false);
                    }
                    if (rd_mostListened.isChecked()) {
                        rd_mostListened.setChecked(false);
                    }
                    rd_Recent.setChecked(true);
                }
                flag = 3;
                showLoadingDialog();
                RecitersMangers.getInstance(getActivity()).performBrowseRecentlyModifiedHolyQuran();
                dialog.hide();
            }
        });
        rd_Rowayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd_Rowayat.isChecked()) {
                    if (rd_Alphabitical.isChecked()) {
                        rd_Alphabitical.setChecked(false);
                    }
                    if (rd_Recent.isChecked()) {
                        rd_Recent.setChecked(false);
                    }
                    if (rd_mostListened.isChecked()) {
                        rd_mostListened.setChecked(false);
                    }
                    rd_Rowayat.setChecked(true);
                }
                flag = 2;
                showLoadingDialog();
                RecitersMangers.getInstance(getActivity()).performBrowseRewayatOrder();
                dialog.hide();
            }
        });
        rd_mostListened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd_Rowayat.isChecked()) {
                    if (rd_Alphabitical.isChecked()) {
                        rd_Alphabitical.setChecked(false);
                    }
                    if (rd_Recent.isChecked()) {
                        rd_Recent.setChecked(false);
                    }
                    if (rd_Rowayat.isChecked()) {
                        rd_Rowayat.setChecked(false);
                    }
                    rd_mostListened.setChecked(true);
                }
                flag = 4;
                showLoadingDialog();
                RecitersMangers.getInstance(getActivity()).performBrowseMostListenedHolyQuran();
                dialog.hide();
            }
        });

        // now that the dialog is set up, it's time to show it


        dialog.show();

    }

    @Override
    public void onClick(int Position, Reciters reciters) {
        Log.v("lolo", "" + Position);
        //Toast.makeText(getContext(),""+ Position, Toast.LENGTH_SHORT).show();
        ((ParentActivity) getActivity()).replaceFragment(ReaderProfileFragment.newInstance(reciters.getId()), false, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
