package com.pentavalue.tvquran.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tvquran.R;

import java.util.ArrayList;

import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

/**
 * Created by Passant on 6/12/2017.
 */

public class SearchResultBean extends DataBean {

    @Expose
    @SerializedName("quran")
    ArrayList<Entries> quraanList;

    @Expose
    @SerializedName("selections")
    ArrayList<Entries> quraanSelectionList;

    @Expose
    @SerializedName("authors")
    ArrayList<AuthorWrapperModel> authorList;

    public ArrayList<Entries> getQuraanList() {
        return quraanList;
    }

    public void setQuraanList(ArrayList<Entries> quraanList) {
        this.quraanList = quraanList;
    }

    public ArrayList<Entries> getQuraanSelectionList() {
        return quraanSelectionList;
    }

    public void setQuraanSelectionList(ArrayList<Entries> quraanSelectionList) {
        this.quraanSelectionList = quraanSelectionList;
    }

    public ArrayList<AuthorWrapperModel> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(ArrayList<AuthorWrapperModel> authorList) {
        this.authorList = authorList;
    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter stickyHeaderViewAdapter) {
        return R.layout.list_item_sorted;
    }
}
