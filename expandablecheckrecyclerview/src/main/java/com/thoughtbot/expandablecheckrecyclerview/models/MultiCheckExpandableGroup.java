package com.thoughtbot.expandablecheckrecyclerview.models;

import android.os.Parcel;
import java.util.List;

/**
 * A subclass of {@link CheckedExpandableGroup} that allows for multiple children to be checked at
 * one time
 */
public class MultiCheckExpandableGroup extends CheckedExpandableGroup {

  public MultiCheckExpandableGroup(String title, List items) {
    super(title, items);
  }

  @Override
  public void onChildClicked(int childIndex, boolean checked) {
    if (checked) {
      checkChild(childIndex);
    } else {
      unCheckChild(childIndex);
    }
  }

  protected MultiCheckExpandableGroup(Parcel in) {
    super(in);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @SuppressWarnings("unused")
  public static final Creator<MultiCheckExpandableGroup> CREATOR =
      new Creator<MultiCheckExpandableGroup>() {
        @Override
        public MultiCheckExpandableGroup createFromParcel(Parcel in) {
          return new MultiCheckExpandableGroup(in);
        }

        @Override
        public MultiCheckExpandableGroup[] newArray(int size) {
          return new MultiCheckExpandableGroup[size];
        }
      };
}
