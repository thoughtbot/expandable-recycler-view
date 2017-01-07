package com.thoughtbot.expandablerecyclerview.sample;

import android.app.Application;
import android.content.Context;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.sample.multitype.MultiTypeGenreAdapter;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test for MultiTypeGenreAdapter
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MultiTypeGenreAdapterTest {

  private Context context;
  private List<Genre> groups;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    Application application = RuntimeEnvironment.application;
    assertNotNull(application);

    context = application;
    groups = GenreDataFactory.makeGenres();
  }

  @Test
  public void test_getItemViewType() {
    MultiTypeGenreAdapter adapter = new MultiTypeGenreAdapter(groups);

    //initial state
    int initialExpected = ExpandableListPosition.GROUP;
    int initialActual = adapter.getItemViewType(3);

    assertEquals(initialExpected, initialActual);

    //expand first group
    adapter.toggleGroup(0);
    int newExpected = MultiTypeGenreAdapter.ARTIST_VIEW_TYPE;
    int newActual = adapter.getItemViewType(3);

    assertEquals(newExpected, newActual);
  }

  @Test
  public void test_isChild() {
    MultiTypeGenreAdapter adapter = new MultiTypeGenreAdapter(groups);

    int validChildViewType = MultiTypeGenreAdapter.ARTIST_VIEW_TYPE;
    int inValidChildViewType = ExpandableListPosition.GROUP;

    assertTrue(adapter.isChild(validChildViewType));
    assertFalse(adapter.isChild(inValidChildViewType));
  }
}
