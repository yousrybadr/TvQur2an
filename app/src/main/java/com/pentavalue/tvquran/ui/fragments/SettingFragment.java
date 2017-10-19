package com.pentavalue.tvquran.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.datasorage.AppSharedPrefs;
import com.pentavalue.tvquran.datasorage.PrefsConstant;
import com.pentavalue.tvquran.datasorage.database.HistoryTable;
import com.pentavalue.tvquran.datasorage.database.SearchHistoryTable;
import com.pentavalue.tvquran.ui.activities.ParentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
  * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    @Bind(R.id.toggle_autoPlay)
    ToggleButton btnAutoPlay;
    @Bind(R.id.toggle_update)
    ToggleButton btnUpdateWIFI;
    @Bind(R.id.clear_history_layout)
    LinearLayout clearHistory;
    @Bind(R.id.clear_download_history_layout)
    LinearLayout clearDownloadHistory;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();

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
        View view=inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this,view);
        ((ParentActivity)getActivity()).hideFilter(true);
        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchHistoryTable.getInstance().clearAll();
                Toast.makeText(getActivity(), getString(R.string.clear_history_done), Toast.LENGTH_SHORT).show();
            }
        });
        clearDownloadHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( HistoryTable.getInstance().GetHistoryList().size() >0){
                    HistoryTable.getInstance().deleteHistory();
                    Toast.makeText(getActivity(), getString(R.string.clear_history_done), Toast.LENGTH_SHORT).show();

                }

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((ParentActivity)getActivity()).setTitleTxt(getString(R.string.title_setting));
        if (AppSharedPrefs.getBooleanVal(PrefsConstant.AUTO_PLAY))
            btnAutoPlay.setChecked(true);
        else
            btnAutoPlay.setChecked(false);

        if (AppSharedPrefs.getBooleanVal(PrefsConstant.WIFI_ONLY))
            btnUpdateWIFI.setChecked(true);
        else
            btnUpdateWIFI.setChecked(false);

            btnAutoPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    AppSharedPrefs.saveBooleanPrefs(PrefsConstant.AUTO_PLAY,b);
                }
            });

        btnUpdateWIFI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppSharedPrefs.saveBooleanPrefs(PrefsConstant.WIFI_ONLY,b);

            }
        });


    }
}
