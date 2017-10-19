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
import com.pentavalue.tvquran.network.listeneres.OnReaderClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by OmniaGamil on 5/21/2017.
 */

public class SectionAlphabiticalRecyclerAdapter extends SectionRecyclerViewAdapter<SectionHeaderAlphabetical, DataAlphabtical, SectionAlphabiticalRecyclerAdapter.SectionViewHolder, SectionAlphabiticalRecyclerAdapter.ChildViewHolder> {

    Context context;
    ArrayList<SectionHeaderAlphabetical> sectionHeaderItemList;

    OnReaderClick onReaderClick;

    public SectionAlphabiticalRecyclerAdapter(Context context, List<SectionHeaderAlphabetical> sectionHeaderItemList, OnReaderClick onReaderClick) {

        super(context, sectionHeaderItemList);
        this.context = context;
        this.sectionHeaderItemList = (ArrayList<SectionHeaderAlphabetical>) sectionHeaderItemList;
        this.onReaderClick = onReaderClick;
    }

    public SectionAlphabiticalRecyclerAdapter(Context context, List<SectionHeaderAlphabetical> sectionHeaderItemList) {

        super(context, sectionHeaderItemList);
        this.context = context;
        this.sectionHeaderItemList= (ArrayList<SectionHeaderAlphabetical>) sectionHeaderItemList;
        this.onReaderClick = null;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_header, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_alphabitcal_child, childViewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeaderAlphabetical sectionHeader) {
        sectionViewHolder.readingType.setText(sectionHeader.getSectionText());
        sectionViewHolder.numOfSorahSection.setText(sectionHeader.getChildList().size()+"");
    }
    @Override
    public void onBindChildViewHolder(final ChildViewHolder childViewHolder, final int sectionPosition, final int childPosition, final DataAlphabtical child) {

        Picasso.with(context)
                .load(child.getPhoto())
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
                if (onReaderClick == null) {
                    return;
                }
                Reciters reciters = new Reciters(child.getName(), child.getPhoto());
                reciters.id = child.getId();
                onReaderClick.onClick(childPosition, reciters);
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
