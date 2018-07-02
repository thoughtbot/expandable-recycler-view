package com.thoughtbot.expandablerecyclerview.models;

import android.os.Parcelable;

import java.util.List;

/**
 * The backing interface for data object.
 */
public interface Group<T extends Parcelable> extends Parcelable {

  String getTitle();

  List<T> getItems();

  int getItemCount();
}
