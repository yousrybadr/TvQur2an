package com.pentavalue.tvquran.model;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.List;

/**
 * Created by OmniaGamil on 5/22/2017.
 */

public class SectionHeaderRewayatOrder implements Section<Reciters> {
    List<Reciters> childList;
    String sectionText;
    int numOFSorah;

    public SectionHeaderRewayatOrder(List<Reciters> childList, String sectionText,int numOFSorah) {
        this.childList = childList;
        this.sectionText = sectionText;
        this.numOFSorah=numOFSorah;
    }

    @Override
    public List<Reciters> getChildItems() {
        return childList;
    }

    public List<Reciters> getChildList() {
        return childList;
    }

    public void setChildList(List<Reciters> childList) {
        this.childList = childList;
    }

    public String getSectionText() {
        return sectionText;
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
}
