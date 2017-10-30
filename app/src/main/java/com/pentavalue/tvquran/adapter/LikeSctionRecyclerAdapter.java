package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;
import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.application.ApplicationController;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.model.HeaderModel;
import com.pentavalue.tvquran.recivers.DownloadReceiver;
import com.pentavalue.tvquran.service.DownloadService;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.ui.fragments.DisplayCompleteListFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Passant on 7/13/2017.
 */

public class LikeSctionRecyclerAdapter extends SectionRecyclerViewAdapter<HeaderModel, Entries,
        LikeSctionRecyclerAdapter.SectionViewHolder, LikeSctionRecyclerAdapter.ChildViewHolder> {



    Context context;
    List<HeaderModel> sectionItemList;
    android.os.Handler handler;

    public LikeSctionRecyclerAdapter(Context context, List<HeaderModel> sectionItemList) {
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
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int i) {
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
    public void onBindChildViewHolder(final ChildViewHolder childViewHolder, final int sectionPosition, final int childPosition, Entries entries) {
        childViewHolder.rectirsName.setText(entries.getReciter_name());


        final Runnable mUpdateDownloadProgressBar = new Runnable() {
            @Override
            public void run() {
                if (DownloadService.isDownloading && DownloadReceiver.Down_progress < 100) {
                    Log.i("BOB", "" + DownloadReceiver.Down_progress);
                    childViewHolder.downloadprogress.setProgress(DownloadReceiver.Down_progress);
                    notifyDataSetChanged();
                    handler.post(this);
                } else {
                    childViewHolder.downloadprogress.setVisibility(View.GONE);
                }
            }
        };
        handler = new android.os.Handler();
        childViewHolder.sorahName.setText(entries.getTitle());
        childViewHolder.sorahDesc.setText(entries.getCategory_desc());
        childViewHolder.numOfLikes.setText(entries.getUpVotes() + "");
        childViewHolder.numOfListen.setText(entries.getViews_count() + "");
        Picasso.with(context).load(entries.getReciter_photo()).placeholder(R.drawable.emosad)
                .error(R.drawable.emosad).into(childViewHolder.profile_image);
        if (sectionPosition == 0) {
            childViewHolder.downloadprogress.setVisibility(View.GONE);
        } else if (sectionPosition == 1) {
            if (DownloadService.isDownloading) {
                if (childPosition == 0) {
                    childViewHolder.downloadprogress.setVisibility(View.VISIBLE);
                    //  childViewHolder.downloadprogress.setProgress(0);
                    ApplicationController.getInstance().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.post(mUpdateDownloadProgressBar);
                        }
                    });
                } else {
                    childViewHolder.downloadprogress.setVisibility(View.GONE);

                }

            }

        }

        childViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(context,"click on position "+childPosition,Toast.LENGTH_SHORT).show();
                if (sectionItemList.get(sectionPosition).getTempdList().get(childPosition).getSoundPath() != null ||
                        !sectionItemList.get(sectionPosition).getTempdList().get(childPosition).getSoundPath().equals(""))
                    ((ParentActivity) context).showPlayerAndPlaySound(sectionItemList.get(sectionPosition).getTempdList(), childPosition, 1); //+1 cause header

            }
        });

//        notifyDataSetChanged();
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
}
