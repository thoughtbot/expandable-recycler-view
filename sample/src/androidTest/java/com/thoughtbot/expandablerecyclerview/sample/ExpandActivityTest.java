package com.thoughtbot.expandablerecyclerview.sample;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.recyclerview.widget.RecyclerView;
import com.thoughtbot.expandablerecyclerview.sample.expand.GenreAdapter;
import com.thoughtbot.expandablerecyclerview.sample.expand.ExpandActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExpandActivityTest {

  @Rule
  public ActivityTestRule<ExpandActivity> activityRule =
      new ActivityTestRule<>(ExpandActivity.class);

  private RecyclerView recyclerView;
  private GenreAdapter adapter;

  @Before
  public void setUp() {
    recyclerView =
        (RecyclerView) activityRule.getActivity().findViewById(R.id.recycler_view);

    adapter = activityRule.getActivity().adapter;
  }

  @Test
  public void testClickGroup() {
    onView(withId(R.id.recycler_view))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    assertTrue(adapter.isGroupExpanded(0));
  }

  @Test
  public void testClickItem() {
    onView(withId(R.id.recycler_view))
        .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
  }
}
