package com.pentavalue.tvquran.model;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.List;

/**
 * Created by OmniaGamil on 5/15/2017.
 */

public class SectionHeader implements Section<Entries> {

    List<Entries> childList;
    String sectionText;
    int numOFSorah;

    public SectionHeader(List<Entries> childList, String sectionText,int numOFSorah) {
        this.childList = childList;
        this.sectionText = sectionText;
        this.numOFSorah=numOFSorah;
    }

    public List<Entries> getChildList() {
        return childList;
    }

    public void setChildList(List<Entries> childList) {
        this.childList = childList;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }

    public int getNumOFSorah() {
        return numOFSorah;
    }

    public void setNumOFSorah(int numOFSorah) {
        this.numOFSorah = numOFSorah;
    }

    @Override
    public List<Entries> getChildItems() {
        return childList;
    }

    public String getSectionText() {
        return sectionText;
    }
}

