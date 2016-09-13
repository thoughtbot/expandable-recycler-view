package com.thoughtbot.expandablerecyclerview.sample.multitypeandcheck;

import android.view.View;
import android.widget.TextView;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class FavoriteBandViewHolder extends GroupViewHolder {

  private TextView bandName;

  public FavoriteBandViewHolder(View itemView) {
    super(itemView);
    bandName = (TextView) itemView.findViewById(R.id.list_item_band_name);
  }

  public void setBandName(ExpandableGroup group) {
    bandName.setText(group.getTitle());
  }
}
