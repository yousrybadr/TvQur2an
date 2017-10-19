package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.network.listeneres.OnReaderClick;
import com.pentavalue.tvquran.model.Reciters;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rabie on 5/22/2017.
 */

public class RecitersSearchAdapter extends RecyclerView.Adapter<RecitersSearchAdapter.CustomViewHolder> {

    private List<Reciters> list;
    private Context context;
    OnReaderClick onReaderClick;
    public RecitersSearchAdapter(Context context, List<Reciters> list,OnReaderClick onReaderClick) {
        this.context = context;
        this.list = list;
        this.onReaderClick=onReaderClick;
    }

    @Override
    public void onBindViewHolder(final RecitersSearchAdapter.CustomViewHolder holder, final int position) {
        final Reciters entity = (Reciters) list.get(position);
        holder.name.setText(entity.getName());
        // holder.profile_image.setImageBitmap(entity.getReciterImage());
        Picasso.with(context)
                .load(entity.getReciterImage())
                .placeholder(R.drawable.emosad)
                .error(R.drawable.emosad)
                .into(holder.photo);
        holder.numOfLikes.setText(entity.getTotal_likes()+"");
        holder.numOfListen.setText(entity.getTotal_listened()+"");
        holder.numOfSorhaRead.setText(entity.getRecitations_count()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReaderClick.onClick(position,entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public RecitersSearchAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_alphabitcal_child, parent, false);
        return new RecitersSearchAdapter.CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
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
        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
