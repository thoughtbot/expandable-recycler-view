package com.thoughtbot.expandablerecyclerview.sample;

import android.app.Application;
import android.content.Context;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.sample.multitype.MultiTypeBandAdapter;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test for MultiTypeBandAdapter
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MultiTypeBandAdapterTest {

  private Context context;
  private List<Band> groups;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    Application application = RuntimeEnvironment.application;
    assertNotNull(application);

    context = application;
    groups = BandFactory.makeBands();
  }

  @Test
  public void test_getItemViewType() {
    MultiTypeBandAdapter adapter = new MultiTypeBandAdapter(groups);

    //initial state
    int initialExpected = ExpandableListPosition.GROUP;
    int initialActual = adapter.getItemViewType(3);

    assertEquals(initialExpected, initialActual);

    //expand first group
    adapter.toggleGroup(0);
    int newExpected = MultiTypeBandAdapter.TOP_HIT_VIEW_TYPE;
    int newActual = adapter.getItemViewType(3);

    assertEquals(newExpected, newActual);
  }

  @Test
  public void test_isChild() {
    MultiTypeBandAdapter adapter = new MultiTypeBandAdapter(groups);

    int validChildViewType = MultiTypeBandAdapter.SONG_VIEW_TYPE;
    int inValidChildViewType = ExpandableListPosition.GROUP;

    assertTrue(adapter.isChild(validChildViewType));
    assertFalse(adapter.isChild(inValidChildViewType));
  }
}
