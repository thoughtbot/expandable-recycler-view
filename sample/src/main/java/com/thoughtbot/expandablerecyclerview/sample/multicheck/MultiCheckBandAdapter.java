package com.thoughtbot.expandablerecyclerview.sample.multicheck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.sample.Song;
import com.thoughtbot.expandablerecyclerview.sample.expand.BandViewHolder;
import java.util.List;

public class MultiCheckBandAdapter extends
    CheckableChildRecyclerViewAdapter<BandViewHolder, MultiCheckSongViewHolder> {

  public MultiCheckBandAdapter(List<MultiCheckBand> groups) {
    super(groups);
  }

  @Override
  public MultiCheckSongViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_multicheck_song, parent, false);
    return new MultiCheckSongViewHolder(view);
  }

  @Override
  public void onBindCheckChildViewHolder(MultiCheckSongViewHolder holder, int position,
      CheckedExpandableGroup group, int childIndex) {
    final Song song = (Song) group.getItems().get(childIndex);
    holder.setSongName(song.getName());
  }

  @Override
  public BandViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_band, parent, false);
    return new BandViewHolder(view);
  }

  @Override
  public void onBindGroupViewHolder(BandViewHolder holder, int flatPosition,
      ExpandableGroup group) {
    holder.setBandName(group);
  }
}
