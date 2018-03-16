![logo](https://images.thoughtbot.com/blog-vellum-image-uploads/lpMtQDMlRindAIJOHlGl_expandable-recycler-view-logo.png)

# Expandable RecyclerView [![CircleCI](https://circleci.com/gh/thoughtbot/expandable-recycler-view/tree/master.svg?style=svg)](https://circleci.com/gh/thoughtbot/expandable-recycler-view/tree/master)
Custom RecyclerViewAdapters for expanding and collapsing groups with support for multiple view types

<img src="https://cloud.githubusercontent.com/assets/5386934/17074123/b9d1efca-502c-11e6-9c9f-fb6180ee337f.gif" width=300 />

## Download
ExpandableRecyclerView:
```groovy
compile 'com.thoughtbot:expandablerecyclerview:1.4'
```

ExpandableCheckRecyclerView:
```groovy
compile 'com.thoughtbot:expandablecheckrecyclerview:1.4'
```

## Usage
Let's say you are a rock star :guitar: and you want to build an app to show a list of your favorite `Genre`s with a list of their top `Artist`s.

First, define your custom `ExpandableGroup` class:

``` java
public class Genre extends ExpandableGroup<Artist> {

  public Genre(String title, List<Artist> items) {
    super(title, items);
  }
}
```

Next up, let's create the `ChildViewHolder` and `GroupViewHolder`. These are both wrappers around regular ol' `RecyclerView.ViewHolder`s so implement any view inflation and binding methods you may need.

``` java
public class GenreViewHolder extends GroupViewHolder {

  private TextView genreTitle;

  public GenreViewHolder(View itemView) {
    super(itemView);
    genreTitle = itemView.findViewById(R.id.genre_title);
  }

  public void setGenreTitle(ExpandableGroup group) {
    genreTitle.setText(group.getTitle());
  }
}
```

``` java
public class ArtistViewHolder extends ChildViewHolder {

  private TextView artistName;

  public ArtistViewHolder(View itemView) {
    super(itemView);
    artistName = itemView.findViewById(R.id.artist_name);
  }

  public void setArtistName(Artist artist) {
    artistName.setText(artist.getTitle());
  }
}
```

Now we are ready for the juicy part - let's make our `ExpandableRecyclerViewAdapter`

By including your `GroupViewHolder` and `ChildViewHolder` in the definition of the class, you'll see that the `onCreateGroupViewHolder` and `onCreateChildViewHolder` methods return the correct type :thumbsup:

``` java
public class GenreAdapter extends ExpandableRecyclerViewAdapter<GenreViewHolder, ArtistViewHolder> {

  public GenreAdapter(List<? extends ExpandableGroup> groups) {
    super(groups);
  }

  @Override
  public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.list_item_genre, parent, false);
    return new GenreViewHolder(view);
  }

  @Override
  public ArtistViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.list_item_artist, parent, false);
    return new ArtistViewHolder(view);
  }

  @Override
  public void onBindChildViewHolder(ArtistViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
    final Artist artist = ((Genre) group).getItems().get(childIndex);
    holder.setArtistName(artist.getName());
  }

  @Override
  public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition, ExpandableGroup group) {
    holder.setGenreTitle(group);
  }
}
```

Lastly you'll need either an `Activity` or `Fragment` to host your adapter. Once you've got that up and running, all that's left is to instantiate your fancy new `GenreAdapter` with a `List<Genre>`

``` java
public class GenreActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    ...

    List<Genre> genres = getGenres(); //see sample project's GenreDataFactory.java class for getGenres() method
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    //instantiate your adapter with the list of genres
    GenreAdapter adapter = new GenreAdapter(genres);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    ...

  }
}
```

## Saving And Restoring Expand / Collapse State

If you want to save the expand and collapse state of your adapter, you have to explicitly call through to the adapters `onSaveInstanceState()` and `onRestoreInstanceState()`in the calling `Activity`

```java
public class GenreActivity extends Activity {

  ...

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    adapter.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    adapter.onRestoreInstanceState(savedInstanceState);
  }
}

```

## Programmatic Expanding and Collapsing

The `ExpandableRecyclerViewAdapter` exposes methods to control the expanded and
collapsed state.

First up we have the toggles, `.toggleGroup(int)` and
`.toggleGroup(ExpandableGroup)`. These are handy for when you control the
states explicitly.

```java
public class GenreActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    ...

    Button showAllToggle = findViewById(R.id.show_all);
    showAllToggle.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        for (int i = adapter.groups().size() - 1; i >= 0; i--) {
          adapter.toggleGroup(i);
        }
      }
    });

  }
}
```

We also expose explicit methods to control the expanding and collapsing of
specific groups, `.expandGroup()` and `.collapseGroup()`. For example, to
expand the first group immediately:

```java
public class GenreActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    ...

    adapter.expandGroup(0);

  }
}
```

## Adding Custom Expand / Collapse Animations

If you want to add a custom `Drawable` that animates based on a groups state, override the `expand()` and `collapse()` methods in your `GroupViewHolder`:

``` java
public class GenreViewHolder extends GroupViewHolder {

  ...

  @Override
  public void expand() {
    animateExpand();
  }

  @Override
  public void collapse() {
    animateCollapse();
  }
}
```

## Listening to Expand/Collapse events

If you want register an `ExpandCollapseListener` outside of the adapter, you can simply call `setOnGroupExpandCollapseListener` on the `ExpandableRecyclerViewAdapter`

``` java
  adapter.setOnGroupExpandCollapseListener(new GroupExpandCollapseListener() {
    @Override
    public void onGroupExpanded(ExpandableGroup group) {

    }

    @Override
    public void onGroupCollapsed(ExpandableGroup group) {

    }
  });
```

## Multiple Child and Group Types

The `MultiTypeExpandableRecyclerViewAdapter` allows subclasses to implement multiple different view types for both children and groups.

Continuing with our genre example, let's say you wanted to display regular artists differently from your favorite artists. Let's start by making a new `FavoriteArtistViewHolder`

``` java
public class FavoriteArtistViewHolder extends ChildViewHolder {

  private TextView favoriteArtistName;

  public FavoriteArtistViewHolder(View itemView) {
    super(itemView);
    favoriteArtistName = (TextView) itemView.findViewById(R.id.list_item_favorite_artist_name);
  }

  public void setArtistName(String name) {
    favoriteArtistName.setText(name);
  }
```

Just like the regular `ArtistViewHolder`, `FavoriteArtistViewHolder` must extends `ChildViewHolder`.

Next up, let's create a subclass of `MultiTypeExpandableRecyclerViewAdapter` called `MultiTypeGenreAdapter` and let's add two static `int`s representing our two artist view types:

```java
public class MultiTypeGenreAdapter extends MultiTypeExpandableRecyclerViewAdapter<GenreViewHolder, ChildViewHolder> {


  public static final int FAVORITE_VIEW_TYPE = 3;
  public static final int ARTIST_VIEW_TYPE = 4;
  ...
```

Notice we started used values > 2. That's because `ExpandableListPosition.CHILD` and `ExpandableListPositon.GROUP` are `1` and `2` respectively so they are already taken.

Since we only want a single view type for groups, we only need to override `getChildViewType()`. As `getGroupViewType()` will default to `ExpandableListPosition.GROUP`.

``` java
  @Override
  public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
    if (((Genre) group).getItems().get(childIndex).isFavorite()) {
      return FAVORITE_VIEW_TYPE;
    } else {
      return ARTIST_VIEW_TYPE;
    }
  }
```

Since we provided custom view types for our children, we must also override `isChild()`

```java
  @Override
  public boolean isChild(int viewType) {
    return viewType == FAVORITE_VIEW_TYPE || viewType == ARTIST_VIEW_TYPE;
  }
```

And now, just like in any other `RecyclerView.Adapter` in our `onCreateChildViewHolder` and our `onBindChildViewHolder` we can use the provided parameters to switch on the different view tyeps:
``` java
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
```

## Expandable Check RecyclerView
An extension of `expandablerecyclerview` for checking single or multiple children within a group

The setup for the single and multi check versions is very similar to the `expandablerecyclerview` we walked through above. Here are a few of the notable differences...

### CheckedExpandableGroup

Instead of `ExpandableGroup` you must use `CheckedExpandableGroup`. `CheckedExpandableGroup` is a subclass of `ExpandableGroup` that uses a `SparseBooleanArray` to hold onto which of it's children are checked.

The `expandablecheckrecyclerview` library comes with two default implementations -  `SingleCheckExpandableGroup` and `MultiCheckExpandableGroup`.

### Clearing Choices

The `CheckableChildRecyclerViewAdapter` has a `clearChoices()` which un checks any currently checked children.

### CheckableChildViewHolder

The `CheckableChildViewHolder` is a subclass of `ChildViewHolder` that has a `Checkable` widget. The `Checkable` interface is initially not set, so in order to see your children view states update, you must set a `View` that implements `Checkable` in your view holder.

``` java
public class SingleCheckArtistViewHolder extends CheckableChildViewHolder {

  private CheckedTextView artistName;

  public SingleCheckArtistViewHolder(View itemView) {
    super(itemView);
    artistName = (CheckedTextView) itemView.findViewById(R.id.list_item_singlecheck_artist_name);
  }

  @Override
  public Checkable getCheckable() {
    return artistName;
  }
  ...
}
```

### Listening to Child Click Events

There is a custom callback for click events on children of a `CheckedExpandableGroup` which returns you the `View` of the row item that was clicked, the current checked state of the child, the containing `CheckedExpandableGroup` group and the index of the child that was clicked.

``` java
  adapter.setChildClickListener(new OnCheckChildClickListener() {
    @Override
    public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group,
        int childIndex) {
    }
  });

```

## Sample App

To see the complete code for all the above examples along with unit tests for the adapters check out the `sample` app. The app has the following packages:

### expand
An example of basic `ExpandableRecyclerViewAdapter`

<img src="https://cloud.githubusercontent.com/assets/5386934/17074123/b9d1efca-502c-11e6-9c9f-fb6180ee337f.gif" width=300 />


### multicheck
An example of a `CheckableChildRecyclerViewAdapter` using `MultiCheckExpandableGroup`

<img src="https://cloud.githubusercontent.com/assets/5386934/17074122/b9d0ec06-502c-11e6-8548-7647f63114dd.gif" width=300 />


### single check
An example of a `CheckableChildRecyclerViewAdapter` using `SingleCheckExpandableGroup`

<img src="https://cloud.githubusercontent.com/assets/5386934/17074124/b9d22df0-502c-11e6-8b9c-d70e00c10909.gif" width=300 />


### multi type
An example of a `MultiTypeExpandableRecyclerViewAdapter` using two different child view holders

<img src="https://cloud.githubusercontent.com/assets/5386934/17262690/ac0eeb9e-5591-11e6-9809-8b76644defee.gif" width=300/>

## Contributing

See the [CONTRIBUTING] document. Thank you, [contributors]!

## License

Expandable RecyclerView is Copyright (c) 2016 thoughtbot, inc. It is free software, and may be redistributed under the terms specified in the [LICENSE] file.

## About

Expandable RecyclerView is maintained by [@mandybess](https://github.com/mandybess)

![thoughtbot](https://thoughtbot.com/logo.png)

Expandable RecyclerView is maintained and funded by thoughtbot, inc. The names and logos for thoughtbot are trademarks of thoughtbot, inc.

We love open source software! See [our other projects][tools] or [hire us][hire] to help build your product.

  [tools]: https://thoughtbot.com/tools?utm_source=github
  [hire]: https://thoughtbot.com/hire-us?utm_source=github
  [LICENSE]: /LICENSE
  [CONTRIBUTING]: CONTRIBUTING.md
  [contributors]: https://github.com/thoughtbot/expandable-recycler-view/graphs/contributors
