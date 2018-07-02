package com.thoughtbot.expandablerecyclerview.testUtils;

import com.thoughtbot.expandablerecyclerview.models.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataFactory {

  public static List<Group> makeGroups() {
    ArrayList list = new ArrayList();
    for (int i = 0; i < 6; i++) {
      List items = Arrays.asList(i + ".0", i + ".1", i + ".2");
      list.add(new Group("Section " + i, items));
    }
    return list;
  }

}
