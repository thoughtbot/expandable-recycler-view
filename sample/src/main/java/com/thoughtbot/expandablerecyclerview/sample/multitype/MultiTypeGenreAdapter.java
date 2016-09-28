package com.thoughtbot.expandablerecyclerview.sample.multitype;

import android.view.View;
import android.view.ViewGroup;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.sample.Artist;
import com.thoughtbot.expandablerecyclerview.sample.Genre;
import com.thoughtbot.expandablerecyclerview.sample.R;
import com.thoughtbot.expandablerecyclerview.sample.expand.ArtistViewHolder;
import com.thoughtbot.expandablerecyclerview.sample.expand.GenreViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import java.util.List;

import static android.view.LayoutInflater.from;

public class MultiTypeGenreAdapter
    extends MultiTypeExpandableRecyclerViewAdapter<GenreViewHolder, ChildViewHolder> {

  public static final int FAVORITE_VIEW_TYPE = 3;
  public static final int ARTIST_VIEW_TYPE = 4;

  public MultiTypeGenreAdapter(List<Genre> groups) {
    super(groups);
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
        View favorite =
            from(parent.getContext()).inflate(R.layout.list_item_favorite_artist, parent, false);
        return new FavoriteArtistViewHolder(favorite);
      default:
        throw new IllegalArgumentException("Invalid viewType");
    }
  }

  @Override
  public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group,
      int childIndex) {
    int viewType = getItemViewType(flatPosition);
    Artist artist = ((Genre) group).getItems().get(childIndex);
    switch (viewType) {
      case ARTIST_VIEW_TYPE:
        ((ArtistViewHolder) holder).setArtistName(artist.getName());
        break;
      case FAVORITE_VIEW_TYPE:
        ((FavoriteArtistViewHolder) holder).setArtistName(artist.getName());
    }
  }

  @Override
  public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition,
      ExpandableGroup group) {
    holder.setGenreTitle(group);
  }

  @Override
  public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
    if (((Genre) group).getItems().get(childIndex).isFavorite()) {
      return FAVORITE_VIEW_TYPE;
    } else {
      return ARTIST_VIEW_TYPE;
    }
  }

  @Override
  public boolean isGroup(int viewType) {
    return viewType == ExpandableListPosition.GROUP;
  }

  @Override
  public boolean isChild(int viewType) {
    return viewType == FAVORITE_VIEW_TYPE || viewType == ARTIST_VIEW_TYPE;
  }
}
