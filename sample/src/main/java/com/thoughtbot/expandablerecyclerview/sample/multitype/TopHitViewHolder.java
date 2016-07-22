package com.thoughtbot.expandablerecyclerview.sample.multitype;

import android.view.View;
import android.widget.TextView;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class TopHitViewHolder extends ChildViewHolder {

  private TextView topHitName;

  public TopHitViewHolder(View itemView) {
    super(itemView);
    topHitName = (TextView) itemView.findViewById(R.id.list_item_top_hit_name);
  }

  public void setSongName(String name) {
    topHitName.setText("Top Hit: " + name);
  }

}
