package com.pentavalue.tvquran.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.ui.fragments.DiscoverFragment;
import com.pentavalue.tvquran.ui.fragments.HomeFragment;
import com.pentavalue.tvquran.ui.fragments.MoreFragment;

public class HomeActivity extends AppCompatActivity   {

    TabLayout tablayout;
    FrameLayout fragmentContainer;
    MoreFragment fragmentOne;
    DiscoverFragment fragmentTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        fragmentContainer = (FrameLayout) findViewById(R.id.container);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.newInstance()).
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentTwo).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                        break;
                    default:

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

      //  fragmentOne= HomeFragment.newInstance();
        fragmentTwo= new DiscoverFragment();
        tablayout.addTab(tablayout.newTab().setText("tab One").setIcon(getResources().getDrawable(R.drawable.homeblue)),true);
        tablayout.addTab(tablayout.newTab().setText("tab two").setIcon(getResources().getDrawable(R.drawable.searchblue)));

    }

    public void replaceFragment(Fragment fragment, boolean clearBackStack, boolean isFirstFrag) {


        if (clearBackStack) {
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStack();
            }

        }
        if (isFirstFrag)
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        else
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

    }


}
