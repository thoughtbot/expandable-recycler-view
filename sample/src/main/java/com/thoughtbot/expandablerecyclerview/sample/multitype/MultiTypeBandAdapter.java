package com.thoughtbot.expandablerecyclerview.sample.multitype;

import android.view.View;
import android.view.ViewGroup;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.sample.Band;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.sample.Song;
import com.thoughtbot.expandablerecyclerview.sample.expand.BandViewHolder;
import com.thoughtbot.expandablerecyclerview.sample.expand.SongViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import java.util.List;

import static android.view.LayoutInflater.from;

public class MultiTypeBandAdapter
    extends MultiTypeExpandableRecyclerViewAdapter<BandViewHolder, ChildViewHolder> {

  public static final int TOP_HIT_VIEW_TYPE = 3;
  public static final int SONG_VIEW_TYPE = 4;

  public MultiTypeBandAdapter(List<Band> groups) {
    super(groups);
  }

  @Override
  public BandViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = from(parent.getContext())
        .inflate(R.layout.list_item_band, parent, false);
    return new BandViewHolder(view);
  }

  @Override
  public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case SONG_VIEW_TYPE:
        View song = from(parent.getContext()).inflate(R.layout.list_item_song, parent, false);
        return new SongViewHolder(song);
      case TOP_HIT_VIEW_TYPE:
        View topHit = from(parent.getContext()).inflate(R.layout.list_item_top_hit, parent, false);
        return new TopHitViewHolder(topHit);
      default:
        throw new IllegalArgumentException("Invalid viewType");
    }
  }

  @Override
  public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group,
      int childIndex) {
    int viewType = getItemViewType(flatPosition);
    Song song = ((Band) group).getItems().get(childIndex);
    switch (viewType) {
      case SONG_VIEW_TYPE:
        ((SongViewHolder) holder).setSongName(song.getName());
        break;
      case TOP_HIT_VIEW_TYPE:
        ((TopHitViewHolder) holder).setSongName(song.getName());
    }
  }

  @Override
  public void onBindGroupViewHolder(BandViewHolder holder, int flatPosition,
      ExpandableGroup group) {
    holder.setBandName(group);
  }

  @Override
  public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
    if (((Band) group).getItems().get(childIndex).isTopHit()) {
      return TOP_HIT_VIEW_TYPE;
    } else {
      return SONG_VIEW_TYPE;
    }
  }

  @Override
  public boolean isGroup(int viewType) {
    return viewType == ExpandableListPosition.GROUP;
  }

  @Override
  public boolean isChild(int viewType) {
    return viewType == TOP_HIT_VIEW_TYPE || viewType == SONG_VIEW_TYPE;
  }
}
