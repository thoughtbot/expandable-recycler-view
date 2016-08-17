# Expandable RecyclerView
Custom RecyclerViewAdapters for expanding and collapsing groups with support for multiple view types

<img src="https://cloud.githubusercontent.com/assets/5386934/17074123/b9d1efca-502c-11e6-9c9f-fb6180ee337f.gif" width=300 />

## Download
ExpandableRecyclerView:
```groovy
compile 'com.thoughtbot:expandablerecyclerview:1.0'
```

ExpandableCheckRecyclerView:
```groovy
compile 'com.thoughtbot:expandablecheckrecyclerview:0.3'
```

## Usage
Let's say you are a rock star :guitar: and you want to build an app to show a list of your favorite `Band`s with all their top `Song`s.

First, define your custom `ExpandableGroup` class:

``` java
public class Band extends ExpandableGroup<Song> {

  public Band(String title, List<Song> items) {
    super(title, items);
  }
}
```

Next up, let's create the `ChildViewHolder` and `GroupViewHolder`. These are both wrappers around regular ol' `RecyclerView.ViewHolder`s so implement any view inflation and binding methods you may need.

``` java
public class BandViewHolder extends GroupViewHolder {

  private TextView bandName;

  public BandViewHolder(View itemView) {
    super(itemView);
    bandName = itemView.findViewById(R.id.band_name);
  }

  public void setBandName(ExpandableGroup group) {
    bandName.setText(group.getTitle());
  }
}
```

``` java
public class SongViewHolder extends ChildViewHolder {

  private TextView songTitle;

  public SongViewHolder(View itemView) {
    super(itemView);
    songTitle = itemView.findViewById(R.id.song_name);
  }

  public void onBind(Song song) {
    songTitle.setText(song.getTitle());
  }
}
```

Now we are ready for the juicy part - let's make our `ExpandableRecyclerViewAdapter`

By including your `GroupViewHolder` and `ChildViewHolder` in the definition of the class, you'll see that the `onCreateGroupViewHolder` and `onCreateChildViewHolder` methods return the correct type :thumbsup:

``` java
public class BandAdapter extends ExpandableRecyclerViewAdapter<BandViewHolder, SongViewHolder> {

  public BandAdapter(List<? extends ExpandableGroup> groups) {
    super(groups);
  }

  @Override
  public BandViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.list_item_band, parent, false);
    return new BandViewHolder(view);
  }

  @Override
  public SongViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.list_item_song, parent, false);
    return new SongViewHolder(view);
  }

  @Override
  public void onBindChildViewHolder(SongViewHolder holder, int flatPosition, ExpandableGroup group,
      int childIndex) {
    final Song song = ((Band) group).getItems().get(childIndex);
    holder.setSongName(song.getName());
  }

  @Override
  public void onBindGroupViewHolder(BandViewHolder holder, int flatPosition,
      ExpandableGroup group) {
    holder.setBandName(group);
  }
}
```

Lastly let's you'll need either an `Activity` or `Fragment` to host your adapter. Once you've got that up and running, all that's left if to instantiate your fancy new `BandAdapter` with a `List<Band>`

``` java
public class BandActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    ...

    List<Band> bands = getBands();
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    //instantiate your adapter with the list of bands
    BandAdapter adapter = new BandAdapter(bands);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    ...

  }
}
```

## Saving And Restoring Expand / Collapse State

If you want to save the expand and collapse state of your adapter, you have to explicitly call through to the adapters `onSaveInstanceState()` and `onRestoreInstanceState()`in the calling `Activity`

```java
public class BandActivity extends Activity {

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

## Adding Custom Expand / Collapse Animations

If you want to add a custom `Drawable` that animates based on a groups state, override the `expand()` and `collapse()` methods in your `GroupViewHolder`:

``` java
public class BandViewHolder extends GroupViewHolder {

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

Continuing with our band example, let's say you wanted to display regular songs differently from top hits in the list. Let's start by making a new `TopHitViewHolder`

``` java
public class TopHitViewHolder extends ChildViewHolder {

  private TextView topHitName;

  public TopHitViewHolder(View itemView) {
    super(itemView);
    topHitName = (TextView) itemView.findViewById(R.id.list_item_top_hit_name);
  }

  public void setSongName(String name) {
    topHitName.setText("Top Hit: " + name);
  }
```

Just like the regular `SongViewHolder`, `TopHitViewHolder` must extends `ChildViewHolder`.

Next up, let's create a subclass of `MultiTypeExpandableRecyclerViewAdapter` called `MultiTypeBandAdapter` and let's add two static `int`s representing our two song view types:

```java
public class MultiTypeBandAdapter extends MultiTypeExpandableRecyclerViewAdapter<BandViewHolder, ChildViewHolder> {

  public static final int TOP_HIT_VIEW_TYPE = 3;
  public static final int SONG_VIEW_TYPE = 4;
  ...
```

Notice we started used values > 2. That's because `ExpandableListPosition.CHILD` and `ExpandableListPositon.GROUP` are `1` and `2` respectively so they are already taken.

Since we only want a single view type for groups, we only need to override `getChildViewType()`. As `getGroupViewType()` will default to `ExpandableListPosition.GROUP`.

``` java
  @Override
  public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
    if (((Band) group).getItems().get(childIndex).isTopHit()) {
      return TOP_HIT_VIEW_TYPE;
    } else {
      return SONG_VIEW_TYPE;
    }
  }
```

Since we provided custom view types for our children, we must also override `isChild()`

```java
  @Override
  public boolean isChild(int viewType) {
    return viewType == TOP_HIT_VIEW_TYPE || viewType == SONG_VIEW_TYPE;
  }
```

And now, just like in any other `RecyclerView.Adapter` in our `onCreateChildViewHolder` and our `onBindChildViewHolder` we can use the provided parameters to switch on the different view tyeps:
``` java
  @Override
  public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case SONG_VIEW_TYPE:
        View song = from(parent.getContext()).inflate(R.layout.list_item_song, parent, false);
        return new SongViewHolder(song);
      case TOP_HIT_VIEW_TYPE:
        View topHit = from(parent.getContext()).inflate(R.layout.list_item_top_hit, parent, false);
        return new TopHitViewHolder(topHit);
      default:
        throw new IllegalArgumentException("Invalid viewType");
    }
  }

  @Override
  public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group,
      int childIndex) {
    int viewType = getItemViewType(flatPosition);
    Song song = ((Band) group).getItems().get(childIndex);
    switch (viewType) {
      case SONG_VIEW_TYPE:
        ((SongViewHolder) holder).setSongName(song.getName());
        break;
      case TOP_HIT_VIEW_TYPE:
        ((TopHitViewHolder) holder).setSongName(song.getName());
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
public class SingleCheckSongViewHolder extends CheckableChildViewHolder {

  private CheckedTextView songName;

  public SongViewHolder(View itemView) {
    super(itemView);
    songName = (CheckedTextView) itemView.findViewById(R.id.song_name);
  }

  @Override
  public Checkable getCheckable() {
    return songName;
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

We love open source software! See [our other projects][community] or [hire us][hire] to help build your product.

  [community]: https://thoughtbot.com/community?utm_source=github
  [hire]: https://thoughtbot.com/hire-us?utm_source=github
  [LICENSE]: /LICENSE
  [CONTRIBUTING]: CONTRIBUTING.md
  [contributors]: https://github.com/thoughtbot/expandable-recycler-view/graphs/contributors
