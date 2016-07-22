package com.thoughtbot.expandablerecyclerview.listeners;

import android.support.v7.widget.RecyclerView;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public interface OnGroupClickListener {

  /**
   * @param flatPos the flat position (raw index within the list of visible items in the {@link
   * RecyclerView}) of a {@link GroupViewHolder}
   * @return true if click expanded group, false if click collapsed group
   */
  boolean onGroupClick(int flatPos);
}