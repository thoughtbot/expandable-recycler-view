package com.thoughtbot.expandablecheckrecyclerview.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import com.thoughtbot.expandablecheckrecyclerview.ChildCheckController;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * An instance of {@link ChildViewHolder} that has a {@link Checkable} widget so that this view may
 * have a checked and unchecked state
 */
public abstract class CheckableChildViewHolder extends ChildViewHolder implements OnClickListener {

  private OnChildCheckChangedListener listener;
  private Checkable checkable;

  public CheckableChildViewHolder(View itemView) {
    super(itemView);
    itemView.setOnClickListener(this);
  }

  /**
   * @param flatPos the raw index of this {@link CheckableChildViewHolder} in the {@link
   * RecyclerView}
   * @param checked the state to set the {@link Checkable} widget to
   * @see {@link ChildCheckController#isChildChecked(ExpandableListPosition)}
   */
  public void onBindViewHolder(int flatPos, boolean checked) {
    checkable = getCheckable();
    checkable.setChecked(checked);
  }

  @Override
  public void onClick(View v) {
    if (checkable instanceof CheckedTextView) {
      checkable.toggle();
    }
    if (listener != null) {
      listener.onChildCheckChanged(v, checkable.isChecked(), getAdapterPosition());
    }
  }

  public void setOnChildCheckedListener(OnChildCheckChangedListener listener) {
    this.listener = listener;
  }

  /**
   * Called during {@link #onBindViewHolder(int, boolean)}
   *
   * return a {@link Checkable} widget associated with this ViewHolder
   */
  public abstract Checkable getCheckable();
}