package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;
import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.HeaderModel;
import com.pentavalue.tvquran.service.DownloadService;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.ui.fragments.DisplayCompleteListFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Passant on 7/13/2017.
 */

public class SearchResultSctionRecyclerAdapter extends SectionRecyclerViewAdapter<HeaderModel, Entries,
        SearchResultSctionRecyclerAdapter.SectionViewHolder, SearchResultSctionRecyclerAdapter.ChildViewHolder> {


    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_ITEM = 1;
    Context context;
    List<HeaderModel> sectionItemList;
    int secPosition;

    public SearchResultSctionRecyclerAdapter(Context context, List<HeaderModel> sectionItemList) {
        super(context, sectionItemList);
        this.context = context;
        this.sectionItemList = sectionItemList;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.section_header, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_sorted, childViewGroup, false);
        return new ChildViewHolder(view);

    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int i, final HeaderModel headerModel) {
        sectionViewHolder.readingType.setText(headerModel.getTitle());
        sectionViewHolder.numOfSorahSection.setText(headerModel.getTotalCount() + "");
        sectionViewHolder.numOfSorahSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ParentActivity) context).replaceFragment(DisplayCompleteListFragment.newInstance(headerModel.getChildList()), false, false);
            }
        });
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, final int sectionPosition, final int childPosition, Entries entries) {
        childViewHolder.rectirsName.setText(entries.getReciter_name());
       /* Picasso.with(context)
                .load(entity.getReciter_photo())
                .placeholder(R.drawable.emosad)
                .error(R.drawable.emosad)
                .into(holder.profile_image);*/
        secPosition = sectionPosition;
        childViewHolder.sorahName.setText(entries.getTitle());
        childViewHolder.sorahDesc.setText(entries.getCategory_desc());
        childViewHolder.numOfLikes.setText(entries.getUpVotes() + "");
        childViewHolder.numOfListen.setText(entries.getViews_count() + "");
        if (DownloadService.isDownloading && entries.getIsHistory() != 1)
            childViewHolder.downloadprogress.setVisibility(View.VISIBLE);
        childViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context,"click on position "+childPosition,Toast.LENGTH_SHORT).show();
                if (sectionItemList.get(sectionPosition).getTempdList().get(childPosition).getSoundPath() != null ||
                        !sectionItemList.get(sectionPosition).getTempdList().get(childPosition).getSoundPath().equals(""))
                    ((ParentActivity) context).showPlayerAndPlaySound(sectionItemList.get(sectionPosition).getTempdList(), childPosition, 1); //+1 cause header

            }
        });
    }

    private boolean isPositionFooter(int position) {
        return position == sectionItemList.get(secPosition).getTempdList().size() + 1;
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
        @Bind(R.id.rectirsName)
        TextView rectirsName;
        @Bind(R.id.rectirs_image)
        ImageView profile_image;
        @Bind(R.id.sorahName)
        TextView sorahName;
        @Bind(R.id.sorahDesc)
        TextView sorahDesc;
        @Bind(R.id.numOfListen)
        TextView numOfListen;
        @Bind(R.id.numOfLikes)
        TextView numOfLikes;
        @Bind(R.id.downloadProgress)
        ProgressBar downloadprogress;
        @Bind(R.id.itemLayout)
        LinearLayout itemLayout;

        public ChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

   /* @Override
    public int getItemViewType(int position) {
        if(isPositionFooter (position)) {
            return TYPE_FOOTER;
        }
            else
        return TYPE_ITEM;
    }*/
}
