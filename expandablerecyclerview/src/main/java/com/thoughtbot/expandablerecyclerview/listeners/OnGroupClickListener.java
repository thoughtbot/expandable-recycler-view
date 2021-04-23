package com.thoughtbot.expandablerecyclerview.listeners;

public interface OnGroupClickListener<O> {

  /**
   * @param flatPos the flat position (raw index within the list of visible items in the
   * RecyclerView of a GroupViewHolder)
   * @return false if click expanded group, true if click collapsed group
   */
  boolean onGroupClick(int flatPos);

  /**
   * @param flatPos the flat position (raw index within the list of visible items in the
   * RecyclerView of a GroupViewHolder)
   * @param group group item object
   */
  void onGroupItemClick(O group, int flatPos);
}