package com.thoughtbot.expandablerecyclerview.sample;

import com.thoughtbot.expandablerecyclerview.sample.multicheck.MultiCheckBand;
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckBand;
import java.util.ArrayList;
import java.util.List;

public class BandFactory {

  public static List<Band> makeBands() {
    ArrayList list = new ArrayList();
    for (int i = 0; i < 6; i++) {
      list.add(new Band("Band " + i, makeSongs()));
    }
    return list;
  }

  public static List<Song> makeSongs() {
    ArrayList list = new ArrayList();
    for (int i = 0; i < 3; i++) {
      list.add(new Song("Song: " + i, i % 2 == 0));
    }
    return list;
  }

  public static List<MultiCheckBand> makeMultiCheckBands() {
    ArrayList list = new ArrayList();
    for (int i = 0; i < 6; i++) {
      list.add(new MultiCheckBand("Band " + i, makeSongs()));
    }
    return list;
  }

  public static List<SingleCheckBand> makeSingleCheckBands() {
    ArrayList list = new ArrayList();
    for (int i = 0; i < 6; i++) {
      list.add(new SingleCheckBand("Band " + i, makeSongs()));
    }
    return list;
  }
}
