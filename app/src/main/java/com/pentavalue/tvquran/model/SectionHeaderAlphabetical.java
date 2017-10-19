package com.pentavalue.tvquran.model;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.List;

/**
 * Created by OmniaGamil on 5/21/2017.
 */

public class SectionHeaderAlphabetical implements Section<DataAlphabtical> {
    List<DataAlphabtical> childList;
    String sectionText;
    int numOFSorah;

    public SectionHeaderAlphabetical(List<DataAlphabtical> childList, String sectionText,int numOFSorah) {
        this.childList = childList;
        this.sectionText = sectionText;
        this.numOFSorah=numOFSorah;
    }

    @Override
    public List<DataAlphabtical> getChildItems() {
       return childList;
    }

    public List<DataAlphabtical> getChildList() {
        return childList;
    }

    public void setChildList(List<DataAlphabtical> childList) {
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
