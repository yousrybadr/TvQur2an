package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;
import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.model.DataAlphabtical;
import com.pentavalue.tvquran.model.Reciters;
import com.pentavalue.tvquran.model.SectionHeaderAlphabetical;
import com.pentavalue.tvquran.model.SectionHeaderRewayatOrder;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rabie on 5/22/2017.
 */

public class SectionCollectionRecyclerAdapter extends SectionRecyclerViewAdapter<SectionHeaderRewayatOrder, Reciters, SectionCollectionRecyclerAdapter.SectionViewHolder, SectionCollectionRecyclerAdapter.ChildViewHolder> {

    Context context;


    public SectionCollectionRecyclerAdapter(Context context, List<SectionHeaderRewayatOrder> sectionHeaderItemList) {

        super(context, sectionHeaderItemList);
        this.context = context;
    }

    @Override
    public SectionCollectionRecyclerAdapter.SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_header, sectionViewGroup, false);
        return new SectionCollectionRecyclerAdapter.SectionViewHolder(view);
    }

    @Override
    public SectionCollectionRecyclerAdapter.ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_alphabitcal_child, childViewGroup, false);
        return new SectionCollectionRecyclerAdapter.ChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionCollectionRecyclerAdapter.SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeaderRewayatOrder sectionHeader) {
        sectionViewHolder.readingType.setText(sectionHeader.getSectionText());
        sectionViewHolder.numOfSorahSection.setText(sectionHeader.getNumOFSorah()+"");
    }
    @Override
    public void onBindChildViewHolder(final SectionCollectionRecyclerAdapter.ChildViewHolder childViewHolder, final int sectionPosition, final int childPosition, final Reciters child) {
        Picasso.with(context)
                .load(child.getReciterImage())
                .placeholder(R.drawable.emosad)
                .error(R.drawable.emosad)
                .into(childViewHolder.photo);
        childViewHolder.numOfLikes.setText(child.getTotal_likes()+"");
        childViewHolder.numOfListen.setText(child.getTotal_listened()+"");
        childViewHolder.numOfSorhaRead.setText(child.getRecitations_count()+"");
        childViewHolder.name.setText(child.getName());
        childViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* childViewHolder.checkbox.setChecked(true);
                child.setChecked(true);
                increaseVote(child.perantNode,child.childNode);
                descreaseVote();*/
                //  itemclickListner.onClickInter(sectionPosition, childPosition, child, childViewHolder);
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
        @Bind(R.id.profile_image)
        ImageView photo;
        @Bind(R.id.profile_name)
        TextView name;
        @Bind(R.id.numOfSorhaRead)
        TextView numOfSorhaRead;

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
