package com.thoughtbot.expandablerecyclerview.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * The backing data object for an {@link Group}
 */
public class Group<T extends Parcelable> implements Group<T> {
  private String title;
  private List<T> items;

  public Group(String title, List<T> items) {
    this.title = title;
    this.items = items;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public List<T> getItems() {
    return items;
  }

  @Override
  public int getItemCount() {
    return items == null ? 0 : items.size();
  }

  @Override
  public String toString() {
    return "Group{" +
        "title='" + title + '\'' +
        ", items=" + items +
        '}';
  }

  protected Group(Parcel in) {
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
  }

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
  }

  @SuppressWarnings("unused")
  public static final Creator<Group> CREATOR =
      new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
          return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
          return new Group[size];
        }
      };
}
