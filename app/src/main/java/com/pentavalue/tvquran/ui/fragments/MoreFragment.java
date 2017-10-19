package com.pentavalue.tvquran.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.data.constants.NetworkConstants;
import com.pentavalue.tvquran.network.listeneres.ConnectivityReceiverListener;
import com.pentavalue.tvquran.recivers.ConnectivityReceiver;
import com.pentavalue.tvquran.ui.activities.ParentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment implements View.OnClickListener,ConnectivityReceiverListener {
    public MoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    @Bind(R.id.settingLayout)
    LinearLayout settingLayout;
    @Bind(R.id.contactUsLayout)
    LinearLayout contactUsLayout;
    @Bind(R.id.aboutLayout)
    LinearLayout aboutLayout;
    @Bind(R.id.termsOfUse)
    LinearLayout termsOfUse;
    @Bind(R.id.noConnectionLayout)
    LinearLayout noConnection;
    boolean isConnected;
    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category_layout, container, false);
        init(view);
        ((ParentActivity)getActivity()).setTitleTxt(getString(R.string.title_more));
        ((ParentActivity)getActivity()).hideFilter(true);

        return  view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        //   titleTxt.setText("Home");
        //   rightImg.setVisibility(View.GONE);
        settingLayout.setOnClickListener(this);
        contactUsLayout.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);
        termsOfUse.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        isConnected = ConnectivityReceiver.isConnected();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settingLayout:
                openSettingFragment();
                break;
            case R.id.contactUsLayout:
                openContactUSFragment();
                break;
            case R.id.aboutLayout:
                ((ParentActivity)getActivity()).replaceFragment(AboutWebFragment.newInstance(NetworkConstants.ABOUT_URL
                ,getString(R.string.About)),false,false);
                break;

            case  R.id.termsOfUse:
                ((ParentActivity)getActivity()).replaceFragment(AboutWebFragment.newInstance(NetworkConstants.TERMS_OF_US,
                        getString(R.string.TermsOfUse)),false,false);

        }
    }

    private void openContactUSFragment() {
        //    getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ContuctUsFragment()).addToBackStack(null).commit();
        ((ParentActivity)getActivity()).replaceFragment(ContactUsFragment.newInstance(),true,false);
    }
    private void openSettingFragment() {
        // getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new SettingFragment()).addToBackStack(null).commit();
        ((ParentActivity)getActivity()).replaceFragment(new SettingFragment(),false,false);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected){
            noConnection.setVisibility(View.GONE);
        }
        else {
            noConnection.setVisibility(View.VISIBLE);
        }
    }
}
