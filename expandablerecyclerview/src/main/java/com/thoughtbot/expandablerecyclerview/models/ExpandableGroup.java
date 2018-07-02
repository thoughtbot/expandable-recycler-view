package com.thoughtbot.expandablerecyclerview.models;

import java.util.List;

/**
 * The backing data object for an {@link Group}
 */
public class ExpandableGroup<T> implements Group<T> {
  private String title;
  private List<T> items;

  public ExpandableGroup(String title, List<T> items) {
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
    return "ExpandableGroup{" +
        "title='" + title + '\'' +
        ", items=" + items +
        '}';
  }
}
