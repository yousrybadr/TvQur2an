package com.pentavalue.tvquran.ui.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.data.constants.NetworkConstants;
import com.pentavalue.tvquran.datasorage.AppSharedPrefs;
import com.pentavalue.tvquran.datasorage.PrefsConstant;
import com.pentavalue.tvquran.model.AboutUsModel;
import com.pentavalue.tvquran.network.NetworkChecker;
import com.pentavalue.tvquran.network.listeneres.OnAboutUsRecitersListener;
import com.pentavalue.tvquran.network.managers.RecitersMangers;
import com.pentavalue.tvquran.ui.activities.ParentActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutWebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutWebFragment extends Fragment implements OnAboutUsRecitersListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutWebFragment.
     */
    // TODO: Rename and change types and number of parameters
    WebView contentWeb;
    ProgressBar progress;
    WebChromeClient customWebClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    };
    WebViewClient customWebView = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //view.loadUrl(mParam1);
            return true;
        }
    };
    private ProgressDialog mDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutWebFragment() {
        // Required empty public constructor
    }

    public static AboutWebFragment newInstance(String param1, String param2) {
        AboutWebFragment fragment = new AboutWebFragment();
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
        View v = inflater.inflate(R.layout.fragment_about_web, container, false);
        RecitersMangers.getInstance(getActivity()).addListener(this);
        contentWeb = (WebView) v.findViewById(R.id.webContent);
        progress = (ProgressBar) v.findViewById(R.id.progress);
        progress.setMax(100);

        progress.setVisibility(View.GONE);

        ((ParentActivity) getActivity()).setTitleTxt(mParam2);
        if (NetworkChecker.isNetworkAvailable(getContext())) {
            showLoadingDialog();
            if (mParam1.equals("terms_tag")) {
                RecitersMangers.getInstance(getActivity()).performAboutUsResult(AppSharedPrefs.getStringVal(PrefsConstant.LANG), NetworkConstants.GET_TERMS);

            } else {
                RecitersMangers.getInstance(getActivity()).performAboutUsResult(AppSharedPrefs.getStringVal(PrefsConstant.LANG), NetworkConstants.GET_ABOUT_US);
            }


        }
        return v;
    }

    private void initWebView(String data) {

        contentWeb.getSettings().setJavaScriptEnabled(true);
        progress.setProgress(0);
        contentWeb.setWebChromeClient(customWebClient);
        contentWeb.setWebViewClient(customWebView);
        contentWeb.loadData(data, "text/html", Xml.Encoding.UTF_8.name());
    }

    /**
     * This method is used to show a progress dialog for the screen.
     *
     * @return True if the progress dialog was actually shown due this call.
     */
    public boolean showLoadingDialog() {
        if (mDialog == null && this != null) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setMessage(getString(R.string.loading));
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setIndeterminate(true);
            mDialog.setCancelable(false);
            mDialog.getWindow().setGravity(Gravity.CENTER);
        }

        if (mDialog != null && !mDialog.isShowing()) {
            try {
                mDialog.show();
            } catch (Exception e) {
                // Nothing
            }
            return true;
        }
        return false;
    }

    /**
     * This method is used to hide the progress dialog off this screen.
     *
     * @return True if the dialog was actually dismissed due this call.
     */
    public boolean hideLoadingDialog() {
        if (this != null && mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                // Nothing
            }
            return true;
        }
        return false;
    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }


    @Override
    public void onException(Exception exception) {
        hideLoadingDialog();
    }

    @Override
    public void onSuccess(AboutUsModel obj) {
        hideLoadingDialog();
        try {
            //Toast.makeText(getContext(), obj.getEContent().getAboutUs().getContent(), Toast.LENGTH_SHORT).show();


            initWebView(obj.getEContent().getAboutUs().getContent());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //
    }
}
