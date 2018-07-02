package com.thoughtbot.expandablerecyclerview.listeners;

import com.thoughtbot.expandablerecyclerview.models.Group;

public interface GroupExpandCollapseListener {

  /**
   * Called when a group is expanded
   * @param group the {@link Group} being expanded
   */
  void onGroupExpanded(Group group);

  /**
   * Called when a group is collapsed
   * @param group the {@link Group} being collapsed
   */
  void onGroupCollapsed(Group group);
}
