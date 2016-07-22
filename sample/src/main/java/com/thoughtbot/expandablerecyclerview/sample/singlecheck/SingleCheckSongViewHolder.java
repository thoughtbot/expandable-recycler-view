package com.thoughtbot.expandablerecyclerview.sample.singlecheck;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;
import com.thoughtbot.expandablerecyclerview.sample.R;

public class SingleCheckSongViewHolder extends CheckableChildViewHolder {

  private CheckedTextView childCheckedTextView;

  public SingleCheckSongViewHolder(View itemView) {
    super(itemView);
    childCheckedTextView =
        (CheckedTextView) itemView.findViewById(R.id.list_item_singlecheck_song_name);
  }

  @Override
  public Checkable getCheckable() {
    return childCheckedTextView;
  }

  public void setSongName(String songName) {
    childCheckedTextView.setText(songName);
  }
}
