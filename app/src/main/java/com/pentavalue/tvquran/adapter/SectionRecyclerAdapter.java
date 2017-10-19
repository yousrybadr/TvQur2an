package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;
import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.SectionHeader;
import com.pentavalue.tvquran.ui.activities.ParentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by OmniaGamil on 5/15/2017.
 */

public class SectionRecyclerAdapter extends SectionRecyclerViewAdapter<SectionHeader, Entries, SectionRecyclerAdapter.SectionViewHolder, SectionRecyclerAdapter.ChildViewHolder> {

    Context context;
    HashMap ratePerQuestion= new HashMap();
    SharedPreferences pref;
    List<SectionHeader> sectionHeaderItemList;
    public SectionRecyclerAdapter(Context context, List<SectionHeader> sectionHeaderItemList) {


        super(context, sectionHeaderItemList);
        this.context = context;
        this.sectionHeaderItemList=sectionHeaderItemList;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_header, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_child, childViewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeader sectionHeader) {
       sectionViewHolder.readingType.setText(sectionHeader.getSectionText());
       sectionViewHolder.numOfSorahSection.setText(sectionHeader.getNumOFSorah()+"");
    }
    @Override
    public void onBindChildViewHolder(final ChildViewHolder childViewHolder, final int sectionPosition, final int childPosition, final Entries child) {

        final ArrayList<Entries> childList= (ArrayList<Entries>) sectionHeaderItemList.get(sectionPosition).getChildItems();
        childViewHolder.numOfSorahChild.setText(childPosition+1+"");
        childViewHolder.numOfLikes.setText(child.getViews_count()+"");
        childViewHolder.numOfListen.setText(child.getViews_count()+"");
        childViewHolder.sorahName.setText(child.getTitle());
        childViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* childViewHolder.checkbox.setChecked(true);
                child.setChecked(true);
                increaseVote(child.perantNode,child.childNode);
                descreaseVote();*/
             /*  itemclickListner.onClickInter(sectionPosition, childPosition, child, childViewHolder);*/
                ((ParentActivity)context).showPlayerAndPlaySound(childList,childPosition);
            }
        });
    }
    public static class SectionViewHolder extends RecyclerView.ViewHolder {
      @Bind(R.id.readingType)
      TextView readingType;
      @Bind(R.id.numOfSorahSection)
      TextView numOfSorahSection;

        public SectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public static class ChildViewHolder extends RecyclerView.ViewHolder {
       @Bind(R.id.numOfSorah)
       TextView numOfSorahChild;
       @Bind(R.id.sorahNameChild)
       TextView sorahName;
        @Bind(R.id.numOfListen)
        TextView numOfListen;
        @Bind(R.id.numOfLikes)
        TextView numOfLikes;
        public ChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

