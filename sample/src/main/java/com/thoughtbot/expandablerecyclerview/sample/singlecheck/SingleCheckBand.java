package com.thoughtbot.expandablerecyclerview.sample.singlecheck;

import android.os.Parcel;
import com.thoughtbot.expandablecheckrecyclerview.models.SingleCheckExpandableGroup;
import java.util.List;

public class SingleCheckBand extends SingleCheckExpandableGroup {
  private boolean favorite;

  public SingleCheckBand(String title, List items, boolean favorite) {
    super(title, items);
    this.favorite = favorite;
  }

  protected SingleCheckBand(Parcel in) {
    super(in);
    favorite = in.readByte() != 0;
  }

  public boolean isFavorite() {
    return favorite;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeByte((byte) (favorite ? 1 : 0));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<SingleCheckBand> CREATOR = new Creator<SingleCheckBand>() {
    @Override
    public SingleCheckBand createFromParcel(Parcel in) {
      return new SingleCheckBand(in);
    }

    @Override
    public SingleCheckBand[] newArray(int size) {
      return new SingleCheckBand[size];
    }
  };
}
