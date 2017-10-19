package com.pentavalue.tvquran.network.listeneres;

import com.pentavalue.tvquran.model.Entries;

import java.util.ArrayList;

/**
 * Created by OmniaGamil on 5/24/2017.
 */

public interface OnAudioPlayListener  {
    void OnPlayTrack(int Postion, Entries entries);
    void OnPlayTrack(int Postion, ArrayList<Entries> entries);
}
