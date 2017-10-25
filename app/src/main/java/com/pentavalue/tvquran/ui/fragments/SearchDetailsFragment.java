package com.pentavalue.tvquran.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.SortedSorahAdapter;
import com.pentavalue.tvquran.data.constants.NetworkConstants;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.Home;
import com.pentavalue.tvquran.network.listeneres.OnAudioPlayListener;
import com.pentavalue.tvquran.network.listeneres.OnBrawseCatogry;
import com.pentavalue.tvquran.network.managers.RecitersMangers;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.utils.ValidatorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDetailsFragment extends BaseFragment implements OnBrawseCatogry, OnAudioPlayListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    OnAudioPlayListener onAudioPlayListener;
    Context context;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.sortedType)
    TextView sortedType;
    ImageView rightImg;
    Dialog dialog;
    int id;
    int category_id;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public SearchDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchDetailsFragment newInstance(String param1, String param2) {
        SearchDetailsFragment fragment = new SearchDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_details, container, false);
        rightImg = ((ParentActivity) getActivity()).getRightImg();
        init(view);
        onAudioPlayListener = this;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            category_id = bundle.getInt("category_id");
            if (category_id == NetworkConstants.categories.HolyQr2an) {
                ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.BeautifulQuranRecitation));
            } else if (category_id == NetworkConstants.categories.DUA) {
                ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.DUA));
            } else if (category_id == NetworkConstants.categories.AdanAndTakbier) {
                ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.AdanAndTakbier));
            } else if (category_id == NetworkConstants.categories.DailyDhikr) {
                ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.DailyDhikr));
            } else if (category_id == NetworkConstants.categories.amazinQuraan) {
                ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.amazinQuraan));
            } else if (category_id == NetworkConstants.categories.VisitorsRecitations) {
                ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.VisitorsRecitations));
            } else if (category_id == NetworkConstants.categories.RareRecitations) {
                ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.RareRecitations));
            } else if (category_id == NetworkConstants.categories.Ruqyah) {
                ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.Ruqyah));
            } else if (category_id == NetworkConstants.categories.RecitationByChildren) {
                ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.RecitationByChildren));
            }


            ((ParentActivity) getActivity()).hideFilter(false);

            context = getActivity();
            performBrowes(category_id, 1);
        }

        return view;
    }

    private void performBrowes(int id, int sort) {
        showLoadingDialog();
        RecitersMangers.getInstance(getActivity()).performBrowseCategoryDetails(id, sort);

    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        //((ParentActivity)getActivity()).setTitleTxt(getString(R.string.discoverTitle));
        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Entries> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setListItems(recyclerView, list);

    }

    private void setListItems(RecyclerView recyclerView, List<Entries> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            SortedSorahAdapter adapter = new SortedSorahAdapter(getActivity(), list, onAudioPlayListener);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
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
    public void onException(Exception exception) {
        if (isAdded()) {
            exception.printStackTrace();
            Toast.makeText(getActivity(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
            hideLoadingDialog();
        }

    }

    @Override
    public void onSuccess(Home entity) {
        hideLoadingDialog();
        if (entity.getEntries().size() > 0) {
            sortedType.setText(entity.getSorted());
            setupRecyclerView(recyclerView, entity.getEntries());

        } else {
            sortedType.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            if (isAdded())
                Toast.makeText(getActivity(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void OnPlayTrack(int Postion, Entries entries) {

    }

    @Override
    public void OnPlayTrack(int Postion, ArrayList<Entries> entries) {
        ((ParentActivity) getActivity()).showPlayerAndPlaySound(entries, Postion, 1);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        RecitersMangers.getInstance(getActivity()).addListener(this);
        // ((ParentActivity)getActivity()).setTitleTxt("Home");
    }

    public void openDialog() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_catogry);
        // dialog.setTitle(getString(R.string.title_filter));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        RadioGroup radioGB = (RadioGroup) dialog.findViewById(R.id.radioGB);
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
                performBrowes(category_id, id);

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
