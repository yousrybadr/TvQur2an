package com.pentavalue.tvquran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.model.MapModel;
import com.pentavalue.tvquran.network.listeneres.OnReaderClick;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.ui.fragments.ReaderProfileFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by OmniaGamil on 5/24/2017.
 */

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.CustomViewHolder> {

    private ArrayList<MapModel> list;
    private Context context;
    OnReaderClick onReaderClick;
    public NameAdapter(Context context, ArrayList<MapModel> list, OnReaderClick onReaderClick) {
        this.context = context;
        this.list = list;
        this.onReaderClick=onReaderClick;
    }

    @Override
    public void onBindViewHolder(final NameAdapter.CustomViewHolder holder, final int position) {
      //  final SearchName entity = (SearchName) list.get(position);

        holder.rectirsName.setText(list.get(position).getValue());
        holder.rectirsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO go to profile info

                ((ParentActivity)context).replaceFragment(ReaderProfileFragment.newInstance(Integer.parseInt(list.get(position).getKey())),false,false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public NameAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search_name, parent, false);
        return new NameAdapter.CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rectirsName)
        TextView rectirsName;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
