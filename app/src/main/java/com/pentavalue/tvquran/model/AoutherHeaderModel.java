package com.pentavalue.tvquran.model;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Passant on 7/13/2017.
 */

public class AoutherHeaderModel   implements Section<AuthorWrapperModel>{

    ArrayList<AuthorWrapperModel> modelArrayList;
    String title;
    ArrayList<AuthorWrapperModel> tempdList;
    int totalCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<AuthorWrapperModel> getTempdList() {
        return tempdList;
    }

    public void setTempdList(ArrayList<AuthorWrapperModel> tempdList) {
        this.tempdList = tempdList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public AoutherHeaderModel(String title, ArrayList<AuthorWrapperModel> modelArrayList,
                              ArrayList<AuthorWrapperModel> tempdList, int totalCount) {
        this.modelArrayList = modelArrayList;
        this.title = title;
        this.tempdList = tempdList;
        this.totalCount = totalCount;
    }

    public ArrayList<AuthorWrapperModel> getModelArrayList() {
        return modelArrayList;
    }

    public void setModelArrayList(ArrayList<AuthorWrapperModel> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    @Override
    public List<AuthorWrapperModel> getChildItems() {
        return null;
    }





    /*public AoutherHeaderModel(String title, int totalCount, ArrayList<Entries> tempList, ArrayList<Entries> mList) {
        super(title, totalCount, tempList, mList);
    }
*/

}
