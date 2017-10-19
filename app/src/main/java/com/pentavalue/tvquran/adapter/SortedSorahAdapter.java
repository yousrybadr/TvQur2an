package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.network.listeneres.OnAudioPlayListener;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.ui.fragments.ReaderProfileFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by OmniaGamil on 5/2/2017.
 */

public class SortedSorahAdapter extends RecyclerView.Adapter<SortedSorahAdapter.CustomViewHolder> {

    private List<Entries> list;
    private Context context;
OnAudioPlayListener onAudioPlayListener;
    public SortedSorahAdapter(Context context, List<Entries> list,OnAudioPlayListener onAudioPlayListener) {
        this.context = context;
        this.list = list;
        this.onAudioPlayListener=onAudioPlayListener;
    }

    @Override
    public void onBindViewHolder(final SortedSorahAdapter.CustomViewHolder holder, final int position) {
        final Entries entity = (Entries) list.get(position);
        holder.rectirsName.setText(entity.getReciter_name());
        Picasso.with(context)
                .load(entity.getReciter_photo())
                .placeholder(R.drawable.emosad)
                .error(R.drawable.emosad)
                .into(holder.profile_image);
        holder.sorahName.setText(entity.getTitle());
        holder.sorahDesc.setText(entity.getCategory_desc());
        holder.numOfLikes.setText(entity.getUpVotes()+"");
        holder.numOfListen.setText(entity.getViews_count()+"");
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onAudioPlayListener.OnPlayTrack(position,list);
                onAudioPlayListener.OnPlayTrack(position, (ArrayList<Entries>) list);
            }
        });
        holder.profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ParentActivity)context).replaceFragment(ReaderProfileFragment.newInstance(Integer.valueOf(entity.getAuthor_id())),false,false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public SortedSorahAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sorted, parent, false);
        return new SortedSorahAdapter.CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

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
        @Bind(R.id.itemLayout)
        LinearLayout itemLayout;
        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
