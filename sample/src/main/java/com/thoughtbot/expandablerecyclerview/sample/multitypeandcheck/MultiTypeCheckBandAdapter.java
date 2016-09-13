package com.thoughtbot.expandablerecyclerview.sample.multitypeandcheck;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thoughtbot.expandablecheckrecyclerview.ChildCheckController;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildrenCheckStateChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.sample.Song;
import com.thoughtbot.expandablerecyclerview.sample.expand.BandViewHolder;
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckBand;
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckSongViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

public class MultiTypeCheckBandAdapter
    extends MultiTypeExpandableRecyclerViewAdapter<GroupViewHolder, SingleCheckSongViewHolder>
    implements OnChildCheckChangedListener, OnChildrenCheckStateChangedListener {

  private static final String CHECKED_STATE_MAP = "child_check_controller_checked_state_map";

  public static final int FAVORITE_BAND_VIEW_TYPE = 3;
  public static final int REGULAR_BAND_VIEW_TYPE = 4;

  private ChildCheckController childCheckController;
  private OnCheckChildClickListener childClickListener;

  public MultiTypeCheckBandAdapter(List<? extends ExpandableGroup> groups) {
    super(groups);
    childCheckController = new ChildCheckController(expandableList, this);
  }

  @Override
  public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case FAVORITE_BAND_VIEW_TYPE:
        View favorite =
            from(parent.getContext()).inflate(R.layout.list_item_favorite_band, parent, false);
        return new FavoriteBandViewHolder(favorite);
      case REGULAR_BAND_VIEW_TYPE:
        View regular = from(parent.getContext()).inflate(R.layout.list_item_band, parent, false);
        return new BandViewHolder(regular);
      default:
        throw new IllegalArgumentException("Invalid viewType");
    }
  }

  @Override
  public SingleCheckSongViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_singlecheck_song, parent, false);
    SingleCheckSongViewHolder holder = new SingleCheckSongViewHolder(view);
    holder.setOnChildCheckedListener(this);
    return holder;
  }

  @Override
  public void onBindChildViewHolder(SingleCheckSongViewHolder holder, int flatPosition,
      ExpandableGroup group, int childIndex) {
    ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPosition);
    holder.onBindViewHolder(flatPosition, childCheckController.isChildChecked(listPosition));
    final Song song = (Song) group.getItems().get(childIndex);
    holder.setSongName(song.getName());
  }

  @Override
  public void onBindGroupViewHolder(GroupViewHolder holder, int flatPosition,
      ExpandableGroup group) {
    int viewType = getItemViewType(flatPosition);
    SingleCheckBand band = ((SingleCheckBand) group);
    switch (viewType) {
      case FAVORITE_BAND_VIEW_TYPE:
        ((FavoriteBandViewHolder) holder).setBandName(band);
        break;
      case REGULAR_BAND_VIEW_TYPE:
        ((BandViewHolder) holder).setBandName(band);
    }
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

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putParcelableArrayList(CHECKED_STATE_MAP,
        (ArrayList<? extends Parcelable>) expandableList.groups);
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    if (savedInstanceState == null || !savedInstanceState.containsKey(CHECKED_STATE_MAP)) {
      return;
    }
    expandableList.groups = savedInstanceState.getParcelableArrayList(CHECKED_STATE_MAP);
    super.onRestoreInstanceState(savedInstanceState);
  }

  public void clearChoices() {
    childCheckController.clearCheckStates();
    notifyDataSetChanged();
  }

  @Override
  public boolean isGroup(int viewType) {
    return viewType == FAVORITE_BAND_VIEW_TYPE || viewType == REGULAR_BAND_VIEW_TYPE;
  }

  @Override
  public int getGroupViewType(int position, ExpandableGroup group) {
    if (((SingleCheckBand) group).isFavorite()) {
      return FAVORITE_BAND_VIEW_TYPE;
    } else {
      return REGULAR_BAND_VIEW_TYPE;
    }
  }
}
