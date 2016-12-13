package com.thoughtbot.expandablerecyclerview.models;

import java.util.List;

/*
 * Terminology:
 * <li> flat position - Flat list position, the position of an item relative to all the
 * other *visible* items on the screen. For example, if you have a three groups, each with
 * 2 children and all are collapsed, the "flat position" of the last group would be 2. And if
 * the first of those three groups was expanded, the flat position of the last group would now be 4.
 *
 *
 * This class acts as a translator between the flat list position - i.e. what groups
 * and children you see on the screen - to and from the full backing list of groups & their children
 */
public class ExpandableList {

  public List<? extends ExpandableGroup> groups;
  public boolean[] expandedGroupIndexes;

  public ExpandableList(List<? extends ExpandableGroup> groups) {
    this.groups = groups;

    expandedGroupIndexes = new boolean[groups.size()];
    for (int i = 0; i < groups.size(); i++) {
      expandedGroupIndexes[i] = false;
    }
  }

  /**
   * @param group the index of the {@link ExpandableGroup} in the full collection {@link #groups}
   * @return the number of visible row items for the particular group. If the group is collapsed,
   * return 1 for the group header. If the group is expanded return total number of children in the
   * group + 1 for the group header
   */
  private int numberOfVisibleItemsInGroup(int group) {
    if (expandedGroupIndexes[group]) {
      return groups.get(group).getItemCount() + 1;
    } else {
      return 1;
    }
  }

  /**
   * @return the total number visible rows
   */
  public int getVisibleItemCount() {
    int count = 0;
    for (int i = 0; i < groups.size(); i++) {
      count += numberOfVisibleItemsInGroup(i);
    }
    return count;
  }

  /**
   * Translates a flat list position (the raw position of an item (child or group) in the list) to
   * either a) group pos if the specified flat list position corresponds to a group, or b) child
   * pos if it corresponds to a child.  Performs a binary search on the expanded
   * groups list to find the flat list pos if it is an exp group, otherwise
   * finds where the flat list pos fits in between the exp groups.
   *
   * @param flPos the flat list position to be translated
   * @return the group position or child position of the specified flat list
   * position encompassed in a {@link ExpandableListPosition} object
   * that contains additional useful info for insertion, etc.
   */
  public ExpandableListPosition getUnflattenedPosition(int flPos) {
    int groupItemCount;
    int adapted = flPos;
    for (int i = 0; i < groups.size(); i++) {
      groupItemCount = numberOfVisibleItemsInGroup(i);
      if (adapted == 0) {
        return ExpandableListPosition.obtain(ExpandableListPosition.GROUP, i, -1, flPos);
      } else if (adapted < groupItemCount) {
        return ExpandableListPosition.obtain(ExpandableListPosition.CHILD, i, adapted - 1, flPos);
      }
      adapted -= groupItemCount;
    }
    throw new RuntimeException("Unknown state");
  }

  /**
   * @param listPosition representing either a child or a group
   * @return the index of a group within the {@link #getVisibleItemCount()}
   */
  public int getFlattenedGroupIndex(ExpandableListPosition listPosition) {
    int groupIndex = listPosition.groupPos;
    int runningTotal = 0;

    for (int i = 0; i < groupIndex; i++) {
      runningTotal += numberOfVisibleItemsInGroup(i);
    }
    return runningTotal;
  }

  /**
   * @param groupIndex representing the index of a group within {@link #groups}
   * @return the index of a group within the {@link #getVisibleItemCount()}
   */
  public int getFlattenedGroupIndex(int groupIndex) {
    int runningTotal = 0;

    for (int i = 0; i < groupIndex; i++) {
      runningTotal += numberOfVisibleItemsInGroup(i);
    }
    return runningTotal;
  }

  /**
   * @param group an {@link ExpandableGroup} within {@link #groups}
   * @return the index of a group within the {@link #getVisibleItemCount()} or 0 if the
   * groups.indexOf cannot find the group
   */
  public int getFlattenedGroupIndex(ExpandableGroup group) {
    int groupIndex = groups.indexOf(group);
    int runningTotal = 0;

    for (int i = 0; i < groupIndex; i++) {
      runningTotal += numberOfVisibleItemsInGroup(i);
    }
    return runningTotal;
  }

  /**
   * Converts a child position to a flat list position.
   *
   * @param packedPosition The child positions to be converted in it's
   * packed position representation.
   * @return The flat list position for the given child
   */
  public int getFlattenedChildIndex(long packedPosition) {
    ExpandableListPosition listPosition = ExpandableListPosition.obtainPosition(packedPosition);
    return getFlattenedChildIndex(listPosition);
  }

  /**
   * Converts a child position to a flat list position.
   *
   * @param listPosition The child positions to be converted in it's
   * {@link ExpandableListPosition} representation.
   * @return The flat list position for the given child
   */
  public int getFlattenedChildIndex(ExpandableListPosition listPosition) {
    int groupIndex = listPosition.groupPos;
    int childIndex = listPosition.childPos;
    int runningTotal = 0;

    for (int i = 0; i < groupIndex; i++) {
      runningTotal += numberOfVisibleItemsInGroup(i);
    }
    return runningTotal + childIndex + 1;
  }

  /**
   * Converts the details of a child's position to a flat list position.
   *
   * @param groupIndex The index of a group within {@link #groups}
   * @param childIndex the index of a child within it's {@link ExpandableGroup}
   * @return The flat list position for the given child
   */
  public int getFlattenedChildIndex(int groupIndex, int childIndex) {
    int runningTotal = 0;

    for (int i = 0; i < groupIndex; i++) {
      runningTotal += numberOfVisibleItemsInGroup(i);
    }
    return runningTotal + childIndex + 1;
  }

  /**
   * @param groupIndex The index of a group within {@link #groups}
   * @return The flat list position for the first child in a group
   */
  public int getFlattenedFirstChildIndex(int groupIndex) {
    return getFlattenedGroupIndex(groupIndex) + 1;
  }

  /**
   * @param listPosition The child positions to be converted in it's
   * {@link ExpandableListPosition} representation.
   * @return The flat list position for the first child in a group
   */
  public int getFlattenedFirstChildIndex(ExpandableListPosition listPosition) {
    return getFlattenedGroupIndex(listPosition) + 1;
  }

  /**
   * @param listPosition An {@link ExpandableListPosition} representing either a child or group
   * @return the total number of children within the group associated with the @param listPosition
   */
  public int getExpandableGroupItemCount(ExpandableListPosition listPosition) {
    return groups.get(listPosition.groupPos).getItemCount();
  }

  /**
   * Translates either a group pos or a child pos to an {@link ExpandableGroup}.
   * If the {@link ExpandableListPosition} is a child position, it returns the {@link
   * ExpandableGroup} it belongs to
   *
   * @param listPosition a {@link ExpandableListPosition} representing either a group position
   * or child position
   * @return the {@link ExpandableGroup} object that contains the listPosition
   */
  public ExpandableGroup getExpandableGroup(ExpandableListPosition listPosition) {
    return groups.get(listPosition.groupPos);
  }
}
