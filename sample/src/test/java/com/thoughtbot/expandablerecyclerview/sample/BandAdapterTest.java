package com.thoughtbot.expandablerecyclerview.sample;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.LinearLayout;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.sample.expand.BandAdapter;
import com.thoughtbot.expandablerecyclerview.sample.expand.SongViewHolder;
import com.thoughtbot.expandablerecyclerview.sample.expand.BandViewHolder;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test for BandAdapter
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class BandAdapterTest {

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
  public void test_onCreateViewHolder() {
    BandAdapter adapter = new BandAdapter(groups);
    LinearLayout parent = new LinearLayout(context);

    //child view holder
    ViewHolder childViewHolder = adapter.onCreateViewHolder(parent, ExpandableListPosition.CHILD);
    assertTrue(childViewHolder instanceof SongViewHolder);

    //group view holder
    ViewHolder groupViewHolder = adapter.onCreateViewHolder(parent, ExpandableListPosition.GROUP);
    assertTrue(groupViewHolder instanceof BandViewHolder);
  }

  @Test
  public void test_getItemCount() {
    BandAdapter adapter = new BandAdapter(groups);

    //initial state
    int initialExpected = 6;
    int initialActual = adapter.getItemCount();

    assertEquals(initialExpected, initialActual);
  }

  @Test
  public void test_getItemViewType() {
    BandAdapter adapter = new BandAdapter(groups);

    //initial state
    int initialExpected = ExpandableListPosition.GROUP;
    int initialActual = adapter.getItemViewType(3);

    assertEquals(initialExpected, initialActual);
  }
}