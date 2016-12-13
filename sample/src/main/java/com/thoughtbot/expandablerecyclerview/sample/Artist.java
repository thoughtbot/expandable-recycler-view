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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Artist)) return false;

    Artist artist = (Artist) o;

    if (isFavorite() != artist.isFavorite()) return false;
    return getName() != null ? getName().equals(artist.getName()) : artist.getName() == null;

  }

  @Override
  public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 0;
    result = 31 * result + (isFavorite() ? 1 : 0);
    return result;
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

