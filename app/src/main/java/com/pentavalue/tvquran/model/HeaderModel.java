package com.pentavalue.tvquran.model;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Passant on 6/8/2017.
 */

public class HeaderModel implements Section<Entries> {


    String title;
    ArrayList<Entries> childList;
    ArrayList<Entries> tempdList;
    int totalCount;



    public ArrayList<Entries> getTempdList() {
        return tempdList;
    }

    public void setTempdList(ArrayList<Entries> tempdList) {
        this.tempdList = tempdList;
    }

    public ArrayList<Entries> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<Entries> childList) {
        this.childList = childList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    public HeaderModel(String title, int totalCount, ArrayList<Entries> tempList, ArrayList<Entries> mList) {
        this.title = title;
        this.totalCount = totalCount;
        this.childList = mList;
        this.tempdList = tempList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Entries> getChildItems() {
        if (tempdList!=null && tempdList.size() >0)
            return tempdList;
        else
        return childList;
    }
}
