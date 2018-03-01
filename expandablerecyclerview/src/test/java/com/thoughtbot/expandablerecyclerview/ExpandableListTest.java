package com.thoughtbot.expandablerecyclerview;

import android.app.Application;
import android.content.Context;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableList;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.testUtils.TestDataFactory;

import junit.framework.Assert;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition.CHILD;
import static com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition.GROUP;
import static com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition.obtain;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ExpandableListTest {

  private Context context;
  private List<? extends ExpandableGroup> groups;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    Application application = RuntimeEnvironment.application;
    assertNotNull(application);

    context = application;
    groups = TestDataFactory.makeGroups();
  }

  @Test
  public void test_getVisibleItemCount() {
    ExpandableList list = new ExpandableList(groups);

    //initial state
    int initialExpected = 6;
    int initialActual = list.getVisibleItemCount();

    assertEquals(initialExpected, initialActual);

    //expand first group
    list.expandGroup(0);

    //new state
    int newExpected = 9;
    int newActual = list.getVisibleItemCount();

    assertEquals(newExpected, newActual);
  }

  @Test
  public void test_getUnflattenedPosition() {
    ExpandableList list = new ExpandableList(groups);
    int flatPos = 3;

    //initial state
    //flatPos 3 == group at index 3
    ExpandableListPosition initialExpected = obtain(GROUP, 3, -1, 3);
    ExpandableListPosition initialActual = list.getUnflattenedPosition(flatPos);

    assertEquals(initialExpected, initialActual);

    //expand first group
    list.expandGroup(0);

    //flatPos 3 == child number 2 within group at index 0
    ExpandableListPosition newExpected = obtain(CHILD, 0, 2, 3);
    ExpandableListPosition newActual = list.getUnflattenedPosition(flatPos);

    assertEquals(newExpected, newActual);
  }

  @Test
  public void test_addGroups() {
    ExpandableList list = new ExpandableList(groups);

    int visibleCount = list.getVisibleItemCount();

    //expand first group
    List<ExpandableGroup> newElements = TestDataFactory.makeGroups();
    list.addGroups(newElements);

    Assert.assertEquals(list.getVisibleItemCount(),visibleCount + newElements.size());

  }
}
