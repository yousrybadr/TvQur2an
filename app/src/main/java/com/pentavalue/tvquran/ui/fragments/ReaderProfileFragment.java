package com.pentavalue.tvquran.ui.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.adapter.SectionRecyclerAdapter;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.Reciters;
import com.pentavalue.tvquran.model.SectionHeader;
import com.pentavalue.tvquran.network.listeneres.OnBrowseProfile;
import com.pentavalue.tvquran.network.managers.RecitersMangers;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.utils.ValidatorUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import butterknife.Bind;
import butterknife.ButterKnife;


public class ReaderProfileFragment extends BaseFragment implements OnBrowseProfile,View.OnClickListener {
    Context context;


    @Bind(R.id.profile_image)
    ImageView profile_image;
    @Bind(R.id.profile_name)
    TextView profile_name;
    @Bind(R.id.numOfListen)
    TextView numOfListen;
    @Bind(R.id.numOfLikes)
    TextView numOfLikes;
    @Bind(R.id.numOfSorhaRead)
    TextView numOfSorhaRead;
    @Bind(R.id.ReaderDetailsTxt)
    ExpandableTextView ReaderDetailsTxt;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.Btn_arrow)
    ImageButton arrow;
    ImageView rightImg;
    ImageView leftImg;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<SectionHeader> sectionHeaders=new ArrayList<>();
    List<Entries> childList=new ArrayList<>();
    boolean isTextViewClicked = false;
    // TODO: Rename and change types of parameters
    private int readerID;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReaderProfileFragment() {
        // Required empty public constructor
    }


    public static ReaderProfileFragment newInstance(int param1) {
        ReaderProfileFragment fragment = new ReaderProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
      //  args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            readerID = getArguments().getInt(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reader, container, false);


            context=getActivity();

     //   }
        ((ParentActivity)getActivity()).hideFilter(true);
       /* leftImg=((ParentActivity)getActivity()).getLeftImg();

        if(leftImg.getVisibility()==View.GONE)
        leftImg.setVisibility(View.VISIBLE);*/
        init(view);
        showLoadingDialog();
        RecitersMangers.getInstance(getActivity()).performBrowseProfile(readerID);
        //TODO change TextView To Expandable TextView
        return view;
    }

    private void init(View rootView) {
        ButterKnife.bind(this, rootView);
///arrow.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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
        hideLoadingDialog();
        exception.printStackTrace();
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<SectionHeader> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        setListItems(recyclerView, list);

    }

    private void setListItems(RecyclerView recyclerView, List<SectionHeader> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            SectionRecyclerAdapter adapter = new SectionRecyclerAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(Reciters entity) {
        hideLoadingDialog();
        ((ParentActivity)context).setTitleTxt(entity.getName());
        profile_name.setText(entity.getName());
        numOfLikes.setText(String.valueOf(entity.getTotal_likes()));
        numOfListen.setText(String.valueOf(entity.getTotal_listened()));
        Picasso.with(getActivity())
                .load(entity.getReciterImage())
                .placeholder(R.drawable.emosad)
                .error(R.drawable.emosad)
                .into(profile_image);
        ReaderDetailsTxt.setInterpolator(new OvershootInterpolator());
        ReaderDetailsTxt.setExpandInterpolator(new OvershootInterpolator());
        ReaderDetailsTxt.setCollapseInterpolator(new OvershootInterpolator());
        ReaderDetailsTxt.setText(entity.getReciterDetails());
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ReaderDetailsTxt.isExpanded()){
                    ReaderDetailsTxt.collapse();
                    arrow.setImageResource( R.drawable.arrowdown );
                }else {
                    ReaderDetailsTxt.expand();
                    arrow.setImageResource( R.drawable.arrowup );
                }
            }
        });
        numOfSorhaRead.setText(entity.getRecitations_count()+"");
        for(int i=0;i<entity.getRecitationses().size();i++)
        {
            sectionHeaders.add(new SectionHeader(entity.getRecitationses().get(i).getEntries(),entity.getRecitationses().get(i).getCollection(),
                    Integer.parseInt(entity.getRecitationses().get(i).getTotal_entries()) ));
        }

       setupRecyclerView(recyclerView,sectionHeaders);
    }

    @Override
    public void onClick(View v) {
       /* switch (v.getId()) {
            case R.id.arrow:{
                if(isTextViewClicked){
                    //This will shrink textview to 2 lines if it is expanded.
                    ReaderDetailsTxt.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = false;
                } else {
                    //This will expand the textview if it is of 2 lines
                    ReaderDetailsTxt.setMaxLines(4);
                    isTextViewClicked = true;
                }

            }
                break;
        }*/
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();

        RecitersMangers.getInstance(getActivity()).addListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        RecitersMangers.getInstance(getActivity()).removeListener(this);

    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
