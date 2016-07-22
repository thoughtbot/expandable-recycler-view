package com.thoughtbot.expandablerecyclerview.sample.expand;

import android.view.View;
import android.widget.TextView;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class SongViewHolder extends ChildViewHolder {

  private TextView childTextView;

  public SongViewHolder(View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.list_item_song_name);
  }

  public void setSongName(String name) {
    childTextView.setText(name);
  }
}
