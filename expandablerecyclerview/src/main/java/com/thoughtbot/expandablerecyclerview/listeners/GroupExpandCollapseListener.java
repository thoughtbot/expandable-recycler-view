package com.thoughtbot.expandablerecyclerview.listeners;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

public interface GroupExpandCollapseListener {

  /**
   * Called when a group is expanded
   */
  void onGroupExpanded(ExpandableGroup group);

  /**
   * Called when a group is collapsed
   */
  void onGroupCollapsed(ExpandableGroup group);
}
