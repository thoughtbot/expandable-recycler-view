package com.thoughtbot.expandablerecyclerview.sample;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {

  private String name;
  private boolean isFavorite;

  public Artist(String name, boolean isFavorite) {
    this.name = name;
    this.isFavorite = isFavorite;
  }

  protected Artist(Parcel in) {
    name = in.readString();
  }

  public String getName() {
    return name;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Artist> CREATOR = new Creator<Artist>() {
    @Override
    public Artist createFromParcel(Parcel in) {
      return new Artist(in);
    }

    @Override
    public Artist[] newArray(int size) {
      return new Artist[size];
    }
  };
}

