package com.thoughtbot.expandablecheckrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildrenCheckStateChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import java.util.ArrayList;
import java.util.List;

public abstract class CheckableChildRecyclerViewAdapter<GVH extends GroupViewHolder, CCVH extends CheckableChildViewHolder>
    extends ExpandableRecyclerViewAdapter<GVH, CCVH>
    implements OnChildCheckChangedListener, OnChildrenCheckStateChangedListener {

  private static final String CHECKED_STATE_MAP = "child_check_controller_checked_state_map";

  private ChildCheckController childCheckController;
  private OnCheckChildClickListener childClickListener;

  public CheckableChildRecyclerViewAdapter(List<? extends CheckedExpandableGroup> groups) {
    super(groups);
    childCheckController = new ChildCheckController(expandableList, this);
  }

  @Override
  public CCVH onCreateChildViewHolder(ViewGroup parent, int viewType) {
    CCVH CCVH = onCreateCheckChildViewHolder(parent, viewType);
    CCVH.setOnChildCheckedListener(this);
    return CCVH;
  }

  @Override
  public void onBindChildViewHolder(CCVH holder, int flatPosition, ExpandableGroup group,
      int childIndex) {
    ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPosition);
    holder.onBindViewHolder(flatPosition, childCheckController.isChildChecked(listPosition));
    onBindCheckChildViewHolder(holder, flatPosition, (CheckedExpandableGroup) group, childIndex);
  }

  @Override
  public void onChildCheckChanged(View view, boolean checked, int flatPos) {
    ExpandableListPosition listPos = expandableList.getUnflattenedPosition(flatPos);
    childCheckController.onChildCheckChanged(checked, listPos);
    if (childClickListener != null) {
      childClickListener.onCheckChildCLick(view, checked,
          (CheckedExpandableGroup) expandableList.getExpandableGroup(listPos), listPos.childPos);
    }
  }

  @Override
  public void updateChildrenCheckState(int firstChildFlattenedIndex, int numChildren) {
    notifyItemRangeChanged(firstChildFlattenedIndex, numChildren);
  }

  public void setChildClickListener(OnCheckChildClickListener listener) {
    childClickListener = listener;
  }

  /**
   * Stores the checked state map across state loss.
   * <p>
   * Should be called from whatever {@link Activity} that hosts the RecyclerView that {@link
   * CheckableChildRecyclerViewAdapter} is attached to.
   * <p>
   * This will make sure to add the checked state map as an extra to the
   * instance state bundle to be used in {@link #onRestoreInstanceState(Bundle)}.
   *
   * @param outState The {@code Bundle} into which to store the
   * chekced state map
   */
  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putParcelableArrayList(CHECKED_STATE_MAP, new ArrayList(expandableList.groups));
    super.onSaveInstanceState(outState);
  }

  /**
   * Fetches the checked state map from the saved instance state {@link Bundle}
   * and restores the checked states of all of the child list items.
   * <p>
   * Should be called from {@link Activity#onRestoreInstanceState(Bundle)}  in
   * the {@link Activity} that hosts the RecyclerView that this
   * {@link CheckableChildRecyclerViewAdapter} is attached to.
   * <p>
   *
   * @param savedInstanceState The {@code Bundle} from which the expanded
   * state map is loaded
   */
  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    if (savedInstanceState == null || !savedInstanceState.containsKey(CHECKED_STATE_MAP)) {
      return;
    }
    expandableList.groups = savedInstanceState.getParcelableArrayList(CHECKED_STATE_MAP);
    super.onRestoreInstanceState(savedInstanceState);
  }

  /**
   * Manually (programmatically) update the check state of a child
   *
   * @param checked the desired check state, true will check the item, false will uncheck it if
   * possible
   * @param groupIndex the index of the {@code ExpandableGroup} within {@code getGroups()}
   * @param childIndex the index of the child within it's group
   */
  public void checkChild(boolean checked, int groupIndex, int childIndex) {
    childCheckController.checkChild(checked, groupIndex, childIndex);
    if (childClickListener != null) {
      childClickListener.onCheckChildCLick(null, checked,
          (CheckedExpandableGroup) expandableList.groups.get(groupIndex), childIndex);
    }
  }

  /**
   * Clear any choices previously checked
   */
  public void clearChoices() {
    childCheckController.clearCheckStates();

    //only update the child views that are visible (i.e. their group is expanded)
    for (int i = 0; i < getGroups().size(); i++) {
      ExpandableGroup group = getGroups().get(i);
      if (isGroupExpanded(group)) {
        notifyItemRangeChanged(expandableList.getFlattenedFirstChildIndex(i), group.getItemCount());
      }
    }
  }

  /**
   * Called from #onCreateViewHolder(ViewGroup, int) when the list item created is a child
   *
   * @param parent the {@link ViewGroup} in the list for which a {@link CCVH}  is being created
   * @return A {@link CCVH} corresponding to child list item with the  {@code ViewGroup} parent
   */
  public abstract CCVH onCreateCheckChildViewHolder(ViewGroup parent, int viewType);

  /**
   * Called from onBindViewHolder(RecyclerView.ViewHolder, int) when the list item
   * bound to is a  child.
   * <p>
   * Bind data to the {@link CCVH} here.
   *
   * @param holder The {@code CCVH} to bind data to
   * @param flatPosition the flat position (raw index) in the list at which to bind the child
   * @param group The {@link CheckedExpandableGroup} that the the child list item belongs to
   * @param childIndex the index of this child within it's {@link CheckedExpandableGroup}
   */
  public abstract void onBindCheckChildViewHolder(CCVH holder, int flatPosition,
      CheckedExpandableGroup group, int childIndex);
}
