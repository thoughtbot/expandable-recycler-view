package com.thoughtbot.expandablerecyclerview.sample;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import com.thoughtbot.expandablerecyclerview.sample.expand.GenreAdapter;
import com.thoughtbot.expandablerecyclerview.sample.expand.ExpandActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
