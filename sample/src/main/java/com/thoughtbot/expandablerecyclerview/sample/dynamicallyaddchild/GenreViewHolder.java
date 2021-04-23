package com.thoughtbot.expandablerecyclerview.sample.dynamicallyaddchild;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.sample.Genre;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.sample.multicheck.MultiCheckGenre;
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckGenre;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class GenreViewHolder extends GroupViewHolder {

  private TextView genreName;
  private ImageView arrow;
  private ImageView icon;
  private ProgressBar progressBar;

  public GenreViewHolder(View itemView) {
    super(itemView);
    genreName = itemView.findViewById(R.id.list_item_genre_name);
    arrow = itemView.findViewById(R.id.list_item_genre_arrow);
    icon = itemView.findViewById(R.id.list_item_genre_icon);
    progressBar = itemView.findViewById(R.id.progress_circular);
  }

  public void setGenreTitle(ExpandableGroup genre) {
    if (genre instanceof Genre) {
      genreName.setText(genre.getTitle());
      icon.setBackgroundResource(((Genre) genre).getIconResId());
    }
    if (genre instanceof MultiCheckGenre) {
      genreName.setText(genre.getTitle());
      icon.setBackgroundResource(((MultiCheckGenre) genre).getIconResId());
    }
    if (genre instanceof SingleCheckGenre) {
      genreName.setText(genre.getTitle());
      icon.setBackgroundResource(((SingleCheckGenre) genre).getIconResId());
    }
  }

  public void showProgressBar() {
    progressBar.setVisibility(View.VISIBLE);
  }

  public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void expand() {
    animateExpand();
  }

  @Override
  public void collapse() {
    animateCollapse();
  }

  private void animateExpand() {
    RotateAnimation rotate =
        new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }

  private void animateCollapse() {
    RotateAnimation rotate =
        new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }
}
