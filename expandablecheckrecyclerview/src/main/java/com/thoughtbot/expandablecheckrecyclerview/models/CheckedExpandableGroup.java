package com.thoughtbot.expandablecheckrecyclerview.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An extension of ExpandableGroup that holds onto the checked state of it's children
 */
public abstract class CheckedExpandableGroup<T extends Parcelable> extends ExpandableGroup<T> implements Parcelable {

  public boolean[] selectedChildren;

  public CheckedExpandableGroup(String title, List<T> items) {
    super(title, items);
    selectedChildren = new boolean[items.size()];
    for (int i = 0; i < items.size(); i++) {
      selectedChildren[i] = false;
    }
  }

  public void checkChild(int childIndex) {
    selectedChildren[childIndex] = true;
  }

  public void unCheckChild(int childIndex) {
    selectedChildren[childIndex] = false;
  }

  public boolean isChildChecked(int childIndex) {
    return selectedChildren[childIndex];
  }

  public void clearSelections() {
    if (selectedChildren != null) {
      Arrays.fill(selectedChildren, false);
    }
  }

  public CheckedExpandableGroup(Parcel in) {
    title = in.readString();
    byte hasItems = in.readByte();
    int size = in.readInt();
    if (hasItems == 0x01) {
      items = new ArrayList<T>(size);
      Class<?> type = (Class<?>) in.readSerializable();
      in.readList(items, type.getClassLoader());
    } else {
      items = null;
    }
    selectedChildren = in.createBooleanArray();
  }

  public abstract void onChildClicked(int childIndex, boolean checked);

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    if (items == null) {
      dest.writeByte((byte) (0x00));
      dest.writeInt(0);
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeInt(items.size());
      final Class<?> objectsType = items.get(0).getClass();
      dest.writeSerializable(objectsType);
      dest.writeList(items);
    }
    dest.writeBooleanArray(selectedChildren);
  }

}
