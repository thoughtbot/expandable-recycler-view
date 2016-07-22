package com.thoughtbot.expandablerecyclerview.sample.multicheck;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;
import com.thoughtbot.expandablerecyclerview.sample.R;

public class MultiCheckSongViewHolder extends CheckableChildViewHolder {

  private CheckedTextView childCheckedTextView;

  public MultiCheckSongViewHolder(View itemView) {
    super(itemView);
    childCheckedTextView =
        (CheckedTextView) itemView.findViewById(R.id.list_item_multicheck_song_name);
  }

  @Override
  public Checkable getCheckable() {
    return childCheckedTextView;
  }

  public void setSongName(String songName) {
    childCheckedTextView.setText(songName);
  }
}
