package com.thoughtbot.expandablerecyclerview.models;

import java.util.List;

/**
 * The backing interface for data object.
 */
public interface Group<T> {

  String getTitle();

  List<T> getItems();

  int getItemCount();
}
