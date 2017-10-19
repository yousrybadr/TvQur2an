package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.network.listeneres.OnPressVidio;
import com.pentavalue.tvquran.model.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by OmniaGamil on 5/22/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.CustomViewHolder> {

    private List<Video> list;
    private Context context;
    OnPressVidio onPressVidio;
    public VideoAdapter(Context context, List<Video> list ,OnPressVidio onPressVidio) {
        this.context = context;
        this.list = list;
        this.onPressVidio=onPressVidio;
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.CustomViewHolder holder, final int position) {
        final Video entity = (Video) list.get(position);
        holder.vieoName.setText(entity.getTitle());
        holder.numOfLikes.setText(String.valueOf(entity.getUp_votes()));
        holder.numOfListen.setText(String.valueOf(entity.getViews_count()));
        holder.calender.setText(entity.getCreated_at());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPressVidio.OnPressClick(position,entity);
            }
        });
        Picasso.with(context).load(entity.getReciter_photo()).into(holder.videoView);
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public VideoAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_videos_items, parent, false);
        return new VideoAdapter.CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.videoView)
        ImageView videoView;
        @Bind(R.id.vieoName)
        TextView vieoName;

        @Bind(R.id.numOfListen)
        TextView numOfListen;
        @Bind(R.id.numOfLikes)
        TextView numOfLikes;
        @Bind(R.id.calender)
        TextView calender;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
