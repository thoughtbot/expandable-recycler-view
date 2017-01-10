package com.thoughtbot.expandablerecyclerview.sample.multitypeandcheck;

import android.os.Bundle;
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
import com.thoughtbot.expandablerecyclerview.sample.Artist;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.sample.expand.ArtistViewHolder;
import com.thoughtbot.expandablerecyclerview.sample.expand.GenreViewHolder;
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckArtistViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

public class MultiTypeCheckGenreAdapter
    extends MultiTypeExpandableRecyclerViewAdapter<GenreViewHolder, ChildViewHolder>
    implements OnChildCheckChangedListener, OnChildrenCheckStateChangedListener {

  private static final String CHECKED_STATE_MAP = "child_check_controller_checked_state_map";

  public static final int FAVORITE_VIEW_TYPE = 3;
  public static final int ARTIST_VIEW_TYPE = 4;

  private ChildCheckController childCheckController;
  private OnCheckChildClickListener childClickListener;

  public MultiTypeCheckGenreAdapter(List<? extends ExpandableGroup> groups) {
    super(groups);
    childCheckController = new ChildCheckController(expandableList, this);
  }

  @Override
  public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = from(parent.getContext())
        .inflate(R.layout.list_item_genre, parent, false);
    return new GenreViewHolder(view);
  }

  @Override
  public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ARTIST_VIEW_TYPE:
        View artist = from(parent.getContext()).inflate(R.layout.list_item_artist, parent, false);
        return new ArtistViewHolder(artist);
      case FAVORITE_VIEW_TYPE:
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_item_singlecheck_arist, parent, false);
        SingleCheckArtistViewHolder holder = new SingleCheckArtistViewHolder(view);
        holder.setOnChildCheckedListener(this);
        return holder;
      default:
        throw new IllegalArgumentException(viewType + " is an Invalid viewType");
    }
  }

  @Override
  public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group,
      int childIndex) {
    int viewType = getItemViewType(flatPosition);
    Artist artist = (Artist) group.getItems().get(childIndex);
    switch (viewType) {
      case ARTIST_VIEW_TYPE:
        ((ArtistViewHolder) holder).setArtistName(artist.getName());
        break;
      case FAVORITE_VIEW_TYPE:
        ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPosition);
        ((SingleCheckArtistViewHolder) holder)
            .onBindViewHolder(flatPosition, childCheckController.isChildChecked(listPosition));
        ((SingleCheckArtistViewHolder) holder).setArtistName(artist.getName());
    }
  }

  @Override
  public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition,
      ExpandableGroup group) {
    holder.setGenreTitle(group);
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
    outState.putParcelableArrayList(CHECKED_STATE_MAP, new ArrayList(expandableList.groups));
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

    //only update the child views that are visible (i.e. their group is expanded)
    for (int i = 0; i < getGroups().size(); i++) {
      ExpandableGroup group = getGroups().get(i);
      if (isGroupExpanded(group)) {
        notifyItemRangeChanged(expandableList.getFlattenedFirstChildIndex(i), group.getItemCount());
      }
    }
  }

  @Override
  public boolean isChild(int viewType) {
    return viewType == FAVORITE_VIEW_TYPE || viewType == ARTIST_VIEW_TYPE;
  }

  @Override
  public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
    if (((Artist) (group).getItems().get(childIndex)).isFavorite()) {
      return FAVORITE_VIEW_TYPE;
    } else {
      return ARTIST_VIEW_TYPE;
    }
  }
}
