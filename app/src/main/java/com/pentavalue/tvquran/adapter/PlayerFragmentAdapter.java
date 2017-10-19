package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.ui.fragments.PlayerFragment;

import java.util.ArrayList;

/**
 * Created by Passant on 6/24/2017.
 */

public class PlayerFragmentAdapter extends FragmentStatePagerAdapter {

    Context mContext;
    ArrayList<Entries> mList;

    public PlayerFragmentAdapter(FragmentManager fm,Context context,ArrayList<Entries> list) {
        super(fm);
        mContext=context;
        mList=list;
    }

    @Override
    public Fragment getItem(int position) {

        PlayerFragment fragment=new PlayerFragment();
        if (position != mList.size() - 1) {
            fragment.setData(mList.get(position) ,mList.get(position+1) );
         }
         else
        {
            fragment.setData(mList.get(position) ,null );

        }

        return fragment;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
//        notifyDataSetChanged();
        return mList.size();
    }
}
