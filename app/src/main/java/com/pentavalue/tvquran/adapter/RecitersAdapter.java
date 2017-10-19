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
 * Created by OmniaGamil on 5/2/2017.
 */

public class RecitersAdapter extends RecyclerView.Adapter<RecitersAdapter.CustomViewHolder> {

    private List<Reciters> list;
    private Context context;
OnReaderClick onReaderClick;
    public RecitersAdapter(Context context, List<Reciters> list,OnReaderClick onReaderClick) {
        this.context = context;
        this.list = list;
        this.onReaderClick=onReaderClick;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        final Reciters entity = (Reciters) list.get(position);
        holder.rectirsName.setText(entity.getName());
       // holder.profile_image.setImageBitmap(entity.getReciterImage());
        Picasso.with(context)
                .load(entity.getReciterImage())
                .placeholder(R.drawable.emosad)
                .error(R.drawable.emosad)
                .into(holder.profile_image);
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
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reciters, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rectirsName)
        TextView rectirsName;
        @Bind(R.id.profile_image)
        ImageView profile_image;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
