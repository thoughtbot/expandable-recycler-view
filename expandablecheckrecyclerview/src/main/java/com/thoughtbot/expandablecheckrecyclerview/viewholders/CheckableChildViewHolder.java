package com.thoughtbot.expandablecheckrecyclerview.viewholders;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Checkable;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * An instance of ChildViewHolder that has a Checkable widget so that this view may
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
   * @param flatPos the raw index of this CheckableChildViewHolder in the
   * RecyclerView
   * @param checked the state to set the  Checkable widget to
   * see ChildCheckController#isChildChecked(ExpandableListPosition)
   */
  public void onBindViewHolder(int flatPos, boolean checked) {
    checkable = getCheckable();
    checkable.setChecked(checked);
  }

  @Override
  public void onClick(View v) {
    checkable.toggle();
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