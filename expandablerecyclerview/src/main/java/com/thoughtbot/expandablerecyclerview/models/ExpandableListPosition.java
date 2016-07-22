package com.thoughtbot.expandablerecyclerview.models;

import android.widget.ExpandableListView;
import java.util.ArrayList;

/**
 * Exact copy of android.widget.ExpandableListPosition because
 * android.widget.ExpandableListPosition has package local scope
 *
 *
 * ExpandableListPosition can refer to either a group's position or a child's
 * position. Referring to a child's position requires both a group position (the
 * group containing the child) and a child position (the child's position within
 * that group). To create objects, use {@link #obtainChildPosition(int, int)} or
 * {@link #obtainGroupPosition(int)}.
 */

public class ExpandableListPosition {

  private static final int MAX_POOL_SIZE = 5;
  private static ArrayList<ExpandableListPosition> sPool =
      new ArrayList<ExpandableListPosition>(MAX_POOL_SIZE);

  /**
   * This data type represents a child position
   */
  public final static int CHILD = 1;

  /**
   * This data type represents a group position
   */
  public final static int GROUP = 2;

  /**
   * The position of either the group being referred to, or the parent
   * group of the child being referred to
   */
  public int groupPos;

  /**
   * The position of the child within its parent group
   */
  public int childPos;

  /**
   * The position of the item in the flat list (optional, used internally when
   * the corresponding flat list position for the group or child is known)
   */
  int flatListPos;

  /**
   * What type of position this ExpandableListPosition represents
   */
  public int type;

  private void resetState() {
    groupPos = 0;
    childPos = 0;
    flatListPos = 0;
    type = 0;
  }

  private ExpandableListPosition() {
  }

  public long getPackedPosition() {
    if (type == CHILD) {
      return ExpandableListView.getPackedPositionForChild(groupPos, childPos);
    } else {
      return ExpandableListView.getPackedPositionForGroup(groupPos);
    }
  }

  static ExpandableListPosition obtainGroupPosition(int groupPosition) {
    return obtain(GROUP, groupPosition, 0, 0);
  }

  static ExpandableListPosition obtainChildPosition(int groupPosition, int childPosition) {
    return obtain(CHILD, groupPosition, childPosition, 0);
  }

  static ExpandableListPosition obtainPosition(long packedPosition) {
    if (packedPosition == ExpandableListView.PACKED_POSITION_VALUE_NULL) {
      return null;
    }

    ExpandableListPosition elp = getRecycledOrCreate();
    elp.groupPos = ExpandableListView.getPackedPositionGroup(packedPosition);
    if (ExpandableListView.getPackedPositionType(packedPosition) ==
        ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
      elp.type = CHILD;
      elp.childPos = ExpandableListView.getPackedPositionChild(packedPosition);
    } else {
      elp.type = GROUP;
    }
    return elp;
  }

  public static ExpandableListPosition obtain(int type, int groupPos, int childPos,
      int flatListPos) {
    ExpandableListPosition elp = getRecycledOrCreate();
    elp.type = type;
    elp.groupPos = groupPos;
    elp.childPos = childPos;
    elp.flatListPos = flatListPos;
    return elp;
  }

  private static ExpandableListPosition getRecycledOrCreate() {
    ExpandableListPosition elp;
    synchronized (sPool) {
      if (sPool.size() > 0) {
        elp = sPool.remove(0);
      } else {
        return new ExpandableListPosition();
      }
    }
    elp.resetState();
    return elp;
  }

  /**
   * Do not call this unless you obtained this via ExpandableListPosition.obtain().
   * PositionMetadata will handle recycling its own children.
   */
  public void recycle() {
    synchronized (sPool) {
      if (sPool.size() < MAX_POOL_SIZE) {
        sPool.add(this);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ExpandableListPosition that = (ExpandableListPosition) o;

    if (groupPos != that.groupPos) return false;
    if (childPos != that.childPos) return false;
    if (flatListPos != that.flatListPos) return false;
    return type == that.type;

  }

  @Override
  public int hashCode() {
    int result = groupPos;
    result = 31 * result + childPos;
    result = 31 * result + flatListPos;
    result = 31 * result + type;
    return result;
  }

  @Override
  public String toString() {
    return "ExpandableListPosition{" +
        "groupPos=" + groupPos +
        ", childPos=" + childPos +
        ", flatListPos=" + flatListPos +
        ", type=" + type +
        '}';
  }
}

