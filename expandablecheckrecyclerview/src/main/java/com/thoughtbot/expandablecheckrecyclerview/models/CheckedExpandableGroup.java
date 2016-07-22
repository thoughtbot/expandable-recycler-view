package com.thoughtbot.expandablecheckrecyclerview.models;

import android.os.Parcel;
import android.util.SparseBooleanArray;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

/**
 * An extension of {@link ExpandableGroup} that holds onto the checked state of it's children
 */
public abstract class CheckedExpandableGroup extends ExpandableGroup {

  public SparseBooleanArray selectedChildren;

  public CheckedExpandableGroup(String title, List items) {
    super(title, items);
    selectedChildren = new SparseBooleanArray();
    for (int i = 0; i < items.size(); i++) {
      selectedChildren.put(i, false);
    }
  }

  void checkChild(int childIndex) {
    selectedChildren.put(childIndex, true);
  }

  void unCheckChild(int childIndex) {
    selectedChildren.put(childIndex, false);
  }

  public boolean isChildChecked(int childIndex) {
    return selectedChildren.get(childIndex);
  }

  public void clearSelections() {
    if (selectedChildren != null) {
      selectedChildren.clear();
    }
  }

  protected CheckedExpandableGroup(Parcel in) {
    super(in);
    selectedChildren = in.readSparseBooleanArray();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeSparseBooleanArray(selectedChildren);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public abstract void onChildClicked(int childIndex, boolean checked);

}
