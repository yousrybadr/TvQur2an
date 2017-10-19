package com.pentavalue.tvquran.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.ui.activities.ParentActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutWebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutWebFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AboutWebFragment() {
        // Required empty public constructor
    }

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
    public static AboutWebFragment newInstance(String param1,String param2) {
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
        View v=inflater.inflate(R.layout.fragment_about_web, container, false);
        contentWeb=(WebView) v.findViewById(R.id.webContent);
        progress=(ProgressBar) v.findViewById(R.id.progress);
        progress.setMax(100);
        ((ParentActivity)getActivity()).setTitleTxt(mParam2);
        initWebView();
        return v;
    }

    private void initWebView() {

        contentWeb.getSettings().setJavaScriptEnabled(true);
        progress.setProgress(0);
        contentWeb.setWebChromeClient(customWebClient);
        contentWeb.setWebViewClient(customWebView);
        contentWeb.loadUrl(mParam1);
    }

    WebViewClient customWebView=new WebViewClient(){

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(mParam1);
            return true;
        }
    };

    WebChromeClient customWebClient=new WebChromeClient(){

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    };

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

}
