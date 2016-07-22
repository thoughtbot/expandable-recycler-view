package com.thoughtbot.expandablerecyclerview.sample.expand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.sample.Band;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.sample.Song;
import java.util.List;

public class BandAdapter extends ExpandableRecyclerViewAdapter<BandViewHolder, SongViewHolder> {

  public BandAdapter(List<? extends ExpandableGroup> groups) {
    super(groups);
  }

  @Override
  public BandViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_band, parent, false);
    return new BandViewHolder(view);
  }

  @Override
  public SongViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_song, parent, false);
    return new SongViewHolder(view);
  }

  @Override
  public void onBindChildViewHolder(SongViewHolder holder, int flatPosition,
      ExpandableGroup group, int childIndex) {

    final Song song = ((Band) group).getItems().get(childIndex);
    holder.setSongName(song.getName());
  }

  @Override
  public void onBindGroupViewHolder(BandViewHolder holder, int flatPosition, ExpandableGroup group) {

    holder.setBandName(group);
  }
}
