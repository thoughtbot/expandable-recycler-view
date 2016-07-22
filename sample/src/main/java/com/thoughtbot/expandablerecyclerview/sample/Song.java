package com.thoughtbot.expandablerecyclerview.sample;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

  private String name;
  private boolean isTopHit;

  public Song(String name, boolean isTopHit) {
    this.name = name;
    this.isTopHit = isTopHit;
  }

  public String getName() {
    return name;
  }

  public boolean isTopHit() {
    return isTopHit;
  }

  protected Song(Parcel in) {
    name = in.readString();
    isTopHit = in.readByte() != 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeByte((byte) (isTopHit ? 1 : 0));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Song> CREATOR = new Creator<Song>() {
    @Override
    public Song createFromParcel(Parcel in) {
      return new Song(in);
    }

    @Override
    public Song[] newArray(int size) {
      return new Song[size];
    }
  };
}
