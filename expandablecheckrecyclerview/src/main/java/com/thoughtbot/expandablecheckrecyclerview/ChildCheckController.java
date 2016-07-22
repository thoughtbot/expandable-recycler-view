package com.thoughtbot.expandablecheckrecyclerview;

import android.widget.Checkable;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildrenCheckStateChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;
import com.thoughtbot.expandablerecyclerview.models.ExpandableList;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;

public class ChildCheckController {

  private ExpandableList expandableList;
  private OnChildrenCheckStateChangedListener childrenUpdateListener;

  public ChildCheckController(ExpandableList expandableList,
      OnChildrenCheckStateChangedListener listener) {
    this.expandableList = expandableList;
    this.childrenUpdateListener = listener;
  }

  /**
   * Triggered by a click event on a {@link CheckableChildViewHolder} causing the {@link Checkable}
   * object to change checked states
   *
   * @param checked The current checked state of the view
   * @param listPosition The flat position (raw index) of the {@link CheckableChildViewHolder}
   */
  void onChildCheckChanged(boolean checked, ExpandableListPosition listPosition) {
    CheckedExpandableGroup group =
        (CheckedExpandableGroup) expandableList.groups.get(listPosition.groupPos);
    group.onChildClicked(listPosition.childPos, checked);
    if (childrenUpdateListener != null) {
      childrenUpdateListener.updateChildrenCheckState(
          expandableList.getFlattenedFirstChildIndex(listPosition),
          expandableList.getExpandableGroupItemCount(listPosition));
    }
  }

  /**
   * @param listPosition the {@link ExpandableListPosition} representation of a child list item
   * @return The current checked state of the view
   */
  boolean isChildChecked(ExpandableListPosition listPosition) {
    CheckedExpandableGroup group =
        (CheckedExpandableGroup) expandableList.groups.get(listPosition.groupPos);
    return group.isChildChecked(listPosition.childPos);
  }

  /**
   * Clear any choices previously checked
   */
  void clearCheckStates() {
    for (int i = 0; i < expandableList.groups.size(); i++) {
      CheckedExpandableGroup group = (CheckedExpandableGroup) expandableList.groups.get(i);
      group.clearSelections();
    }
  }
}
