package com.pentavalue.tvquran.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.network.listeneres.OnAudioPlayListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Passant on 6/8/2017.
 */

public class HistoryItemViewType /*extends ViewBinder<Entries,HistoryItemViewType.CustomViewHolder>*/ {

    OnAudioPlayListener onAudioPlayListener;

    public HistoryItemViewType(OnAudioPlayListener onAudioPlayListener) {
        this.onAudioPlayListener = onAudioPlayListener;
    }

  /*  @Override
    public CustomViewHolder provideViewHolder(View view) {
        return new CustomViewHolder(view);
    }

    @Override
    public void bindView(StickyHeaderViewAdapter stickyHeaderViewAdapter, CustomViewHolder holder, final int position, final  Entries entity) {
      //  final Entries entity = (Entries) list.get(position);
        holder.rectirsName.setText(entity.getReciter_name());
       *//* Picasso.with(context)
                .load(entity.getReciter_photo())
                .placeholder(R.drawable.emosad)
                .error(R.drawable.emosad)
                .into(holder.profile_image);*//*
        holder.sorahName.setText(entity.getTitle());
        holder.sorahDesc.setText(entity.getCategory_desc());
        holder.numOfLikes.setText(entity.getUpVotes()+"");
        holder.numOfListen.setText(entity.getViews_count()+"");
        if (DownloadService.isDownloading && entity.getIsHistory()!=1)
          holder. downloadprogress.setVisibility(View.VISIBLE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onAudioPlayListener.OnPlayTrack(position,entity);
//                ((ParentActivity)()).showPlayerAndPlaySound(dataList,position);

            }
        });

    }


    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter stickyHeaderViewAdapter) {
        return R.layout.list_item_sorted;
    }
*/
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rectirsName)
        TextView rectirsName;
        @Bind(R.id.rectirs_image)
        ImageView profile_image;
        @Bind(R.id.sorahName)
        TextView sorahName;
        @Bind(R.id.sorahDesc)
        TextView sorahDesc ;
        @Bind(R.id.numOfListen)
        TextView numOfListen;
        @Bind(R.id.numOfLikes)
        TextView numOfLikes;
        @Bind(R.id.downloadProgress)
        ProgressBar downloadprogress;
        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
