package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.pentavalue.tvquran.R;

import tellh.com.stickyheaderview_rv.adapter.ViewBinder;

/**
 * Created by Passant on 6/8/2017.
 */

public class ItemHeaderViewBinder /*extends ViewBinder<HeaderModel, ItemHeaderViewBinder.ViewHolder> */{

   // int totalCount;
    Context mContext;

    public ItemHeaderViewBinder(Context mContext) {
        this.mContext = mContext;
    }
/* public ItemHeaderViewBinder(int totalCount, Context mContext) {
        this.totalCount = totalCount;
        this.mContext = mContext;
    }*/

  /*  @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(StickyHeaderViewAdapter stickyHeaderViewAdapter, ViewHolder viewHolder, int i, final HeaderModel headerModel) {
        viewHolder.tvPrefix.setText(headerModel.getTitle());
        viewHolder.totalCount.setText(String.valueOf(headerModel.getTotalCount()));
        viewHolder.totalCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ApplicationController.getInstance().getApplicationContext(),"Toaaaa",Toast.LENGTH_SHORT).show();
                ((ParentActivity)mContext).replaceFragment(DisplayCompleteListFragment.newInstance(headerModel.getFavoriteList()),false,false);
            }
        });
    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter stickyHeaderViewAdapter) {
        return R.layout.section_header;
    }*/

    static class ViewHolder extends ViewBinder.ViewHolder {
        TextView tvPrefix;
        TextView totalCount;
        public ViewHolder(View rootView) {
            super(rootView);
            this.tvPrefix = (TextView) rootView.findViewById(R.id.readingType);
            totalCount=(TextView)rootView.findViewById(R.id.numOfSorahSection);
        }
    }
}
