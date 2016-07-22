package com.thoughtbot.expandablerecyclerview.sample;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

public class Band extends ExpandableGroup<Song> {

  public Band(String title, List<Song> items) {
    super(title, items);
  }
}
